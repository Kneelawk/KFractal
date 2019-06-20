package com.kneelawk.kfractal.generator.api.ir.instruction.io;

public interface IInstructionOutputVisitor<R> {
	R visitVariableReference(VariableReference reference);

	R visitVoid();
}
