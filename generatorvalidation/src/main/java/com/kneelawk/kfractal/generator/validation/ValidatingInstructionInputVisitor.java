package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionCreate;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;
import com.kneelawk.kfractal.generator.api.ir.reference.VariableScope;
import com.kneelawk.kfractal.generator.api.ir.reference.VariableReference;

import java.util.List;
import java.util.stream.Collectors;

class ValidatingInstructionInputVisitor implements IInstructionInputVisitor<ValueInfo> {
    private List<FunctionDefinition> functions;
    private List<VariableDeclaration> globalVariables;
    private List<VariableDeclaration> contextVariables;
    private List<VariableDeclaration> arguments;
    private List<VariableDeclaration> localVariables;

    public ValidatingInstructionInputVisitor(
            List<FunctionDefinition> functions,
            List<VariableDeclaration> globalVariables,
            List<VariableDeclaration> contextVariables,
            List<VariableDeclaration> arguments,
            List<VariableDeclaration> localVariables) {
        this.functions = functions;
        this.globalVariables = globalVariables;
        this.contextVariables = contextVariables;
        this.arguments = arguments;
        this.localVariables = localVariables;
    }

    @Override
    public ValueInfo visitVariableReference(VariableReference reference) throws FractalIRException {
        int index = reference.getIndex();
        VariableScope scope = reference.getScope();
        List<VariableDeclaration> scopeList;
        switch (scope) {
            case GLOBAL:
                scopeList = globalVariables;
                break;
            case CONTEXT:
                scopeList = contextVariables;
                break;
            case ARGUMENTS:
                scopeList = arguments;
                break;
            case LOCAL:
                scopeList = localVariables;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + scope);
        }

        if (index < scopeList.size()) {
            VariableDeclaration declaration = scopeList.get(index);
            return new ValueInfo.Builder(true, declaration.getType(), declaration.getAttributes()).build();
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
    public ValueInfo visitFunctionContextConstant(FunctionCreate contextConstant) throws FractalException {
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
