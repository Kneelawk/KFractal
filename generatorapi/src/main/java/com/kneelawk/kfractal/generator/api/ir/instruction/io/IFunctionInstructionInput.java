package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IFunctionInstructionInput extends INotVoidInstructionInput {
    <R> R acceptFunction(IFunctionInstructionInputVisitor<R> visitor) throws FractalException;
}
