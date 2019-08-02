package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IIntInstructionInput extends INotVoidInstructionInput {
    <R> R acceptInt(IIntInstructionInputVisitor<R> visitor) throws FractalException;
}
