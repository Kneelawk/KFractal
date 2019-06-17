package com.kneelawk.kfractal.generator.api.ir.instruction.io;

public interface IInstructionOutputVisitor {
	void visitVariableReference(VariableReference reference);

	void visitVoid();
}
