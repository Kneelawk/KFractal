package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.BasicBlock;
import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInputVisitor;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentScope;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionScope;
import com.kneelawk.kfractal.generator.simple.impl.*;

public class PhiInputVisitor implements IPhiInputVisitor<IEngineValue> {
    private final ValueManager valueManager;
    private final PhiInputVisitorContext context;

    public PhiInputVisitor(ValueManager valueManager,
                           PhiInputVisitorContext context) {
        this.valueManager = valueManager;
        this.context = context;
    }

    @Override
    public IEngineValue visitArgumentReference(ArgumentReference argumentReference) {
        int index = argumentReference.getIndex();
        ArgumentScope scope = argumentReference.getScope();
        switch (scope) {
            case CONTEXT:
                return context.getContextVariables().get(index);
            case ARGUMENTS:
                return context.getArguments().get(index);
            default:
                throw new IllegalStateException("Unexpected value: " + scope);
        }
    }

    @Override
    public IEngineValue visitInstructionReference(InstructionReference instructionReference)
            throws FractalException {
        int blockIndex = instructionReference.getBlockIndex();
        InstructionScope scope = instructionReference.getScope();
        int instructionIndex = instructionReference.getInstructionIndex();
        BasicBlock block = context.getFunction().getBlocks().get(blockIndex);
        switch (scope) {
            case PHI:
                return valueManager.getValue(block.getPhis().get(instructionIndex));
            case BODY:
                return valueManager.getValue(block.getBody().get(instructionIndex));
            default:
                throw new IllegalStateException("Unexpected value: " + scope);
        }
    }

    @Override
    public IEngineValue visitBoolConstant(BoolConstant constant) {
        return new SimpleBoolValue(constant.isValue());
    }

    @Override
    public IEngineValue visitIntConstant(IntConstant constant) {
        return new SimpleIntValue(constant.getValue());
    }

    @Override
    public IEngineValue visitRealConstant(RealConstant constant) {
        return new SimpleRealValue(constant.getValue());
    }

    @Override
    public IEngineValue visitComplexConstant(ComplexConstant constant) {
        return new SimpleComplexValue(constant.getValue());
    }

    @Override
    public IEngineValue visitNullPointer() {
        return SimpleNullPointerValue.INSTANCE;
    }

    @Override
    public IEngineValue visitNullFunction() {
        return SimpleNullFunctionValue.INSTANCE;
    }

    @Override
    public IEngineValue visitVoid() {
        return SimpleVoidValue.INSTANCE;
    }
}
