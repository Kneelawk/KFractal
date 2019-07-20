package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public class NullFunction implements IInstructionInput {
    public static final NullFunction INSTANCE = new NullFunction();

    private NullFunction() {
    }

    @Override
    public <R> R accept(IInstructionInputVisitor<R> visitor) throws FractalException {
        return visitor.visitNullFunction();
    }

    @Override
    public String toString() {
        return "NullFunction";
    }
}
