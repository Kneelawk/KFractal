package com.kneelawk.kfractal.generator.api.ir;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.Set;

public class VariableDeclaration {
	private ValueType type;
	private String name;
	private Set<IAttribute> attributes;

	private VariableDeclaration(ValueType type, String name,
								Set<IAttribute> attributes) {
		this.type = type;
		this.name = name;
		this.attributes = attributes;
	}

	public ValueType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Set<IAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
				.append("type", type)
				.append("name", name)
				.append("attributes", attributes)
				.toString();
	}

	public static VariableDeclaration create(ValueType type, String name) {
		return new VariableDeclaration(type, name, ImmutableSet.of());
	}

	public static VariableDeclaration create(ValueType type, String name, Iterable<IAttribute> attributes) {
		return new VariableDeclaration(type, name, ImmutableSet.copyOf(attributes));
	}

	public static class Builder {
		private ValueType type;
		private String name;
		private Set<IAttribute> attributes = Sets.newHashSet();

		public Builder() {
		}

		public Builder(ValueType type, String name,
					   Collection<IAttribute> attributes) {
			this.type = type;
			this.name = name;
			this.attributes.addAll(attributes);
		}

		public VariableDeclaration build() {
			return new VariableDeclaration(type, name, ImmutableSet.copyOf(attributes));
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
	}
}
