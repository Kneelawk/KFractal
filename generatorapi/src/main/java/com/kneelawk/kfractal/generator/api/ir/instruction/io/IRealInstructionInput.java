package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IRealInstructionInput extends INotVoidInstructionInput {
    <R> R acceptReal(IRealInstructionInputVisitor<R> visitor) throws FractalException;
}
