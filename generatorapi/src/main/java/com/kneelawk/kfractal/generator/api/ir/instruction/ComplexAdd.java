package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexAdd - Instruction. Adds the last two complex numbers and stores the sum in the variable referenced by the
 * first argument.
 * <p>
 * ComplexAdd(Complex sum, Complex leftAddend, Complex rightAddend)
 */
public class ComplexAdd implements IInstruction {
	private IInstructionOutput sum;
	private IInstructionInput leftAddend;
	private IInstructionInput rightAddend;

	private ComplexAdd(IInstructionOutput sum, IInstructionInput leftAddend, IInstructionInput rightAddend) {
		this.sum = sum;
		this.leftAddend = leftAddend;
		this.rightAddend = rightAddend;
	}

	public IInstructionOutput getSum() {
		return sum;
	}

	public IInstructionInput getLeftAddend() {
		return leftAddend;
	}

	public IInstructionInput getRightAddend() {
		return rightAddend;
	}

	@Override
	public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
		return visitor.visitComplexAdd(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("sum", sum)
				.append("leftAddend", leftAddend)
				.append("rightAddend", rightAddend)
				.toString();
	}

	public static ComplexAdd create(IInstructionOutput sum,
									IInstructionInput leftAddend,
									IInstructionInput rightAddend) {
		return new ComplexAdd(sum, leftAddend, rightAddend);
	}

	public static class Builder {
		private IInstructionOutput sum;
		private IInstructionInput leftAddend;
		private IInstructionInput rightAddend;

		public Builder() {
		}

		public Builder(IInstructionOutput sum, IInstructionInput leftAddend, IInstructionInput rightAddend) {
			this.sum = sum;
			this.leftAddend = leftAddend;
			this.rightAddend = rightAddend;
		}

		public ComplexAdd build() {
			return new ComplexAdd(sum, leftAddend, rightAddend);
		}

		public IInstructionOutput getSum() {
			return sum;
		}

		public Builder setSum(IInstructionOutput sum) {
			this.sum = sum;
			return this;
		}

		public IInstructionInput getLeftAddend() {
			return leftAddend;
		}

		public Builder setLeftAddend(IInstructionInput leftAddend) {
			this.leftAddend = leftAddend;
			return this;
		}

		public IInstructionInput getRightAddend() {
			return rightAddend;
		}

		public Builder setRightAddend(IInstructionInput rightAddend) {
			this.rightAddend = rightAddend;
			return this;
		}
	}
}
