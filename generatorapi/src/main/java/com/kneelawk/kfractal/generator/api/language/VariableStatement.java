package com.kneelawk.kfractal.generator.api.language;

import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstructionVisitor;

public class VariableStatement implements IInstruction {
	private String name;

	private VariableStatement(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void accept(IInstructionVisitor visitor) {
		visitor.visitVariable(this);
	}

	public static class Builder {
		private String name;

		public Builder() {
		}

		public Builder(String name) {
			this.name = name;
		}

		public VariableStatement build() {
			return new VariableStatement(name);
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
