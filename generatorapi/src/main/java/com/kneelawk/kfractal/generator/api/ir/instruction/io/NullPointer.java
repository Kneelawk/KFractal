package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public class NullPointer implements IInstructionInput {
    public static final NullPointer INSTANCE = new NullPointer();

    private NullPointer() {
    }

    @Override
    public <R> R accept(IInstructionInputVisitor<R> visitor) throws FractalException {
        return visitor.visitNullPointer();
    }

    @Override
    public String toString() {
        return "NullPointer";
    }
}
