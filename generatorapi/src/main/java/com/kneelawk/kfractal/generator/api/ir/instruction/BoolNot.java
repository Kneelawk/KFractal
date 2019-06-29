package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
	public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
		return visitor.visitBoolNot(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("output", output)
				.append("input", input)
				.toString();
	}

	public static BoolNot create(IInstructionOutput output,
								 IInstructionInput input) {
		return new BoolNot(output, input);
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
