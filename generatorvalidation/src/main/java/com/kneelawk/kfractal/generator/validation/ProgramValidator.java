package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableSet;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.generator.api.ir.phi.Phi;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ProgramValidator {
    public static void checkValidity(Program program) throws FractalException {
        checkGlobals(program.getGlobalVariables());

        for (FunctionDefinition function : program.getFunctions()) {
            checkFunction(function, program);
        }
    }

    private static void checkGlobals(Collection<GlobalDeclaration> variables)
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

    private static void checkFunction(FunctionDefinition function, Program program)
            throws FractalException {
        // setup local variable scope
        checkArguments(function.getContextVariables());
        checkArguments(function.getArguments());

        TypeCache cache = new TypeCache();

        List<BasicBlock> blocks = function.getBlocks();
        int size = blocks.size();
        if (size < 1) {
            throw new FractalIRValidationException("Function contains no basic blocks");
        }

        for (int i = 0; i < size; i++) {
            checkBasicBlock(program, function, i, cache);
        }
    }

    private static void checkArguments(Collection<ArgumentDeclaration> arguments) throws FractalIRValidationException {
        for (ArgumentDeclaration v : arguments) {
            ValueType type = v.getType();

            // check variable types
            checkVariableType(type);
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

    private static void checkBasicBlock(Program program, FunctionDefinition function, int blockIndex, TypeCache cache)
            throws FractalException {
        BasicBlock block = function.getBlocks().get(blockIndex);

        List<Phi> phis = block.getPhis();
        int phisSize = phis.size();
        for (int i = 0; i < phisSize; i++) {
            ValidatingPhiInputVisitor.checkPhi(phis.get(i), cache,
                    ValidatingVisitorContext.create(ImmutableSet.of(), program, function, blockIndex, i));
        }

        List<IProceduralValue> body = block.getBody();
        int size = body.size();
        boolean terminated = false;
        for (int i = 0; i < size; i++) {
            IProceduralValue value = body.get(i);
            ValidatingProceduralValueVisitor visitor =
                    new ValidatingProceduralValueVisitor(cache,
                            ValidatingVisitorContext.create(ImmutableSet.of(), program, function, blockIndex, i));
            ValidatingVisitorResult result = value.accept(visitor);

            if (result.isTerminated() && i < size - 1) {
                throw new FractalIRValidationException(
                        "BasicBlock contains instructions after a terminator statement.");
            }
            terminated = result.isTerminated();
        }
        if (!terminated) {
            throw new FractalIRValidationException("BasicBlock is lacking a terminator statement.");
        }
    }
}
