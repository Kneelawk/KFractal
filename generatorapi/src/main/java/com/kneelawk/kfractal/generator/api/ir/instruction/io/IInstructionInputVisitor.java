package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;

/**
 * Created by Kneelawk on 5/26/19.
 */
public interface IInstructionInputVisitor<R> {
	R visitVariableReference(VariableReference reference) throws FractalIRException;

	R visitBoolConstant(BoolConstant constant) throws FractalIRException;

	R visitIntConstant(IntConstant constant) throws FractalIRException;

	R visitRealConstant(RealConstant constant) throws FractalIRException;

	R visitComplexConstant(ComplexConstant constant) throws FractalIRException;

	R visitFunctionContextConstant(FunctionContextConstant contextConstant) throws FractalIRException;

	R visitNullPointer() throws FractalIRException;

	R visitVoid() throws FractalIRException;
}
