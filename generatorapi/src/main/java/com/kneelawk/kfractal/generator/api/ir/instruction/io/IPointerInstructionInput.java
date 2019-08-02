package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IPointerInstructionInput extends INotVoidInstructionInput {
    <R> R acceptPointer(IPointerInstructionInputVisitor<R> visitor) throws FractalException;
}
