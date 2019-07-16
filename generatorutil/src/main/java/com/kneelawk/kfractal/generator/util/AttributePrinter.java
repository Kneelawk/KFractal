package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.ir.attribute.IAttributeVisitor;

class AttributePrinter implements IAttributeVisitor {
	private final StringBuilder builder;

	AttributePrinter(StringBuilder builder) {
		this.builder = builder;
	}

	@Override
	public void visitConstant() {
		builder.append("Constant");
	}

	@Override
	public void visitPreallocated() {
		builder.append("Preallocated");
	}
}
