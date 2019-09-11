package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;

import java.util.List;
import java.util.function.Function;

class ValidatingInstructionVisitor implements IInstructionVisitor<Void> {
    private List<FunctionDefinition> functions;
    private List<VariableDeclaration> globalVariables;
    private List<VariableDeclaration> contextVariables;
    private List<VariableDeclaration> arguments;
    private List<VariableDeclaration> localVariables;
    private final ValueType returnType;

    private final ValidatingInstructionInputVisitor inputVisitor;
    private final ValidatingInstructionOutputVisitor outputVisitor;

    private boolean returned = false;

    public ValidatingInstructionVisitor(List<FunctionDefinition> functions,
                                        List<VariableDeclaration> globalVariables,
                                        List<VariableDeclaration> contextVariables,
                                        List<VariableDeclaration> arguments,
                                        List<VariableDeclaration> localVariables,
                                        ValueType returnType) {
        this.functions = functions;
        this.globalVariables = globalVariables;
        this.contextVariables = contextVariables;
        this.arguments = arguments;
        this.localVariables = localVariables;
        this.returnType = returnType;
        inputVisitor = new ValidatingInstructionInputVisitor(functions, globalVariables, contextVariables, arguments,
                localVariables);
        outputVisitor =
                new ValidatingInstructionOutputVisitor(globalVariables, contextVariables, arguments, localVariables);
    }

    private void checkState() throws FractalIRException {
        if (returned) {
            throw new FractalIRValidationException("This set of instructions has already reached a return statement");
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

    boolean isReturned() {
        return returned;
    }

    @Override
    public Void visitAssign(Assign assign) throws FractalException {
        checkState();
        ValueInfo dest = assign.getDest().accept(outputVisitor);
        ValueInfo source = assign.getSource().accept(inputVisitor);
        validValueTypeIfTrue(dest.getType().isAssignableFrom(source.getType()), "Unable to assign incompatible types");
        return null;
    }

    @Override
    public Void visitReturn(Return aReturn) throws FractalException {
        checkState();
        returned = true;
        ValueInfo returnValue = aReturn.getReturnValue().accept(inputVisitor);
        validValueTypeIfTrue(returnType.isAssignableFrom(returnValue.getType()),
                "Unable to return incompatible type");
        return null;
    }

    @Override
    public Void visitBoolNot(BoolNot boolNot) throws FractalException {
        checkState();
        validValueTypeIfTrue(boolNot.getOutput().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "BoolNot output is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolNot.getInput().accept(inputVisitor).getType()),
                "BoolNot input is not a bool");
        return null;
    }

    @Override
    public Void visitBoolAnd(BoolAnd boolAnd) throws FractalException {
        checkState();
        validValueTypeIfTrue(boolAnd.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "BoolAnd result is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolAnd.getLeft().accept(inputVisitor).getType()),
                "BoolAnd left is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolAnd.getRight().accept(inputVisitor).getType()),
                "BoolAnd right is not a bool");
        return null;
    }

    @Override
    public Void visitBoolOr(BoolOr boolOr) throws FractalException {
        checkState();
        validValueTypeIfTrue(boolOr.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "BoolOr result is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolOr.getLeft().accept(inputVisitor).getType()),
                "BoolOr left is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolOr.getRight().accept(inputVisitor).getType()),
                "BoolOr right is not a bool");
        return null;
    }

    @Override
    public Void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(boolIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "BoolIsEqual result is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolIsEqual.getLeft().accept(inputVisitor).getType()),
                "BoolIsEqual left is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolIsEqual.getRight().accept(inputVisitor).getType()),
                "BoolIsEqual right is not a bool");
        return null;
    }

    @Override
    public Void visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                boolIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "BoolIsNotEqual result is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolIsNotEqual.getLeft().accept(inputVisitor).getType()),
                "BoolIsNotEqual left is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(boolIsNotEqual.getRight().accept(inputVisitor).getType()),
                "BoolIsNotEqual right is not a bool");
        return null;
    }

    @Override
    public Void visitIntAdd(IntAdd intAdd) throws FractalException {
        checkState();
        validValueTypeIfTrue(intAdd.getSum().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
                "IntAdd sum is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intAdd.getLeftAddend().accept(inputVisitor).getType()),
                "IntAdd left addend is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intAdd.getRightAddend().accept(inputVisitor).getType()),
                "IntAdd right addend is not an Int");
        return null;
    }

    @Override
    public Void visitIntSubtract(IntSubtract intSubtract) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                intSubtract.getDifference().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
                "IntSubtract difference is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intSubtract.getMinuend().accept(inputVisitor).getType()),
                "IntSubtract minuend is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intSubtract.getSubtrahend().accept(inputVisitor).getType()),
                "IntSubtract subtrahend is not an Int");
        return null;
    }

    @Override
    public Void visitIntMultiply(IntMultiply intMultiply) throws FractalException {
        checkState();
        validValueTypeIfTrue(intMultiply.getProduct().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
                "IntMultiply product is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intMultiply.getLeftFactor().accept(inputVisitor).getType()),
                "IntMultiply left factor is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intMultiply.getRightFactor().accept(inputVisitor).getType()),
                "IntMultiply right factor is not an Int");
        return null;
    }

    @Override
    public Void visitIntDivide(IntDivide intDivide) throws FractalException {
        checkState();
        validValueTypeIfTrue(intDivide.getQuotient().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
                "IntDivide quotient is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intDivide.getDividend().accept(inputVisitor).getType()),
                "IntDivide dividend is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intDivide.getDivisor().accept(inputVisitor).getType()),
                "IntDivide divisor is not an Int");
        return null;
    }

    @Override
    public Void visitIntModulo(IntModulo intModulo) throws FractalException {
        checkState();
        validValueTypeIfTrue(intModulo.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
                "IntModulo result is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intModulo.getLeft().accept(inputVisitor).getType()),
                "IntModulo left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intModulo.getRight().accept(inputVisitor).getType()),
                "IntModulo right is not an Int");
        return null;
    }

    @Override
    public Void visitIntPower(IntPower intPower) throws FractalException {
        checkState();
        validValueTypeIfTrue(intPower.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
                "IntPower result is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intPower.getBase().accept(inputVisitor).getType()),
                "IntPower base is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intPower.getExponent().accept(inputVisitor).getType()),
                "IntPower exponent is not an Int");
        return null;
    }

    @Override
    public Void visitIntNot(IntNot intNot) throws FractalException {
        checkState();
        validValueTypeIfTrue(intNot.getOutput().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
                "IntNot output is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intNot.getInput().accept(inputVisitor).getType()),
                "IntNot input is not an Int");
        return null;
    }

    @Override
    public Void visitIntAnd(IntAnd intAnd) throws FractalException {
        checkState();
        validValueTypeIfTrue(intAnd.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
                "IntAnd result is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intAnd.getLeft().accept(inputVisitor).getType()),
                "IntAnd left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intAnd.getRight().accept(inputVisitor).getType()),
                "IntAnd right is not an Int");
        return null;
    }

    @Override
    public Void visitIntOr(IntOr intOr) throws FractalException {
        checkState();
        validValueTypeIfTrue(intOr.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
                "IntOr result is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intOr.getLeft().accept(inputVisitor).getType()),
                "IntOr left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intOr.getRight().accept(inputVisitor).getType()),
                "IntOr right is not an Int");
        return null;
    }

    @Override
    public Void visitIntXor(IntXor intXor) throws FractalException {
        checkState();
        validValueTypeIfTrue(intXor.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.INT),
                "IntXor result is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intXor.getLeft().accept(inputVisitor).getType()),
                "IntXor left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intXor.getRight().accept(inputVisitor).getType()),
                "IntXor right is not an Int");
        return null;
    }

    @Override
    public Void visitIntIsEqual(IntIsEqual intIsEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(intIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "IntIsEqual result is not a Bool");
        validValueTypeIfTrue(ValueTypes.isInt(intIsEqual.getLeft().accept(inputVisitor).getType()),
                "IntIsEqual left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intIsEqual.getRight().accept(inputVisitor).getType()),
                "IntIsEqual right is not an Int");
        return null;
    }

    @Override
    public Void visitIntIsNotEqual(IntIsNotEqual intIsNotEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                intIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "IntIsNotEqual result is not a Bool");
        validValueTypeIfTrue(ValueTypes.isInt(intIsNotEqual.getLeft().accept(inputVisitor).getType()),
                "IntIsNotEqual left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intIsNotEqual.getRight().accept(inputVisitor).getType()),
                "IntIsNotEqual right is not an Int");
        return null;
    }

    @Override
    public Void visitIntIsGreater(IntIsGreater intIsGreater) throws FractalException {
        checkState();
        validValueTypeIfTrue(intIsGreater.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "IntIsGreater result is not a Bool");
        validValueTypeIfTrue(ValueTypes.isInt(intIsGreater.getSubject().accept(inputVisitor).getType()),
                "IntIsGreater subject is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intIsGreater.getBasis().accept(inputVisitor).getType()),
                "IntIsGreater basis is not an Int");
        return null;
    }

    @Override
    public Void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                intIsGreaterOrEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "IntIsGreaterOrEqual result is not a Bool");
        validValueTypeIfTrue(ValueTypes.isInt(intIsGreaterOrEqual.getSubject().accept(inputVisitor).getType()),
                "IntIsGreaterOrEqual subject is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(intIsGreaterOrEqual.getBasis().accept(inputVisitor).getType()),
                "IntIsGreaterOrEqual is not an Int");
        return null;
    }

    @Override
    public Void visitRealAdd(RealAdd realAdd) throws FractalException {
        checkState();
        validValueTypeIfTrue(realAdd.getSum().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
                "RealAdd sum is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realAdd.getLeftAddend().accept(inputVisitor).getType()),
                "RealAdd left addend is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realAdd.getRightAddend().accept(inputVisitor).getType()),
                "RealAdd right addend is not a Real");
        return null;
    }

    @Override
    public Void visitRealSubtract(RealSubtract realSubtract) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                realSubtract.getDifference().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
                "RealSubtract difference is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realSubtract.getMinuend().accept(inputVisitor).getType()),
                "RealSubtract minuend is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realSubtract.getSubtrahend().accept(inputVisitor).getType()),
                "RealSubtract subtract is not a Real");
        return null;
    }

    @Override
    public Void visitRealMultiply(RealMultiply realMultiply) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                realMultiply.getProduct().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
                "RealMultiply product is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realMultiply.getLeftFactor().accept(inputVisitor).getType()),
                "RealMultiply left factor is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realMultiply.getRightFactor().accept(inputVisitor).getType()),
                "RealMultiply right factor is not a Real");
        return null;
    }

    @Override
    public Void visitRealDivide(RealDivide realDivide) throws FractalException {
        checkState();
        validValueTypeIfTrue(realDivide.getQuotient().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
                "RealDivide quotient is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realDivide.getDividend().accept(inputVisitor).getType()),
                "RealDivide dividend is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realDivide.getDivisor().accept(inputVisitor).getType()),
                "RealDivide divisor is not a Real");
        return null;
    }

    @Override
    public Void visitRealPower(RealPower realPower) throws FractalException {
        checkState();
        validValueTypeIfTrue(realPower.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
                "RealPower result is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realPower.getBase().accept(inputVisitor).getType()),
                "RealPower base is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realPower.getExponent().accept(inputVisitor).getType()),
                "RealPower exponent is not a Real");
        return null;
    }

    @Override
    public Void visitRealIsEqual(RealIsEqual realIsEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(realIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "RealIsEqual result is not a Bool");
        validValueTypeIfTrue(ValueTypes.isReal(realIsEqual.getLeft().accept(inputVisitor).getType()),
                "RealIsEqual left is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realIsEqual.getRight().accept(inputVisitor).getType()),
                "RealIsEqual right is not a Real");
        return null;
    }

    @Override
    public Void visitRealIsNotEqual(RealIsNotEqual realIsNotEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                realIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "RealIsNotEqual result is not a Bool");
        validValueTypeIfTrue(ValueTypes.isReal(realIsNotEqual.getLeft().accept(inputVisitor).getType()),
                "RealIsNotEqual left is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realIsNotEqual.getRight().accept(inputVisitor).getType()),
                "RealIsNotEqual right is not a Real");
        return null;
    }

    @Override
    public Void visitRealIsGreater(RealIsGreater realIsGreater) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                realIsGreater.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "RealIsGreater result is not a Bool");
        validValueTypeIfTrue(ValueTypes.isReal(realIsGreater.getSubject().accept(inputVisitor).getType()),
                "RealIsGreater subject is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realIsGreater.getBasis().accept(inputVisitor).getType()),
                "RealIsGreater basis is not a Real");
        return null;
    }

    @Override
    public Void visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                realIsGreaterOrEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "RealIsGreaterOrEqual result is not a Bool");
        validValueTypeIfTrue(ValueTypes.isReal(realIsGreaterOrEqual.getSubject().accept(inputVisitor).getType()),
                "RealIsGreaterOrEqual subject is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realIsGreaterOrEqual.getBasis().accept(inputVisitor).getType()),
                "RealIsGreaterOrEqual basis is not a Real");
        return null;
    }

    @Override
    public Void visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                realComposeComplex.getComplex().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
                "RealComposeComplex complex is not a Complex");
        validValueTypeIfTrue(ValueTypes.isReal(realComposeComplex.getReal().accept(inputVisitor).getType()),
                "RealComposeComplex real is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(realComposeComplex.getImaginary().accept(inputVisitor).getType()),
                "RealComposeComplex imaginary is not a Real");
        return null;
    }

    @Override
    public Void visitComplexAdd(ComplexAdd complexAdd) throws FractalException {
        checkState();
        validValueTypeIfTrue(complexAdd.getSum().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
                "ComplexAdd sum is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexAdd.getLeftAddend().accept(inputVisitor).getType()),
                "ComplexAdd left addend is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexAdd.getRightAddend().accept(inputVisitor).getType()),
                "ComplexAdd right addend is not a Complex");
        return null;
    }

    @Override
    public Void visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                complexSubtract.getDifference().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
                "ComplexSubtract difference is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexSubtract.getMinuend().accept(inputVisitor).getType()),
                "ComplexSubtract minuend is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexSubtract.getSubtrahend().accept(inputVisitor).getType()),
                "ComplexSubtract subtrahend is not a Complex");
        return null;
    }

    @Override
    public Void visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                complexMultiply.getProduct().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
                "ComplexMultiply product is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexMultiply.getLeftFactor().accept(inputVisitor).getType()),
                "ComplexMultiply left factor is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexMultiply.getRightFactor().accept(inputVisitor).getType()),
                "ComplexMultiply right factor is not a Complex");
        return null;
    }

    @Override
    public Void visitComplexDivide(ComplexDivide complexDivide) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                complexDivide.getQuotient().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
                "ComplexDivide quotient is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexDivide.getDividend().accept(inputVisitor).getType()),
                "ComplexDivide dividend is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexDivide.getDivisor().accept(inputVisitor).getType()),
                "ComplexDivide divisor is not a Complex");
        return null;
    }

    @Override
    public Void visitComplexPower(ComplexPower complexPower) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                complexPower.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.COMPLEX),
                "ComplexPower result is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexPower.getBase().accept(inputVisitor).getType()),
                "ComplexPower base is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(complexPower.getExponent().accept(inputVisitor).getType()),
                "ComplexPower exponent is not a Complex");
        return null;
    }

    @Override
    public Void visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalException {
        checkState();
        validValueTypeIfTrue(complexGetReal.getReal().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
                "ComplexGetReal real is not a Real");
        validValueTypeIfTrue(ValueTypes.isComplex(complexGetReal.getComplex().accept(inputVisitor).getType()),
                "ComplexGetReal complex is not a complex");
        return null;
    }

    @Override
    public Void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                complexGetImaginary.getImaginary().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
                "ComplexGetReal imaginary is not a Real");
        validValueTypeIfTrue(ValueTypes.isComplex(complexGetImaginary.getComplex().accept(inputVisitor).getType()),
                "ComplexGetReal complex is not a complex");
        return null;
    }

    @Override
    public Void visitComplexModulo(ComplexModulo complexModulo) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                complexModulo.getModulus().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.REAL),
                "ComplexModulo modulus is not a Real");
        validValueTypeIfTrue(ValueTypes.isComplex(complexModulo.getComplex().accept(inputVisitor).getType()),
                "ComplexGetReal complex is not a complex");
        return null;
    }

    @Override
    public Void visitFunctionCall(FunctionCall functionCall) throws FractalException {
        checkState();

        // check the function argument
        ValueType functionArg = functionCall.getFunction().accept(inputVisitor).getType();
        validValueTypeIfTrue(ValueTypes.isFunction(functionArg),
                "FunctionCall function argument is not a Function");
        validValueTypeIfTrue(!ValueTypes.isNullFunction(functionArg),
                "FunctionCall cannot call the null-function");
        ValueTypes.FunctionType functionType = ValueTypes.toFunction(functionArg);

        // check the return type
        validValueTypeIfTrue(
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
                throw new IncompatibleFunctionArgumentException("FunctionCall has extra arguments: " +
                        instructionArguments.subList(i, instructionArgumentsSize));
            }
            if (i >= instructionArgumentsSize) {
                throw new IncompatibleFunctionArgumentException(
                        "FunctionCall is missing arguments: " + targetArguments.subList(i, targetArgumentsSize));
            }
            ValueType targetArgument = targetArguments.get(i);
            IInstructionInput instructionArgument = instructionArguments.get(i);
            validIfTrue(targetArgument.isAssignableFrom(instructionArgument.accept(inputVisitor).getType()),
                    "Function defines argument: " + targetArgument + " but FunctionCall instruction supplies: " +
                            instructionArgument, IncompatibleFunctionArgumentException::new);
        }
        return null;
    }

    @Override
    public Void visitFunctionIsEqual(FunctionIsEqual functionIsEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                functionIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "FunctionIsEqual result is not a bool");
        validValueTypeIfTrue(ValueTypes.isFunction(functionIsEqual.getLeft().accept(inputVisitor).getType()),
                "FunctionIsEqual left is not a function");
        validValueTypeIfTrue(ValueTypes.isFunction(functionIsEqual.getRight().accept(inputVisitor).getType()),
                "FunctionIsEqual right is not a function");

        return null;
    }

    @Override
    public Void visitFunctionIsNotEqual(FunctionIsNotEqual functionIsNotEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                functionIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "FunctionIsNotEqual result is not a bool");
        validValueTypeIfTrue(ValueTypes.isFunction(functionIsNotEqual.getLeft().accept(inputVisitor).getType()),
                "FunctionIsNotEqual left is not a function");
        validValueTypeIfTrue(ValueTypes.isFunction(functionIsNotEqual.getRight().accept(inputVisitor).getType()),
                "FunctionIsNotEqual right is not a function");

        return null;
    }

    @Override
    public Void visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalException {
        checkState();
        // this feels somewhat inextensible to me
        validValueTypeIfTrue(ValueTypes.isPointer(pointerAllocate.getPointer().accept(outputVisitor).getType()),
                "PointerAllocate pointer argument is not a Pointer");
        return null;
    }

    @Override
    public Void visitPointerFree(PointerFree pointerFree) throws FractalException {
        checkState();
        ValueType pointerType = pointerFree.getPointer().accept(inputVisitor).getType();
        validValueTypeIfTrue(ValueTypes.isPointer(pointerType),
                "PointerFree pointer argument is not a Pointer");
        validValueTypeIfTrue(!ValueTypes.isNullPointer(pointerType),
                "PointerFree cannot free the null-pointer");
        return null;
    }

    @Override
    public Void visitPointerGet(PointerGet pointerGet) throws FractalException {
        checkState();
        ValueInfo pointerArg = pointerGet.getPointer().accept(inputVisitor);
        validValueTypeIfTrue(ValueTypes.isPointer(pointerArg.getType()),
                "PointerGet pointer argument is not a Pointer");
        ValueTypes.PointerType pointerType = ValueTypes.toPointer(pointerArg.getType());
        validValueTypeIfTrue(!ValueTypes.isNullPointer(pointerType),
                "PointerGet cannot get the value of the null-pointer");
        validValueTypeIfTrue(
                pointerGet.getData().accept(outputVisitor).getType().isAssignableFrom(pointerType.getPointerType()),
                "PointerGet pointer type and data type are incompatible");
        return null;
    }

    @Override
    public Void visitPointerSet(PointerSet pointerSet) throws FractalException {
        checkState();
        ValueInfo pointerArg = pointerSet.getPointer().accept(inputVisitor);
        validValueTypeIfTrue(ValueTypes.isPointer(pointerArg.getType()),
                "PointerSet pointer argument is not a Pointer");
        ValueTypes.PointerType pointerType = ValueTypes.toPointer(pointerArg.getType());
        validValueTypeIfTrue(!ValueTypes.isNullPointer(pointerType),
                "PointerSet cannot set the value of the null-pointer");
        validValueTypeIfTrue(
                pointerType.getPointerType().isAssignableFrom(pointerSet.getData().accept(inputVisitor).getType()),
                "PointerSet pointer type and data type are incompatible");
        return null;
    }

    @Override
    public Void visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                pointerIsEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "PointerIsEqual result is not a Bool");
        validValueTypeIfTrue(ValueTypes.isPointer(pointerIsEqual.getLeft().accept(inputVisitor).getType()),
                "PointerIsEqual left is not a Pointer");
        validValueTypeIfTrue(ValueTypes.isPointer(pointerIsEqual.getRight().accept(inputVisitor).getType()),
                "PointerIsEqual right is not a Pointer");
        return null;
    }

    @Override
    public Void visitPointerIsNotEqual(PointerIsNotEqual pointerIsNotEqual) throws FractalException {
        checkState();
        validValueTypeIfTrue(
                pointerIsNotEqual.getResult().accept(outputVisitor).getType().isAssignableFrom(ValueTypes.BOOL),
                "PointerIsNotEqual result is not a Bool");
        validValueTypeIfTrue(ValueTypes.isPointer(pointerIsNotEqual.getLeft().accept(inputVisitor).getType()),
                "PointerIsNotEqual left is not a Pointer");
        validValueTypeIfTrue(ValueTypes.isPointer(pointerIsNotEqual.getRight().accept(inputVisitor).getType()),
                "PointerIsNotEqual right is not a Pointer");
        return null;
    }

    @Override
    public Void visitIf(If anIf) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isBool(anIf.getCondition().accept(inputVisitor).getType()),
                "If condition is not a Bool");

        // check the true condition
        ValidatingInstructionVisitor ifTrueVisitor =
                new ValidatingInstructionVisitor(functions, globalVariables, contextVariables, arguments,
                        localVariables, returnType);
        for (IInstruction instruction : anIf.getIfTrue()) {
            instruction.accept(ifTrueVisitor);
        }

        // check the false condition
        ValidatingInstructionVisitor ifFalseVisitor =
                new ValidatingInstructionVisitor(functions, globalVariables, contextVariables, arguments,
                        localVariables, returnType);
        for (IInstruction instruction : anIf.getIfFalse()) {
            instruction.accept(ifFalseVisitor);
        }

        // if both conditions return, then we return
        returned = ifTrueVisitor.isReturned() && ifFalseVisitor.isReturned();

        return null;
    }

    @Override
    public Void visitWhile(While aWhile) throws FractalException {
        checkState();
        validValueTypeIfTrue(ValueTypes.isBool(aWhile.getCondition().accept(inputVisitor).getType()),
                "While condition is not a Bool");

        // check the loop instructions
        ValidatingInstructionVisitor loopVisitor =
                new ValidatingInstructionVisitor(functions, globalVariables, contextVariables, arguments,
                        localVariables, returnType);
        for (IInstruction instruction : aWhile.getWhileTrue()) {
            instruction.accept(loopVisitor);
        }

        // if the while returned, then we return
        returned = loopVisitor.isReturned();

        return null;
    }
}
