package com.kneelawk.kfractal.generator.api.language;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class FunctionCallStatement implements IStatement {
	private IStatement function;
	private List<IStatement> arguments;

	private FunctionCallStatement(IStatement function, List<IStatement> arguments) {
		this.function = function;
		this.arguments = arguments;
	}

	public IStatement getFunction() {
		return function;
	}

	public List<IStatement> getArguments() {
		return arguments;
	}

	@Override
	public void accept(IStatementVisitor visitor) {
		visitor.visitFunctionCall(this);
	}

	public static class Builder {
		private IStatement function;
		private List<IStatement> arguments;

		public Builder() {
		}

		public Builder(IStatement function, List<IStatement> arguments) {
			this.function = function;
			this.arguments = arguments;
		}

		public FunctionCallStatement build() {
			return new FunctionCallStatement(function, ImmutableList.copyOf(arguments));
		}

		public IStatement getFunction() {
			return function;
		}

		public Builder setFunction(IStatement function) {
			this.function = function;
			return this;
		}

		public List<IStatement> getArguments() {
			return arguments;
		}

		public Builder setArguments(List<IStatement> arguments) {
			this.arguments = arguments;
			return this;
		}
	}
}
