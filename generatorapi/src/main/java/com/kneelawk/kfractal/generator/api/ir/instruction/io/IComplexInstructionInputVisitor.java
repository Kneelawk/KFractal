package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IComplexInstructionInputVisitor<R> {
    R visitVariableReference(VariableReference reference) throws FractalException;

    R visitComplexConstant(ComplexConstant constant) throws FractalException;
}
