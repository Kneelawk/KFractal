package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.value.*;
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
    public Boolean visitAssign(Assign assign) throws FractalException {
        IEngineValue value = assign.getSource().accept(inputVisitor);
        assign.getDest().accept(new SimpleGeneratorInstructionOutputVisitor(scope, value));
        return false;
    }

    @Override
    public Boolean visitReturn(Return aReturn) throws FractalException {
        returnValue = aReturn.getReturnValue().accept(inputVisitor);
        return true;
    }

    @Override
    public Boolean visitBoolNot(BoolNot boolNot) throws FractalException {
        IEngineValue input = boolNot.getInput().accept(inputVisitor);
        IEngineValue output = new SimpleBoolValue(!((IBoolValue) input).getValue());
        boolNot.getOutput().accept(new SimpleGeneratorInstructionOutputVisitor(scope, output));
        return false;
    }

    @Override
    public Boolean visitBoolAnd(BoolAnd boolAnd) throws FractalException {
        IEngineValue left = boolAnd.getLeft().accept(inputVisitor);
        IEngineValue right = boolAnd.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IBoolValue) left).getValue() && ((IBoolValue) right).getValue());
        boolAnd.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitBoolOr(BoolOr boolOr) throws FractalException {
        IEngineValue left = boolOr.getLeft().accept(inputVisitor);
        IEngineValue right = boolOr.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IBoolValue) left).getValue() || ((IBoolValue) right).getValue());
        boolOr.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalException {
        IEngineValue left = boolIsEqual.getLeft().accept(inputVisitor);
        IEngineValue right = boolIsEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IBoolValue) left).getValue() == ((IBoolValue) right).getValue());
        boolIsEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalException {
        IEngineValue left = boolIsNotEqual.getLeft().accept(inputVisitor);
        IEngineValue right = boolIsNotEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IBoolValue) left).getValue() != ((IBoolValue) right).getValue());
        boolIsNotEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntAdd(IntAdd intAdd) throws FractalException {
        IEngineValue leftAddend = intAdd.getLeftAddend().accept(inputVisitor);
        IEngineValue rightAddend = intAdd.getRightAddend().accept(inputVisitor);
        IEngineValue sum =
                new SimpleIntValue(((IIntValue) leftAddend).getValue() + ((IIntValue) rightAddend).getValue());
        intAdd.getSum().accept(new SimpleGeneratorInstructionOutputVisitor(scope, sum));
        return false;
    }

    @Override
    public Boolean visitIntSubtract(IntSubtract intSubtract) throws FractalException {
        IEngineValue minuend = intSubtract.getMinuend().accept(inputVisitor);
        IEngineValue subtrahend = intSubtract.getSubtrahend().accept(inputVisitor);
        IEngineValue difference =
                new SimpleIntValue(((IIntValue) minuend).getValue() - ((IIntValue) subtrahend).getValue());
        intSubtract.getDifference().accept(new SimpleGeneratorInstructionOutputVisitor(scope, difference));
        return false;
    }

    @Override
    public Boolean visitIntMultiply(IntMultiply intMultiply) throws FractalException {
        IEngineValue leftFactor = intMultiply.getLeftFactor().accept(inputVisitor);
        IEngineValue rightFactor = intMultiply.getRightFactor().accept(inputVisitor);
        IEngineValue product =
                new SimpleIntValue(((IIntValue) leftFactor).getValue() * ((IIntValue) rightFactor).getValue());
        intMultiply.getProduct().accept(new SimpleGeneratorInstructionOutputVisitor(scope, product));
        return false;
    }

    @Override
    public Boolean visitIntDivide(IntDivide intDivide) throws FractalException {
        IEngineValue dividend = intDivide.getDividend().accept(inputVisitor);
        IEngineValue divisor = intDivide.getDivisor().accept(inputVisitor);
        IEngineValue quotient =
                new SimpleIntValue(((IIntValue) dividend).getValue() / ((IIntValue) divisor).getValue());
        intDivide.getQuotient().accept(new SimpleGeneratorInstructionOutputVisitor(scope, quotient));
        return false;
    }

    @Override
    public Boolean visitIntModulo(IntModulo intModulo) throws FractalException {
        IEngineValue left = intModulo.getLeft().accept(inputVisitor);
        IEngineValue right = intModulo.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleIntValue(((IIntValue) left).getValue() % ((IIntValue) right).getValue());
        intModulo.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntPower(IntPower intPower) throws FractalException {
        IEngineValue leftAddend = intPower.getBase().accept(inputVisitor);
        IEngineValue rightAddend = intPower.getExponent().accept(inputVisitor);
        IEngineValue sum = new SimpleIntValue(
                (int) Math.pow(((IIntValue) leftAddend).getValue(), ((IIntValue) rightAddend).getValue()));
        intPower.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, sum));
        return false;
    }

    @Override
    public Boolean visitIntNot(IntNot intNot) throws FractalException {
        IEngineValue input = intNot.getInput().accept(inputVisitor);
        IEngineValue output = new SimpleIntValue(~((IIntValue) input).getValue());
        intNot.getOutput().accept(new SimpleGeneratorInstructionOutputVisitor(scope, output));
        return false;
    }

    @Override
    public Boolean visitIntAnd(IntAnd intAnd) throws FractalException {
        IEngineValue left = intAnd.getLeft().accept(inputVisitor);
        IEngineValue right = intAnd.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleIntValue(((IIntValue) left).getValue() & ((IIntValue) right).getValue());
        intAnd.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntOr(IntOr intOr) throws FractalException {
        IEngineValue left = intOr.getLeft().accept(inputVisitor);
        IEngineValue right = intOr.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleIntValue(((IIntValue) left).getValue() | ((IIntValue) right).getValue());
        intOr.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntXor(IntXor intXor) throws FractalException {
        IEngineValue left = intXor.getLeft().accept(inputVisitor);
        IEngineValue right = intXor.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleIntValue(((IIntValue) left).getValue() ^ ((IIntValue) right).getValue());
        intXor.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntIsEqual(IntIsEqual intIsEqual) throws FractalException {
        IEngineValue left = intIsEqual.getLeft().accept(inputVisitor);
        IEngineValue right = intIsEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IIntValue) left).getValue() == ((IIntValue) right).getValue());
        intIsEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntIsNotEqual(IntIsNotEqual intIsNotEqual) throws FractalException {
        IEngineValue left = intIsNotEqual.getLeft().accept(inputVisitor);
        IEngineValue right = intIsNotEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IIntValue) left).getValue() != ((IIntValue) right).getValue());
        intIsNotEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntIsGreater(IntIsGreater intIsGreater) throws FractalException {
        IEngineValue subject = intIsGreater.getSubject().accept(inputVisitor);
        IEngineValue basis = intIsGreater.getBasis().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IIntValue) subject).getValue() > ((IIntValue) basis).getValue());
        intIsGreater.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalException {
        IEngineValue subject = intIsGreaterOrEqual.getSubject().accept(inputVisitor);
        IEngineValue basis = intIsGreaterOrEqual.getBasis().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IIntValue) subject).getValue() >= ((IIntValue) basis).getValue());
        intIsGreaterOrEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealAdd(RealAdd realAdd) throws FractalException {
        IEngineValue leftAddend = realAdd.getLeftAddend().accept(inputVisitor);
        IEngineValue rightAddend = realAdd.getRightAddend().accept(inputVisitor);
        IEngineValue sum =
                new SimpleRealValue(((IRealValue) leftAddend).getValue() + ((IRealValue) rightAddend).getValue());
        realAdd.getSum().accept(new SimpleGeneratorInstructionOutputVisitor(scope, sum));
        return false;
    }

    @Override
    public Boolean visitRealSubtract(RealSubtract realSubtract) throws FractalException {
        IEngineValue minuend = realSubtract.getMinuend().accept(inputVisitor);
        IEngineValue subtrahend = realSubtract.getSubtrahend().accept(inputVisitor);
        IEngineValue difference =
                new SimpleRealValue(((IRealValue) minuend).getValue() - ((IRealValue) subtrahend).getValue());
        realSubtract.getDifference().accept(new SimpleGeneratorInstructionOutputVisitor(scope, difference));
        return false;
    }

    @Override
    public Boolean visitRealMultiply(RealMultiply realMultiply) throws FractalException {
        IEngineValue leftFactor = realMultiply.getLeftFactor().accept(inputVisitor);
        IEngineValue rightFactor = realMultiply.getRightFactor().accept(inputVisitor);
        IEngineValue product =
                new SimpleRealValue(((IRealValue) leftFactor).getValue() * ((IRealValue) rightFactor).getValue());
        realMultiply.getProduct().accept(new SimpleGeneratorInstructionOutputVisitor(scope, product));
        return false;
    }

    @Override
    public Boolean visitRealDivide(RealDivide realDivide) throws FractalException {
        IEngineValue dividend = realDivide.getDividend().accept(inputVisitor);
        IEngineValue divisor = realDivide.getDivisor().accept(inputVisitor);
        IEngineValue quotient =
                new SimpleRealValue(((IRealValue) dividend).getValue() / ((IRealValue) divisor).getValue());
        realDivide.getQuotient().accept(new SimpleGeneratorInstructionOutputVisitor(scope, quotient));
        return false;
    }

    @Override
    public Boolean visitRealPower(RealPower realPower) throws FractalException {
        IEngineValue base = realPower.getBase().accept(inputVisitor);
        IEngineValue exponent = realPower.getExponent().accept(inputVisitor);
        IEngineValue result =
                new SimpleRealValue(Math.pow(((IRealValue) base).getValue(), ((IRealValue) exponent).getValue()));
        realPower.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealIsEqual(RealIsEqual realIsEqual) throws FractalException {
        IEngineValue left = realIsEqual.getLeft().accept(inputVisitor);
        IEngineValue right = realIsEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IRealValue) left).getValue() == ((IRealValue) right).getValue());
        realIsEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealIsNotEqual(RealIsNotEqual realIsNotEqual) throws FractalException {
        IEngineValue left = realIsNotEqual.getLeft().accept(inputVisitor);
        IEngineValue right = realIsNotEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IRealValue) left).getValue() != ((IRealValue) right).getValue());
        realIsNotEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealIsGreater(RealIsGreater realIsGreater) throws FractalException {
        IEngineValue subject = realIsGreater.getSubject().accept(inputVisitor);
        IEngineValue basis = realIsGreater.getBasis().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IRealValue) subject).getValue() > ((IRealValue) basis).getValue());
        realIsGreater.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalException {
        IEngineValue subject = realIsGreaterOrEqual.getSubject().accept(inputVisitor);
        IEngineValue basis = realIsGreaterOrEqual.getBasis().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(((IRealValue) subject).getValue() >= ((IRealValue) basis).getValue());
        realIsGreaterOrEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalException {
        IEngineValue real = realComposeComplex.getReal().accept(inputVisitor);
        IEngineValue imaginary = realComposeComplex.getImaginary().accept(inputVisitor);
        IEngineValue complex = new SimpleComplexValue(
                new Complex(((IRealValue) real).getValue(), ((IRealValue) imaginary).getValue()));
        realComposeComplex.getComplex().accept(new SimpleGeneratorInstructionOutputVisitor(scope, complex));
        return false;
    }

    @Override
    public Boolean visitComplexAdd(ComplexAdd complexAdd) throws FractalException {
        IEngineValue leftAddend = complexAdd.getLeftAddend().accept(inputVisitor);
        IEngineValue rightAddend = complexAdd.getRightAddend().accept(inputVisitor);
        IEngineValue sum = new SimpleComplexValue(
                ((IComplexValue) leftAddend).getValue().add(((IComplexValue) rightAddend).getValue()));
        complexAdd.getSum().accept(new SimpleGeneratorInstructionOutputVisitor(scope, sum));
        return false;
    }

    @Override
    public Boolean visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalException {
        IEngineValue minuend = complexSubtract.getMinuend().accept(inputVisitor);
        IEngineValue subtrahend = complexSubtract.getSubtrahend().accept(inputVisitor);
        IEngineValue difference = new SimpleComplexValue(
                ((IComplexValue) minuend).getValue().subtract(((IComplexValue) subtrahend).getValue()));
        complexSubtract.getDifference().accept(new SimpleGeneratorInstructionOutputVisitor(scope, difference));
        return false;
    }

    @Override
    public Boolean visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalException {
        IEngineValue leftFactor = complexMultiply.getLeftFactor().accept(inputVisitor);
        IEngineValue rightFactor = complexMultiply.getRightFactor().accept(inputVisitor);
        IEngineValue product = new SimpleComplexValue(
                ((IComplexValue) leftFactor).getValue().multiply(((IComplexValue) rightFactor).getValue()));
        complexMultiply.getProduct().accept(new SimpleGeneratorInstructionOutputVisitor(scope, product));
        return false;
    }

    @Override
    public Boolean visitComplexDivide(ComplexDivide complexDivide) throws FractalException {
        IEngineValue dividend = complexDivide.getDividend().accept(inputVisitor);
        IEngineValue divisor = complexDivide.getDivisor().accept(inputVisitor);
        IEngineValue quotient = new SimpleComplexValue(
                ((IComplexValue) dividend).getValue().divide(((IComplexValue) divisor).getValue()));
        complexDivide.getQuotient().accept(new SimpleGeneratorInstructionOutputVisitor(scope, quotient));
        return false;
    }

    @Override
    public Boolean visitComplexPower(ComplexPower complexPower) throws FractalException {
        IEngineValue base = complexPower.getBase().accept(inputVisitor);
        IEngineValue exponent = complexPower.getExponent().accept(inputVisitor);
        IEngineValue result = new SimpleComplexValue(
                ((IComplexValue) base).getValue().pow(((IComplexValue) exponent).getValue()));
        complexPower.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalException {
        IEngineValue complex = complexGetReal.getComplex().accept(inputVisitor);
        IEngineValue real = new SimpleRealValue(((IComplexValue) complex).getValue().getReal());
        complexGetReal.getReal().accept(new SimpleGeneratorInstructionOutputVisitor(scope, real));
        return false;
    }

    @Override
    public Boolean visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalException {
        IEngineValue complex = complexGetImaginary.getComplex().accept(inputVisitor);
        IEngineValue imaginary = new SimpleRealValue(((IComplexValue) complex).getValue().getImaginary());
        complexGetImaginary.getImaginary().accept(new SimpleGeneratorInstructionOutputVisitor(scope, imaginary));
        return false;
    }

    @Override
    public Boolean visitComplexModulo(ComplexModulo complexModulo) throws FractalException {
        IEngineValue complex = complexModulo.getComplex().accept(inputVisitor);
        IEngineValue modulus = new SimpleRealValue(((IComplexValue) complex).getValue().abs());
        complexModulo.getModulus().accept(new SimpleGeneratorInstructionOutputVisitor(scope, modulus));
        return false;
    }

    @Override
    public Boolean visitFunctionCall(FunctionCall functionCall) throws FractalException {
        ImmutableList.Builder<IEngineValue> argumentsBuilder = ImmutableList.builder();
        for (IInstructionInput argument : functionCall.getArguments()) {
            argumentsBuilder.add(argument.accept(inputVisitor));
        }
        IEngineValue result =
                ((IFunctionValue) functionCall.getFunction().accept(inputVisitor)).invoke(argumentsBuilder.build());
        functionCall.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitFunctionIsEqual(FunctionIsEqual functionIsEqual) throws FractalException {
        IEngineValue left = functionIsEqual.getLeft().accept(inputVisitor);
        IEngineValue right = functionIsEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(left.equals(right));
        functionIsEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitFunctionIsNotEqual(FunctionIsNotEqual functionIsNotEqual) throws FractalException {
        IEngineValue left = functionIsNotEqual.getLeft().accept(inputVisitor);
        IEngineValue right = functionIsNotEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(!left.equals(right));
        functionIsNotEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalException {
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
    public Boolean visitPointerFree(PointerFree pointerFree) throws FractalException {
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
    public Boolean visitPointerGet(PointerGet pointerGet) throws FractalException {
        IPointerValue pointer = (IPointerValue) pointerGet.getPointer().accept(inputVisitor);
        IEngineValue data = pointer.getReferencedData();
        pointerGet.getData().accept(new SimpleGeneratorInstructionOutputVisitor(scope, data));
        return false;
    }

    @Override
    public Boolean visitPointerSet(PointerSet pointerSet) throws FractalException {
        IEngineValue data = pointerSet.getData().accept(inputVisitor);
        IPointerValue pointer = (IPointerValue) pointerSet.getPointer().accept(inputVisitor);
        pointer.setReferencedData(data);
        return false;
    }

    @Override
    public Boolean visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalException {
        IEngineValue left = pointerIsEqual.getLeft().accept(inputVisitor);
        IEngineValue right = pointerIsEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(left == right);
        pointerIsEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitPointerIsNotEqual(PointerIsNotEqual pointerIsNotEqual) throws FractalException {
        IEngineValue left = pointerIsNotEqual.getLeft().accept(inputVisitor);
        IEngineValue right = pointerIsNotEqual.getRight().accept(inputVisitor);
        IEngineValue result = new SimpleBoolValue(left != right);
        pointerIsNotEqual.getResult().accept(new SimpleGeneratorInstructionOutputVisitor(scope, result));
        return false;
    }

    @Override
    public Boolean visitIf(If anIf) throws FractalException {
        IBoolValue condition = (IBoolValue) anIf.getCondition().accept(inputVisitor);
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
        return false;
    }

    @Override
    public Boolean visitWhile(While aWhile) throws FractalException {
        while (true) {
            IBoolValue condition = (IBoolValue) aWhile.getCondition().accept(inputVisitor);
            if (!condition.getValue()) break;
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
