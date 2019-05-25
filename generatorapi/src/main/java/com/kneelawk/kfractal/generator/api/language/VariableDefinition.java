package com.kneelawk.kfractal.generator.api.language;

public class VariableDefinition {
	private ValueType type;
	private String name;

	private VariableDefinition(ValueType type, String name) {
		this.type = type;
		this.name = name;
	}

	public ValueType getType() {
		return type;
	}

	public String getName() {
		return name;
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

		public VariableDefinition build() {
			return new VariableDefinition(type, name);
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
