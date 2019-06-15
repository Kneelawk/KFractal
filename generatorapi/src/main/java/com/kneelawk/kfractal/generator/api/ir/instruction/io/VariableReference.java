package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Kneelawk on 5/26/19.
 */
public class VariableReference implements IInstructionInput, IInstructionOutput {
	private String name;

	private VariableReference(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void accept(IInstructionInputVisitor visitor) {
		visitor.visitVariableReference(this);
	}

	@Override
	public void accept(IInstructionOutputVisitor visitor) {
		visitor.visitVariableReference(this);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("name", name)
				.toString();
	}

	public static class Builder {
		private String name;

		public Builder() {
		}

		public Builder(String name) {
			this.name = name;
		}

		public VariableReference build() {
			return new VariableReference(name);
		}

		public String getName() {
			return name;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}
	}
}