package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;

import java.util.List;
import java.util.stream.Collectors;

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
			throw new FractalIRValidationException("Reference to missing variable: '" + referenceName + '\'');
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
		// find the function
		FunctionDefinition target;
		String functionName = contextConstant.getFunctionName();
		if (program.getFunctions().containsKey(functionName)) {
			target = program.getFunctions().get(functionName);
		} else {
			throw new FractalIRValidationException("Reference to missing function: '" + functionName + '\'');
		}

		// compare context variable types
		List<VariableDeclaration> targetContextVariables = target.getContextVariableList();
		List<IInstructionInput> constantContextVariables = contextConstant.getContextVariables();
		int targetContextVariablesSize = targetContextVariables.size();
		int constantContextVariablesSize = constantContextVariables.size();
		int size = Math.max(targetContextVariablesSize, constantContextVariablesSize);
		for (int i = 0; i < size; i++) {
			if (i >= targetContextVariablesSize) {
				throw new FractalIRValidationException("FunctionContextConstant has extra context variables: " +
						constantContextVariables.subList(i, constantContextVariablesSize));
			}
			if (i >= constantContextVariablesSize) {
				throw new FractalIRValidationException("FunctionContextConstant is missing context variables: " +
						targetContextVariables.subList(i, targetContextVariablesSize));
			}
			VariableDeclaration targetVariable = targetContextVariables.get(i);
			IInstructionInput constantInput = constantContextVariables.get(i);
			if (!targetVariable.getType().equals(constantInput.accept(this).getType())) {
				throw new FractalIRValidationException(
						"Function defines context variable: " + targetVariable + " but constant supplies: " +
								constantInput);
			}
		}

		// build the function type from the target function details
		return new ValueInfo.Builder().setType(ValueType.FUNCTION(target.getReturnType(),
				target.getArgumentList().stream().map(VariableDeclaration::getType).collect(
						Collectors.toList()))).build();
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
