package com.kneelawk.kfractal.generator.api.ir;

public class NullPointer implements IStatementIO {
	public static final NullPointer INSTANCE = new NullPointer();

	private NullPointer() {
	}

	@Override
	public void accept(IStatementIOVisitor visitor) {
		visitor.visitNullPointer();
	}

	@Override
	public String toString() {
		return "NullPointer";
	}
}
