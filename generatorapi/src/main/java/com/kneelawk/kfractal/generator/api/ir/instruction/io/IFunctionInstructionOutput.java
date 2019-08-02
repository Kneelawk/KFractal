package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IFunctionInstructionOutput extends INotVoidInstructionOutput {
    <R> R acceptFunction(IFunctionInstructionOutputVisitor<R> visitor) throws FractalException;
}
