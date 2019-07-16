package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;

public class VoidConstant implements IInstructionInput, IInstructionOutput {
    public static final VoidConstant INSTANCE = new VoidConstant();

    private VoidConstant() {
    }

    @Override
    public <R> R accept(IInstructionInputVisitor<R> visitor) throws FractalIRException {
        return visitor.visitVoid();
    }

    @Override
    public <R> R accept(IInstructionOutputVisitor<R> visitor) throws FractalIRException {
        return visitor.visitVoid();
    }

    @Override
    public String toString() {
        return "VoidConstant";
    }
}
