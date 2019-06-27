package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.VariableDeclaration;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidatingInstructionInputVisitor implements IInstructionInputVisitor<ValueInfo> {
	private Map<String, FunctionDefinition> functions;
	private Map<String, ValueInfo> variables;

	public ValidatingInstructionInputVisitor(
			Map<String, FunctionDefinition> functions,
			Map<String, ValueInfo> variables) {
		this.functions = functions;
		this.variables = variables;
	}

	@Override
	public ValueInfo visitVariableReference(VariableReference reference) throws FractalIRException {
		String referenceName = reference.getName();
		if (variables.containsKey(referenceName)) {
			return variables.get(referenceName);
		} else {
			throw new MissingVariableReferenceException("Reference to missing variable: '" + referenceName + '\'');
		}
	}

	@Override
	public ValueInfo visitBoolConstant(BoolConstant constant) throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueTypes.BOOL).build();
	}

	@Override
	public ValueInfo visitIntConstant(IntConstant constant) throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueTypes.INT).build();
	}

	@Override
	public ValueInfo visitRealConstant(RealConstant constant) throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueTypes.REAL).build();
	}

	@Override
	public ValueInfo visitComplexConstant(ComplexConstant constant) throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueTypes.COMPLEX).build();
	}

	@Override
	public ValueInfo visitFunctionContextConstant(FunctionContextConstant contextConstant) throws FractalIRException {
		// find the function
		FunctionDefinition target;
		String functionName = contextConstant.getFunctionName();
		if (functions.containsKey(functionName)) {
			target = functions.get(functionName);
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
			if (!targetVariable.getType().isAssignableFrom(constantInput.accept(this).getType())) {
				throw new FractalIRValidationException(
						"Function defines context variable: " + targetVariable + " but constant supplies: " +
								constantInput);
			}
		}

		// build the function type from the target function details
		return new ValueInfo.Builder().setType(ValueTypes.FUNCTION(target.getReturnType(),
				target.getArgumentList().stream().map(VariableDeclaration::getType).collect(
						Collectors.toList()))).build();
	}

	@Override
	public ValueInfo visitNullPointer() throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueTypes.POINTER(ValueTypes.VOID)).build();
	}

	@Override
	public ValueInfo visitVoid() throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueTypes.VOID).build();
	}
}
