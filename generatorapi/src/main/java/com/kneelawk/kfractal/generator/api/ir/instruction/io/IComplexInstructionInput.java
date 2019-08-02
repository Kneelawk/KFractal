package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IComplexInstructionInput extends INotVoidInstructionInput {
    <R> R acceptComplex(IComplexInstructionInputVisitor<R> visitor) throws FractalException;
}
