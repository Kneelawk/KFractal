package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;

public interface IInstructionOutputVisitor<R> {
    R visitVariableReference(VariableReference reference) throws FractalIRException;

    R visitVoid() throws FractalIRException;
}
