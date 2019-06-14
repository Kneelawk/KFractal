package com.kneelawk.kfractal.generator.api.ir.instruction;

public interface IInstructionVisitor {
	/* General Purpose Operations */

	void visitAssign(Assign assign);

	/* Bool Operations */

	void visitBoolAnd(BoolAnd boolAnd);

	void visitBoolOr(BoolOr boolOr);

	/* Int Operations */

	/* Real Operations */

	/* Complex Operations */

	void visitComplexAdd(ComplexAdd complexAdd);

	void visitComplexSubtract(ComplexSubtract complexSubtract);

	void visitComplexMultiply(ComplexMultiply complexMultiply);

	void visitComplexDivide(ComplexDivide complexDivide);

	/* Function Operations */
}
