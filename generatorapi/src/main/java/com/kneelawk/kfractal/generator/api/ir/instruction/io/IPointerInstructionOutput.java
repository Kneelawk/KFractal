package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IPointerInstructionOutput extends INotVoidInstructionOutput {
    <R> R acceptPointer(IPointerInstructionOutputVisitor<R> visitor) throws FractalException;
}
