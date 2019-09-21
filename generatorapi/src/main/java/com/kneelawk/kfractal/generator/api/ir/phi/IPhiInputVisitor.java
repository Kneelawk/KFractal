package com.kneelawk.kfractal.generator.api.ir.phi;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;

public interface IPhiInputVisitor<R> {
    R visitArgumentReference(ArgumentReference argumentReference) throws FractalException;

    R visitInstructionReference(InstructionReference instructionReference) throws FractalException;

    R visitBoolConstant(BoolConstant constant) throws FractalException;

    R visitIntConstant(IntConstant constant) throws FractalException;

    R visitRealConstant(RealConstant constant) throws FractalException;

    R visitComplexConstant(ComplexConstant constant) throws FractalException;

    R visitNullPointer() throws FractalException;

    R visitNullFunction() throws FractalException;

    R visitVoid() throws FractalException;
}
