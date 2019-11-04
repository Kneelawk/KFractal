package com.kneelawk.kfractal.generator.api.ir;

import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ArgumentDeclaration {
    private ValueType type;

    private ArgumentDeclaration(ValueType type) {
        this.type = type;
    }

    public ValueType getType() {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("type", type)
                .toString();
    }

    public static ArgumentDeclaration create(ValueType type) {
        if (type == null)
            throw new NullPointerException("Type cannot be null");
        return new ArgumentDeclaration(type);
    }

    public static class Builder {
        private ValueType type;

        public Builder() {
        }

        public Builder(ValueType type) {
            this.type = type;
        }

        public ArgumentDeclaration build() {
            if (type == null)
                throw new IllegalStateException("No type specified");
            return new ArgumentDeclaration(type);
        }

        public ValueType getType() {
            return type;
        }

        public Builder setType(ValueType type) {
            this.type = type;
            return this;
        }
    }
}
