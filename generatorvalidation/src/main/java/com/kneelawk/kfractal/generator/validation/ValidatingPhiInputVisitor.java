package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInputVisitor;
import com.kneelawk.kfractal.generator.api.ir.phi.Phi;
import com.kneelawk.kfractal.generator.api.ir.phi.PhiBranch;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentScope;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;

import java.util.List;

public class ValidatingPhiInputVisitor implements IPhiInputVisitor<ValueType> {
    private final List<FunctionDefinition> functions;
    private final List<GlobalDeclaration> globalVariables;
    private final List<BasicBlock> blocks;
    private final int blockIndex;
    private final List<ArgumentDeclaration> contextVariables;
    private final List<ArgumentDeclaration> arguments;
    private final ValueType returnType;

    public ValidatingPhiInputVisitor(List<FunctionDefinition> functions,
                                     List<GlobalDeclaration> globalVariables,
                                     List<BasicBlock> blocks,
                                     int blockIndex, List<ArgumentDeclaration> contextVariables,
                                     List<ArgumentDeclaration> arguments,
                                     ValueType returnType) {
        this.functions = functions;
        this.globalVariables = globalVariables;
        this.blocks = blocks;
        this.blockIndex = blockIndex;
        this.contextVariables = contextVariables;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    @Override
    public ValueType visitArgumentReference(ArgumentReference argumentReference) throws FractalException {
        ArgumentScope argumentScope = argumentReference.getScope();
        int argumentIndex = argumentReference.getIndex();

        List<ArgumentDeclaration> scope;
        switch (argumentScope) {
            case CONTEXT:
                scope = contextVariables;
                break;
            case ARGUMENTS:
                scope = arguments;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + argumentScope);
        }

        return scope.get(argumentIndex).getType();
    }

    @Override
    public ValueType visitInstructionReference(InstructionReference instructionReference) throws FractalException {
        BasicBlock block = blocks.get(instructionReference.getBlockIndex());
        int instructionIndex = instructionReference.getInstructionIndex();
        switch (instructionReference.getScope()) {
            case PHI:
                Phi phi = block.getPhis().get(instructionIndex);
                return getPhiValueType(phi);
            case BODY:
                ValidatingValueVisitor valueVisitor =
                        new ValidatingValueVisitor(functions, globalVariables, blocks, blockIndex, contextVariables,
                                arguments, instructionIndex, returnType);
                return block.getBody().get(instructionIndex).accept(valueVisitor);
            default:
                throw new IllegalStateException("Unexpected value: " + instructionReference.getScope());
        }
    }

    private ValueType getPhiValueType(Phi phi) throws FractalException {
        ValueType phiType = null;

        for (PhiBranch branch : phi.getBranches()) {
            if (branch.getPreviousBlockIndex() < 0 || branch.getPreviousBlockIndex() > blocks.size()) {
                throw new PhiInputValidationException("PhiBranch references BasicBlock that does not exist");
            }

            ValueType branchType = branch.getValue().accept(this);
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

    @Override
    public ValueType visitBoolConstant(BoolConstant constant) throws FractalException {
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitIntConstant(IntConstant constant) throws FractalException {
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitRealConstant(RealConstant constant) throws FractalException {
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitComplexConstant(ComplexConstant constant) throws FractalException {
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitNullPointer() throws FractalException {
        return ValueTypes.NULL_POINTER;
    }

    @Override
    public ValueType visitNullFunction() throws FractalException {
        return ValueTypes.NULL_FUNCTION;
    }

    @Override
    public ValueType visitVoid() throws FractalException {
        return ValueTypes.VOID;
    }
}
