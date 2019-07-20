package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;

/**
 * Created by Kneelawk on 5/26/19.
 */
public interface IInstructionInputVisitor<R> {
    R visitVariableReference(VariableReference reference) throws FractalException;

    R visitBoolConstant(BoolConstant constant) throws FractalException;

    R visitIntConstant(IntConstant constant) throws FractalException;

    R visitRealConstant(RealConstant constant) throws FractalException;

    R visitComplexConstant(ComplexConstant constant) throws FractalException;

    R visitFunctionContextConstant(FunctionContextConstant contextConstant) throws FractalException;

    R visitNullPointer() throws FractalException;

    R visitNullFunction() throws FractalException;

    R visitVoid() throws FractalException;
}
