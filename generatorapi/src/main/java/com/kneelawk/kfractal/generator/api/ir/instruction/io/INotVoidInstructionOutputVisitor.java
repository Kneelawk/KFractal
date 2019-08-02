package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface INotVoidInstructionOutputVisitor<R> {
    R visitVariableReference(VariableReference reference) throws FractalException;
}
