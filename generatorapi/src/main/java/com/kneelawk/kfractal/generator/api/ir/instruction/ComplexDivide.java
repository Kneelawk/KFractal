package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionIO;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ComplexDivide implements IInstruction {
	private IInstructionIO quotient;
	private IInstructionIO dividend;
	private IInstructionIO divisor;

	private ComplexDivide(IInstructionIO quotient, IInstructionIO dividend, IInstructionIO divisor) {
		this.quotient = quotient;
		this.dividend = dividend;
		this.divisor = divisor;
	}

	public IInstructionIO getQuotient() {
		return quotient;
	}

	public IInstructionIO getDividend() {
		return dividend;
	}

	public IInstructionIO getDivisor() {
		return divisor;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitComplexDivide(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("quotient", quotient)
				.append("dividend", dividend)
				.append("divisor", divisor)
				.toString();
	}

	public static class Builder {
		private IInstructionIO quotient;
		private IInstructionIO dividend;
		private IInstructionIO divisor;

		public Builder() {
		}

		public Builder(IInstructionIO quotient, IInstructionIO dividend, IInstructionIO divisor) {
			this.quotient = quotient;
			this.dividend = dividend;
			this.divisor = divisor;
		}

		public ComplexDivide build() {
			return new ComplexDivide(quotient, dividend, divisor);
		}

		public IInstructionIO getQuotient() {
			return quotient;
		}

		public Builder setQuotient(IInstructionIO quotient) {
			this.quotient = quotient;
			return this;
		}

		public IInstructionIO getDividend() {
			return dividend;
		}

		public Builder setDividend(IInstructionIO dividend) {
			this.dividend = dividend;
			return this;
		}

		public IInstructionIO getDivisor() {
			return divisor;
		}

		public Builder setDivisor(IInstructionIO divisor) {
			this.divisor = divisor;
			return this;
		}
	}
}
