package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IBoolInstructionOutput extends INotVoidInstructionOutput {
    <R> R acceptBool(IBoolInstructionOutputVisitor<R> visitor) throws FractalException;
}
