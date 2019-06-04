package com.kneelawk.kfractal.generator.api.ir.instruction.io;

public interface IInstructionOutput {
	void accept(IInstructionOutputVisitor visitor);
}
