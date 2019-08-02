package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IBoolInstructionInput extends INotVoidInstructionInput {
    <R> R acceptBool(IBoolInstructionInputVisitor<R> visitor) throws FractalException;
}
