package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.List;

class ValueInfo {
	private final boolean variable;
	private final ValueType type;
	private final List<IAttribute> variableAttributes;

	private ValueInfo(boolean variable, ValueType type,
					  List<IAttribute> variableAttributes) {
		this.variable = variable;
		this.type = type;
		this.variableAttributes = variableAttributes;
	}

	boolean isVariable() {
		return variable;
	}

	ValueType getType() {
		return type;
	}

	List<IAttribute> getVariableAttributes() {
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

	static class Builder {
		private boolean variable = false;
		private ValueType type;
		private final List<IAttribute> variableAttributes = Lists.newArrayList();

		Builder() {
		}

		Builder(boolean variable, ValueType type,
				Collection<IAttribute> variableAttributes) {
			this.variable = variable;
			this.type = type;
			this.variableAttributes.addAll(variableAttributes);
		}

		ValueInfo build() {
			return new ValueInfo(variable, type, variableAttributes);
		}

		boolean isVariable() {
			return variable;
		}

		Builder setVariable(boolean variable) {
			this.variable = variable;
			return this;
		}

		ValueType getType() {
			return type;
		}

		Builder setType(ValueType type) {
			this.type = type;
			return this;
		}

		List<IAttribute> getVariableAttributes() {
			return variableAttributes;
		}

		Builder setVariableAttributes(
				Collection<IAttribute> variableAttributes) {
			this.variableAttributes.clear();
			this.variableAttributes.addAll(variableAttributes);
			return this;
		}

		Builder addVariableAttribute(IAttribute variableAttribute) {
			variableAttributes.add(variableAttribute);
			return this;
		}
	}
}
