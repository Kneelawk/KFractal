package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IRealInstructionInputVisitor<R> {
    R visitVariableReference(VariableReference reference) throws FractalException;

    R visitRealConstant(RealConstant constant) throws FractalException;
}
