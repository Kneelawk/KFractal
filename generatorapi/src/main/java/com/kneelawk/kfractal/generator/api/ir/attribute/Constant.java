package com.kneelawk.kfractal.generator.api.ir.attribute;

/**
 * Constant - Variables declared with the constant attribute cannot be reassigned.
 */
public class Constant implements IAttribute {
	static final Constant INSTANCE = new Constant();

	private Constant() {
	}

	@Override
	public void accept(IAttributeVisitor visitor) {
		visitor.visitConstant();
	}
}
