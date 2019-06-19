package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;

/**
 * Created by Kneelawk on 5/26/19.
 */
public interface IInstructionInputVisitor {
	void visitVariableReference(VariableReference reference) throws FractalIRException;

	void visitBoolConstant(BoolConstant constant) throws FractalIRException;

	void visitIntConstant(IntConstant constant) throws FractalIRException;

	void visitRealConstant(RealConstant constant) throws FractalIRException;

	void visitComplexConstant(ComplexConstant constant) throws FractalIRException;

	void visitFunctionContextConstant(FunctionContextConstant contextConstant) throws FractalIRException;

	void visitNullPointer() throws FractalIRException;

	void visitVoid() throws FractalIRException;
}
