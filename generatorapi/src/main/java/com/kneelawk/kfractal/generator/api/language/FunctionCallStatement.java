package com.kneelawk.kfractal.generator.api.language;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstructionVisitor;

import java.util.List;

public class FunctionCallStatement implements IInstruction {
	private IInstruction function;
	private List<IInstruction> arguments;

	private FunctionCallStatement(IInstruction function, List<IInstruction> arguments) {
		this.function = function;
		this.arguments = arguments;
	}

	public IInstruction getFunction() {
		return function;
	}

	public List<IInstruction> getArguments() {
		return arguments;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitFunctionCall(this);
	}

	public static class Builder {
		private IInstruction function;
		private List<IInstruction> arguments;

		public Builder() {
		}

		public Builder(IInstruction function, List<IInstruction> arguments) {
			this.function = function;
			this.arguments = arguments;
		}

		public FunctionCallStatement build() {
			return new FunctionCallStatement(function, ImmutableList.copyOf(arguments));
		}

		public IInstruction getFunction() {
			return function;
		}

		public Builder setFunction(IInstruction function) {
			this.function = function;
			return this;
		}

		public List<IInstruction> getArguments() {
			return arguments;
		}

		public Builder setArguments(List<IInstruction> arguments) {
			this.arguments = arguments;
			return this;
		}
	}
}
