package com.kneelawk.kfractal.generator.api.ir.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;

public class ValidatingInstructionInputVisitor implements IInstructionInputVisitor<ValueType> {
	@Override
	public ValueType visitVariableReference(VariableReference reference) throws FractalIRException {
		return null;
	}

	@Override
	public ValueType visitBoolConstant(BoolConstant constant) throws FractalIRException {
		return ValueType.BOOL;
	}

	@Override
	public ValueType visitIntConstant(IntConstant constant) throws FractalIRException {
		return ValueType.INT;
	}

	@Override
	public ValueType visitRealConstant(RealConstant constant) throws FractalIRException {
		return ValueType.REAL;
	}

	@Override
	public ValueType visitComplexConstant(ComplexConstant constant) throws FractalIRException {
		return ValueType.COMPLEX;
	}

	@Override
	public ValueType visitFunctionContextConstant(FunctionContextConstant contextConstant) throws FractalIRException {
		return null;
	}

	@Override
	public ValueType visitNullPointer() throws FractalIRException {
		return ValueType.POINTER(ValueType.VOID);
	}

	@Override
	public ValueType visitVoid() throws FractalIRException {
		return ValueType.VOID;
	}
}
