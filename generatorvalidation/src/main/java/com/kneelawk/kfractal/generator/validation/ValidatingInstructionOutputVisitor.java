package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutputVisitor;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;

public class ValidatingInstructionOutputVisitor implements IInstructionOutputVisitor<ValueInfo> {
	private Program program;
	private FunctionDefinition function;

	public ValidatingInstructionOutputVisitor(Program program,
											  FunctionDefinition function) {
		this.program = program;
		this.function = function;
	}

	@Override
	public ValueInfo visitVariableReference(VariableReference reference) throws FractalIRException {
		String referenceName = reference.getName();
		VariableDeclaration declaration = null;
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
		return new ValueInfo.Builder().setVariable(true).setType(declaration.getType())
				.setVariableAttributes(declaration.getAttributes()).build();
	}

	@Override
	public ValueInfo visitVoid() throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueType.VOID).build();
	}
}
