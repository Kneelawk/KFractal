package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionIO;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexAdd - Instruction. Adds the last two complex numbers and stores the sum in the variable referenced by the
 * first argument.
 *
 * ComplexAdd(Complex sum, Complex leftAddend, Complex rightAddend)
 */
public class ComplexAdd implements IInstruction {
	private IInstructionIO sum;
	private IInstructionIO leftAddend;
	private IInstructionIO rightAddend;

	private ComplexAdd(IInstructionIO sum, IInstructionIO leftAddend, IInstructionIO rightAddend) {
		this.sum = sum;
		this.leftAddend = leftAddend;
		this.rightAddend = rightAddend;
	}

	public IInstructionIO getSum() {
		return sum;
	}

	public IInstructionIO getLeftAddend() {
		return leftAddend;
	}

	public IInstructionIO getRightAddend() {
		return rightAddend;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitComplexAdd(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("sum", sum)
				.append("leftAddend", leftAddend)
				.append("rightAddend", rightAddend)
				.toString();
	}

	public static class Builder {
		private IInstructionIO sum;
		private IInstructionIO leftAddend;
		private IInstructionIO rightAddend;

		public Builder() {
		}

		public Builder(IInstructionIO sum, IInstructionIO leftAddend, IInstructionIO rightAddend) {
			this.sum = sum;
			this.leftAddend = leftAddend;
			this.rightAddend = rightAddend;
		}

		public ComplexAdd build() {
			return new ComplexAdd(sum, leftAddend, rightAddend);
		}

		public IInstructionIO getSum() {
			return sum;
		}

		public Builder setSum(IInstructionIO sum) {
			this.sum = sum;
			return this;
		}

		public IInstructionIO getLeftAddend() {
			return leftAddend;
		}

		public Builder setLeftAddend(IInstructionIO leftAddend) {
			this.leftAddend = leftAddend;
			return this;
		}

		public IInstructionIO getRightAddend() {
			return rightAddend;
		}

		public Builder setRightAddend(IInstructionIO rightAddend) {
			this.rightAddend = rightAddend;
			return this;
		}
	}
}
