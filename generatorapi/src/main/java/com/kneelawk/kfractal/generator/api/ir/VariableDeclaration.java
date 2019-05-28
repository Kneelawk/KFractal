package com.kneelawk.kfractal.generator.api.ir;

import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VariableDeclaration {
	private ValueType type;
	private String name;

	private VariableDeclaration(ValueType type, String name) {
		this.type = type;
		this.name = name;
	}

	public ValueType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("type", type)
				.append("name", name)
				.toString();
	}

	public static class Builder {
		private ValueType type;
		private String name;

		public Builder() {
		}

		public Builder(ValueType type, String name) {
			this.type = type;
			this.name = name;
		}

		public VariableDeclaration build() {
			return new VariableDeclaration(type, name);
		}

		public ValueType getType() {
			return type;
		}

		public Builder setType(ValueType type) {
			this.type = type;
			return this;
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
