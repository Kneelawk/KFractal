package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;

import java.util.List;
import java.util.function.Function;

class ValidatingValueVisitor implements IValueVisitor<ValueType> {
    private final List<FunctionDefinition> functions;
    private final List<GlobalDeclaration> globalVariables;
    private final List<BasicBlock> blocks;
    private final int blockIndex;
    private final List<ArgumentDeclaration> contextVariables;
    private final List<ArgumentDeclaration> arguments;
    private final int instructionIndex;
    private final ValueType returnType;

    private boolean terminated = false;

    ValidatingValueVisitor(List<FunctionDefinition> functions,
                           List<GlobalDeclaration> globalVariables,
                           List<BasicBlock> blocks,
                           int blockIndex, List<ArgumentDeclaration> contextVariables,
                           List<ArgumentDeclaration> arguments,
                           int instructionIndex, ValueType returnType) {
        this.functions = functions;
        this.globalVariables = globalVariables;
        this.blocks = blocks;
        this.blockIndex = blockIndex;
        this.contextVariables = contextVariables;
        this.arguments = arguments;
        this.instructionIndex = instructionIndex;
        this.returnType = returnType;
    }

    private void checkState() throws FractalIRException {
        if (terminated) {
            throw new FractalIRValidationException(
                    "This set of instructions has already reached a terminator statement");
        }
    }

    private void validValueTypeIfTrue(boolean condition, String message) throws FractalIRException {
        if (!condition) {
            throw new IncompatibleValueTypeException(message);
        }
    }

    private void validIfTrue(boolean condition, String message,
                             Function<String, ? extends FractalIRValidationException> exception)
            throws FractalIRValidationException {
        if (!condition) {
            throw exception.apply(message);
        }
    }

    boolean isTerminated() {
        return terminated;
    }

    @Override
    public ValueType visitArgumentReference(ArgumentReference argumentReference) throws FractalException {
        return null;
    }

    @Override
    public ValueType visitInstructionReference(InstructionReference instructionReference) throws FractalException {
        return null;
    }

    @Override
    public ValueType visitBoolConstant(BoolConstant constant) throws FractalException {
        return null;
    }

    @Override
    public ValueType visitIntConstant(IntConstant constant) throws FractalException {
        return null;
    }

    @Override
    public ValueType visitRealConstant(RealConstant constant) throws FractalException {
        return null;
    }

    @Override
    public ValueType visitComplexConstant(ComplexConstant constant) throws FractalException {
        return null;
    }

    @Override
    public ValueType visitNullPointer() throws FractalException {
        return null;
    }

    @Override
    public ValueType visitNullFunction() throws FractalException {
        return null;
    }

    @Override
    public ValueType visitVoid() throws FractalException {
        return null;
    }

    @Override
    public ValueType visitGlobalGet(GlobalGet globalGet) throws FractalException {
        return null;
    }

    @Override
    public ValueType visitGlobalSet(GlobalSet globalSet) throws FractalException {
        return null;
    }

    @Override
    public ValueType visitComplexIsEqual(ComplexIsEqual complexIsEqual) throws FractalException {
        return null;
    }

    @Override
    public ValueType visitFunctionCreate(FunctionCreate functionCreate) throws FractalException {
        return null;
    }

    @Override
    public ValueType visitReturn(Return aReturn) throws FractalException {
        checkState();
        terminated = true;
        ValueType returnValue = aReturn.getReturnValue().accept(this);
        validValueTypeIfTrue(returnType.isAssignableFrom(returnValue),
                "Unable to return incompatible type");
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitBoolNot(BoolNot boolNot) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isBool(boolNot.getInput().accept(this)),
                "BoolNot input is not a bool");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitBoolAnd(BoolAnd boolAnd) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isBool(boolAnd.getLeft().accept(this)),
                "BoolAnd left is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolAnd.getRight().accept(this)),
                "BoolAnd right is not a bool");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitBoolOr(BoolOr boolOr) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isBool(boolOr.getLeft().accept(this)),
                "BoolOr left is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolOr.getRight().accept(this)),
                "BoolOr right is not a bool");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isBool(boolIsEqual.getLeft().accept(this)),
                "BoolIsEqual left is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolIsEqual.getRight().accept(this)),
                "BoolIsEqual right is not a bool");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitIntAdd(IntAdd intAdd) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intAdd.getLeftAddend().accept(this)),
                "IntAdd left addend is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intAdd.getRightAddend().accept(this)),
                "IntAdd right addend is not an Int");
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntSubtract(IntSubtract intSubtract) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intSubtract.getMinuend().accept(this)),
                "IntSubtract minuend is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intSubtract.getSubtrahend().accept(this)),
                "IntSubtract subtrahend is not an Int");
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntMultiply(IntMultiply intMultiply) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intMultiply.getLeftFactor().accept(this)),
                "IntMultiply left factor is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intMultiply.getRightFactor().accept(this)),
                "IntMultiply right factor is not an Int");
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntDivide(IntDivide intDivide) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intDivide.getDividend().accept(this)),
                "IntDivide dividend is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intDivide.getDivisor().accept(this)),
                "IntDivide divisor is not an Int");
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntModulo(IntModulo intModulo) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intModulo.getLeft().accept(this)),
                "IntModulo left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intModulo.getRight().accept(this)),
                "IntModulo right is not an Int");
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntPower(IntPower intPower) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intPower.getBase().accept(this)),
                "IntPower base is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intPower.getExponent().accept(this)),
                "IntPower exponent is not an Int");
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntNot(IntNot intNot) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intNot.getInput().accept(this)),
                "IntNot input is not an Int");
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntAnd(IntAnd intAnd) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intAnd.getLeft().accept(this)),
                "IntAnd left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intAnd.getRight().accept(this)),
                "IntAnd right is not an Int");
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntOr(IntOr intOr) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intOr.getLeft().accept(this)),
                "IntOr left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intOr.getRight().accept(this)),
                "IntOr right is not an Int");
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntXor(IntXor intXor) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intXor.getLeft().accept(this)),
                "IntXor left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intXor.getRight().accept(this)),
                "IntXor right is not an Int");
        return ValueTypes.INT;
    }

    @Override
    public ValueType visitIntIsEqual(IntIsEqual intIsEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intIsEqual.getLeft().accept(this)),
                "IntIsEqual left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intIsEqual.getRight().accept(this)),
                "IntIsEqual right is not an Int");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitIntIsGreater(IntIsGreater intIsGreater) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intIsGreater.getSubject().accept(this)),
                "IntIsGreater subject is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intIsGreater.getBasis().accept(this)),
                "IntIsGreater basis is not an Int");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isInt(intIsGreaterOrEqual.getSubject().accept(this)),
                "IntIsGreaterOrEqual subject is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intIsGreaterOrEqual.getBasis().accept(this)),
                "IntIsGreaterOrEqual is not an Int");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitRealAdd(RealAdd realAdd) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isReal(realAdd.getLeftAddend().accept(this)),
                "RealAdd left addend is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realAdd.getRightAddend().accept(this)),
                "RealAdd right addend is not a Real");
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitRealSubtract(RealSubtract realSubtract) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isReal(realSubtract.getMinuend().accept(this)),
                "RealSubtract minuend is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realSubtract.getSubtrahend().accept(this)),
                "RealSubtract subtract is not a Real");
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitRealMultiply(RealMultiply realMultiply) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isReal(realMultiply.getLeftFactor().accept(this)),
                "RealMultiply left factor is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realMultiply.getRightFactor().accept(this)),
                "RealMultiply right factor is not a Real");
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitRealDivide(RealDivide realDivide) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isReal(realDivide.getDividend().accept(this)),
                "RealDivide dividend is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realDivide.getDivisor().accept(this)),
                "RealDivide divisor is not a Real");
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitRealPower(RealPower realPower) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isReal(realPower.getBase().accept(this)),
                "RealPower base is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realPower.getExponent().accept(this)),
                "RealPower exponent is not a Real");
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitRealIsEqual(RealIsEqual realIsEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isReal(realIsEqual.getLeft().accept(this)),
                "RealIsEqual left is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realIsEqual.getRight().accept(this)),
                "RealIsEqual right is not a Real");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitRealIsGreater(RealIsGreater realIsGreater) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isReal(realIsGreater.getSubject().accept(this)),
                "RealIsGreater subject is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realIsGreater.getBasis().accept(this)),
                "RealIsGreater basis is not a Real");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isReal(realIsGreaterOrEqual.getSubject().accept(this)),
                "RealIsGreaterOrEqual subject is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realIsGreaterOrEqual.getBasis().accept(this)),
                "RealIsGreaterOrEqual basis is not a Real");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isReal(realComposeComplex.getReal().accept(this)),
                "RealComposeComplex real is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realComposeComplex.getImaginary().accept(this)),
                "RealComposeComplex imaginary is not a Real");
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexAdd(ComplexAdd complexAdd) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isComplex(complexAdd.getLeftAddend().accept(this)),
                "ComplexAdd left addend is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexAdd.getRightAddend().accept(this)),
                "ComplexAdd right addend is not a Complex");
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isComplex(complexSubtract.getMinuend().accept(this)),
                "ComplexSubtract minuend is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexSubtract.getSubtrahend().accept(this)),
                "ComplexSubtract subtrahend is not a Complex");
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isComplex(complexMultiply.getLeftFactor().accept(this)),
                "ComplexMultiply left factor is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexMultiply.getRightFactor().accept(this)),
                "ComplexMultiply right factor is not a Complex");
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexDivide(ComplexDivide complexDivide) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isComplex(complexDivide.getDividend().accept(this)),
                "ComplexDivide dividend is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexDivide.getDivisor().accept(this)),
                "ComplexDivide divisor is not a Complex");
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexPower(ComplexPower complexPower) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isComplex(complexPower.getBase().accept(this)),
                "ComplexPower base is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexPower.getExponent().accept(this)),
                "ComplexPower exponent is not a Complex");
        return ValueTypes.COMPLEX;
    }

    @Override
    public ValueType visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isComplex(complexGetReal.getComplex().accept(this)),
                "ComplexGetReal complex is not a complex");
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isComplex(complexGetImaginary.getComplex().accept(this)),
                "ComplexGetReal complex is not a complex");
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitComplexModulo(ComplexModulo complexModulo) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isComplex(complexModulo.getComplex().accept(this)),
                "ComplexGetReal complex is not a complex");
        return ValueTypes.REAL;
    }

    @Override
    public ValueType visitFunctionCall(FunctionCall functionCall) throws FractalException {
        checkState();

        // check the function argument
        ValueType functionArg = functionCall.getFunction().accept(this);
        validValueTypeIfTrue(ValueTypes.isFunction(functionArg),
                "FunctionCall function argument is not a Function");
        validValueTypeIfTrue(!ValueTypes.isNullFunction(functionArg),
                "FunctionCall cannot call the null-function");
        ValueTypes.FunctionType functionType = ValueTypes.toFunction(functionArg);

        // check the function's argument types
        List<ValueType> targetArguments = functionType.getArgumentTypes();
        List<IValue> instructionArguments = functionCall.getArguments();
        int targetArgumentsSize = targetArguments.size();
        int instructionArgumentsSize = instructionArguments.size();
        int size = Math.max(targetArgumentsSize, instructionArgumentsSize);
        for (int i = 0; i < size; i++) {
            if (i >= targetArgumentsSize) {
                throw new IncompatibleFunctionArgumentException("FunctionCall has extra arguments: " +
                        instructionArguments.subList(i, instructionArgumentsSize));
            }
            if (i >= instructionArgumentsSize) {
                throw new IncompatibleFunctionArgumentException(
                        "FunctionCall is missing arguments: " + targetArguments.subList(i, targetArgumentsSize));
            }
            ValueType targetArgument = targetArguments.get(i);
            IValue instructionArgument = instructionArguments.get(i);
            validIfTrue(targetArgument.isAssignableFrom(instructionArgument.accept(this)),
                    "Function defines argument: " + targetArgument + " but FunctionCall instruction supplies: " +
                            instructionArgument, IncompatibleFunctionArgumentException::new);
        }

        return functionType.getReturnType();
    }

    @Override
    public ValueType visitFunctionIsEqual(FunctionIsEqual functionIsEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isFunction(functionIsEqual.getLeft().accept(this)),
                "FunctionIsEqual left is not a function");
        validValueTypeIfTrue(ValueTypes.isFunction(functionIsEqual.getRight().accept(this)),
                "FunctionIsEqual right is not a function");

        return null;
    }

    @Override
    public ValueType visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalException {
        checkState();
        return ValueTypes.POINTER(pointerAllocate.getType());
    }

    @Override
    public ValueType visitPointerFree(PointerFree pointerFree) throws FractalException {
        checkState();
        ValueType pointerType = pointerFree.getPointer().accept(this);
        validValueTypeIfTrue(ValueTypes.isPointer(pointerType),
                "PointerFree pointer argument is not a Pointer");
        validValueTypeIfTrue(!ValueTypes.isNullPointer(pointerType),
                "PointerFree cannot free the null-pointer");
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitPointerGet(PointerGet pointerGet) throws FractalException {
        checkState();
        ValueType pointerArg = pointerGet.getPointer().accept(this);
        validValueTypeIfTrue(ValueTypes.isPointer(pointerArg),
                "PointerGet pointer argument is not a Pointer");
        ValueTypes.PointerType pointerType = ValueTypes.toPointer(pointerArg);
        validValueTypeIfTrue(!ValueTypes.isNullPointer(pointerType),
                "PointerGet cannot get the value of the null-pointer");
        return pointerType.getPointerType();
    }

    @Override
    public ValueType visitPointerSet(PointerSet pointerSet) throws FractalException {
        checkState();
        ValueType pointerArg = pointerSet.getPointer().accept(this);
        validValueTypeIfTrue(ValueTypes.isPointer(pointerArg),
                "PointerSet pointer argument is not a Pointer");
        ValueTypes.PointerType pointerType = ValueTypes.toPointer(pointerArg);
        validValueTypeIfTrue(!ValueTypes.isNullPointer(pointerType),
                "PointerSet cannot set the value of the null-pointer");
        validValueTypeIfTrue(
                pointerType.getPointerType().isAssignableFrom(pointerSet.getData().accept(this)),
                "PointerSet pointer type and data type are incompatible");
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isPointer(pointerIsEqual.getLeft().accept(this)),
                "PointerIsEqual left is not a Pointer");
        validValueTypeIfTrue(ValueTypes.isPointer(pointerIsEqual.getRight().accept(this)),
                "PointerIsEqual right is not a Pointer");
        return ValueTypes.BOOL;
    }

    @Override
    public ValueType visitBranchConditional(BranchConditional branchConditional) throws FractalException {
        terminated = true;
        return ValueTypes.VOID;
    }

    @Override
    public ValueType visitBranch(Branch branch) throws FractalException {
        terminated = true;
        return ValueTypes.VOID;
    }
}
