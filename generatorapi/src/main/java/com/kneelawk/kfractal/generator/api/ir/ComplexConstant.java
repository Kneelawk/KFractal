package com.kneelawk.kfractal.generator.api.ir;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.complex.Complex;

/**
 * Created by Kneelawk on 5/27/19.
 */
public class ComplexConstant implements IStatementIO {
	private Complex value;

	private ComplexConstant(Complex value) {
		this.value = value;
	}

	public Complex getValue() {
		return value;
	}

	@Override
	public void accept(IStatementIOVisitor visitor) {
		visitor.visitComplexConstant(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("value", value)
				.toString();
	}

	public static class Builder {
		private Complex value;

		public Builder() {
		}

		public Builder(Complex value) {
			this.value = value;
		}

		public ComplexConstant build() {
			return new ComplexConstant(value);
		}

		public Complex getValue() {
			return value;
		}

		public Builder setValue(Complex value) {
			this.value = value;
			return this;
		}
	}
}