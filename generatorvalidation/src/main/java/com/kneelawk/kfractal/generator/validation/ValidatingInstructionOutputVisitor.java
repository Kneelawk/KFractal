package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutputVisitor;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;

public class ValidatingInstructionOutputVisitor implements IInstructionOutputVisitor<ValueType> {
	@Override
	public ValueType visitVariableReference(VariableReference reference) {
		return null;
	}

	@Override
	public ValueType visitVoid() {
		return ValueType.VOID;
	}
}
