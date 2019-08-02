package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface INotVoidInstructionInput extends IInstructionInput {
    <R> R acceptNotVoid(INotVoidInstructionInputVisitor<R> visitor) throws FractalException;
}
