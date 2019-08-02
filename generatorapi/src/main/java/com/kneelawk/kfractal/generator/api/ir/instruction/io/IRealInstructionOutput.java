package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IRealInstructionOutput extends INotVoidInstructionOutput {
    <R> R acceptReal(IRealInstructionOutputVisitor<R> visitor) throws FractalException;
}
