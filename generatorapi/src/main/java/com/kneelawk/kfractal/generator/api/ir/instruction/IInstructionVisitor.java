package com.kneelawk.kfractal.generator.api.ir.instruction;

/**
 * This interface is implemented by anything that wants to be able to traverse a Fractal IR instruction tree.
 */
public interface IInstructionVisitor {
	/* General Purpose Operations */

	void visitAssign(Assign assign);

	/* Bool Operations */

	void visitBoolNot(BoolNot boolNot);

	void visitBoolAnd(BoolAnd boolAnd);

	void visitBoolOr(BoolOr boolOr);

	void visitBoolIsEqual(BoolIsEqual boolIsEqual);

	void visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual);

	/* Int Operations */

	void visitIntAdd(IntAdd intAdd);

	void visitIntSubtract(IntSubtract intSubtract);

	void visitIntMultiply(IntMultiply intMultiply);

	void visitIntDivide(IntDivide intDivide);

	void visitIntModulo(IntModulo intModulo);

	void visitIntAnd(IntAnd intAnd);

	void visitIntOr(IntOr intOr);

	void visitIntXor(IntXor intXor);

	void visitIntIsEqual(IntIsEqual intIsEqual);

	void visitIntIsNotEqual(IntIsNotEqual intIsNotEqual);

	void visitIntIsGreater(IntIsGreater intIsGreater);

	void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual);

	/* Real Operations */

	/* Complex Operations */

	void visitComplexAdd(ComplexAdd complexAdd);

	void visitComplexSubtract(ComplexSubtract complexSubtract);

	void visitComplexMultiply(ComplexMultiply complexMultiply);

	void visitComplexDivide(ComplexDivide complexDivide);

	void visitComplexGetReal(ComplexGetReal complexGetReal);

	void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary);

	void visitComplexModulo(ComplexModulo complexModulo);

	/* Function Operations */
}
