package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import com.kneelawk.kfractal.generator.util.FunctionScope;

import java.util.Collection;
import java.util.Set;

public class ProgramValidator {
    public static void checkValidity(Program program) throws FractalException {
        checkVariables(program.getGlobalVariables());

        for (FunctionDefinition function : program.getFunctions()) {
            // setup local variable scope
            checkArguments(function.getContextVariables());
            checkArguments(function.getArguments());
            checkVariables(function.getLocalVariables());

            FunctionScope<ValueInfo> functionScope = new FunctionScope<>(
                    program.getGlobalVariables().stream()
                            .map(v -> new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build())
                            .collect(ImmutableList.toImmutableList()),
                    function.getContextVariables().stream()
                            .map(v -> new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build())
                            .collect(ImmutableList.toImmutableList()),
                    function.getArguments().stream()
                            .map(v -> new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build())
                            .collect(ImmutableList.toImmutableList()),
                    function.getLocalVariables().stream()
                            .map(v -> new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build())
                            .collect(ImmutableList.toImmutableList()));

            ValidatingInstructionVisitor visitor =
                    new ValidatingInstructionVisitor(program.getFunctions(), functionScope, function.getReturnType());

            for (IInstruction instruction : function.getBody()) {
                instruction.accept(visitor);
            }

            if (!visitor.isReturned()) {
                throw new FractalIRValidationException("Function is lacking a return statement");
            }
        }
    }

    private static void checkArguments(Collection<VariableDeclaration> arguments) throws FractalIRValidationException {
        for (VariableDeclaration v : arguments) {
            ValueType type = v.getType();
            Set<IAttribute> attributes = v.getAttributes();

            // check variable types
            checkVariableType(type);

            // check variable attributes
            if (attributes.contains(IAttribute.PREALLOCATED)) {
                throw new IllegalVariableAttributeException("Illegal argument attribute: PREALLOCATED");
            }
        }
    }

    private static void checkVariables(Collection<VariableDeclaration> variables)
            throws FractalIRValidationException {
        for (VariableDeclaration v : variables) {
            ValueType type = v.getType();
            Set<IAttribute> attributes = v.getAttributes();

            // check variable types
            checkVariableType(type);

            // check variable attributes
            if (!ValueTypes.isPointer(type)) {
                if (attributes.contains(IAttribute.PREALLOCATED)) {
                    throw new IllegalVariableAttributeException("Illegal variable attribute: PREALLOCATED");
                }
            }
        }
    }

    private static void checkVariableType(ValueType type) throws FractalIRValidationException {
        if (ValueTypes.isVoid(type)) {
            throw new IllegalVariableTypeException("Illegal variable type: VOID");
        } else if (ValueTypes.isFunction(type)) {
            if (ValueTypes.isNullFunction(type)) {
                throw new IllegalVariableTypeException("Illegal variable type: NULL_FUNCTION");
            }
        } else if (ValueTypes.isPointer(type)) {
            if (ValueTypes.isNullPointer(type)) {
                throw new IllegalVariableTypeException("Illegal variable type: NULL_POINTER");
            }
        }
    }
}
