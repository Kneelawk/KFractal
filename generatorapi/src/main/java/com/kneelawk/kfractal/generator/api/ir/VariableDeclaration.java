package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class VariableDeclaration {
    private ValueType type;
    private Set<IAttribute> attributes;

    private VariableDeclaration(ValueType type,
                                Set<IAttribute> attributes) {
        this.type = type;
        this.attributes = attributes;
    }

    public ValueType getType() {
        return type;
    }

    public Set<IAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("type", type)
                .append("attributes", attributes)
                .toString();
    }

    public static VariableDeclaration create(ValueType type) {
        if (type == null)
            throw new NullPointerException("Type cannot be null");
        return new VariableDeclaration(type, ImmutableSet.of());
    }

    public static VariableDeclaration create(ValueType type, IAttribute... attributes) {
        if (type == null)
            throw new NullPointerException("Type cannot be null");
        return new VariableDeclaration(type, ImmutableSet.copyOf(attributes));
    }

    public static VariableDeclaration create(ValueType type,
                                             Iterable<IAttribute> attributes) {
        if (type == null)
            throw new NullPointerException("Type cannot be null");
        return new VariableDeclaration(type, ImmutableSet.copyOf(attributes));
    }

    public static class Builder {
        private ValueType type;
        private Set<IAttribute> attributes = Sets.newHashSet();

        public Builder() {
        }

        public Builder(ValueType type,
                       Collection<IAttribute> attributes) {
            this.type = type;
            this.attributes.addAll(attributes);
        }

        public VariableDeclaration build() {
            if (type == null)
                throw new IllegalStateException("No type specified");
            return new VariableDeclaration(type, ImmutableSet.copyOf(attributes));
        }

        public ValueType getType() {
            return type;
        }

        public Builder setType(ValueType type) {
            this.type = type;
            return this;
        }

        public Set<IAttribute> getAttributes() {
            return attributes;
        }

        public Builder setAttributes(
                Collection<IAttribute> attributes) {
            this.attributes.clear();
            this.attributes.addAll(attributes);
            return this;
        }

        public Builder addAttribute(IAttribute attribute) {
            attributes.add(attribute);
            return this;
        }

        public Builder addAttributes(IAttribute... attributes) {
            this.attributes.addAll(Arrays.asList(attributes));
            return this;
        }

        public Builder addAttributes(Collection<IAttribute> attributes) {
            this.attributes.addAll(attributes);
            return this;
        }
    }
}
