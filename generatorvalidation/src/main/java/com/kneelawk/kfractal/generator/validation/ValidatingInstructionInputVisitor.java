package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;
import com.kneelawk.kfractal.generator.util.FunctionScope;

import java.util.List;
import java.util.stream.Collectors;

class ValidatingInstructionInputVisitor implements IInstructionInputVisitor<ValueInfo> {
    private List<FunctionDefinition> functions;
    private FunctionScope<ValueInfo> functionScope;

    public ValidatingInstructionInputVisitor(
            List<FunctionDefinition> functions,
            FunctionScope<ValueInfo> functionScope) {
        this.functions = functions;
        this.functionScope = functionScope;
    }

    @Override
    public ValueInfo visitVariableReference(VariableReference reference) throws FractalIRException {
        int index = reference.getIndex();
        Scope scope = reference.getScope();

        if (index < functionScope.getScope(scope).size()) {
            return functionScope.get(scope, index);
        } else {
            throw new MissingVariableReferenceException(
                    "Reference to missing variable: scope: " + scope + ", index: " + index);
        }
    }

    @Override
    public ValueInfo visitBoolConstant(BoolConstant constant) {
        return new ValueInfo.Builder().setType(ValueTypes.BOOL).build();
    }

    @Override
    public ValueInfo visitIntConstant(IntConstant constant) {
        return new ValueInfo.Builder().setType(ValueTypes.INT).build();
    }

    @Override
    public ValueInfo visitRealConstant(RealConstant constant) {
        return new ValueInfo.Builder().setType(ValueTypes.REAL).build();
    }

    @Override
    public ValueInfo visitComplexConstant(ComplexConstant constant) {
        return new ValueInfo.Builder().setType(ValueTypes.COMPLEX).build();
    }

    @Override
    public ValueInfo visitFunctionContextConstant(FunctionContextConstant contextConstant) throws FractalException {
        // find the function
        FunctionDefinition target;
        int functionIndex = contextConstant.getFunctionIndex();
        if (functionIndex < functions.size()) {
            target = functions.get(functionIndex);
        } else {
            throw new MissingFunctionReferenceException("Reference to missing function: index: " + functionIndex);
        }

        // compare context variable types
        List<VariableDeclaration> targetContextVariables = target.getContextVariables();
        List<IInstructionInput> constantContextVariables = contextConstant.getContextVariables();
        int targetContextVariablesSize = targetContextVariables.size();
        int constantContextVariablesSize = constantContextVariables.size();
        int size = Math.max(targetContextVariablesSize, constantContextVariablesSize);
        for (int i = 0; i < size; i++) {
            if (i >= targetContextVariablesSize) {
                throw new IncompatibleFunctionContextException("FunctionContextConstant has extra context variables: " +
                        constantContextVariables.subList(i, constantContextVariablesSize));
            }
            if (i >= constantContextVariablesSize) {
                throw new IncompatibleFunctionContextException(
                        "FunctionContextConstant is missing context variables: " +
                                targetContextVariables.subList(i, targetContextVariablesSize));
            }
            VariableDeclaration targetVariable = targetContextVariables.get(i);
            IInstructionInput constantInput = constantContextVariables.get(i);
            if (!targetVariable.getType().isAssignableFrom(constantInput.accept(this).getType())) {
                throw new IncompatibleFunctionContextException(
                        "Function defines context variable: " + targetVariable + " but constant supplies: " +
                                constantInput);
            }
        }

        // build the function type from the target function details
        return new ValueInfo.Builder().setType(ValueTypes.FUNCTION(target.getReturnType(),
                target.getArguments().stream().map(VariableDeclaration::getType).collect(
                        Collectors.toList()))).build();
    }

    @Override
    public ValueInfo visitNullFunction() {
        return new ValueInfo.Builder().setType(ValueTypes.NULL_FUNCTION).build();
    }

    @Override
    public ValueInfo visitNullPointer() {
        return new ValueInfo.Builder().setType(ValueTypes.NULL_POINTER).build();
    }

    @Override
    public ValueInfo visitVoid() {
        return new ValueInfo.Builder().setType(ValueTypes.VOID).build();
    }
}
