package com.kneelawk.kfractal.generator.api.ir.instruction;

public interface IInstruction {
	void accept(IInstructionVisitor visitor);
}
