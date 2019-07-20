package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.*;
import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;
import com.kneelawk.kfractal.generator.simple.impl.*;
import org.apache.commons.math3.complex.Complex;

import java.util.Map;

/**
 * Created by Kneelawk on 7/18/19.
 */
public class SimpleGeneratorInstructionVisitor implements IInstructionVisitor<Boolean> {
    private SimpleProgramEngine engine;
    private Map<String, ValueContainer> scope;

    private SimpleGeneratorInstructionInputVisitor inputVisitor;

    private IEngineValue returnValue = SimpleVoidValue.INSTANCE;

    public SimpleGeneratorInstructionVisitor(SimpleProgramEngine engine,
                                             Map<String, ValueContainer> scope) {
        this.engine = engine;
        this.scope = scope;
        inputVisitor = new SimpleGeneratorInstructionInputVisitor(engine, scope);
    }

    public IEngineValue getReturnValue() {
        return returnValue;
    }

    @Override
    public Boolean visitAssign(Assign assign) throws FractalIRException {
        IEngineValue value = assign.getSource().accept(inputVisitor);
        assign.getDest().accept(new SimpleGeneratorInstructionOutputVisitor(scope, value));
        return false;
    }

    @Override
    public Boolean visitReturn(Return aReturn) throws FractalIRException {
        returnValue = aReturn.getReturnValue().accept(inputVisitor);
        return true;
    }

    @Override
    public Boolean visitBoolNot(BoolNot boolNot) throws FractalIRException {
        IEngineValue input = boolNot.getInput().accept(inputVisitor);
        IEngineValue output;
        try {
            output = new SimpleBoolValue(!((IBoolValue) input).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        boolNot.getOutput().accept(new SimpleGeneratorInstructionOutputVisitor(scope, output));
        return false;
    }

    @Override
    public Boolean visitBoolAnd(BoolAnd boolAnd) throws FractalIRException {
        IEngineValue left = boolAnd.getLeft().accept(inputVisitor);
        IEngineValue right = boolAnd.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IBoolValue) left).getValue() && ((IBoolValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        boolAnd.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitBoolOr(BoolOr boolOr) throws FractalIRException {
        IEngineValue left = boolOr.getLeft().accept(inputVisitor);
        IEngineValue right = boolOr.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IBoolValue) left).getValue() || ((IBoolValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        boolOr.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalIRException {
        IEngineValue left = boolIsEqual.getLeft().accept(inputVisitor);
        IEngineValue right = boolIsEqual.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IBoolValue) left).getValue() == ((IBoolValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        boolIsEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalIRException {
        IEngineValue left = boolIsNotEqual.getLeft().accept(inputVisitor);
        IEngineValue right = boolIsNotEqual.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IBoolValue) left).getValue() != ((IBoolValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        boolIsNotEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntAdd(IntAdd intAdd) throws FractalIRException {
        IEngineValue leftAddend = intAdd.getLeftAddend().accept(inputVisitor);
        IEngineValue rightAddend = intAdd.getRightAddend().accept(inputVisitor);
        IEngineValue sum;
        try {
            sum = new SimpleIntValue(((IIntValue) leftAddend).getValue() + ((IIntValue) rightAddend).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intAdd.getSum().accept(new SimpleGeneratorInstructionOutputVisitor(scope, sum));
        return false;
    }

    @Override
    public Boolean visitIntSubtract(IntSubtract intSubtract) throws FractalIRException {
        IEngineValue minuend = intSubtract.getMinuend().accept(inputVisitor);
        IEngineValue subtrahend = intSubtract.getSubtrahend().accept(inputVisitor);
        IEngineValue difference;
        try {
            difference = new SimpleIntValue(((IIntValue) minuend).getValue() - ((IIntValue) subtrahend).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intSubtract.getDifference().accept(new SimpleGeneratorInstructionOutputVisitor(scope, difference));
        return false;
    }

    @Override
    public Boolean visitIntMultiply(IntMultiply intMultiply) throws FractalIRException {
        IEngineValue leftFactor = intMultiply.getLeftFactor().accept(inputVisitor);
        IEngineValue rightFactor = intMultiply.getRightFactor().accept(inputVisitor);
        IEngineValue product;
        try {
            product = new SimpleIntValue(((IIntValue) leftFactor).getValue() * ((IIntValue) rightFactor).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intMultiply.getProduct().accept(new SimpleGeneratorInstructionOutputVisitor(scope, product));
        return false;
    }

    @Override
    public Boolean visitIntDivide(IntDivide intDivide) throws FractalIRException {
        IEngineValue dividend = intDivide.getDividend().accept(inputVisitor);
        IEngineValue divisor = intDivide.getDivisor().accept(inputVisitor);
        IEngineValue quotient;
        try {
            quotient = new SimpleIntValue(((IIntValue) dividend).getValue() / ((IIntValue) divisor).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intDivide.getQuotient().accept(new SimpleGeneratorInstructionOutputVisitor(scope, quotient));
        return false;
    }

    @Override
    public Boolean visitIntModulo(IntModulo intModulo) throws FractalIRException {
        IEngineValue left = intModulo.getLeft().accept(inputVisitor);
        IEngineValue right = intModulo.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleIntValue(((IIntValue) left).getValue() % ((IIntValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intModulo.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntPower(IntPower intPower) throws FractalIRException {
        IEngineValue leftAddend = intPower.getBase().accept(inputVisitor);
        IEngineValue rightAddend = intPower.getExponent().accept(inputVisitor);
        IEngineValue sum;
        try {
            sum = new SimpleIntValue(
                    (int) Math.pow(((IIntValue) leftAddend).getValue(), ((IIntValue) rightAddend).getValue()));
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intPower.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, sum));
        return false;
    }

    @Override
    public Boolean visitIntNot(IntNot intNot) throws FractalIRException {
        IEngineValue input = intNot.getInput().accept(inputVisitor);
        IEngineValue output;
        try {
            output = new SimpleIntValue(~((IIntValue) input).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intNot.getOutput().accept(new SimpleGeneratorInstructionOutputVisitor(scope, output));
        return false;
    }

    @Override
    public Boolean visitIntAnd(IntAnd intAnd) throws FractalIRException {
        IEngineValue left = intAnd.getLeft().accept(inputVisitor);
        IEngineValue right = intAnd.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleIntValue(((IIntValue) left).getValue() & ((IIntValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intAnd.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntOr(IntOr intOr) throws FractalIRException {
        IEngineValue left = intOr.getLeft().accept(inputVisitor);
        IEngineValue right = intOr.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleIntValue(((IIntValue) left).getValue() | ((IIntValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intOr.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntXor(IntXor intXor) throws FractalIRException {
        IEngineValue left = intXor.getLeft().accept(inputVisitor);
        IEngineValue right = intXor.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleIntValue(((IIntValue) left).getValue() ^ ((IIntValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intXor.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntIsEqual(IntIsEqual intIsEqual) throws FractalIRException {
        IEngineValue left = intIsEqual.getLeft().accept(inputVisitor);
        IEngineValue right = intIsEqual.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IIntValue) left).getValue() == ((IIntValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intIsEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntIsNotEqual(IntIsNotEqual intIsNotEqual) throws FractalIRException {
        IEngineValue left = intIsNotEqual.getLeft().accept(inputVisitor);
        IEngineValue right = intIsNotEqual.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IIntValue) left).getValue() != ((IIntValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intIsNotEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntIsGreater(IntIsGreater intIsGreater) throws FractalIRException {
        IEngineValue subject = intIsGreater.getSubject().accept(inputVisitor);
        IEngineValue basis = intIsGreater.getBasis().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IIntValue) subject).getValue() > ((IIntValue) basis).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intIsGreater.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalIRException {
        IEngineValue subject = intIsGreaterOrEqual.getSubject().accept(inputVisitor);
        IEngineValue basis = intIsGreaterOrEqual.getBasis().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IIntValue) subject).getValue() >= ((IIntValue) basis).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        intIsGreaterOrEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealAdd(RealAdd realAdd) throws FractalIRException {
        IEngineValue leftAddend = realAdd.getLeftAddend().accept(inputVisitor);
        IEngineValue rightAddend = realAdd.getRightAddend().accept(inputVisitor);
        IEngineValue sum;
        try {
            sum = new SimpleRealValue(((IRealValue) leftAddend).getValue() + ((IRealValue) rightAddend).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        realAdd.getSum().accept(new SimpleGeneratorInstructionOutputVisitor(scope, sum));
        return false;
    }

    @Override
    public Boolean visitRealSubtract(RealSubtract realSubtract) throws FractalIRException {
        IEngineValue minuend = realSubtract.getMinuend().accept(inputVisitor);
        IEngineValue subtrahend = realSubtract.getSubtrahend().accept(inputVisitor);
        IEngineValue difference;
        try {
            difference = new SimpleRealValue(((IRealValue) minuend).getValue() - ((IRealValue) subtrahend).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        realSubtract.getDifference().accept(new SimpleGeneratorInstructionOutputVisitor(scope, difference));
        return false;
    }

    @Override
    public Boolean visitRealMultiply(RealMultiply realMultiply) throws FractalIRException {
        IEngineValue leftFactor = realMultiply.getLeftFactor().accept(inputVisitor);
        IEngineValue rightFactor = realMultiply.getRightFactor().accept(inputVisitor);
        IEngineValue product;
        try {
            product = new SimpleRealValue(((IRealValue) leftFactor).getValue() * ((IRealValue) rightFactor).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        realMultiply.getProduct().accept(new SimpleGeneratorInstructionOutputVisitor(scope, product));
        return false;
    }

    @Override
    public Boolean visitRealDivide(RealDivide realDivide) throws FractalIRException {
        IEngineValue dividend = realDivide.getDividend().accept(inputVisitor);
        IEngineValue divisor = realDivide.getDivisor().accept(inputVisitor);
        IEngineValue quotient;
        try {
            quotient = new SimpleRealValue(((IRealValue) dividend).getValue() / ((IRealValue) divisor).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        realDivide.getQuotient().accept(new SimpleGeneratorInstructionOutputVisitor(scope, quotient));
        return false;
    }

    @Override
    public Boolean visitRealPower(RealPower realPower) throws FractalIRException {
        IEngineValue base = realPower.getBase().accept(inputVisitor);
        IEngineValue exponent = realPower.getExponent().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleRealValue(Math.pow(((IRealValue) base).getValue(), ((IRealValue) exponent).getValue()));
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        realPower.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealIsEqual(RealIsEqual realIsEqual) throws FractalIRException {
        IEngineValue left = realIsEqual.getLeft().accept(inputVisitor);
        IEngineValue right = realIsEqual.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IRealValue) left).getValue() == ((IRealValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        realIsEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealIsNotEqual(RealIsNotEqual realIsNotEqual) throws FractalIRException {
        IEngineValue left = realIsNotEqual.getLeft().accept(inputVisitor);
        IEngineValue right = realIsNotEqual.getRight().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IRealValue) left).getValue() != ((IRealValue) right).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        realIsNotEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealIsGreater(RealIsGreater realIsGreater) throws FractalIRException {
        IEngineValue subject = realIsGreater.getSubject().accept(inputVisitor);
        IEngineValue basis = realIsGreater.getBasis().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IRealValue) subject).getValue() > ((IRealValue) basis).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        realIsGreater.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalIRException {
        IEngineValue subject = realIsGreaterOrEqual.getSubject().accept(inputVisitor);
        IEngineValue basis = realIsGreaterOrEqual.getBasis().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleBoolValue(((IRealValue) subject).getValue() >= ((IRealValue) basis).getValue());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        realIsGreaterOrEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalIRException {
        IEngineValue real = realComposeComplex.getReal().accept(inputVisitor);
        IEngineValue imaginary = realComposeComplex.getImaginary().accept(inputVisitor);
        IEngineValue complex;
        try {
            complex = new SimpleComplexValue(
                    new Complex(((IRealValue) real).getValue(), ((IRealValue) imaginary).getValue()));
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        realComposeComplex.getComplex().accept(new SimpleGeneratorInstructionOutputVisitor(scope, complex));
        return false;
    }

    @Override
    public Boolean visitComplexAdd(ComplexAdd complexAdd) throws FractalIRException {
        IEngineValue leftAddend = complexAdd.getLeftAddend().accept(inputVisitor);
        IEngineValue rightAddend = complexAdd.getRightAddend().accept(inputVisitor);
        IEngineValue sum;
        try {
            sum = new SimpleComplexValue(
                    ((IComplexValue) leftAddend).getValue().add(((IComplexValue) rightAddend).getValue()));
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        complexAdd.getSum().accept(new SimpleGeneratorInstructionOutputVisitor(scope, sum));
        return false;
    }

    @Override
    public Boolean visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalIRException {
        IEngineValue minuend = complexSubtract.getMinuend().accept(inputVisitor);
        IEngineValue subtrahend = complexSubtract.getSubtrahend().accept(inputVisitor);
        IEngineValue difference;
        try {
            difference = new SimpleComplexValue(
                    ((IComplexValue) minuend).getValue().subtract(((IComplexValue) subtrahend).getValue()));
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        complexSubtract.getDifference().accept(new SimpleGeneratorInstructionOutputVisitor(scope, difference));
        return false;
    }

    @Override
    public Boolean visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalIRException {
        IEngineValue leftFactor = complexMultiply.getLeftFactor().accept(inputVisitor);
        IEngineValue rightFactor = complexMultiply.getRightFactor().accept(inputVisitor);
        IEngineValue product;
        try {
            product = new SimpleComplexValue(
                    ((IComplexValue) leftFactor).getValue().multiply(((IComplexValue) rightFactor).getValue()));
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        complexMultiply.getProduct().accept(new SimpleGeneratorInstructionOutputVisitor(scope, product));
        return false;
    }

    @Override
    public Boolean visitComplexDivide(ComplexDivide complexDivide) throws FractalIRException {
        IEngineValue dividend = complexDivide.getDividend().accept(inputVisitor);
        IEngineValue divisor = complexDivide.getDivisor().accept(inputVisitor);
        IEngineValue quotient;
        try {
            quotient = new SimpleComplexValue(
                    ((IComplexValue) dividend).getValue().divide(((IComplexValue) divisor).getValue()));
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        complexDivide.getQuotient().accept(new SimpleGeneratorInstructionOutputVisitor(scope, quotient));
        return false;
    }

    @Override
    public Boolean visitComplexPower(ComplexPower complexPower) throws FractalIRException {
        IEngineValue base = complexPower.getBase().accept(inputVisitor);
        IEngineValue exponent = complexPower.getExponent().accept(inputVisitor);
        IEngineValue result;
        try {
            result = new SimpleComplexValue(
                    ((IComplexValue) base).getValue().pow(((IComplexValue) exponent).getValue()));
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        complexPower.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalIRException {
        IEngineValue complex = complexGetReal.getComplex().accept(inputVisitor);
        IEngineValue real;
        try {
            real = new SimpleRealValue(((IComplexValue) complex).getValue().getReal());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        complexGetReal.getReal().accept(new SimpleGeneratorInstructionOutputVisitor(scope, real));
        return false;
    }

    @Override
    public Boolean visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalIRException {
        IEngineValue complex = complexGetImaginary.getComplex().accept(inputVisitor);
        IEngineValue imaginary;
        try {
            imaginary = new SimpleRealValue(((IComplexValue) complex).getValue().getImaginary());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        complexGetImaginary.getImaginary().accept(new SimpleGeneratorInstructionOutputVisitor(scope, imaginary));
        return false;
    }

    @Override
    public Boolean visitComplexModulo(ComplexModulo complexModulo) throws FractalIRException {
        IEngineValue complex = complexModulo.getComplex().accept(inputVisitor);
        IEngineValue modulus;
        try {
            modulus = new SimpleRealValue(((IComplexValue) complex).getValue().abs());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        complexModulo.getModulus().accept(new SimpleGeneratorInstructionOutputVisitor(scope, modulus));
        return false;
    }

    @Override
    public Boolean visitFunctionCall(FunctionCall functionCall) throws FractalIRException {
        ImmutableList.Builder<IEngineValue> argumentsBuilder = ImmutableList.builder();
        for (IInstructionInput argument : functionCall.getArguments()) {
            argumentsBuilder.add(argument.accept(inputVisitor));
        }
        IEngineValue result;
        try {
            result =
                    ((IFunctionValue) functionCall.getFunction().accept(inputVisitor)).invoke(argumentsBuilder.build());
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        functionCall.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitFunctionIsEqual(FunctionIsEqual functionIsEqual) throws FractalIRException {
        IEngineValue left = functionIsEqual.getLeft().accept(inputVisitor);
        IEngineValue right = functionIsEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(left.equals(right));
        functionIsEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitFunctionIsNotEqual(FunctionIsNotEqual functionIsNotEqual) throws FractalIRException {
        IEngineValue left = functionIsNotEqual.getLeft().accept(inputVisitor);
        IEngineValue right = functionIsNotEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(!left.equals(right));
        functionIsNotEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalIRException {
        ValueType outputType = pointerAllocate.getPointer().accept(new IInstructionOutputVisitor<>() {
            @Override
            public ValueType visitVariableReference(VariableReference reference) {
                return scope.get(reference.getName()).getType();
            }

            @Override
            public ValueType visitVoid() {
                return ValueTypes.VOID;
            }
        });
        if (ValueTypes.isPointer(outputType)) {
            ValueTypes.PointerType pointerType = ValueTypes.toPointer(outputType);
            pointerAllocate.getPointer().accept(new SimpleGeneratorInstructionOutputVisitor(scope,
                    new SimplePointerValue(getDefaultValue(pointerType.getPointerType()))));
        }
        return false;
    }

    private IEngineValue getDefaultValue(ValueType type) {
        if (ValueTypes.isVoid(type)) {
            throw new RuntimeException("Unable to create a value type for void");
        } else if (ValueTypes.isBool(type)) {
            return new SimpleBoolValue(false);
        } else if (ValueTypes.isInt(type)) {
            return new SimpleIntValue(0);
        } else if (ValueTypes.isReal(type)) {
            return new SimpleRealValue(0);
        } else if (ValueTypes.isComplex(type)) {
            return new SimpleComplexValue(new Complex(0, 0));
        } else if (ValueTypes.isFunction(type)) {
            return SimpleNullFunctionValue.INSTANCE;
        } else if (ValueTypes.isPointer(type)) {
            return SimpleNullPointerValue.INSTANCE;
        } else {
            throw new RuntimeException("Unknown value type: " + type);
        }
    }

    @Override
    public Boolean visitPointerFree(PointerFree pointerFree) throws FractalIRException {
        pointerFree.getPointer().accept(new IInstructionInputVisitor<Void>() {
            @Override
            public Void visitVariableReference(VariableReference reference) {
                scope.get(reference.getName()).setValue(SimpleNullPointerValue.INSTANCE);
                return null;
            }

            @Override
            public Void visitBoolConstant(BoolConstant constant) {
                return null;
            }

            @Override
            public Void visitIntConstant(IntConstant constant) {
                return null;
            }

            @Override
            public Void visitRealConstant(RealConstant constant) {
                return null;
            }

            @Override
            public Void visitComplexConstant(ComplexConstant constant) {
                return null;
            }

            @Override
            public Void visitFunctionContextConstant(FunctionContextConstant contextConstant) {
                return null;
            }

            @Override
            public Void visitNullPointer() {
                return null;
            }

            @Override
            public Void visitNullFunction() {
                return null;
            }

            @Override
            public Void visitVoid() {
                return null;
            }
        });
        return false;
    }

    @Override
    public Boolean visitPointerGet(PointerGet pointerGet) throws FractalIRException {
        IPointerValue pointer = (IPointerValue) pointerGet.getPointer().accept(inputVisitor);
        IEngineValue data;
        try {
            data = pointer.getReferencedData();
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        pointerGet.getData().accept(new SimpleGeneratorInstructionOutputVisitor(scope, data));
        return false;
    }

    @Override
    public Boolean visitPointerSet(PointerSet pointerSet) throws FractalIRException {
        IEngineValue data = pointerSet.getData().accept(inputVisitor);
        IPointerValue pointer = (IPointerValue) pointerSet.getPointer().accept(inputVisitor);
        try {
            pointer.setReferencedData(data);
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        return false;
    }

    @Override
    public Boolean visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalIRException {
        IEngineValue left = pointerIsEqual.getLeft().accept(inputVisitor);
        IEngineValue right = pointerIsEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(left == right);
        pointerIsEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitPointerIsNotEqual(PointerIsNotEqual pointerIsNotEqual) throws FractalIRException {
        IEngineValue left = pointerIsNotEqual.getLeft().accept(inputVisitor);
        IEngineValue right = pointerIsNotEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(left != right);
        pointerIsNotEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIf(If anIf) throws FractalIRException {
        IBoolValue condition = (IBoolValue) anIf.getCondition().accept(inputVisitor);
        try {
            if (condition.getValue()) {
                for (IInstruction instruction : anIf.getIfTrue()) {
                    boolean ret = instruction.accept(this);
                    if (ret) {
                        return true;
                    }
                }
            } else {
                for (IInstruction instruction : anIf.getIfFalse()) {
                    boolean ret = instruction.accept(this);
                    if (ret) {
                        return true;
                    }
                }
            }
        } catch (FractalEngineException e) {
            throw new FractalIRException("FractalEngineException", e);
        }
        return false;
    }

    @Override
    public Boolean visitWhile(While aWhile) throws FractalIRException {
        while (true) {
            IBoolValue condition = (IBoolValue) aWhile.getCondition().accept(inputVisitor);
            try {
                if (!condition.getValue()) break;
            } catch (FractalEngineException e) {
                throw new FractalIRException("FractalEngineException", e);
            }
            for (IInstruction instruction : aWhile.getWhileTrue()) {
                boolean ret = instruction.accept(this);
                if (ret) {
                    return true;
                }
            }
        }
        return false;
    }
}
