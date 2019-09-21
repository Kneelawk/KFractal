package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class GlobalDeclaration {
    private ValueType type;
    private Set<IGlobalAttribute> attributes;

    private GlobalDeclaration(ValueType type,
                              Set<IGlobalAttribute> attributes) {
        this.type = type;
        this.attributes = attributes;
    }

    public ValueType getType() {
        return type;
    }

    public Set<IGlobalAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("type", type)
                .append("attributes", attributes)
                .toString();
    }

    public static GlobalDeclaration create(ValueType type) {
        if (type == null)
            throw new NullPointerException("Type cannot be null");
        return new GlobalDeclaration(type, ImmutableSet.of());
    }

    public static GlobalDeclaration create(ValueType type, IGlobalAttribute... attributes) {
        if (type == null)
            throw new NullPointerException("Type cannot be null");
        return new GlobalDeclaration(type, ImmutableSet.copyOf(attributes));
    }

    public static GlobalDeclaration create(ValueType type,
                                           Iterable<IGlobalAttribute> attributes) {
        if (type == null)
            throw new NullPointerException("Type cannot be null");
        return new GlobalDeclaration(type, ImmutableSet.copyOf(attributes));
    }

    public static class Builder {
        private ValueType type;
        private Set<IGlobalAttribute> attributes = Sets.newHashSet();

        public Builder() {
        }

        public Builder(ValueType type,
                       Collection<IGlobalAttribute> attributes) {
            this.type = type;
            this.attributes.addAll(attributes);
        }

        public GlobalDeclaration build() {
            if (type == null)
                throw new IllegalStateException("No type specified");
            return new GlobalDeclaration(type, ImmutableSet.copyOf(attributes));
        }

        public ValueType getType() {
            return type;
        }

        public Builder setType(ValueType type) {
            this.type = type;
            return this;
        }

        public Set<IGlobalAttribute> getAttributes() {
            return attributes;
        }

        public Builder setAttributes(
                Collection<IGlobalAttribute> attributes) {
            this.attributes.clear();
            this.attributes.addAll(attributes);
            return this;
        }

        public Builder addAttribute(IGlobalAttribute attribute) {
            attributes.add(attribute);
            return this;
        }

        public Builder addAttributes(IGlobalAttribute... attributes) {
            this.attributes.addAll(Arrays.asList(attributes));
            return this;
        }

        public Builder addAttributes(Collection<IGlobalAttribute> attributes) {
            this.attributes.addAll(attributes);
            return this;
        }
    }
}
