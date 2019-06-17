package com.kneelawk.kfractal.generator.api.ir.instruction;

/**
 * This interface is implemented by anything that wants to be able to traverse a Fractal IR instruction tree.
 */
public interface IInstructionVisitor {
	/* General Purpose Operations */

	void visitAssign(Assign assign);

	void visitReturn(Return aReturn);

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

	void visitIntPower(IntPower intPower);

	void visitIntNot(IntNot intNot);

	void visitIntAnd(IntAnd intAnd);

	void visitIntOr(IntOr intOr);

	void visitIntXor(IntXor intXor);

	void visitIntIsEqual(IntIsEqual intIsEqual);

	void visitIntIsNotEqual(IntIsNotEqual intIsNotEqual);

	void visitIntIsGreater(IntIsGreater intIsGreater);

	void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual);

	/* Real Operations */

	void visitRealAdd(RealAdd realAdd);

	void visitRealSubtract(RealSubtract realSubtract);

	void visitRealMultiply(RealMultiply realMultiply);

	void visitRealDivide(RealDivide realDivide);

	void visitRealPower(RealPower realPower);

	void visitRealIsEqual(RealIsEqual realIsEqual);

	void visitRealIsNotEqual(RealIsNotEqual realIsNotEqual);

	void visitRealIsGreater(RealIsGreater realIsGreater);

	void visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual);

	void visitRealComposeComplex(RealComposeComplex realComposeComplex);

	/* Complex Operations */

	void visitComplexAdd(ComplexAdd complexAdd);

	void visitComplexSubtract(ComplexSubtract complexSubtract);

	void visitComplexMultiply(ComplexMultiply complexMultiply);

	void visitComplexDivide(ComplexDivide complexDivide);

	void visitComplexPower(ComplexPower complexPower);

	void visitComplexGetReal(ComplexGetReal complexGetReal);

	void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary);

	void visitComplexModulo(ComplexModulo complexModulo);

	/* Function Operations */

	void visitFunctionCall(FunctionCall functionCall);

	/* Pointer Operations */

	void visitPointerAllocate(PointerAllocate pointerAllocate);

	void visitPointerFree(PointerFree pointerFree);

	void visitPointerGet(PointerGet pointerGet);

	void visitPointerSet(PointerSet pointerSet);

	/* Control-Flow Operations */

	void visitIf(If anIf);

	void visitWhile(While aWhile);
}
