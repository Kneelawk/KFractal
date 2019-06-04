package com.kneelawk.kfractal.generator.api.ir.instruction.io;

public class NullPointer implements IInstructionInput {
	public static final NullPointer INSTANCE = new NullPointer();

	private NullPointer() {
	}

	@Override
	public void accept(IInstructionInputVisitor visitor) {
		visitor.visitNullPointer();
	}

	@Override
	public String toString() {
		return "NullPointer";
	}
}
