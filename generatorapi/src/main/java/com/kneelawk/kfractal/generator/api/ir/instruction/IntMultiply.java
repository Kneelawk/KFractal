package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;

/**
 * IntMultiply - Instruction. Multiplies the last two arguments together and stores the result in the variable
 * referenced by the first argument.
 * <p>
 * IntMultiply(Int product, Int leftFactor, Int rightFactor)
 */
public class IntMultiply implements IInstruction {
	private IInstructionOutput product;
	private IInstructionInput leftFactor;
	private IInstructionInput rightFactor;

	public IntMultiply(IInstructionOutput product,
					   IInstructionInput leftFactor,
					   IInstructionInput rightFactor) {
		this.product = product;
		this.leftFactor = leftFactor;
		this.rightFactor = rightFactor;
	}

	public IInstructionOutput getProduct() {
		return product;
	}

	public IInstructionInput getLeftFactor() {
		return leftFactor;
	}

	public IInstructionInput getRightFactor() {
		return rightFactor;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitIntMultiply(this);
	}

	public static class Builder {
		private IInstructionOutput product;
		private IInstructionInput leftFactor;
		private IInstructionInput rightFactor;

		public Builder() {
		}

		public Builder(IInstructionOutput product,
					   IInstructionInput leftFactor,
					   IInstructionInput rightFactor) {
			this.product = product;
			this.leftFactor = leftFactor;
			this.rightFactor = rightFactor;
		}

		public IntMultiply build() {
			return new IntMultiply(product, leftFactor, rightFactor);
		}

		public IInstructionOutput getProduct() {
			return product;
		}

		public Builder setProduct(IInstructionOutput product) {
			this.product = product;
			return this;
		}

		public IInstructionInput getLeftFactor() {
			return leftFactor;
		}

		public Builder setLeftFactor(
				IInstructionInput leftFactor) {
			this.leftFactor = leftFactor;
			return this;
		}

		public IInstructionInput getRightFactor() {
			return rightFactor;
		}

		public Builder setRightFactor(
				IInstructionInput rightFactor) {
			this.rightFactor = rightFactor;
			return this;
		}
	}
}
