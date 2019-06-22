package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;

public class ValidatingInstructionVisitor implements IInstructionVisitor<Void> {
	private Program program;
	private FunctionDefinition function;
	private ValidatingInstructionInputVisitor inputVisitor;
	private ValidatingInstructionOutputVisitor outputVisitor;

	private boolean returned = false;

	public ValidatingInstructionVisitor(Program program, FunctionDefinition function) {
		this.program = program;
		this.function = function;
		inputVisitor = new ValidatingInstructionInputVisitor(program, function);
		outputVisitor = new ValidatingInstructionOutputVisitor(program, function);
	}

	private void checkState() throws FractalIRException {
		if (returned) {
			throw new FractalIRValidationException("This set of instructions has already reached a return statement");
		}
	}

	private void validIfTrue(boolean condition, String message) throws FractalIRException {
		if (!condition) {
			throw new FractalIRValidationException(message);
		}
	}

	@Override
	public Void visitAssign(Assign assign) throws FractalIRException {
		checkState();
		ValueInfo dest = assign.getDest().accept(outputVisitor);
		ValueInfo source = assign.getSource().accept(inputVisitor);
		validIfTrue(dest.getType().equals(source.getType()), "Unable to assign incompatible types");
		return null;
	}

	@Override
	public Void visitReturn(Return aReturn) throws FractalIRException {
		checkState();
		returned = true;
		ValueInfo returnValue = aReturn.getReturnValue().accept(inputVisitor);
		validIfTrue(function.getReturnType().equals(returnValue.getType()), "Unable to return incompatible type");
		return null;
	}

	@Override
	public Void visitBoolNot(BoolNot boolNot) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isBool(boolNot.getOutput().accept(outputVisitor).getType()),
				"BoolNot output is not a bool");
		validIfTrue(ValueType.isBool(boolNot.getInput().accept(inputVisitor).getType()), "BoolNot input is not a bool");
		return null;
	}

	@Override
	public Void visitBoolAnd(BoolAnd boolAnd) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isBool(boolAnd.getResult().accept(outputVisitor).getType()),
				"BoolAnd result is not a bool");
		validIfTrue(ValueType.isBool(boolAnd.getLeft().accept(inputVisitor).getType()), "BoolAnd left is not a bool");
		validIfTrue(ValueType.isBool(boolAnd.getRight().accept(inputVisitor).getType()), "BoolAnd right is not a bool");
		return null;
	}

	@Override
	public Void visitBoolOr(BoolOr boolOr) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isBool(boolOr.getResult().accept(outputVisitor).getType()),
				"BoolOr result is not a bool");
		validIfTrue(ValueType.isBool(boolOr.getLeft().accept(inputVisitor).getType()), "BoolOr left is not a bool");
		validIfTrue(ValueType.isBool(boolOr.getLeft().accept(inputVisitor).getType()), "BoolOr right is not a bool");
		return null;
	}

	@Override
	public Void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isBool(boolIsEqual.getResult().accept(outputVisitor).getType()),
				"BoolIsEqual result is not a bool");
		validIfTrue(ValueType.isBool(boolIsEqual.getLeft().accept(inputVisitor).getType()),
				"BoolIsEqual left is not a bool");
		validIfTrue(ValueType.isBool(boolIsEqual.getRight().accept(inputVisitor).getType()),
				"BoolIsEqual right is not a bool");
		return null;
	}

	@Override
	public Void visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isBool(boolIsNotEqual.getResult().accept(outputVisitor).getType()),
				"BoolIsNotEqual result is not a bool");
		validIfTrue(ValueType.isBool(boolIsNotEqual.getLeft().accept(inputVisitor).getType()),
				"BoolIsNotEqual left is not a bool");
		validIfTrue(ValueType.isBool(boolIsNotEqual.getRight().accept(inputVisitor).getType()),
				"BoolIsNotEqual right is not a bool");
		return null;
	}

	@Override
	public Void visitIntAdd(IntAdd intAdd) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isInt(intAdd.getSum().accept(outputVisitor).getType()), "IntAdd sum is not an Int");
		validIfTrue(ValueType.isInt(intAdd.getLeftAddend().accept(inputVisitor).getType()),
				"IntAdd left addend is not an Int");
		validIfTrue(ValueType.isInt(intAdd.getRightAddend().accept(inputVisitor).getType()),
				"IntAdd right addend is not an Int");
		return null;
	}

	@Override
	public Void visitIntSubtract(IntSubtract intSubtract) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isInt(intSubtract.getDifference().accept(outputVisitor).getType()),
				"IntSubtract difference is not an Int");
		validIfTrue(ValueType.isInt(intSubtract.getMinuend().accept(inputVisitor).getType()),
				"IntSubtract minuend is not an Int");
		validIfTrue(ValueType.isInt(intSubtract.getSubtrahend().accept(inputVisitor).getType()),
				"IntSubtract subtrahend is not an Int");
		return null;
	}

	@Override
	public Void visitIntMultiply(IntMultiply intMultiply) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isInt(intMultiply.getProduct().accept(outputVisitor).getType()),
				"IntMultiply product is not an Int");
		validIfTrue(ValueType.isInt(intMultiply.getLeftFactor().accept(inputVisitor).getType()),
				"IntMultiply left factor is not an Int");
		validIfTrue(ValueType.isInt(intMultiply.getRightFactor().accept(inputVisitor).getType()),
				"IntMultiply right factor is not an Int");
		return null;
	}

	@Override
	public Void visitIntDivide(IntDivide intDivide) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isInt(intDivide.getQuotient().accept(outputVisitor).getType()),
				"IntDivide quotient is not an Int");
		validIfTrue(ValueType.isInt(intDivide.getDividend().accept(inputVisitor).getType()),
				"IntDivide dividend is not an Int");
		validIfTrue(ValueType.isInt(intDivide.getDivisor().accept(inputVisitor).getType()),
				"IntDivide divisor is not an Int");
		return null;
	}

	@Override
	public Void visitIntModulo(IntModulo intModulo) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isInt(intModulo.getResult().accept(outputVisitor).getType()),
				"IntModulo result is not an Int");
		validIfTrue(ValueType.isInt(intModulo.getLeft().accept(inputVisitor).getType()),
				"IntModulo left is not an Int");
		validIfTrue(ValueType.isInt(intModulo.getRight().accept(inputVisitor).getType()),
				"IntModulo right is not an Int");
		return null;
	}

	@Override
	public Void visitIntPower(IntPower intPower) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isInt(intPower.getResult().accept(outputVisitor).getType()),
				"IntPower result is not an Int");
		validIfTrue(ValueType.isInt(intPower.getBase().accept(inputVisitor).getType()), "IntPower base is not an Int");
		validIfTrue(ValueType.isInt(intPower.getExponent().accept(inputVisitor).getType()),
				"IntPower exponent is not an Int");
		return null;
	}

	@Override
	public Void visitIntNot(IntNot intNot) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isInt(intNot.getOutput().accept(outputVisitor).getType()), "IntNot output is not an Int");
		validIfTrue(ValueType.isInt(intNot.getInput().accept(inputVisitor).getType()), "IntNot input is not an Int");
		return null;
	}

	@Override
	public Void visitIntAnd(IntAnd intAnd) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isInt(intAnd.getResult().accept(outputVisitor).getType()), "IntAnd result is not an Int");
		validIfTrue(ValueType.isInt(intAnd.getLeft().accept(inputVisitor).getType()), "IntAnd left is not an Int");
		validIfTrue(ValueType.isInt(intAnd.getRight().accept(inputVisitor).getType()), "IntAnd right is not an Int");
		return null;
	}

	@Override
	public Void visitIntOr(IntOr intOr) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isInt(intOr.getResult().accept(outputVisitor).getType()), "IntOr result is not an Int");
		validIfTrue(ValueType.isInt(intOr.getLeft().accept(inputVisitor).getType()), "IntOr left is not an Int");
		validIfTrue(ValueType.isInt(intOr.getRight().accept(inputVisitor).getType()), "IntOr right is not an Int");
		return null;
	}

	@Override
	public Void visitIntXor(IntXor intXor) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isInt(intXor.getResult().accept(outputVisitor).getType()), "IntXor result is not an Int");
		validIfTrue(ValueType.isInt(intXor.getLeft().accept(inputVisitor).getType()), "IntXor left is not an Int");
		validIfTrue(ValueType.isInt(intXor.getRight().accept(inputVisitor).getType()), "IntXor right is not an Int");
		return null;
	}

	@Override
	public Void visitIntIsEqual(IntIsEqual intIsEqual) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isBool(intIsEqual.getResult().accept(outputVisitor).getType()),
				"IntIsEqual result is not a Bool");
		validIfTrue(ValueType.isInt(intIsEqual.getLeft().accept(inputVisitor).getType()),
				"IntIsEqual left is not an Int");
		validIfTrue(ValueType.isInt(intIsEqual.getRight().accept(inputVisitor).getType()),
				"IntIsEqual right is not an Int");
		return null;
	}

	@Override
	public Void visitIntIsNotEqual(IntIsNotEqual intIsNotEqual) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isBool(intIsNotEqual.getResult().accept(outputVisitor).getType()),
				"IntIsNotEqual result is not a Bool");
		validIfTrue(ValueType.isInt(intIsNotEqual.getLeft().accept(inputVisitor).getType()),
				"IntIsNotEqual left is not an Int");
		validIfTrue(ValueType.isInt(intIsNotEqual.getRight().accept(inputVisitor).getType()),
				"IntIsNotEqual right is not an Int");
		return null;
	}

	@Override
	public Void visitIntIsGreater(IntIsGreater intIsGreater) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isBool(intIsGreater.getResult().accept(outputVisitor).getType()),
				"IntIsGreater result is not a Bool");
		validIfTrue(ValueType.isInt(intIsGreater.getSubject().accept(inputVisitor).getType()),
				"IntIsGreater subject is not an Int");
		validIfTrue(ValueType.isInt(intIsGreater.getBasis().accept(inputVisitor).getType()),
				"IntIsGreater basis is not an Int");
		return null;
	}

	@Override
	public Void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isBool(intIsGreaterOrEqual.getResult().accept(outputVisitor).getType()),
				"IntIsGreaterOrEqual result is not a Bool");
		validIfTrue(ValueType.isInt(intIsGreaterOrEqual.getSubject().accept(inputVisitor).getType()),
				"IntIsGreaterOrEqual subject is not an Int");
		validIfTrue(ValueType.isInt(intIsGreaterOrEqual.getBasis().accept(inputVisitor).getType()),
				"IntIsGreaterOrEqual is not an Int");
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
	public Void visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalIRException {
		return null;
	}

	@Override
	public Void visitPointerIsNotEqual(PointerIsNotEqual pointerIsNotEqual) throws FractalIRException {
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
