package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * BoolAnd - Ands the last two arguments together and stores it in the first argument.
 *
 * BoolAnd(Bool result, Bool left, Bool right)
 */
public class BoolAnd implements IInstruction {
	private IInstructionOutput result;
	private IInstructionInput left;
	private IInstructionInput right;

	private BoolAnd(IInstructionOutput result, IInstructionInput left, IInstructionInput right) {
		this.result = result;
		this.left = left;
		this.right = right;
	}

	public IInstructionOutput getResult() {
		return result;
	}

	public IInstructionInput getLeft() {
		return left;
	}

	public IInstructionInput getRight() {
		return right;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitBoolAnd(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("result", result)
				.append("left", left)
				.append("right", right)
				.toString();
	}

	public static class Builder {
		private IInstructionOutput result;
		private IInstructionInput left;
		private IInstructionInput right;

		public Builder() {
		}

		public Builder(IInstructionOutput result,
					   IInstructionInput left,
					   IInstructionInput right) {
			this.result = result;
			this.left = left;
			this.right = right;
		}

		public BoolAnd build() {
			return new BoolAnd(result, left, right);
		}

		public IInstructionOutput getResult() {
			return result;
		}

		public Builder setResult(IInstructionOutput result) {
			this.result = result;
			return this;
		}

		public IInstructionInput getLeft() {
			return left;
		}

		public Builder setLeft(IInstructionInput left) {
			this.left = left;
			return this;
		}

		public IInstructionInput getRight() {
			return right;
		}

		public Builder setRight(IInstructionInput right) {
			this.right = right;
			return this;
		}
	}
}