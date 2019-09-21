package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;

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

            ValidatingInstructionVisitor visitor =
                    new ValidatingInstructionVisitor(program.getFunctions(), program.getGlobalVariables(),
                            function.getContextVariables(), function.getArguments(), function.getLocalVariables(),
                            function.getReturnType());

            for (IInstruction instruction : function.getBlocks()) {
                instruction.accept(visitor);
            }

            if (!visitor.isReturned()) {
                throw new FractalIRValidationException("Function is lacking a return statement");
            }
        }
    }

    private static void checkArguments(Collection<GlobalDeclaration> arguments) throws FractalIRValidationException {
        for (GlobalDeclaration v : arguments) {
            ValueType type = v.getType();
            Set<IGlobalAttribute> attributes = v.getAttributes();

            // check variable types
            checkVariableType(type);

            // check variable attributes
            if (attributes.contains(IGlobalAttribute.PREALLOCATED)) {
                throw new IllegalVariableAttributeException("Illegal argument attribute: PREALLOCATED");
            }
        }
    }

    private static void checkVariables(Collection<GlobalDeclaration> variables)
            throws FractalIRValidationException {
        for (GlobalDeclaration v : variables) {
            ValueType type = v.getType();
            Set<IGlobalAttribute> attributes = v.getAttributes();

            // check variable types
            checkVariableType(type);

            // check variable attributes
            if (!ValueTypes.isPointer(type)) {
                if (attributes.contains(IGlobalAttribute.PREALLOCATED)) {
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
