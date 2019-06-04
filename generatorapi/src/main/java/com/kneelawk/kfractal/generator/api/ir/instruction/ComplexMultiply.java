package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionIO;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexMultiply - Instruction. Multiplies the last two complex numbers, and stores the product in the first.
 *
 * ComplexMultiply(Complex product, Complex a, Complex b)
 */
public class ComplexMultiply implements IInstruction {
	private IInstructionIO product;
	private IInstructionIO leftFactor;
	private IInstructionIO rightFactor;

	private ComplexMultiply(IInstructionIO product, IInstructionIO leftFactor, IInstructionIO rightFactor) {
		this.product = product;
		this.leftFactor = leftFactor;
		this.rightFactor = rightFactor;
	}

	public IInstructionIO getProduct() {
		return product;
	}

	public IInstructionIO getLeftFactor() {
		return leftFactor;
	}

	public IInstructionIO getRightFactor() {
		return rightFactor;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitComplexMultiply(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("product", product)
				.append("leftFactor", leftFactor)
				.append("rightFactor", rightFactor)
				.toString();
	}

	public static class Builder {
		private IInstructionIO product;
		private IInstructionIO leftFactor;
		private IInstructionIO rightFactor;

		public Builder() {
		}

		public Builder(IInstructionIO product, IInstructionIO leftFactor, IInstructionIO rightFactor) {
			this.product = product;
			this.leftFactor = leftFactor;
			this.rightFactor = rightFactor;
		}

		public ComplexMultiply build() {
			return new ComplexMultiply(product, leftFactor, rightFactor);
		}

		public IInstructionIO getProduct() {
			return product;
		}

		public Builder setProduct(IInstructionIO product) {
			this.product = product;
			return this;
		}

		public IInstructionIO getLeftFactor() {
			return leftFactor;
		}

		public Builder setLeftFactor(IInstructionIO leftFactor) {
			this.leftFactor = leftFactor;
			return this;
		}

		public IInstructionIO getRightFactor() {
			return rightFactor;
		}

		public Builder setRightFactor(IInstructionIO rightFactor) {
			this.rightFactor = rightFactor;
			return this;
		}
	}
}
