package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IIntInstructionOutput extends INotVoidInstructionOutput {
    <R> R visitInt(IIntInstructionOutputVisitor<R> visitor) throws FractalException;
}
