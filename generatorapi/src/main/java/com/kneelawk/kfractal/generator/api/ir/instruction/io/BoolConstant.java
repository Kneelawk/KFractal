package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Kneelawk on 5/27/19.
 */
public class BoolConstant implements IInstructionInput {
	private boolean value;

	private BoolConstant(boolean value) {
		this.value = value;
	}

	public boolean isValue() {
		return value;
	}

	@Override
	public <R> R accept(IInstructionInputVisitor<R> visitor) throws FractalIRException {
		return visitor.visitBoolConstant(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("value", value)
				.toString();
	}

	public static BoolConstant create(boolean value) {
		return new BoolConstant(value);
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
