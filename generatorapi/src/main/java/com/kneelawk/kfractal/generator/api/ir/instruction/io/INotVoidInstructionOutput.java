package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface INotVoidInstructionOutput extends IInstructionOutput {
    <R> R acceptNotVoid(INotVoidInstructionOutputVisitor<R> visitor) throws FractalException;
}
