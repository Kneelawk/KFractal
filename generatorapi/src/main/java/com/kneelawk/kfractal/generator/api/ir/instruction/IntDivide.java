package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;

/**
 * IntDivide - Instruction. Divides the second to last argument by the last argument and stores the result in the
 * variable referenced by the first argument.
 * <p>
 * IntDivide(Int quotient, Int dividend, Int divisor)
 */
public class IntDivide implements IInstruction {
	private IInstructionOutput quotient;
	private IInstructionInput dividend;
	private IInstructionInput divisor;

	public IntDivide(IInstructionOutput quotient,
					 IInstructionInput dividend,
					 IInstructionInput divisor) {
		this.quotient = quotient;
		this.dividend = dividend;
		this.divisor = divisor;
	}

	public IInstructionOutput getQuotient() {
		return quotient;
	}

	public IInstructionInput getDividend() {
		return dividend;
	}

	public IInstructionInput getDivisor() {
		return divisor;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitIntDivide(this);
	}

	public static class Builder {
		private IInstructionOutput quotient;
		private IInstructionInput dividend;
		private IInstructionInput divisor;

		public Builder() {
		}

		public Builder(IInstructionOutput quotient,
					   IInstructionInput dividend,
					   IInstructionInput divisor) {
			this.quotient = quotient;
			this.dividend = dividend;
			this.divisor = divisor;
		}

		public IntDivide build() {
			return new IntDivide(quotient, dividend, divisor);
		}

		public IInstructionOutput getQuotient() {
			return quotient;
		}

		public Builder setQuotient(IInstructionOutput quotient) {
			this.quotient = quotient;
			return this;
		}

		public IInstructionInput getDividend() {
			return dividend;
		}

		public Builder setDividend(IInstructionInput dividend) {
			this.dividend = dividend;
			return this;
		}

		public IInstructionInput getDivisor() {
			return divisor;
		}

		public Builder setDivisor(IInstructionInput divisor) {
			this.divisor = divisor;
			return this;
		}
	}
}
