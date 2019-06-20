package com.kneelawk.kfractal.generator.api.ir.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;

public class ValidatingInstructionVisitor implements IInstructionVisitor<Void> {
	private Program program;
	private FunctionDefinition function;

	public ValidatingInstructionVisitor(Program program, FunctionDefinition function) {
		this.program = program;
		this.function = function;
	}

	@Override
	public Void visitAssign(Assign assign) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitReturn(Return aReturn) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitBoolNot(BoolNot boolNot) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitBoolAnd(BoolAnd boolAnd) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitBoolOr(BoolOr boolOr) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntAdd(IntAdd intAdd) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntSubtract(IntSubtract intSubtract) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntMultiply(IntMultiply intMultiply) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntDivide(IntDivide intDivide) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntModulo(IntModulo intModulo) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntPower(IntPower intPower) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntNot(IntNot intNot) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntAnd(IntAnd intAnd) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntOr(IntOr intOr) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntXor(IntXor intXor) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntIsEqual(IntIsEqual intIsEqual) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntIsNotEqual(IntIsNotEqual intIsNotEqual) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntIsGreater(IntIsGreater intIsGreater) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitRealAdd(RealAdd realAdd) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitRealSubtract(RealSubtract realSubtract) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitRealMultiply(RealMultiply realMultiply) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitRealDivide(RealDivide realDivide) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitRealPower(RealPower realPower) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitRealIsEqual(RealIsEqual realIsEqual) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitRealIsNotEqual(RealIsNotEqual realIsNotEqual) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitRealIsGreater(RealIsGreater realIsGreater) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitComplexAdd(ComplexAdd complexAdd) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitComplexDivide(ComplexDivide complexDivide) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitComplexPower(ComplexPower complexPower) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitComplexModulo(ComplexModulo complexModulo) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitFunctionCall(FunctionCall functionCall) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitPointerFree(PointerFree pointerFree) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitPointerGet(PointerGet pointerGet) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitPointerSet(PointerSet pointerSet) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitIf(If anIf) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitWhile(While aWhile) throws FractalIRException {
		return null;
	}
}
