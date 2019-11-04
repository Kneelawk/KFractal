package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.ArgumentDeclaration;
import com.kneelawk.kfractal.generator.api.ir.BasicBlock;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
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

class ValidatingPhiInputVisitor implements IPhiInputVisitor<Void> {
    private final ValidatingVisitorContext context;

    private ValidatingPhiInputVisitor(ValidatingVisitorContext context) {
        this.context = context;
    }

    @Override
    public Void visitArgumentReference(ArgumentReference argumentReference) throws FractalException {
        ArgumentScope argumentScope = argumentReference.getScope();
        int argumentIndex = argumentReference.getIndex();

        List<ArgumentDeclaration> scope;
        switch (argumentScope) {
            case CONTEXT:
                scope = context.getFunction().getContextVariables();
                break;
            case ARGUMENTS:
                scope = context.getFunction().getArguments();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + argumentScope);
        }

        if (argumentIndex < 0 || argumentIndex >= scope.size()) {
            throw new FractalIRValidationException("Invalid argument reference index");
        }

        return null;
    }

    @Override
    public Void visitInstructionReference(InstructionReference instructionReference) throws FractalException {
        List<BasicBlock> blocks = context.getFunction().getBlocks();
        int blockIndex = instructionReference.getBlockIndex();

        if (blockIndex < 0 || blockIndex >= blocks.size()) {
            throw new FractalIRValidationException("Invalid instruction reference block index");
        }

        BasicBlock block = blocks.get(blockIndex);
        int instructionIndex = instructionReference.getInstructionIndex();
        switch (instructionReference.getScope()) {
            case PHI:
                if (instructionIndex < 0 || instructionIndex >= block.getPhis().size()) {
                    throw new FractalIRValidationException("Invalid instruction reference instruction index");
                }
                break;
            case BODY:
                if (instructionIndex < 0 || instructionIndex >= block.getBody().size()) {
                    throw new FractalIRValidationException("Invalid instruction reference instruction index");
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + instructionReference.getScope());
        }

        return null;
    }

    @Override
    public Void visitBoolConstant(BoolConstant constant) {
        return null;
    }

    @Override
    public Void visitIntConstant(IntConstant constant) {
        return null;
    }

    @Override
    public Void visitRealConstant(RealConstant constant) {
        return null;
    }

    @Override
    public Void visitComplexConstant(ComplexConstant constant) {
        return null;
    }

    @Override
    public Void visitNullPointer() {
        return null;
    }

    @Override
    public Void visitNullFunction() {
        return null;
    }

    @Override
    public Void visitVoid() {
        return null;
    }

    static void checkPhi(Phi phi, TypeCache cache, ValidatingVisitorContext context)
            throws FractalException {
        ValidatingPhiInputVisitor phiInputVisitor =
                new ValidatingPhiInputVisitor(context);

        ValueType phiType = null;
        for (PhiBranch branch : phi.getBranches()) {
            if (branch.getPreviousBlockIndex() < 0 ||
                    branch.getPreviousBlockIndex() > context.getFunction().getBlocks().size()) {
                throw new PhiInputValidationException("PhiBranch references BasicBlock that does not exist");
            }

            ValueType branchType = cache.getType(branch.getValue(), context);
            if (phiType == null) {
                phiType = branchType;
            } else {
                if (branchType != phiType) {
                    throw new PhiInputValidationException("Phi has branches that are different types");
                }
            }

            branch.getValue().accept(phiInputVisitor);
        }

        if (phiType == null) {
            throw new PhiInputValidationException("Phi does not contain any branches");
        }
    }
}
