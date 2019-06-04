package com.kneelawk.kfractal.generator.api.language;

import com.kneelawk.kfractal.generator.api.ir.IInstruction;
import com.kneelawk.kfractal.generator.api.ir.IInstructionVisitor;

public class BinaryOperationStatement implements IInstruction {
	private IInstruction left;
	private IInstruction right;
	private String operation;

	private BinaryOperationStatement(IInstruction left, IInstruction right, String operation) {
		this.left = left;
		this.right = right;
		this.operation = operation;
	}

	public IInstruction getLeft() {
		return left;
	}

	public IInstruction getRight() {
		return right;
	}

	public String getOperation() {
		return operation;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitBinaryOperation(this);
	}

	public static class Builder {
		private IInstruction left;
		private IInstruction right;
		private String operation;

		public Builder() {
		}

		public Builder(IInstruction left, IInstruction right, String operation) {
			this.left = left;
			this.right = right;
			this.operation = operation;
		}

		public BinaryOperationStatement build() {
			return new BinaryOperationStatement(left, right, operation);
		}

		public IInstruction getLeft() {
			return left;
		}

		public Builder setLeft(IInstruction left) {
			this.left = left;
			return this;
		}

		public IInstruction getRight() {
			return right;
		}

		public Builder setRight(IInstruction right) {
			this.right = right;
			return this;
		}

		public String getOperation() {
			return operation;
		}

		public Builder setOperation(String operation) {
			this.operation = operation;
			return this;
		}
	}
}
