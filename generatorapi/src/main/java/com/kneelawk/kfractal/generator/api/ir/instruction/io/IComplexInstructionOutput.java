package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IComplexInstructionOutput extends INotVoidInstructionOutput {
    <R> R acceptComplex(IComplexInstructionOutputVisitor<R> visitor) throws FractalException;
}
