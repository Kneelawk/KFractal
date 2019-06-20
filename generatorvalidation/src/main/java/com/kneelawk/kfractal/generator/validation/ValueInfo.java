package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.List;

public class ValueInfo {
	private boolean variable;
	private ValueType type;
	private List<IAttribute> variableAttributes;

	private ValueInfo(boolean variable, ValueType type,
					  List<IAttribute> variableAttributes) {
		this.variable = variable;
		this.type = type;
		this.variableAttributes = variableAttributes;
	}

	public boolean isVariable() {
		return variable;
	}

	public ValueType getType() {
		return type;
	}

	public List<IAttribute> getVariableAttributes() {
		return variableAttributes;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("variable", variable)
				.append("type", type)
				.append("variableAttributes", variableAttributes)
				.toString();
	}

	public static class Builder {
		private boolean variable;
		private ValueType type;
		private List<IAttribute> variableAttributes = Lists.newArrayList();

		public Builder() {
		}

		public Builder(boolean variable, ValueType type,
					   Collection<IAttribute> variableAttributes) {
			this.variable = variable;
			this.type = type;
			this.variableAttributes.addAll(variableAttributes);
		}

		public ValueInfo build() {
			return new ValueInfo(variable, type, variableAttributes);
		}

		public boolean isVariable() {
			return variable;
		}

		public Builder setVariable(boolean variable) {
			this.variable = variable;
			return this;
		}

		public ValueType getType() {
			return type;
		}

		public Builder setType(ValueType type) {
			this.type = type;
			return this;
		}

		public List<IAttribute> getVariableAttributes() {
			return variableAttributes;
		}

		public Builder setVariableAttributes(
				Collection<IAttribute> variableAttributes) {
			this.variableAttributes.clear();
			this.variableAttributes.addAll(variableAttributes);
			return this;
		}

		public Builder addVariableAttribute(IAttribute variableAttribute) {
			variableAttributes.add(variableAttribute);
			return this;
		}
	}
}
