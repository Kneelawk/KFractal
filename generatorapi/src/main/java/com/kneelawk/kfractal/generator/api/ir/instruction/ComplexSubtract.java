package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionIO;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ComplexSubtract implements IInstruction {
	private IInstructionIO difference;
	private IInstructionIO minuend;
	private IInstructionIO subtrahend;

	private ComplexSubtract(IInstructionIO difference, IInstructionIO minuend, IInstructionIO subtrahend) {
		this.difference = difference;
		this.minuend = minuend;
		this.subtrahend = subtrahend;
	}

	public IInstructionIO getDifference() {
		return difference;
	}

	public IInstructionIO getMinuend() {
		return minuend;
	}

	public IInstructionIO getSubtrahend() {
		return subtrahend;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitComplexSubtract(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("difference", difference)
				.append("minuend", minuend)
				.append("subtrahend", subtrahend)
				.toString();
	}

	public static class Builder {
		private IInstructionIO difference;
		private IInstructionIO minuend;
		private IInstructionIO subtrahend;

		public Builder() {
		}

		public Builder(IInstructionIO difference, IInstructionIO minuend, IInstructionIO subtrahend) {
			this.difference = difference;
			this.minuend = minuend;
			this.subtrahend = subtrahend;
		}

		public ComplexSubtract build() {
			return new ComplexSubtract(difference, minuend, subtrahend);
		}

		public IInstructionIO getDifference() {
			return difference;
		}

		public Builder setDifference(IInstructionIO difference) {
			this.difference = difference;
			return this;
		}

		public IInstructionIO getMinuend() {
			return minuend;
		}

		public Builder setMinuend(IInstructionIO minuend) {
			this.minuend = minuend;
			return this;
		}

		public IInstructionIO getSubtrahend() {
			return subtrahend;
		}

		public Builder setSubtrahend(IInstructionIO subtrahend) {
			this.subtrahend = subtrahend;
			return this;
		}
	}
}
