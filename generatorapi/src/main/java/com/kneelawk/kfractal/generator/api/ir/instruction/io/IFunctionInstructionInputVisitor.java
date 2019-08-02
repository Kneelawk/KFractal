package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

public interface IFunctionInstructionInputVisitor<R> {
    R visitVariableReference(VariableReference reference) throws FractalException;

    R visitFunctionContextConstant(FunctionContextConstant constant) throws FractalException;

    R visitNullFunction() throws FractalException;
}
