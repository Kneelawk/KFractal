package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;

public class ValidatingInstructionInputVisitor implements IInstructionInputVisitor<ValueInfo> {
	private Program program;
	private FunctionDefinition function;

	public ValidatingInstructionInputVisitor(Program program,
											 FunctionDefinition function) {
		this.program = program;
		this.function = function;
	}

	@Override
	public ValueInfo visitVariableReference(VariableReference reference) throws FractalIRException {
		String referenceName = reference.getName();
		VariableDeclaration declaration;
		if (function.getLocalVariables().containsKey(referenceName)) {
			declaration = function.getLocalVariables().get(referenceName);
		} else if (function.getArguments().containsKey(referenceName)) {
			declaration = function.getArguments().get(referenceName);
		} else if (function.getContextVariables().containsKey(referenceName)) {
			declaration = function.getContextVariables().get(referenceName);
		} else if (program.getGlobalVariables().containsKey(referenceName)) {
			declaration = program.getGlobalVariables().get(referenceName);
		} else {
			throw new FractalIRValidationException("Reference to missing variable '" + referenceName + '\'');
		}

		return new ValueInfo.Builder(true, declaration.getType(), declaration.getAttributes()).build();
	}

	@Override
	public ValueInfo visitBoolConstant(BoolConstant constant) throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueType.BOOL).build();
	}

	@Override
	public ValueInfo visitIntConstant(IntConstant constant) throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueType.INT).build();
	}

	@Override
	public ValueInfo visitRealConstant(RealConstant constant) throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueType.REAL).build();
	}

	@Override
	public ValueInfo visitComplexConstant(ComplexConstant constant) throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueType.COMPLEX).build();
	}

	@Override
	public ValueInfo visitFunctionContextConstant(FunctionContextConstant contextConstant) throws FractalIRException {
		return null;
	}

	@Override
	public ValueInfo visitNullPointer() throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueType.POINTER(ValueType.VOID)).build();
	}

	@Override
	public ValueInfo visitVoid() throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueType.VOID).build();
	}
}
