package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RealConstant implements IInstructionInput {
	private double value;

	private RealConstant(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public void accept(IInstructionInputVisitor visitor) {
		visitor.visitRealConstant(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("value", value)
				.toString();
	}

	public static class Builder {
		private double value;

		public Builder() {
		}

		public Builder(double value) {
			this.value = value;
		}

		public RealConstant build() {
			return new RealConstant(value);
		}

		public double getValue() {
			return value;
		}

		public Builder setValue(double value) {
			this.value = value;
			return this;
		}
	}
}