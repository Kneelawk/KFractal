package com.kneelawk.kfractal.generator.api.ir.instruction.io;

public class VoidConstant implements IInstructionInput, IInstructionOutput {
	public static final VoidConstant INSTANCE = new VoidConstant();

	private VoidConstant() {
	}

	@Override
	public void accept(IInstructionInputVisitor visitor) {
		visitor.visitVoid();
	}

	@Override
	public void accept(IInstructionOutputVisitor visitor) {
		visitor.visitVoid();
	}

	@Override
	public String toString() {
		return "VoidConstant";
	}
}
