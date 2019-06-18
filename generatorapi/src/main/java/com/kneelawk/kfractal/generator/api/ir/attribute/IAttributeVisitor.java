package com.kneelawk.kfractal.generator.api.ir.attribute;

public interface IAttributeVisitor {
	void visitConstant();

	void visitPreallocated();
}
