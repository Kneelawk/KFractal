package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;

/**
 * This interface is implemented by anything that wants to be able to traverse a Fractal IR instruction tree.
 */
public interface IInstructionVisitor {
	/* General Purpose Operations */

	void visitAssign(Assign assign) throws FractalIRException;

	void visitReturn(Return aReturn) throws FractalIRException;

	/* Bool Operations */

	void visitBoolNot(BoolNot boolNot) throws FractalIRException;

	void visitBoolAnd(BoolAnd boolAnd) throws FractalIRException;

	void visitBoolOr(BoolOr boolOr) throws FractalIRException;

	void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalIRException;

	void visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalIRException;

	/* Int Operations */

	void visitIntAdd(IntAdd intAdd) throws FractalIRException;

	void visitIntSubtract(IntSubtract intSubtract) throws FractalIRException;

	void visitIntMultiply(IntMultiply intMultiply) throws FractalIRException;

	void visitIntDivide(IntDivide intDivide) throws FractalIRException;

	void visitIntModulo(IntModulo intModulo) throws FractalIRException;

	void visitIntPower(IntPower intPower) throws FractalIRException;

	void visitIntNot(IntNot intNot) throws FractalIRException;

	void visitIntAnd(IntAnd intAnd) throws FractalIRException;

	void visitIntOr(IntOr intOr) throws FractalIRException;

	void visitIntXor(IntXor intXor) throws FractalIRException;

	void visitIntIsEqual(IntIsEqual intIsEqual) throws FractalIRException;

	void visitIntIsNotEqual(IntIsNotEqual intIsNotEqual) throws FractalIRException;

	void visitIntIsGreater(IntIsGreater intIsGreater) throws FractalIRException;

	void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalIRException;

	/* Real Operations */

	void visitRealAdd(RealAdd realAdd) throws FractalIRException;

	void visitRealSubtract(RealSubtract realSubtract) throws FractalIRException;

	void visitRealMultiply(RealMultiply realMultiply) throws FractalIRException;

	void visitRealDivide(RealDivide realDivide) throws FractalIRException;

	void visitRealPower(RealPower realPower) throws FractalIRException;

	void visitRealIsEqual(RealIsEqual realIsEqual) throws FractalIRException;

	void visitRealIsNotEqual(RealIsNotEqual realIsNotEqual) throws FractalIRException;

	void visitRealIsGreater(RealIsGreater realIsGreater) throws FractalIRException;

	void visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalIRException;

	void visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalIRException;

	/* Complex Operations */

	void visitComplexAdd(ComplexAdd complexAdd) throws FractalIRException;

	void visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalIRException;

	void visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalIRException;

	void visitComplexDivide(ComplexDivide complexDivide) throws FractalIRException;

	void visitComplexPower(ComplexPower complexPower) throws FractalIRException;

	void visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalIRException;

	void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalIRException;

	void visitComplexModulo(ComplexModulo complexModulo) throws FractalIRException;

	/* Function Operations */

	void visitFunctionCall(FunctionCall functionCall) throws FractalIRException;

	/* Pointer Operations */

	void visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalIRException;

	void visitPointerFree(PointerFree pointerFree) throws FractalIRException;

	void visitPointerGet(PointerGet pointerGet) throws FractalIRException;

	void visitPointerSet(PointerSet pointerSet) throws FractalIRException;

	/* Control-Flow Operations */

	void visitIf(If anIf) throws FractalIRException;

	void visitWhile(While aWhile) throws FractalIRException;
}
