package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.generator.api.ir.phi.Phi;
import com.kneelawk.kfractal.generator.api.ir.phi.PhiBranch;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ProgramValidator {
    public static void checkValidity(Program program) throws FractalException {
        checkGlobals(program.getGlobalVariables());

        for (FunctionDefinition function : program.getFunctions()) {
            // setup local variable scope
            checkArguments(function.getContextVariables());
            checkArguments(function.getArguments());

            List<BasicBlock> blocks = function.getBlocks();
            int size = blocks.size();
            for (int i = 0; i < size; i++) {
                checkBasicBlock(blocks, i, program.getFunctions(), program.getGlobalVariables(),
                        function.getContextVariables(), function.getArguments(), function.getReturnType());
            }
        }
    }

    private static void checkArguments(Collection<ArgumentDeclaration> arguments) throws FractalIRValidationException {
        for (ArgumentDeclaration v : arguments) {
            ValueType type = v.getType();

            // check variable types
            checkVariableType(type);
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

    private static void checkBasicBlock(List<BasicBlock> blocks, int blockIndex, List<FunctionDefinition> functions,
                                        List<GlobalDeclaration> globalVariables,
                                        List<ArgumentDeclaration> contextVariables, List<ArgumentDeclaration> arguments,
                                        ValueType returnType) throws FractalException {
        BasicBlock block = blocks.get(blockIndex);

        List<ValueType> phiTypes = Lists.newArrayList();
        for (Phi phi : block.getPhis()) {
            phiTypes.add(checkPhi(phi, functions, globalVariables, blocks, blockIndex, contextVariables, arguments,
                    returnType));
        }

        List<IValue> body = block.getBody();
        int size = body.size();
        for (int i = 0; i < size; i++) {
            IValue value = body.get(i);
            ValidatingValueVisitor visitor =
                    new ValidatingValueVisitor(functions, globalVariables, blocks, blockIndex, contextVariables,
                            arguments,
                            i, returnType);
            value.accept(visitor);

            if (visitor.isTerminated() && i < size - 1) {
                throw new FractalIRValidationException(
                        "BasicBlock contains instructions after a terminator statement.");
            } else if (!visitor.isTerminated() && i == size - 1) {
                throw new FractalIRValidationException("BasicBlock is lacking a terminator statement.");
            }
        }
    }

    private static ValueType checkPhi(Phi phi, List<FunctionDefinition> functions,
                                      List<GlobalDeclaration> globalVariables, List<BasicBlock> blocks, int blockIndex,
                                      List<ArgumentDeclaration> contextVariables,
                                      List<ArgumentDeclaration> arguments, ValueType returnType)
            throws FractalException {
        ValidatingPhiInputVisitor phiInputVisitor =
                new ValidatingPhiInputVisitor(functions, globalVariables, blocks, blockIndex, contextVariables,
                        arguments,
                        returnType);

        ValueType phiType = null;
        for (PhiBranch branch : phi.getBranches()) {
            if (branch.getPreviousBlockIndex() < 0 || branch.getPreviousBlockIndex() > blocks.size()) {
                throw new PhiInputValidationException("PhiBranch references BasicBlock that does not exist");
            }

            ValueType branchType = branch.getValue().accept(phiInputVisitor);
            if (phiType == null) {
                phiType = branchType;
            } else {
                if (branchType != phiType) {
                    throw new PhiInputValidationException("Phi has branches that are different types");
                }
            }
        }

        if (phiType == null) {
            throw new PhiInputValidationException("Phi does not contain any branches");
        }

        return phiType;
    }
}
