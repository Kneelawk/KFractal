package com.kneelawk.kfractal.generator.api.ir;

public interface IInstruction {
	void accept(IInstructionVisitor visitor);
}
