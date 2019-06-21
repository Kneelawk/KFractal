package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
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
		// find the variable
		// This might be appropriate for its own class
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

		// check for illegal output variable attributes (constant, or preallocated)
		if (declaration.getAttributes().contains(IAttribute.CONSTANT)) {
			throw new FractalIRValidationException(
					"Variable: '" + referenceName + "' has illegal output attribute CONSTANT");
		} else if (declaration.getAttributes().contains(IAttribute.PREALLOCATED)) {
			throw new FractalIRValidationException(
					"Variable: '" + referenceName + "' has illegal output attribute PREALLOCATED");
		}

		return new ValueInfo.Builder(true, declaration.getType(), declaration.getAttributes()).build();
	}

	@Override
	public ValueInfo visitVoid() throws FractalIRException {
		return new ValueInfo.Builder().setType(ValueType.VOID).build();
	}
}
