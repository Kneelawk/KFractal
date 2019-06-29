package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * RealMultiply - Instruction. Multiplies the last two arguments together and stores the result in the variable
 * referenced by the first argument.
 * <p>
 * RealMultiply(Real product, Real leftFactor, Real rightFactor)
 */
public class RealMultiply implements IInstruction {
	private IInstructionOutput product;
	private IInstructionInput leftFactor;
	private IInstructionInput rightFactor;

	private RealMultiply(IInstructionOutput product,
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
	public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
		return visitor.visitRealMultiply(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("product", product)
				.append("leftFactor", leftFactor)
				.append("rightFactor", rightFactor)
				.toString();
	}

	public static RealMultiply create(IInstructionOutput product,
									  IInstructionInput leftFactor,
									  IInstructionInput rightFactor) {
		return new RealMultiply(product, leftFactor, rightFactor);
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

		public RealMultiply build() {
			return new RealMultiply(product, leftFactor, rightFactor);
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
