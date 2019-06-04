package com.kneelawk.kfractal.generator.api.ir;

public class NullPointer implements IInstructionIO {
	public static final NullPointer INSTANCE = new NullPointer();

	private NullPointer() {
	}

	@Override
	public void accept(IInstructionIOVisitor visitor) {
		visitor.visitNullPointer();
	}

	@Override
	public String toString() {
		return "NullPointer";
	}
}
