package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.List;

class ValueInfo {
    private final boolean variable;
    private final ValueType type;
    private final List<IGlobalAttribute> variableAttributes;

    private ValueInfo(boolean variable, ValueType type,
                      List<IGlobalAttribute> variableAttributes) {
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

    List<IGlobalAttribute> getVariableAttributes() {
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
        private final List<IGlobalAttribute> variableAttributes = Lists.newArrayList();

        Builder() {
        }

        Builder(boolean variable, ValueType type,
                Collection<IGlobalAttribute> variableAttributes) {
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

        List<IGlobalAttribute> getVariableAttributes() {
            return variableAttributes;
        }

        Builder setVariableAttributes(
                Collection<IGlobalAttribute> variableAttributes) {
            this.variableAttributes.clear();
            this.variableAttributes.addAll(variableAttributes);
            return this;
        }

        Builder addVariableAttribute(IGlobalAttribute variableAttribute) {
            variableAttributes.add(variableAttribute);
            return this;
        }
    }
}
