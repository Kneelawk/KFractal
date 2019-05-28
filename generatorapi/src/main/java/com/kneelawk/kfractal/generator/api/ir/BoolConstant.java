package com.kneelawk.kfractal.generator.api.ir;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Kneelawk on 5/27/19.
 */
public class BoolConstant implements IStatementIO {
	private boolean value;

	private BoolConstant(boolean value) {
		this.value = value;
	}

	public boolean isValue() {
		return value;
	}

	@Override
	public void accept(IStatementIOVisitor visitor) {
		visitor.visitBoolConstant(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("value", value)
				.toString();
	}

	public static class Builder {
		private boolean value;

		public Builder() {
		}

		public Builder(boolean value) {
			this.value = value;
		}

		public BoolConstant build() {
			return new BoolConstant(value);
		}

		public boolean isValue() {
			return value;
		}

		public Builder setValue(boolean value) {
			this.value = value;
			return this;
		}
	}
}