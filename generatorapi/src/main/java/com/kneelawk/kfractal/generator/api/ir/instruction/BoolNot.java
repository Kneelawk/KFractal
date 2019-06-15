package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;

/**
 * BoolNot - Instruction. Gets the not value of the second argument and stores it in the variable referenced by the
 * first argument.
 * <p>
 * BoolNot(Bool output, Bool input)
 */
public class BoolNot implements IInstruction {
	private IInstructionOutput output;
	private IInstructionInput input;

	private BoolNot(IInstructionOutput output, IInstructionInput input) {
		this.output = output;
		this.input = input;
	}

	public IInstructionOutput getOutput() {
		return output;
	}

	public IInstructionInput getInput() {
		return input;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitBoolNot(this);
	}

	public static class Builder {
		private IInstructionOutput output;
		private IInstructionInput input;

		public Builder() {
		}

		public Builder(IInstructionOutput output,
					   IInstructionInput input) {
			this.output = output;
			this.input = input;
		}

		public BoolNot build() {
			return new BoolNot(output, input);
		}

		public IInstructionOutput getOutput() {
			return output;
		}

		public Builder setOutput(IInstructionOutput output) {
			this.output = output;
			return this;
		}

		public IInstructionInput getInput() {
			return input;
		}

		public Builder setInput(IInstructionInput input) {
			this.input = input;
			return this;
		}
	}
}
