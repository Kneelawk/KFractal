package com.kneelawk.kfractal.generator.api.ir.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;

public class ValidatingInstructionVisitor implements IInstructionVisitor {
	private Program program;
	private FunctionDefinition function;

	public ValidatingInstructionVisitor(Program program, FunctionDefinition function) {
		this.program = program;
		this.function = function;
	}

	@Override
	public void visitAssign(Assign assign) throws FractalIRException {

	}

	@Override
	public void visitReturn(Return aReturn) throws FractalIRException {

	}

	@Override
	public void visitBoolNot(BoolNot boolNot) throws FractalIRException {

	}

	@Override
	public void visitBoolAnd(BoolAnd boolAnd) throws FractalIRException {

	}

	@Override
	public void visitBoolOr(BoolOr boolOr) throws FractalIRException {

	}

	@Override
	public void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalIRException {

	}

	@Override
	public void visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalIRException {

	}

	@Override
	public void visitIntAdd(IntAdd intAdd) throws FractalIRException {

	}

	@Override
	public void visitIntSubtract(IntSubtract intSubtract) throws FractalIRException {

	}

	@Override
	public void visitIntMultiply(IntMultiply intMultiply) throws FractalIRException {

	}

	@Override
	public void visitIntDivide(IntDivide intDivide) throws FractalIRException {

	}

	@Override
	public void visitIntModulo(IntModulo intModulo) throws FractalIRException {

	}

	@Override
	public void visitIntPower(IntPower intPower) throws FractalIRException {

	}

	@Override
	public void visitIntNot(IntNot intNot) throws FractalIRException {

	}

	@Override
	public void visitIntAnd(IntAnd intAnd) throws FractalIRException {

	}

	@Override
	public void visitIntOr(IntOr intOr) throws FractalIRException {

	}

	@Override
	public void visitIntXor(IntXor intXor) throws FractalIRException {

	}

	@Override
	public void visitIntIsEqual(IntIsEqual intIsEqual) throws FractalIRException {

	}

	@Override
	public void visitIntIsNotEqual(IntIsNotEqual intIsNotEqual) throws FractalIRException {

	}

	@Override
	public void visitIntIsGreater(IntIsGreater intIsGreater) throws FractalIRException {

	}

	@Override
	public void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalIRException {

	}

	@Override
	public void visitRealAdd(RealAdd realAdd) throws FractalIRException {

	}

	@Override
	public void visitRealSubtract(RealSubtract realSubtract) throws FractalIRException {

	}

	@Override
	public void visitRealMultiply(RealMultiply realMultiply) throws FractalIRException {

	}

	@Override
	public void visitRealDivide(RealDivide realDivide) throws FractalIRException {

	}

	@Override
	public void visitRealPower(RealPower realPower) throws FractalIRException {

	}

	@Override
	public void visitRealIsEqual(RealIsEqual realIsEqual) throws FractalIRException {

	}

	@Override
	public void visitRealIsNotEqual(RealIsNotEqual realIsNotEqual) throws FractalIRException {

	}

	@Override
	public void visitRealIsGreater(RealIsGreater realIsGreater) throws FractalIRException {

	}

	@Override
	public void visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalIRException {

	}

	@Override
	public void visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalIRException {

	}

	@Override
	public void visitComplexAdd(ComplexAdd complexAdd) throws FractalIRException {

	}

	@Override
	public void visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalIRException {

	}

	@Override
	public void visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalIRException {

	}

	@Override
	public void visitComplexDivide(ComplexDivide complexDivide) throws FractalIRException {

	}

	@Override
	public void visitComplexPower(ComplexPower complexPower) throws FractalIRException {

	}

	@Override
	public void visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalIRException {

	}

	@Override
	public void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalIRException {

	}

	@Override
	public void visitComplexModulo(ComplexModulo complexModulo) throws FractalIRException {

	}

	@Override
	public void visitFunctionCall(FunctionCall functionCall) throws FractalIRException {

	}

	@Override
	public void visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalIRException {

	}

	@Override
	public void visitPointerFree(PointerFree pointerFree) throws FractalIRException {

	}

	@Override
	public void visitPointerGet(PointerGet pointerGet) throws FractalIRException {

	}

	@Override
	public void visitPointerSet(PointerSet pointerSet) throws FractalIRException {

	}

	@Override
	public void visitIf(If anIf) throws FractalIRException {

	}

	@Override
	public void visitWhile(While aWhile) throws FractalIRException {

	}
}
