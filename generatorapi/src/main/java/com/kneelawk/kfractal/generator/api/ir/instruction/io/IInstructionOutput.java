package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;

public interface IInstructionOutput {
    <R> R accept(IInstructionOutputVisitor<R> visitor) throws FractalIRException;
}
