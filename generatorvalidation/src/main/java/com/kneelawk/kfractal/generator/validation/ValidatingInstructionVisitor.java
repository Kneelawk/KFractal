package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;

import java.util.List;
import java.util.Map;

public class ValidatingInstructionVisitor implements IInstructionVisitor<Void> {
	private Map<String, FunctionDefinition> functions;
	private Map<String, ValueInfo> variables;
	private ValueType returnType;

	private ValidatingInstructionInputVisitor inputVisitor;
	private ValidatingInstructionOutputVisitor outputVisitor;

	private boolean returned = false;

	public ValidatingInstructionVisitor(
			Map<String, FunctionDefinition> functions,
			Map<String, ValueInfo> variables, ValueType returnType) {
		this.functions = functions;
		this.variables = variables;
		this.returnType = returnType;
		inputVisitor = new ValidatingInstructionInputVisitor(functions, variables);
		outputVisitor = new ValidatingInstructionOutputVisitor(variables);
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

	public boolean isReturned() {
		return returned;
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
		validIfTrue(returnType.isAssignableFrom(returnValue.getType()),
				"Unable to return incompatible type");
		return null;
	}

	@Override
	public Void visitBoolNot(BoolNot boolNot) throws FractalIRException {
		checkState();
		validIfTrue(boolNot.getOutput().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"BoolNot output is not a bool");
		validIfTrue(ValueTypes.isBool(boolNot.getInput().accept(inputVisitor).getType()),
				"BoolNot input is not a bool");
		return null;
	}

	@Override
	public Void visitBoolAnd(BoolAnd boolAnd) throws FractalIRException {
		checkState();
		validIfTrue(boolAnd.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"BoolAnd result is not a bool");
		validIfTrue(ValueTypes.isBool(boolAnd.getLeft().accept(inputVisitor).getType()), "BoolAnd left is not a bool");
		validIfTrue(ValueTypes.isBool(boolAnd.getRight().accept(inputVisitor).getType()),
				"BoolAnd right is not a bool");
		return null;
	}

	@Override
	public Void visitBoolOr(BoolOr boolOr) throws FractalIRException {
		checkState();
		validIfTrue(boolOr.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"BoolOr result is not a bool");
		validIfTrue(ValueTypes.isBool(boolOr.getLeft().accept(inputVisitor).getType()), "BoolOr left is not a bool");
		validIfTrue(ValueTypes.isBool(boolOr.getLeft().accept(inputVisitor).getType()), "BoolOr right is not a bool");
		return null;
	}

	@Override
	public Void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalIRException {
		checkState();
		validIfTrue(boolIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"BoolIsEqual result is not a bool");
		validIfTrue(ValueTypes.isBool(boolIsEqual.getLeft().accept(inputVisitor).getType()),
				"BoolIsEqual left is not a bool");
		validIfTrue(ValueTypes.isBool(boolIsEqual.getRight().accept(inputVisitor).getType()),
				"BoolIsEqual right is not a bool");
		return null;
	}

	@Override
	public Void visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalIRException {
		checkState();
		validIfTrue(boolIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"BoolIsNotEqual result is not a bool");
		validIfTrue(ValueTypes.isBool(boolIsNotEqual.getLeft().accept(inputVisitor).getType()),
				"BoolIsNotEqual left is not a bool");
		validIfTrue(ValueTypes.isBool(boolIsNotEqual.getRight().accept(inputVisitor).getType()),
				"BoolIsNotEqual right is not a bool");
		return null;
	}

	@Override
	public Void visitIntAdd(IntAdd intAdd) throws FractalIRException {
		checkState();
		validIfTrue(intAdd.getSum().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
				"IntAdd sum is not an Int");
		validIfTrue(ValueTypes.isInt(intAdd.getLeftAddend().accept(inputVisitor).getType()),
				"IntAdd left addend is not an Int");
		validIfTrue(ValueTypes.isInt(intAdd.getRightAddend().accept(inputVisitor).getType()),
				"IntAdd right addend is not an Int");
		return null;
	}

	@Override
	public Void visitIntSubtract(IntSubtract intSubtract) throws FractalIRException {
		checkState();
		validIfTrue(intSubtract.getDifference().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
				"IntSubtract difference is not an Int");
		validIfTrue(ValueTypes.isInt(intSubtract.getMinuend().accept(inputVisitor).getType()),
				"IntSubtract minuend is not an Int");
		validIfTrue(ValueTypes.isInt(intSubtract.getSubtrahend().accept(inputVisitor).getType()),
				"IntSubtract subtrahend is not an Int");
		return null;
	}

	@Override
	public Void visitIntMultiply(IntMultiply intMultiply) throws FractalIRException {
		checkState();
		validIfTrue(intMultiply.getProduct().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
				"IntMultiply product is not an Int");
		validIfTrue(ValueTypes.isInt(intMultiply.getLeftFactor().accept(inputVisitor).getType()),
				"IntMultiply left factor is not an Int");
		validIfTrue(ValueTypes.isInt(intMultiply.getRightFactor().accept(inputVisitor).getType()),
				"IntMultiply right factor is not an Int");
		return null;
	}

	@Override
	public Void visitIntDivide(IntDivide intDivide) throws FractalIRException {
		checkState();
		validIfTrue(intDivide.getQuotient().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
				"IntDivide quotient is not an Int");
		validIfTrue(ValueTypes.isInt(intDivide.getDividend().accept(inputVisitor).getType()),
				"IntDivide dividend is not an Int");
		validIfTrue(ValueTypes.isInt(intDivide.getDivisor().accept(inputVisitor).getType()),
				"IntDivide divisor is not an Int");
		return null;
	}

	@Override
	public Void visitIntModulo(IntModulo intModulo) throws FractalIRException {
		checkState();
		validIfTrue(intModulo.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
				"IntModulo result is not an Int");
		validIfTrue(ValueTypes.isInt(intModulo.getLeft().accept(inputVisitor).getType()),
				"IntModulo left is not an Int");
		validIfTrue(ValueTypes.isInt(intModulo.getRight().accept(inputVisitor).getType()),
				"IntModulo right is not an Int");
		return null;
	}

	@Override
	public Void visitIntPower(IntPower intPower) throws FractalIRException {
		checkState();
		validIfTrue(intPower.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
				"IntPower result is not an Int");
		validIfTrue(ValueTypes.isInt(intPower.getBase().accept(inputVisitor).getType()), "IntPower base is not an Int");
		validIfTrue(ValueTypes.isInt(intPower.getExponent().accept(inputVisitor).getType()),
				"IntPower exponent is not an Int");
		return null;
	}

	@Override
	public Void visitIntNot(IntNot intNot) throws FractalIRException {
		checkState();
		validIfTrue(intNot.getOutput().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
				"IntNot output is not an Int");
		validIfTrue(ValueTypes.isInt(intNot.getInput().accept(inputVisitor).getType()), "IntNot input is not an Int");
		return null;
	}

	@Override
	public Void visitIntAnd(IntAnd intAnd) throws FractalIRException {
		checkState();
		validIfTrue(intAnd.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
				"IntAnd result is not an Int");
		validIfTrue(ValueTypes.isInt(intAnd.getLeft().accept(inputVisitor).getType()), "IntAnd left is not an Int");
		validIfTrue(ValueTypes.isInt(intAnd.getRight().accept(inputVisitor).getType()), "IntAnd right is not an Int");
		return null;
	}

	@Override
	public Void visitIntOr(IntOr intOr) throws FractalIRException {
		checkState();
		validIfTrue(intOr.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
				"IntOr result is not an Int");
		validIfTrue(ValueTypes.isInt(intOr.getLeft().accept(inputVisitor).getType()), "IntOr left is not an Int");
		validIfTrue(ValueTypes.isInt(intOr.getRight().accept(inputVisitor).getType()), "IntOr right is not an Int");
		return null;
	}

	@Override
	public Void visitIntXor(IntXor intXor) throws FractalIRException {
		checkState();
		validIfTrue(intXor.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
				"IntXor result is not an Int");
		validIfTrue(ValueTypes.isInt(intXor.getLeft().accept(inputVisitor).getType()), "IntXor left is not an Int");
		validIfTrue(ValueTypes.isInt(intXor.getRight().accept(inputVisitor).getType()), "IntXor right is not an Int");
		return null;
	}

	@Override
	public Void visitIntIsEqual(IntIsEqual intIsEqual) throws FractalIRException {
		checkState();
		validIfTrue(intIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"IntIsEqual result is not a Bool");
		validIfTrue(ValueTypes.isInt(intIsEqual.getLeft().accept(inputVisitor).getType()),
				"IntIsEqual left is not an Int");
		validIfTrue(ValueTypes.isInt(intIsEqual.getRight().accept(inputVisitor).getType()),
				"IntIsEqual right is not an Int");
		return null;
	}

	@Override
	public Void visitIntIsNotEqual(IntIsNotEqual intIsNotEqual) throws FractalIRException {
		checkState();
		validIfTrue(intIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"IntIsNotEqual result is not a Bool");
		validIfTrue(ValueTypes.isInt(intIsNotEqual.getLeft().accept(inputVisitor).getType()),
				"IntIsNotEqual left is not an Int");
		validIfTrue(ValueTypes.isInt(intIsNotEqual.getRight().accept(inputVisitor).getType()),
				"IntIsNotEqual right is not an Int");
		return null;
	}

	@Override
	public Void visitIntIsGreater(IntIsGreater intIsGreater) throws FractalIRException {
		checkState();
		validIfTrue(intIsGreater.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"IntIsGreater result is not a Bool");
		validIfTrue(ValueTypes.isInt(intIsGreater.getSubject().accept(inputVisitor).getType()),
				"IntIsGreater subject is not an Int");
		validIfTrue(ValueTypes.isInt(intIsGreater.getBasis().accept(inputVisitor).getType()),
				"IntIsGreater basis is not an Int");
		return null;
	}

	@Override
	public Void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalIRException {
		checkState();
		validIfTrue(intIsGreaterOrEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"IntIsGreaterOrEqual result is not a Bool");
		validIfTrue(ValueTypes.isInt(intIsGreaterOrEqual.getSubject().accept(inputVisitor).getType()),
				"IntIsGreaterOrEqual subject is not an Int");
		validIfTrue(ValueTypes.isInt(intIsGreaterOrEqual.getBasis().accept(inputVisitor).getType()),
				"IntIsGreaterOrEqual is not an Int");
		return null;
	}

	@Override
	public Void visitRealAdd(RealAdd realAdd) throws FractalIRException {
		checkState();
		validIfTrue(realAdd.getSum().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
				"RealAdd sum is not a Real");
		validIfTrue(ValueTypes.isReal(realAdd.getLeftAddend().accept(inputVisitor).getType()),
				"RealAdd left addend is not a Real");
		validIfTrue(ValueTypes.isReal(realAdd.getRightAddend().accept(inputVisitor).getType()),
				"RealAdd right addend is not a Real");
		return null;
	}

	@Override
	public Void visitRealSubtract(RealSubtract realSubtract) throws FractalIRException {
		checkState();
		validIfTrue(realSubtract.getDifference().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
				"RealSubtract difference is not a Real");
		validIfTrue(ValueTypes.isReal(realSubtract.getMinuend().accept(inputVisitor).getType()),
				"RealSubtract minuend is not a Real");
		validIfTrue(ValueTypes.isReal(realSubtract.getSubtrahend().accept(inputVisitor).getType()),
				"RealSubtract subtract is not a Real");
		return null;
	}

	@Override
	public Void visitRealMultiply(RealMultiply realMultiply) throws FractalIRException {
		checkState();
		validIfTrue(realMultiply.getProduct().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
				"RealMultiply product is not a Real");
		validIfTrue(ValueTypes.isReal(realMultiply.getLeftFactor().accept(inputVisitor).getType()),
				"RealMultiply left factor is not a Real");
		validIfTrue(ValueTypes.isReal(realMultiply.getRightFactor().accept(inputVisitor).getType()),
				"RealMultiply right factor is not a Real");
		return null;
	}

	@Override
	public Void visitRealDivide(RealDivide realDivide) throws FractalIRException {
		checkState();
		validIfTrue(realDivide.getQuotient().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
				"RealDivide quotient is not a Real");
		validIfTrue(ValueTypes.isReal(realDivide.getDividend().accept(inputVisitor).getType()),
				"RealDivide dividend is not a Real");
		validIfTrue(ValueTypes.isReal(realDivide.getDivisor().accept(inputVisitor).getType()),
				"RealDivide divisor is not a Real");
		return null;
	}

	@Override
	public Void visitRealPower(RealPower realPower) throws FractalIRException {
		checkState();
		validIfTrue(realPower.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
				"RealPower result is not a Real");
		validIfTrue(ValueTypes.isReal(realPower.getBase().accept(inputVisitor).getType()),
				"RealPower base is not a Real");
		validIfTrue(ValueTypes.isReal(realPower.getExponent().accept(inputVisitor).getType()),
				"RealPower exponent is not a Real");
		return null;
	}

	@Override
	public Void visitRealIsEqual(RealIsEqual realIsEqual) throws FractalIRException {
		checkState();
		validIfTrue(realIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"RealIsEqual result is not a Bool");
		validIfTrue(ValueTypes.isReal(realIsEqual.getLeft().accept(inputVisitor).getType()),
				"RealIsEqual left is not a Real");
		validIfTrue(ValueTypes.isReal(realIsEqual.getRight().accept(inputVisitor).getType()),
				"RealIsEqual right is not a Real");
		return null;
	}

	@Override
	public Void visitRealIsNotEqual(RealIsNotEqual realIsNotEqual) throws FractalIRException {
		checkState();
		validIfTrue(realIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"RealIsNotEqual result is not a Bool");
		validIfTrue(ValueTypes.isReal(realIsNotEqual.getLeft().accept(inputVisitor).getType()),
				"RealIsNotEqual left is not a Real");
		validIfTrue(ValueTypes.isReal(realIsNotEqual.getRight().accept(inputVisitor).getType()),
				"RealIsNotEqual right is not a Real");
		return null;
	}

	@Override
	public Void visitRealIsGreater(RealIsGreater realIsGreater) throws FractalIRException {
		checkState();
		validIfTrue(realIsGreater.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"RealIsGreater result is not a Bool");
		validIfTrue(ValueTypes.isReal(realIsGreater.getSubject().accept(inputVisitor).getType()),
				"RealIsGreater subject is not a Real");
		validIfTrue(ValueTypes.isReal(realIsGreater.getBasis().accept(inputVisitor).getType()),
				"RealIsGreater basis is not a Real");
		return null;
	}

	@Override
	public Void visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalIRException {
		checkState();
		validIfTrue(realIsGreaterOrEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"RealIsGreaterOrEqual result is not a Bool");
		validIfTrue(ValueTypes.isReal(realIsGreaterOrEqual.getSubject().accept(inputVisitor).getType()),
				"RealIsGreaterOrEqual subject is not a Real");
		validIfTrue(ValueTypes.isReal(realIsGreaterOrEqual.getBasis().accept(inputVisitor).getType()),
				"RealIsGreaterOrEqual basis is not a Real");
		return null;
	}

	@Override
	public Void visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalIRException {
		checkState();
		validIfTrue(
				realComposeComplex.getComplex().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
				"RealComposeComplex complex is not a Complex");
		validIfTrue(ValueTypes.isReal(realComposeComplex.getReal().accept(inputVisitor).getType()),
				"RealComposeComplex real is not a Real");
		validIfTrue(ValueTypes.isReal(realComposeComplex.getImaginary().accept(inputVisitor).getType()),
				"RealComposeComplex imaginary is not a Real");
		return null;
	}

	@Override
	public Void visitComplexAdd(ComplexAdd complexAdd) throws FractalIRException {
		checkState();
		validIfTrue(complexAdd.getSum().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
				"ComplexAdd sum is not a Complex");
		validIfTrue(ValueTypes.isComplex(complexAdd.getLeftAddend().accept(inputVisitor).getType()),
				"ComplexAdd left addend is not a Complex");
		validIfTrue(ValueTypes.isComplex(complexAdd.getRightAddend().accept(inputVisitor).getType()),
				"ComplexAdd right addend is not a Complex");
		return null;
	}

	@Override
	public Void visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalIRException {
		checkState();
		validIfTrue(
				complexSubtract.getDifference().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
				"ComplexSubtract difference is not a Complex");
		validIfTrue(ValueTypes.isComplex(complexSubtract.getMinuend().accept(inputVisitor).getType()),
				"ComplexSubtract minuend is not a Complex");
		validIfTrue(ValueTypes.isComplex(complexSubtract.getSubtrahend().accept(inputVisitor).getType()),
				"ComplexSubtract subtrahend is not a Complex");
		return null;
	}

	@Override
	public Void visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalIRException {
		checkState();
		validIfTrue(complexMultiply.getProduct().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
				"ComplexMultiply product is not a Complex");
		validIfTrue(ValueTypes.isComplex(complexMultiply.getLeftFactor().accept(inputVisitor).getType()),
				"ComplexMultiply left factor is not a Complex");
		validIfTrue(ValueTypes.isComplex(complexMultiply.getRightFactor().accept(inputVisitor).getType()),
				"ComplexMultiply right factor is not a Complex");
		return null;
	}

	@Override
	public Void visitComplexDivide(ComplexDivide complexDivide) throws FractalIRException {
		checkState();
		validIfTrue(complexDivide.getQuotient().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
				"ComplexDivide quotient is not a Complex");
		validIfTrue(ValueTypes.isComplex(complexDivide.getDividend().accept(inputVisitor).getType()),
				"ComplexDivide dividend is not a Complex");
		validIfTrue(ValueTypes.isComplex(complexDivide.getDivisor().accept(inputVisitor).getType()),
				"ComplexDivide divisor is not a Complex");
		return null;
	}

	@Override
	public Void visitComplexPower(ComplexPower complexPower) throws FractalIRException {
		checkState();
		validIfTrue(complexPower.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
				"ComplexPower result is not a Complex");
		validIfTrue(ValueTypes.isComplex(complexPower.getBase().accept(inputVisitor).getType()),
				"ComplexPower base is not a Complex");
		validIfTrue(ValueTypes.isComplex(complexPower.getExponent().accept(inputVisitor).getType()),
				"ComplexPower exponent is not a Complex");
		return null;
	}

	@Override
	public Void visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalIRException {
		checkState();
		validIfTrue(complexGetReal.getReal().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
				"ComplexGetReal real is not a Real");
		validIfTrue(ValueTypes.isComplex(complexGetReal.getComplex().accept(inputVisitor).getType()),
				"ComplexGetReal complex is not a complex");
		return null;
	}

	@Override
	public Void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalIRException {
		checkState();
		validIfTrue(
				complexGetImaginary.getImaginary().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
				"ComplexGetReal imaginary is not a Real");
		validIfTrue(ValueTypes.isComplex(complexGetImaginary.getComplex().accept(inputVisitor).getType()),
				"ComplexGetReal complex is not a complex");
		return null;
	}

	@Override
	public Void visitComplexModulo(ComplexModulo complexModulo) throws FractalIRException {
		checkState();
		validIfTrue(complexModulo.getModulus().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
				"ComplexModulo modulus is not a Real");
		validIfTrue(ValueTypes.isComplex(complexModulo.getComplex().accept(inputVisitor).getType()),
				"ComplexGetReal complex is not a complex");
		return null;
	}

	@Override
	public Void visitFunctionCall(FunctionCall functionCall) throws FractalIRException {
		checkState();

		// check the function argument
		ValueInfo functionArg = functionCall.getFunction().accept(inputVisitor);
		validIfTrue(ValueTypes.isFunction(functionArg.getType()), "FunctionCall function argument is not a Function");
		ValueTypes.FunctionType functionType = ValueTypes.toFunction(functionArg.getType());

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
						.isAssignableFrom(ValueTypes.POINTER(ValueTypes.VOID)),
				"PointerAllocate pointer argument is not a Pointer");
		return null;
	}

	@Override
	public Void visitPointerFree(PointerFree pointerFree) throws FractalIRException {
		checkState();
		validIfTrue(ValueTypes.isPointer(pointerFree.getPointer().accept(inputVisitor).getType()),
				"PointerFree pointer argument is not a Pointer");
		return null;
	}

	@Override
	public Void visitPointerGet(PointerGet pointerGet) throws FractalIRException {
		checkState();
		ValueInfo pointerArg = pointerGet.getPointer().accept(inputVisitor);
		validIfTrue(ValueTypes.isPointer(pointerArg.getType()), "PointerGet pointer argument is not a Pointer");
		ValueTypes.PointerType pointerType = ValueTypes.toPointer(pointerArg.getType());
		validIfTrue(pointerGet.getData().accept(outputVisitor).getType().isAssignableFrom(pointerType.getPointerType()),
				"PointerGet pointer type and data type are incompatible");
		return null;
	}

	@Override
	public Void visitPointerSet(PointerSet pointerSet) throws FractalIRException {
		checkState();
		ValueInfo pointerArg = pointerSet.getPointer().accept(inputVisitor);
		validIfTrue(ValueTypes.isPointer(pointerArg.getType()), "PointerSet pointer argument is not a Pointer");
		ValueTypes.PointerType pointerType = ValueTypes.toPointer(pointerArg.getType());
		validIfTrue(pointerType.getPointerType().isAssignableFrom(pointerSet.getData().accept(inputVisitor).getType()),
				"PointerSet pointer type and data type are incompatible");
		validIfTrue(!ValueTypes.isVoid(pointerType.getPointerType()),
				"PointerSet cannot set the value of the null-pointer");
		return null;
	}

	@Override
	public Void visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalIRException {
		checkState();
		validIfTrue(pointerIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"PointerIsEqual result is not a Bool");
		validIfTrue(ValueTypes.isPointer(pointerIsEqual.getLeft().accept(inputVisitor).getType()),
				"PointerIsEqual left is not a Pointer");
		validIfTrue(ValueTypes.isPointer(pointerIsEqual.getRight().accept(inputVisitor).getType()),
				"PointerIsEqual right is not a Pointer");
		return null;
	}

	@Override
	public Void visitPointerIsNotEqual(PointerIsNotEqual pointerIsNotEqual) throws FractalIRException {
		checkState();
		validIfTrue(pointerIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
				"PointerIsNotEqual result is not a Bool");
		validIfTrue(ValueTypes.isPointer(pointerIsNotEqual.getLeft().accept(inputVisitor).getType()),
				"PointerIsNotEqual left is not a Pointer");
		validIfTrue(ValueTypes.isPointer(pointerIsNotEqual.getRight().accept(inputVisitor).getType()),
				"PointerIsNotEqual right is not a Pointer");
		return null;
	}

	@Override
	public Void visitIf(If anIf) throws FractalIRException {
		checkState();
		validIfTrue(ValueTypes.isBool(anIf.getCondition().accept(inputVisitor).getType()),
				"If condition is not a Bool");

		// check the true condition
		ValidatingInstructionVisitor ifTrueVisitor = new ValidatingInstructionVisitor(functions, variables, returnType);
		for (IInstruction instruction : anIf.getIfTrue()) {
			instruction.accept(ifTrueVisitor);
		}

		// check the false condition
		ValidatingInstructionVisitor ifFalseVisitor =
				new ValidatingInstructionVisitor(functions, variables, returnType);
		for (IInstruction instruction : anIf.getIfFalse()) {
			instruction.accept(ifFalseVisitor);
		}

		// if both conditions return, then we return
		returned = ifTrueVisitor.isReturned() && ifFalseVisitor.isReturned();

		return null;
	}

	@Override
	public Void visitWhile(While aWhile) throws FractalIRException {
		checkState();
		validIfTrue(ValueTypes.isBool(aWhile.getCondition().accept(inputVisitor).getType()),
				"While condition is not a Bool");

		// check the loop instructions
		ValidatingInstructionVisitor loopVisitor = new ValidatingInstructionVisitor(functions, variables, returnType);
		for (IInstruction instruction : aWhile.getWhileTrue()) {
			instruction.accept(loopVisitor);
		}

		// if the while returned, then we return
		returned = loopVisitor.isReturned();

		return null;
	}
}
