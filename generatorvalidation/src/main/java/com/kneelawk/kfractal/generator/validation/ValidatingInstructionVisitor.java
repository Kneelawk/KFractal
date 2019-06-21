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

	@Override
	public Void visitAssign(Assign assign) throws FractalIRException {
		checkState();
		ValueInfo dest = assign.getDest().accept(outputVisitor);
		ValueInfo source = assign.getSource().accept(inputVisitor);
		if (!dest.getType().equals(source.getType())) {
			throw new FractalIRValidationException("Unable to assign incompatible types");
		}
		return null;
	}

	@Override
	public Void visitReturn(Return aReturn) throws FractalIRException {
		checkState();
		returned = true;
		ValueInfo returnValue = aReturn.getReturnValue().accept(inputVisitor);
		if (!function.getReturnType().equals(returnValue.getType())) {
			throw new FractalIRValidationException("Unable to return incompatible type");
		}
		return null;
	}

	@Override
	public Void visitBoolNot(BoolNot boolNot) throws FractalIRException {
		checkState();
		if (!ValueType.isBool(boolNot.getOutput().accept(outputVisitor).getType())) {
			throw new FractalIRValidationException("BoolNot output is not a bool");
		}
		if (!ValueType.isBool(boolNot.getInput().accept(inputVisitor).getType())) {
			throw new FractalIRValidationException("BoolNot input is not a bool");
		}
		return null;
	}

	@Override
	public Void visitBoolAnd(BoolAnd boolAnd) throws FractalIRException {
		checkState();
		if (!ValueType.isBool(boolAnd.getResult().accept(outputVisitor).getType())) {
			throw new FractalIRValidationException("BoolAnd result is not a bool");
		}
		if (!ValueType.isBool(boolAnd.getLeft().accept(inputVisitor).getType())) {
			throw new FractalIRValidationException("BoolAnd left is not a bool");
		}
		if (!ValueType.isBool(boolAnd.getRight().accept(inputVisitor).getType())) {
			throw new FractalIRValidationException("BoolAnd right is not a bool");
		}
		return null;
	}

	@Override
	public Void visitBoolOr(BoolOr boolOr) throws FractalIRException {
		checkState();
		if (!ValueType.isBool(boolOr.getResult().accept(outputVisitor).getType())) {
			throw new FractalIRValidationException("BoolOr result is not a bool");
		}
		if (!ValueType.isBool(boolOr.getLeft().accept(inputVisitor).getType())) {
			throw new FractalIRValidationException("BoolOr left is not a bool");
		}
		if (!ValueType.isBool(boolOr.getLeft().accept(inputVisitor).getType())) {
			throw new FractalIRValidationException("BoolOr right is not a bool");
		}
		return null;
	}

	@Override
	public Void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalIRException {
		checkState();
		if (!ValueType.isBool(boolIsEqual.getResult().accept(outputVisitor).getType())) {
			throw new FractalIRValidationException("BoolIsEqual result is not a bool");
		}
		if (!ValueType.isBool(boolIsEqual.getLeft().accept(inputVisitor).getType())) {
			throw new FractalIRValidationException("BoolIsEqual left is not a bool");
		}
		if (!ValueType.isBool(boolIsEqual.getRight().accept(inputVisitor).getType())) {
			throw new FractalIRValidationException("BoolIsEqual right is not a bool");
		}
		return null;
	}

	@Override
	public Void visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalIRException {
		checkState();
		if (!ValueType.isBool(boolIsNotEqual.getResult().accept(outputVisitor).getType())) {
			throw new FractalIRValidationException("BoolIsNotEqual result is not a bool");
		}
		if (!ValueType.isBool(boolIsNotEqual.getLeft().accept(inputVisitor).getType())) {
			throw new FractalIRValidationException("BoolIsNotEqual left is not a bool");
		}
		if (!ValueType.isBool(boolIsNotEqual.getRight().accept(inputVisitor).getType())) {
			throw new FractalIRValidationException("BoolIsNotEqual right is not a bool");
		}
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
