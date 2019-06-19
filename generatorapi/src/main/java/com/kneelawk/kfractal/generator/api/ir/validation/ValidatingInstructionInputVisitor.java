package com.kneelawk.kfractal.generator.api.ir.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;

public class ValidatingInstructionInputVisitor implements IInstructionInputVisitor {
	@Override
	public void visitVariableReference(VariableReference reference) throws FractalIRException {

	}

	@Override
	public void visitBoolConstant(BoolConstant constant) throws FractalIRException {

	}

	@Override
	public void visitIntConstant(IntConstant constant) throws FractalIRException {

	}

	@Override
	public void visitRealConstant(RealConstant constant) throws FractalIRException {

	}

	@Override
	public void visitComplexConstant(ComplexConstant constant) throws FractalIRException {

	}

	@Override
	public void visitFunctionContextConstant(FunctionContextConstant contextConstant) throws FractalIRException {

	}

	@Override
	public void visitNullPointer() throws FractalIRException {

	}

	@Override
	public void visitVoid() throws FractalIRException {

	}
}
