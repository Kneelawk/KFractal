package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IIntInstructionInputVisitor<R> {
    R visitVariableReference(VariableReference reference) throws FractalException;

    R visitIntConstant(IntConstant constant) throws FractalException;
}
