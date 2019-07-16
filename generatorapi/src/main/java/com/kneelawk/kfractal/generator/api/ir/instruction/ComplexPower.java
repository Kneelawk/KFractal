package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexPower - Instruction. Raises the second to last argument to the power of the last argument and stores the
 * result in the variable referenced by the first argument.
 * <p>
 * ComplexPower(Complex result, Complex base, Complex exponent)
 */
public class ComplexPower implements IInstruction {
	private IInstructionOutput result;
	private IInstructionInput base;
	private IInstructionInput exponent;

	private ComplexPower(IInstructionOutput result,
						 IInstructionInput base,
						 IInstructionInput exponent) {
		this.result = result;
		this.base = base;
		this.exponent = exponent;
	}

	public IInstructionOutput getResult() {
		return result;
	}

	public IInstructionInput getBase() {
		return base;
	}

	public IInstructionInput getExponent() {
		return exponent;
	}

	@Override
	public <R> R accept(IInstructionVisitor<R> visitor) throws FractalIRException {
		return visitor.visitComplexPower(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("result", result)
				.append("base", base)
				.append("exponent", exponent)
				.toString();
	}

	public static ComplexPower create(IInstructionOutput result,
									  IInstructionInput base,
									  IInstructionInput exponent) {
		return new ComplexPower(result, base, exponent);
	}

	public static class Builder {
		private IInstructionOutput result;
		private IInstructionInput base;
		private IInstructionInput exponent;

		public Builder() {
		}

		public Builder(IInstructionOutput result,
					   IInstructionInput base,
					   IInstructionInput exponent) {
			this.result = result;
			this.base = base;
			this.exponent = exponent;
		}

		public ComplexPower build() {
			return new ComplexPower(result, base, exponent);
		}

		public IInstructionOutput getResult() {
			return result;
		}

		public Builder setResult(IInstructionOutput result) {
			this.result = result;
			return this;
		}

		public IInstructionInput getBase() {
			return base;
		}

		public Builder setBase(IInstructionInput base) {
			this.base = base;
			return this;
		}

		public IInstructionInput getExponent() {
			return exponent;
		}

		public Builder setExponent(IInstructionInput exponent) {
			this.exponent = exponent;
			return this;
		}
	}
}
