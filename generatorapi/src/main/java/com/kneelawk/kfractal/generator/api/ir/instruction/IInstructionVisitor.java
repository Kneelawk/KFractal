package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;

/**
 * This interface is implemented by anything that wants to be able to traverse a Fractal IR instruction tree.
 */
public interface IInstructionVisitor<R> {
	/* General Purpose Operations */

	R visitAssign(Assign assign) throws FractalIRException;

	R visitReturn(Return aReturn) throws FractalIRException;

	/* Bool Operations */

	R visitBoolNot(BoolNot boolNot) throws FractalIRException;

	R visitBoolAnd(BoolAnd boolAnd) throws FractalIRException;

	R visitBoolOr(BoolOr boolOr) throws FractalIRException;

	R visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalIRException;

	R visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalIRException;

	/* Int Operations */

	R visitIntAdd(IntAdd intAdd) throws FractalIRException;

	R visitIntSubtract(IntSubtract intSubtract) throws FractalIRException;

	R visitIntMultiply(IntMultiply intMultiply) throws FractalIRException;

	R visitIntDivide(IntDivide intDivide) throws FractalIRException;

	R visitIntModulo(IntModulo intModulo) throws FractalIRException;

	R visitIntPower(IntPower intPower) throws FractalIRException;

	R visitIntNot(IntNot intNot) throws FractalIRException;

	R visitIntAnd(IntAnd intAnd) throws FractalIRException;

	R visitIntOr(IntOr intOr) throws FractalIRException;

	R visitIntXor(IntXor intXor) throws FractalIRException;

	R visitIntIsEqual(IntIsEqual intIsEqual) throws FractalIRException;

	R visitIntIsNotEqual(IntIsNotEqual intIsNotEqual) throws FractalIRException;

	R visitIntIsGreater(IntIsGreater intIsGreater) throws FractalIRException;

	R visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalIRException;

	/* Real Operations */

	R visitRealAdd(RealAdd realAdd) throws FractalIRException;

	R visitRealSubtract(RealSubtract realSubtract) throws FractalIRException;

	R visitRealMultiply(RealMultiply realMultiply) throws FractalIRException;

	R visitRealDivide(RealDivide realDivide) throws FractalIRException;

	R visitRealPower(RealPower realPower) throws FractalIRException;

	R visitRealIsEqual(RealIsEqual realIsEqual) throws FractalIRException;

	R visitRealIsNotEqual(RealIsNotEqual realIsNotEqual) throws FractalIRException;

	R visitRealIsGreater(RealIsGreater realIsGreater) throws FractalIRException;

	R visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalIRException;

	R visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalIRException;

	/* Complex Operations */

	R visitComplexAdd(ComplexAdd complexAdd) throws FractalIRException;

	R visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalIRException;

	R visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalIRException;

	R visitComplexDivide(ComplexDivide complexDivide) throws FractalIRException;

	R visitComplexPower(ComplexPower complexPower) throws FractalIRException;

	R visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalIRException;

	R visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalIRException;

	R visitComplexModulo(ComplexModulo complexModulo) throws FractalIRException;

	/* Function Operations */

	R visitFunctionCall(FunctionCall functionCall) throws FractalIRException;

	/* Pointer Operations */

	R visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalIRException;

	R visitPointerFree(PointerFree pointerFree) throws FractalIRException;

	R visitPointerGet(PointerGet pointerGet) throws FractalIRException;

	R visitPointerSet(PointerSet pointerSet) throws FractalIRException;

	R visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalIRException;

	R visitPointerIsNotEqual(PointerIsNotEqual pointerIsNotEqual) throws FractalIRException;

	/* Control-Flow Operations */

	R visitIf(If anIf) throws FractalIRException;

	R visitWhile(While aWhile) throws FractalIRException;
}
