package com.kneelawk.kfractal.generator.api.language;

public class BinaryOperationStatement implements IStatement {
	private IStatement left;
	private IStatement right;
	private String operation;

	private BinaryOperationStatement(IStatement left, IStatement right, String operation) {
		this.left = left;
		this.right = right;
		this.operation = operation;
	}

	public IStatement getLeft() {
		return left;
	}

	public IStatement getRight() {
		return right;
	}

	public String getOperation() {
		return operation;
	}

	@Override
	public void accept(IStatementVisitor visitor) {
		visitor.visitBinaryOperation(this);
	}

	public static class Builder {
		private IStatement left;
		private IStatement right;
		private String operation;

		public Builder() {
		}

		public Builder(IStatement left, IStatement right, String operation) {
			this.left = left;
			this.right = right;
			this.operation = operation;
		}

		public BinaryOperationStatement build() {
			return new BinaryOperationStatement(left, right, operation);
		}

		public IStatement getLeft() {
			return left;
		}

		public Builder setLeft(IStatement left) {
			this.left = left;
			return this;
		}

		public IStatement getRight() {
			return right;
		}

		public Builder setRight(IStatement right) {
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
