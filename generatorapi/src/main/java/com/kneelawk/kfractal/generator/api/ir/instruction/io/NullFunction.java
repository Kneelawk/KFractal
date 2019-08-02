package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public class NullFunction implements IFunctionInstructionInput {
    public static final NullFunction INSTANCE = new NullFunction();

    private NullFunction() {
    }

    @Override
    public <R> R acceptFunction(IFunctionInstructionInputVisitor<R> visitor) throws FractalException {
        return visitor.visitNullFunction();
    }

    @Override
    public <R> R acceptNotVoid(INotVoidInstructionInputVisitor<R> visitor) throws FractalException {
        return visitor.visitNullFunction();
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
