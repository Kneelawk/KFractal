package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;

import java.util.List;

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
		validIfTrue(dest.getType().isAssignableFrom(source.getType()), "Unable to assign incompatible types");
		return null;
	}

	@Override
	public Void visitReturn(Return aReturn) throws FractalIRException {
		checkState();
		returned = true;
		ValueInfo returnValue = aReturn.getReturnValue().accept(inputVisitor);
		validIfTrue(function.getReturnType().isAssignableFrom(returnValue.getType()),
				"Unable to return incompatible type");
		return null;
	}

	@Override
	public Void visitBoolNot(BoolNot boolNot) throws FractalIRException {
		checkState();
		validIfTrue(boolNot.getOutput().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
				"BoolNot output is not a bool");
		validIfTrue(ValueType.isBool(boolNot.getInput().accept(inputVisitor).getType()), "BoolNot input is not a bool");
		return null;
	}

	@Override
	public Void visitBoolAnd(BoolAnd boolAnd) throws FractalIRException {
		checkState();
		validIfTrue(boolAnd.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
				"BoolAnd result is not a bool");
		validIfTrue(ValueType.isBool(boolAnd.getLeft().accept(inputVisitor).getType()), "BoolAnd left is not a bool");
		validIfTrue(ValueType.isBool(boolAnd.getRight().accept(inputVisitor).getType()), "BoolAnd right is not a bool");
		return null;
	}

	@Override
	public Void visitBoolOr(BoolOr boolOr) throws FractalIRException {
		checkState();
		validIfTrue(boolOr.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
				"BoolOr result is not a bool");
		validIfTrue(ValueType.isBool(boolOr.getLeft().accept(inputVisitor).getType()), "BoolOr left is not a bool");
		validIfTrue(ValueType.isBool(boolOr.getLeft().accept(inputVisitor).getType()), "BoolOr right is not a bool");
		return null;
	}

	@Override
	public Void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalIRException {
		checkState();
		validIfTrue(boolIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
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
		validIfTrue(boolIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
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
		validIfTrue(intAdd.getSum().accept(outputVisitor).getType().isAssignableFrom(ValueType.INT),
				"IntAdd sum is not an Int");
		validIfTrue(ValueType.isInt(intAdd.getLeftAddend().accept(inputVisitor).getType()),
				"IntAdd left addend is not an Int");
		validIfTrue(ValueType.isInt(intAdd.getRightAddend().accept(inputVisitor).getType()),
				"IntAdd right addend is not an Int");
		return null;
	}

	@Override
	public Void visitIntSubtract(IntSubtract intSubtract) throws FractalIRException {
		checkState();
		validIfTrue(intSubtract.getDifference().accept(outputVisitor).getType().isAssignableFrom(ValueType.INT),
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
		validIfTrue(intMultiply.getProduct().accept(outputVisitor).getType().isAssignableFrom(ValueType.INT),
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
		validIfTrue(intDivide.getQuotient().accept(outputVisitor).getType().isAssignableFrom(ValueType.INT),
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
		validIfTrue(intModulo.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.INT),
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
		validIfTrue(intPower.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.INT),
				"IntPower result is not an Int");
		validIfTrue(ValueType.isInt(intPower.getBase().accept(inputVisitor).getType()), "IntPower base is not an Int");
		validIfTrue(ValueType.isInt(intPower.getExponent().accept(inputVisitor).getType()),
				"IntPower exponent is not an Int");
		return null;
	}

	@Override
	public Void visitIntNot(IntNot intNot) throws FractalIRException {
		checkState();
		validIfTrue(intNot.getOutput().accept(outputVisitor).getType().isAssignableFrom(ValueType.INT),
				"IntNot output is not an Int");
		validIfTrue(ValueType.isInt(intNot.getInput().accept(inputVisitor).getType()), "IntNot input is not an Int");
		return null;
	}

	@Override
	public Void visitIntAnd(IntAnd intAnd) throws FractalIRException {
		checkState();
		validIfTrue(intAnd.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.INT),
				"IntAnd result is not an Int");
		validIfTrue(ValueType.isInt(intAnd.getLeft().accept(inputVisitor).getType()), "IntAnd left is not an Int");
		validIfTrue(ValueType.isInt(intAnd.getRight().accept(inputVisitor).getType()), "IntAnd right is not an Int");
		return null;
	}

	@Override
	public Void visitIntOr(IntOr intOr) throws FractalIRException {
		checkState();
		validIfTrue(intOr.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.INT),
				"IntOr result is not an Int");
		validIfTrue(ValueType.isInt(intOr.getLeft().accept(inputVisitor).getType()), "IntOr left is not an Int");
		validIfTrue(ValueType.isInt(intOr.getRight().accept(inputVisitor).getType()), "IntOr right is not an Int");
		return null;
	}

	@Override
	public Void visitIntXor(IntXor intXor) throws FractalIRException {
		checkState();
		validIfTrue(intXor.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.INT),
				"IntXor result is not an Int");
		validIfTrue(ValueType.isInt(intXor.getLeft().accept(inputVisitor).getType()), "IntXor left is not an Int");
		validIfTrue(ValueType.isInt(intXor.getRight().accept(inputVisitor).getType()), "IntXor right is not an Int");
		return null;
	}

	@Override
	public Void visitIntIsEqual(IntIsEqual intIsEqual) throws FractalIRException {
		checkState();
		validIfTrue(intIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
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
		validIfTrue(intIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
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
		validIfTrue(intIsGreater.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
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
		validIfTrue(intIsGreaterOrEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
				"IntIsGreaterOrEqual result is not a Bool");
		validIfTrue(ValueType.isInt(intIsGreaterOrEqual.getSubject().accept(inputVisitor).getType()),
				"IntIsGreaterOrEqual subject is not an Int");
		validIfTrue(ValueType.isInt(intIsGreaterOrEqual.getBasis().accept(inputVisitor).getType()),
				"IntIsGreaterOrEqual is not an Int");
		return null;
	}

	@Override
	public Void visitRealAdd(RealAdd realAdd) throws FractalIRException {
		checkState();
		validIfTrue(realAdd.getSum().accept(outputVisitor).getType().isAssignableFrom(ValueType.REAL),
				"RealAdd sum is not a Real");
		validIfTrue(ValueType.isReal(realAdd.getLeftAddend().accept(inputVisitor).getType()),
				"RealAdd left addend is not a Real");
		validIfTrue(ValueType.isReal(realAdd.getRightAddend().accept(inputVisitor).getType()),
				"RealAdd right addend is not a Real");
		return null;
	}

	@Override
	public Void visitRealSubtract(RealSubtract realSubtract) throws FractalIRException {
		checkState();
		validIfTrue(realSubtract.getDifference().accept(outputVisitor).getType().isAssignableFrom(ValueType.REAL),
				"RealSubtract difference is not a Real");
		validIfTrue(ValueType.isReal(realSubtract.getMinuend().accept(inputVisitor).getType()),
				"RealSubtract minuend is not a Real");
		validIfTrue(ValueType.isReal(realSubtract.getSubtrahend().accept(inputVisitor).getType()),
				"RealSubtract subtract is not a Real");
		return null;
	}

	@Override
	public Void visitRealMultiply(RealMultiply realMultiply) throws FractalIRException {
		checkState();
		validIfTrue(realMultiply.getProduct().accept(outputVisitor).getType().isAssignableFrom(ValueType.REAL),
				"RealMultiply product is not a Real");
		validIfTrue(ValueType.isReal(realMultiply.getLeftFactor().accept(inputVisitor).getType()),
				"RealMultiply left factor is not a Real");
		validIfTrue(ValueType.isReal(realMultiply.getRightFactor().accept(inputVisitor).getType()),
				"RealMultiply right factor is not a Real");
		return null;
	}

	@Override
	public Void visitRealDivide(RealDivide realDivide) throws FractalIRException {
		checkState();
		validIfTrue(realDivide.getQuotient().accept(outputVisitor).getType().isAssignableFrom(ValueType.REAL),
				"RealDivide quotient is not a Real");
		validIfTrue(ValueType.isReal(realDivide.getDividend().accept(inputVisitor).getType()),
				"RealDivide dividend is not a Real");
		validIfTrue(ValueType.isReal(realDivide.getDivisor().accept(inputVisitor).getType()),
				"RealDivide divisor is not a Real");
		return null;
	}

	@Override
	public Void visitRealPower(RealPower realPower) throws FractalIRException {
		checkState();
		validIfTrue(realPower.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.REAL),
				"RealPower result is not a Real");
		validIfTrue(ValueType.isReal(realPower.getBase().accept(inputVisitor).getType()),
				"RealPower base is not a Real");
		validIfTrue(ValueType.isReal(realPower.getExponent().accept(inputVisitor).getType()),
				"RealPower exponent is not a Real");
		return null;
	}

	@Override
	public Void visitRealIsEqual(RealIsEqual realIsEqual) throws FractalIRException {
		checkState();
		validIfTrue(realIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
				"RealIsEqual result is not a Bool");
		validIfTrue(ValueType.isReal(realIsEqual.getLeft().accept(inputVisitor).getType()),
				"RealIsEqual left is not a Real");
		validIfTrue(ValueType.isReal(realIsEqual.getRight().accept(inputVisitor).getType()),
				"RealIsEqual right is not a Real");
		return null;
	}

	@Override
	public Void visitRealIsNotEqual(RealIsNotEqual realIsNotEqual) throws FractalIRException {
		checkState();
		validIfTrue(realIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
				"RealIsNotEqual result is not a Bool");
		validIfTrue(ValueType.isReal(realIsNotEqual.getLeft().accept(inputVisitor).getType()),
				"RealIsNotEqual left is not a Real");
		validIfTrue(ValueType.isReal(realIsNotEqual.getRight().accept(inputVisitor).getType()),
				"RealIsNotEqual right is not a Real");
		return null;
	}

	@Override
	public Void visitRealIsGreater(RealIsGreater realIsGreater) throws FractalIRException {
		checkState();
		validIfTrue(realIsGreater.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
				"RealIsGreater result is not a Bool");
		validIfTrue(ValueType.isReal(realIsGreater.getSubject().accept(inputVisitor).getType()),
				"RealIsGreater subject is not a Real");
		validIfTrue(ValueType.isReal(realIsGreater.getBasis().accept(inputVisitor).getType()),
				"RealIsGreater basis is not a Real");
		return null;
	}

	@Override
	public Void visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalIRException {
		checkState();
		validIfTrue(realIsGreaterOrEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
				"RealIsGreaterOrEqual result is not a Bool");
		validIfTrue(ValueType.isReal(realIsGreaterOrEqual.getSubject().accept(inputVisitor).getType()),
				"RealIsGreaterOrEqual subject is not a Real");
		validIfTrue(ValueType.isReal(realIsGreaterOrEqual.getBasis().accept(inputVisitor).getType()),
				"RealIsGreaterOrEqual basis is not a Real");
		return null;
	}

	@Override
	public Void visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalIRException {
		checkState();
		validIfTrue(realComposeComplex.getComplex().accept(outputVisitor).getType().isAssignableFrom(ValueType.COMPLEX),
				"RealComposeComplex complex is not a Complex");
		validIfTrue(ValueType.isReal(realComposeComplex.getReal().accept(inputVisitor).getType()),
				"RealComposeComplex real is not a Real");
		validIfTrue(ValueType.isReal(realComposeComplex.getImaginary().accept(inputVisitor).getType()),
				"RealComposeComplex imaginary is not a Real");
		return null;
	}

	@Override
	public Void visitComplexAdd(ComplexAdd complexAdd) throws FractalIRException {
		checkState();
		validIfTrue(complexAdd.getSum().accept(outputVisitor).getType().isAssignableFrom(ValueType.COMPLEX),
				"ComplexAdd sum is not a Complex");
		validIfTrue(ValueType.isComplex(complexAdd.getLeftAddend().accept(inputVisitor).getType()),
				"ComplexAdd left addend is not a Complex");
		validIfTrue(ValueType.isComplex(complexAdd.getRightAddend().accept(inputVisitor).getType()),
				"ComplexAdd right addend is not a Complex");
		return null;
	}

	@Override
	public Void visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalIRException {
		checkState();
		validIfTrue(complexSubtract.getDifference().accept(outputVisitor).getType().isAssignableFrom(ValueType.COMPLEX),
				"ComplexSubtract difference is not a Complex");
		validIfTrue(ValueType.isComplex(complexSubtract.getMinuend().accept(inputVisitor).getType()),
				"ComplexSubtract minuend is not a Complex");
		validIfTrue(ValueType.isComplex(complexSubtract.getSubtrahend().accept(inputVisitor).getType()),
				"ComplexSubtract subtrahend is not a Complex");
		return null;
	}

	@Override
	public Void visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalIRException {
		checkState();
		validIfTrue(complexMultiply.getProduct().accept(outputVisitor).getType().isAssignableFrom(ValueType.COMPLEX),
				"ComplexMultiply product is not a Complex");
		validIfTrue(ValueType.isComplex(complexMultiply.getLeftFactor().accept(inputVisitor).getType()),
				"ComplexMultiply left factor is not a Complex");
		validIfTrue(ValueType.isComplex(complexMultiply.getRightFactor().accept(inputVisitor).getType()),
				"ComplexMultiply right factor is not a Complex");
		return null;
	}

	@Override
	public Void visitComplexDivide(ComplexDivide complexDivide) throws FractalIRException {
		checkState();
		validIfTrue(complexDivide.getQuotient().accept(outputVisitor).getType().isAssignableFrom(ValueType.COMPLEX),
				"ComplexDivide quotient is not a Complex");
		validIfTrue(ValueType.isComplex(complexDivide.getDividend().accept(inputVisitor).getType()),
				"ComplexDivide dividend is not a Complex");
		validIfTrue(ValueType.isComplex(complexDivide.getDivisor().accept(inputVisitor).getType()),
				"ComplexDivide divisor is not a Complex");
		return null;
	}

	@Override
	public Void visitComplexPower(ComplexPower complexPower) throws FractalIRException {
		checkState();
		validIfTrue(complexPower.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.COMPLEX),
				"ComplexPower result is not a Complex");
		validIfTrue(ValueType.isComplex(complexPower.getBase().accept(inputVisitor).getType()),
				"ComplexPower base is not a Complex");
		validIfTrue(ValueType.isComplex(complexPower.getExponent().accept(inputVisitor).getType()),
				"ComplexPower exponent is not a Complex");
		return null;
	}

	@Override
	public Void visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalIRException {
		checkState();
		validIfTrue(complexGetReal.getReal().accept(outputVisitor).getType().isAssignableFrom(ValueType.REAL),
				"ComplexGetReal real is not a Real");
		validIfTrue(ValueType.isComplex(complexGetReal.getComplex().accept(inputVisitor).getType()),
				"ComplexGetReal complex is not a complex");
		return null;
	}

	@Override
	public Void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalIRException {
		checkState();
		validIfTrue(complexGetImaginary.getImaginary().accept(outputVisitor).getType().isAssignableFrom(ValueType.REAL),
				"ComplexGetReal imaginary is not a Real");
		validIfTrue(ValueType.isComplex(complexGetImaginary.getComplex().accept(inputVisitor).getType()),
				"ComplexGetReal complex is not a complex");
		return null;
	}

	@Override
	public Void visitComplexModulo(ComplexModulo complexModulo) throws FractalIRException {
		checkState();
		validIfTrue(complexModulo.getModulus().accept(outputVisitor).getType().isAssignableFrom(ValueType.REAL),
				"ComplexModulo modulus is not a Real");
		validIfTrue(ValueType.isComplex(complexModulo.getComplex().accept(inputVisitor).getType()),
				"ComplexGetReal complex is not a complex");
		return null;
	}

	@Override
	public Void visitFunctionCall(FunctionCall functionCall) throws FractalIRException {
		checkState();

		// check the function argument
		ValueInfo functionArg = functionCall.getFunction().accept(inputVisitor);
		validIfTrue(ValueType.isFunction(functionArg.getType()), "FunctionCall function argument is not a Function");
		ValueType.FunctionType functionType = ValueType.toFunction(functionArg.getType());

		// check the return type
		validIfTrue(
				functionCall.getResult().accept(outputVisitor).getType().isAssignableFrom(functionType.getReturnType()),
				"FunctionCall result type is incompatible with the return type of the function being called");

		// check the function's argument types
		List<ValueType> targetArguments = functionType.getArgumentTypes();
		List<IInstructionInput> instructionArguments = functionCall.getArguments();
		int targetArgumentsSize = targetArguments.size();
		int instructionArgumentsSize = instructionArguments.size();
		int size = Math.max(targetArgumentsSize, instructionArgumentsSize);
		for (int i = 0; i < size; i++) {
			if (i >= targetArgumentsSize) {
				throw new FractalIRValidationException("FunctionCall has extra arguments: " +
						instructionArguments.subList(i, instructionArgumentsSize));
			}
			if (i >= instructionArgumentsSize) {
				throw new FractalIRValidationException(
						"FunctionCall is missing arguments: " + targetArguments.subList(i, targetArgumentsSize));
			}
			ValueType targetArgument = targetArguments.get(i);
			IInstructionInput instructionArgument = instructionArguments.get(i);
			validIfTrue(targetArgument.isAssignableFrom(instructionArgument.accept(inputVisitor).getType()),
					"Function defines argument: " + targetArgument + " but FunctionCall instruction supplies: " +
							instructionArgument);
		}
		return null;
	}

	@Override
	public Void visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalIRException {
		checkState();
		// this feels mildly hacky to me
		validIfTrue(pointerAllocate.getPointer().accept(outputVisitor).getType()
						.isAssignableFrom(ValueType.POINTER(ValueType.VOID)),
				"PointerAllocate pointer argument is not a Pointer");
		return null;
	}

	@Override
	public Void visitPointerFree(PointerFree pointerFree) throws FractalIRException {
		checkState();
		validIfTrue(ValueType.isPointer(pointerFree.getPointer().accept(inputVisitor).getType()),
				"PointerFree pointer argument is not a Pointer");
		return null;
	}

	@Override
	public Void visitPointerGet(PointerGet pointerGet) throws FractalIRException {
		checkState();
		ValueInfo pointerArg = pointerGet.getPointer().accept(inputVisitor);
		validIfTrue(ValueType.isPointer(pointerArg.getType()), "PointerGet pointer argument is not a Pointer");
		ValueType.PointerType pointerType = ValueType.toPointer(pointerArg.getType());
		validIfTrue(pointerGet.getData().accept(outputVisitor).getType().isAssignableFrom(pointerType.getPointerType()),
				"PointerGet pointer type and data type are incompatible");
		return null;
	}

	@Override
	public Void visitPointerSet(PointerSet pointerSet) throws FractalIRException {
		checkState();
		ValueInfo pointerArg = pointerSet.getPointer().accept(inputVisitor);
		validIfTrue(ValueType.isPointer(pointerArg.getType()), "PointerSet pointer argument is not a Pointer");
		ValueType.PointerType pointerType = ValueType.toPointer(pointerArg.getType());
		validIfTrue(pointerType.getPointerType().isAssignableFrom(pointerSet.getData().accept(inputVisitor).getType()),
				"PointerSet pointer type and data type are incompatible");
		validIfTrue(!ValueType.isVoid(pointerType.getPointerType()),
				"PointerSet cannot set the value of the null-pointer");
		return null;
	}

	@Override
	public Void visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalIRException {
		checkState();
		validIfTrue(pointerIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
				"PointerIsEqual result is not a Bool");
		validIfTrue(ValueType.isPointer(pointerIsEqual.getLeft().accept(inputVisitor).getType()),
				"PointerIsEqual left is not a Pointer");
		validIfTrue(ValueType.isPointer(pointerIsEqual.getRight().accept(inputVisitor).getType()),
				"PointerIsEqual right is not a Pointer");
		return null;
	}

	@Override
	public Void visitPointerIsNotEqual(PointerIsNotEqual pointerIsNotEqual) throws FractalIRException {
		checkState();
		validIfTrue(pointerIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueType.BOOL),
				"PointerIsNotEqual result is not a Bool");
		validIfTrue(ValueType.isPointer(pointerIsNotEqual.getLeft().accept(inputVisitor).getType()),
				"PointerIsNotEqual left is not a Pointer");
		validIfTrue(ValueType.isPointer(pointerIsNotEqual.getRight().accept(inputVisitor).getType()),
				"PointerIsNotEqual right is not a Pointer");
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
