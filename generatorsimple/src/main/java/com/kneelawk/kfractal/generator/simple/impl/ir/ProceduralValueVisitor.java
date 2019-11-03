package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.*;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentScope;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionScope;
import com.kneelawk.kfractal.generator.simple.impl.*;
import org.apache.commons.math3.complex.Complex;

import java.util.List;

/**
 * Created by Kneelawk on 7/18/19.
 */
public class ProceduralValueVisitor implements IProceduralValueVisitor<ProceduralValueVisitorResult> {
    private final ValueManager valueManager;
    private final ProceduralValueVisitorContext context;

    public ProceduralValueVisitor(ValueManager valueManager,
                                  ProceduralValueVisitorContext context) {
        this.valueManager = valueManager;
        this.context = context;
    }

    private IEngineValue shouldNotTerminate(ProceduralValueVisitorResult result) throws FractalEngineException {
        if (result.isTerminated()) {
            throw new FractalEngineException("Encountered an unexpected terminating value");
        }
        return result.getResultValue();
    }

    @Override
    public ProceduralValueVisitorResult visitArgumentReference(ArgumentReference argumentReference) {
        int index = argumentReference.getIndex();
        ArgumentScope scope = argumentReference.getScope();
        switch (scope) {
            case CONTEXT:
                return ProceduralValueVisitorResult.create(context.getContextVariables().get(index));
            case ARGUMENTS:
                return ProceduralValueVisitorResult.create(context.getArguments().get(index));
            default:
                throw new IllegalStateException("Unexpected value: " + scope);
        }
    }

    @Override
    public ProceduralValueVisitorResult visitInstructionReference(InstructionReference instructionReference)
            throws FractalException {
        int blockIndex = instructionReference.getBlockIndex();
        InstructionScope scope = instructionReference.getScope();
        int instructionIndex = instructionReference.getInstructionIndex();
        BasicBlock block = context.getFunction().getBlocks().get(blockIndex);
        switch (scope) {
            case PHI:
                return ProceduralValueVisitorResult
                        .create(valueManager.getValue(block.getPhis().get(instructionIndex)));
            case BODY:
                return ProceduralValueVisitorResult
                        .create(valueManager.getValue(block.getBody().get(instructionIndex)));
            default:
                throw new IllegalStateException("Unexpected value: " + scope);
        }
    }

    @Override
    public ProceduralValueVisitorResult visitBoolConstant(BoolConstant constant) {
        return ProceduralValueVisitorResult.create(new SimpleBoolValue(constant.isValue()));
    }

    @Override
    public ProceduralValueVisitorResult visitIntConstant(IntConstant constant) {
        return ProceduralValueVisitorResult.create(new SimpleIntValue(constant.getValue()));
    }

    @Override
    public ProceduralValueVisitorResult visitRealConstant(RealConstant constant) {
        return ProceduralValueVisitorResult.create(new SimpleRealValue(constant.getValue()));
    }

    @Override
    public ProceduralValueVisitorResult visitComplexConstant(ComplexConstant constant) {
        return ProceduralValueVisitorResult.create(new SimpleComplexValue(constant.getValue()));
    }

    @Override
    public ProceduralValueVisitorResult visitNullPointer() {
        return ProceduralValueVisitorResult.create(SimpleNullPointerValue.INSTANCE);
    }

    @Override
    public ProceduralValueVisitorResult visitNullFunction() {
        return ProceduralValueVisitorResult.create(SimpleNullFunctionValue.INSTANCE);
    }

    @Override
    public ProceduralValueVisitorResult visitVoid() {
        return ProceduralValueVisitorResult.create(SimpleVoidValue.INSTANCE);
    }

    @Override
    public ProceduralValueVisitorResult visitGlobalGet(GlobalGet globalGet) {
        return ProceduralValueVisitorResult
                .create(context.getGlobalVariables().get(globalGet.getGlobalIndex()).getValue());
    }

    @Override
    public ProceduralValueVisitorResult visitGlobalSet(GlobalSet globalSet) throws FractalException {
        IEngineValue value = shouldNotTerminate(valueManager.getOrGenerateValue(globalSet.getData(), context));
        context.getGlobalVariables().get(globalSet.getGlobalIndex()).setValue(value);
        return ProceduralValueVisitorResult.create(SimpleVoidValue.INSTANCE);
    }

    @Override
    public ProceduralValueVisitorResult visitReturn(Return aReturn) throws FractalException {
        IEngineValue returnValue = shouldNotTerminate(valueManager.getOrGenerateValue(aReturn.getReturnValue(), context));
        return new ProceduralValueVisitorResult.Builder().setResultValue(SimpleVoidValue.INSTANCE).setTerminated(true)
                .setTerminationType(BlockTerminationType.RETURNED).setReturnValue(returnValue).build();
    }

    @Override
    public ProceduralValueVisitorResult visitBoolNot(BoolNot boolNot) throws FractalException {
        IEngineValue input = shouldNotTerminate(valueManager.getOrGenerateValue(boolNot.getInput(), context));
        IEngineValue output = new SimpleBoolValue(!((IBoolValue) input).getValue());
        return ProceduralValueVisitorResult.create(output);
    }

    @Override
    public ProceduralValueVisitorResult visitBoolAnd(BoolAnd boolAnd) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(boolAnd.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(boolAnd.getRight(), context));
        IEngineValue result = new SimpleBoolValue(((IBoolValue) left).getValue() && ((IBoolValue) right).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitBoolOr(BoolOr boolOr) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(boolOr.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(boolOr.getRight(), context));
        IEngineValue result = new SimpleBoolValue(((IBoolValue) left).getValue() || ((IBoolValue) right).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(boolIsEqual.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(boolIsEqual.getRight(), context));
        IEngineValue result = new SimpleBoolValue(((IBoolValue) left).getValue() == ((IBoolValue) right).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitIntAdd(IntAdd intAdd) throws FractalException {
        IEngineValue leftAddend = shouldNotTerminate(valueManager.getOrGenerateValue(intAdd.getLeftAddend(), context));
        IEngineValue rightAddend = shouldNotTerminate(valueManager.getOrGenerateValue(intAdd.getRightAddend(), context));
        IEngineValue sum =
                new SimpleIntValue(((IIntValue) leftAddend).getValue() + ((IIntValue) rightAddend).getValue());
        return ProceduralValueVisitorResult.create(sum);
    }

    @Override
    public ProceduralValueVisitorResult visitIntSubtract(IntSubtract intSubtract) throws FractalException {
        IEngineValue minuend = shouldNotTerminate(valueManager.getOrGenerateValue(intSubtract.getMinuend(), context));
        IEngineValue subtrahend = shouldNotTerminate(valueManager.getOrGenerateValue(intSubtract.getSubtrahend(), context));
        IEngineValue difference =
                new SimpleIntValue(((IIntValue) minuend).getValue() - ((IIntValue) subtrahend).getValue());
        return ProceduralValueVisitorResult.create(difference);
    }

    @Override
    public ProceduralValueVisitorResult visitIntMultiply(IntMultiply intMultiply) throws FractalException {
        IEngineValue leftFactor = shouldNotTerminate(valueManager.getOrGenerateValue(intMultiply.getLeftFactor(), context));
        IEngineValue rightFactor = shouldNotTerminate(valueManager.getOrGenerateValue(intMultiply.getRightFactor(), context));
        IEngineValue product =
                new SimpleIntValue(((IIntValue) leftFactor).getValue() * ((IIntValue) rightFactor).getValue());
        return ProceduralValueVisitorResult.create(product);
    }

    @Override
    public ProceduralValueVisitorResult visitIntDivide(IntDivide intDivide) throws FractalException {
        IEngineValue dividend = shouldNotTerminate(valueManager.getOrGenerateValue(intDivide.getDividend(), context));
        IEngineValue divisor = shouldNotTerminate(valueManager.getOrGenerateValue(intDivide.getDivisor(), context));
        IEngineValue quotient =
                new SimpleIntValue(((IIntValue) dividend).getValue() / ((IIntValue) divisor).getValue());
        return ProceduralValueVisitorResult.create(quotient);
    }

    @Override
    public ProceduralValueVisitorResult visitIntModulo(IntModulo intModulo) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(intModulo.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(intModulo.getRight(), context));
        IEngineValue result = new SimpleIntValue(((IIntValue) left).getValue() % ((IIntValue) right).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitIntPower(IntPower intPower) throws FractalException {
        IEngineValue leftAddend = shouldNotTerminate(valueManager.getOrGenerateValue(intPower.getBase(), context));
        IEngineValue rightAddend = shouldNotTerminate(valueManager.getOrGenerateValue(intPower.getExponent(), context));
        IEngineValue sum = new SimpleIntValue(
                (int) Math.pow(((IIntValue) leftAddend).getValue(), ((IIntValue) rightAddend).getValue()));
        return ProceduralValueVisitorResult.create(sum);
    }

    @Override
    public ProceduralValueVisitorResult visitIntNot(IntNot intNot) throws FractalException {
        IEngineValue input = shouldNotTerminate(valueManager.getOrGenerateValue(intNot.getInput(), context));
        IEngineValue output = new SimpleIntValue(~((IIntValue) input).getValue());
        return ProceduralValueVisitorResult.create(output);
    }

    @Override
    public ProceduralValueVisitorResult visitIntAnd(IntAnd intAnd) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(intAnd.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(intAnd.getRight(), context));
        IEngineValue result = new SimpleIntValue(((IIntValue) left).getValue() & ((IIntValue) right).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitIntOr(IntOr intOr) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(intOr.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(intOr.getRight(), context));
        IEngineValue result = new SimpleIntValue(((IIntValue) left).getValue() | ((IIntValue) right).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitIntXor(IntXor intXor) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(intXor.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(intXor.getRight(), context));
        IEngineValue result = new SimpleIntValue(((IIntValue) left).getValue() ^ ((IIntValue) right).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitIntIsEqual(IntIsEqual intIsEqual) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(intIsEqual.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(intIsEqual.getRight(), context));
        IEngineValue result = new SimpleBoolValue(((IIntValue) left).getValue() == ((IIntValue) right).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitIntIsGreater(IntIsGreater intIsGreater) throws FractalException {
        IEngineValue subject = shouldNotTerminate(valueManager.getOrGenerateValue(intIsGreater.getSubject(), context));
        IEngineValue basis = shouldNotTerminate(valueManager.getOrGenerateValue(intIsGreater.getBasis(), context));
        IEngineValue result = new SimpleBoolValue(((IIntValue) subject).getValue() > ((IIntValue) basis).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual)
            throws FractalException {
        IEngineValue subject = shouldNotTerminate(valueManager.getOrGenerateValue(intIsGreaterOrEqual.getSubject(), context));
        IEngineValue basis = shouldNotTerminate(valueManager.getOrGenerateValue(intIsGreaterOrEqual.getBasis(), context));
        IEngineValue result = new SimpleBoolValue(((IIntValue) subject).getValue() >= ((IIntValue) basis).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitRealAdd(RealAdd realAdd) throws FractalException {
        IEngineValue leftAddend = shouldNotTerminate(valueManager.getOrGenerateValue(realAdd.getLeftAddend(), context));
        IEngineValue rightAddend = shouldNotTerminate(valueManager.getOrGenerateValue(realAdd.getRightAddend(), context));
        IEngineValue sum =
                new SimpleRealValue(((IRealValue) leftAddend).getValue() + ((IRealValue) rightAddend).getValue());
        return ProceduralValueVisitorResult.create(sum);
    }

    @Override
    public ProceduralValueVisitorResult visitRealSubtract(RealSubtract realSubtract) throws FractalException {
        IEngineValue minuend = shouldNotTerminate(valueManager.getOrGenerateValue(realSubtract.getMinuend(), context));
        IEngineValue subtrahend = shouldNotTerminate(valueManager.getOrGenerateValue(realSubtract.getSubtrahend(), context));
        IEngineValue difference =
                new SimpleRealValue(((IRealValue) minuend).getValue() - ((IRealValue) subtrahend).getValue());
        return ProceduralValueVisitorResult.create(difference);
    }

    @Override
    public ProceduralValueVisitorResult visitRealMultiply(RealMultiply realMultiply) throws FractalException {
        IEngineValue leftFactor = shouldNotTerminate(valueManager.getOrGenerateValue(realMultiply.getLeftFactor(), context));
        IEngineValue rightFactor = shouldNotTerminate(valueManager.getOrGenerateValue(realMultiply.getRightFactor(), context));
        IEngineValue product =
                new SimpleRealValue(((IRealValue) leftFactor).getValue() * ((IRealValue) rightFactor).getValue());
        return ProceduralValueVisitorResult.create(product);
    }

    @Override
    public ProceduralValueVisitorResult visitRealDivide(RealDivide realDivide) throws FractalException {
        IEngineValue dividend = shouldNotTerminate(valueManager.getOrGenerateValue(realDivide.getDividend(), context));
        IEngineValue divisor = shouldNotTerminate(valueManager.getOrGenerateValue(realDivide.getDivisor(), context));
        IEngineValue quotient =
                new SimpleRealValue(((IRealValue) dividend).getValue() / ((IRealValue) divisor).getValue());
        return ProceduralValueVisitorResult.create(quotient);
    }

    @Override
    public ProceduralValueVisitorResult visitRealPower(RealPower realPower) throws FractalException {
        IEngineValue base = shouldNotTerminate(valueManager.getOrGenerateValue(realPower.getBase(), context));
        IEngineValue exponent = shouldNotTerminate(valueManager.getOrGenerateValue(realPower.getExponent(), context));
        IEngineValue result =
                new SimpleRealValue(Math.pow(((IRealValue) base).getValue(), ((IRealValue) exponent).getValue()));
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitRealIsEqual(RealIsEqual realIsEqual) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(realIsEqual.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(realIsEqual.getRight(), context));
        IEngineValue result = new SimpleBoolValue(((IRealValue) left).getValue() == ((IRealValue) right).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitRealIsGreater(RealIsGreater realIsGreater) throws FractalException {
        IEngineValue subject = shouldNotTerminate(valueManager.getOrGenerateValue(realIsGreater.getSubject(), context));
        IEngineValue basis = shouldNotTerminate(valueManager.getOrGenerateValue(realIsGreater.getBasis(), context));
        IEngineValue result = new SimpleBoolValue(((IRealValue) subject).getValue() > ((IRealValue) basis).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual)
            throws FractalException {
        IEngineValue subject = shouldNotTerminate(valueManager.getOrGenerateValue(realIsGreaterOrEqual.getSubject(), context));
        IEngineValue basis = shouldNotTerminate(valueManager.getOrGenerateValue(realIsGreaterOrEqual.getBasis(), context));
        IEngineValue result = new SimpleBoolValue(((IRealValue) subject).getValue() >= ((IRealValue) basis).getValue());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitRealComposeComplex(RealComposeComplex realComposeComplex)
            throws FractalException {
        IEngineValue real = shouldNotTerminate(valueManager.getOrGenerateValue(realComposeComplex.getReal(), context));
        IEngineValue imaginary = shouldNotTerminate(valueManager.getOrGenerateValue(realComposeComplex.getImaginary(), context));
        IEngineValue complex = new SimpleComplexValue(
                new Complex(((IRealValue) real).getValue(), ((IRealValue) imaginary).getValue()));
        return ProceduralValueVisitorResult.create(complex);
    }

    @Override
    public ProceduralValueVisitorResult visitComplexAdd(ComplexAdd complexAdd) throws FractalException {
        IEngineValue leftAddend = shouldNotTerminate(valueManager.getOrGenerateValue(complexAdd.getLeftAddend(), context));
        IEngineValue rightAddend = shouldNotTerminate(valueManager.getOrGenerateValue(complexAdd.getRightAddend(), context));
        IEngineValue sum = new SimpleComplexValue(
                ((IComplexValue) leftAddend).getValue().add(((IComplexValue) rightAddend).getValue()));
        return ProceduralValueVisitorResult.create(sum);
    }

    @Override
    public ProceduralValueVisitorResult visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalException {
        IEngineValue minuend = shouldNotTerminate(valueManager.getOrGenerateValue(complexSubtract.getMinuend(), context));
        IEngineValue subtrahend = shouldNotTerminate(valueManager.getOrGenerateValue(complexSubtract.getSubtrahend(), context));
        IEngineValue difference = new SimpleComplexValue(
                ((IComplexValue) minuend).getValue().subtract(((IComplexValue) subtrahend).getValue()));
        return ProceduralValueVisitorResult.create(difference);
    }

    @Override
    public ProceduralValueVisitorResult visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalException {
        IEngineValue leftFactor = shouldNotTerminate(valueManager.getOrGenerateValue(complexMultiply.getLeftFactor(), context));
        IEngineValue rightFactor =
                shouldNotTerminate(valueManager.getOrGenerateValue(complexMultiply.getRightFactor(), context));
        IEngineValue product = new SimpleComplexValue(
                ((IComplexValue) leftFactor).getValue().multiply(((IComplexValue) rightFactor).getValue()));
        return ProceduralValueVisitorResult.create(product);
    }

    @Override
    public ProceduralValueVisitorResult visitComplexDivide(ComplexDivide complexDivide) throws FractalException {
        IEngineValue dividend = shouldNotTerminate(valueManager.getOrGenerateValue(complexDivide.getDividend(), context));
        IEngineValue divisor = shouldNotTerminate(valueManager.getOrGenerateValue(complexDivide.getDivisor(), context));
        IEngineValue quotient = new SimpleComplexValue(
                ((IComplexValue) dividend).getValue().divide(((IComplexValue) divisor).getValue()));
        return ProceduralValueVisitorResult.create(quotient);
    }

    @Override
    public ProceduralValueVisitorResult visitComplexPower(ComplexPower complexPower) throws FractalException {
        IEngineValue base = shouldNotTerminate(valueManager.getOrGenerateValue(complexPower.getBase(), context));
        IEngineValue exponent = shouldNotTerminate(valueManager.getOrGenerateValue(complexPower.getExponent(), context));
        IEngineValue result = new SimpleComplexValue(
                ((IComplexValue) base).getValue().pow(((IComplexValue) exponent).getValue()));
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalException {
        IEngineValue complex = shouldNotTerminate(valueManager.getOrGenerateValue(complexGetReal.getComplex(), context));
        IEngineValue real = new SimpleRealValue(((IComplexValue) complex).getValue().getReal());
        return ProceduralValueVisitorResult.create(real);
    }

    @Override
    public ProceduralValueVisitorResult visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary)
            throws FractalException {
        IEngineValue complex = shouldNotTerminate(valueManager.getOrGenerateValue(complexGetImaginary.getComplex(), context));
        IEngineValue imaginary = new SimpleRealValue(((IComplexValue) complex).getValue().getImaginary());
        return ProceduralValueVisitorResult.create(imaginary);
    }

    @Override
    public ProceduralValueVisitorResult visitComplexModulo(ComplexModulo complexModulo) throws FractalException {
        IEngineValue complex = shouldNotTerminate(valueManager.getOrGenerateValue(complexModulo.getComplex(), context));
        IEngineValue modulus = new SimpleRealValue(((IComplexValue) complex).getValue().abs());
        return ProceduralValueVisitorResult.create(modulus);
    }

    @Override
    public ProceduralValueVisitorResult visitComplexIsEqual(ComplexIsEqual complexIsEqual) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(complexIsEqual.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(complexIsEqual.getRight(), context));
        IEngineValue result =
                new SimpleBoolValue(((IComplexValue) left).getValue().equals(((IComplexValue) right).getValue()));
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitFunctionCreate(FunctionCreate functionCreate) throws FractalException {
        List<IProceduralValue> contextInputs = functionCreate.getContextVariables();
        ImmutableList.Builder<IEngineValue> contextValues = ImmutableList.builder();
        for (IProceduralValue input : contextInputs) {
            contextValues.add(shouldNotTerminate(valueManager.getOrGenerateValue(input, context)));
        }
        return ProceduralValueVisitorResult
                .create(context.getEngine().getFunction(functionCreate.getFunctionIndex(), contextValues.build()));
    }

    @Override
    public ProceduralValueVisitorResult visitFunctionCall(FunctionCall functionCall) throws FractalException {
        ImmutableList.Builder<IEngineValue> argumentsBuilder = ImmutableList.builder();
        for (IProceduralValue argument : functionCall.getArguments()) {
            argumentsBuilder.add(shouldNotTerminate(valueManager.getOrGenerateValue(argument, context)));
        }
        IEngineValue function = shouldNotTerminate(valueManager.getOrGenerateValue(functionCall.getFunction(), context));
        IEngineValue result = ((IFunctionValue) function).invoke(argumentsBuilder.build());
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitFunctionIsEqual(FunctionIsEqual functionIsEqual) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(functionIsEqual.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(functionIsEqual.getRight(), context));
        IEngineValue result = new SimpleBoolValue(left.equals(right));
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitPointerAllocate(PointerAllocate pointerAllocate) {
        ValueType containedType = pointerAllocate.getType();
        IEngineValue pointer = new SimplePointerValue(getDefaultValue(containedType));
        return ProceduralValueVisitorResult.create(pointer);
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
    public ProceduralValueVisitorResult visitPointerFree(PointerFree pointerFree) {
        // There could be some kind of mechanism for releasing the resources contained within pointers
        return ProceduralValueVisitorResult.create(SimpleVoidValue.INSTANCE);
    }

    @Override
    public ProceduralValueVisitorResult visitPointerGet(PointerGet pointerGet) throws FractalException {
        IPointerValue pointer =
                (IPointerValue) shouldNotTerminate(valueManager.getOrGenerateValue(pointerGet.getPointer(), context));
        IEngineValue data = pointer.getReferencedData();
        return ProceduralValueVisitorResult.create(data);
    }

    @Override
    public ProceduralValueVisitorResult visitPointerSet(PointerSet pointerSet) throws FractalException {
        IEngineValue data = shouldNotTerminate(valueManager.getOrGenerateValue(pointerSet.getData(), context));
        IPointerValue pointer =
                (IPointerValue) shouldNotTerminate(valueManager.getOrGenerateValue(pointerSet.getPointer(), context));
        pointer.setReferencedData(data);
        return ProceduralValueVisitorResult.create(SimpleVoidValue.INSTANCE);
    }

    @Override
    public ProceduralValueVisitorResult visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalException {
        IEngineValue left = shouldNotTerminate(valueManager.getOrGenerateValue(pointerIsEqual.getLeft(), context));
        IEngineValue right = shouldNotTerminate(valueManager.getOrGenerateValue(pointerIsEqual.getRight(), context));
        IEngineValue result = new SimpleBoolValue(left == right);
        return ProceduralValueVisitorResult.create(result);
    }

    @Override
    public ProceduralValueVisitorResult visitBranchConditional(BranchConditional branchConditional)
            throws FractalException {
        IEngineValue condition = shouldNotTerminate(valueManager.getOrGenerateValue(branchConditional.getCondition(), context));
        int blockIndex;
        if (((IBoolValue) condition).getValue()) {
            blockIndex = branchConditional.getTrueBlockIndex();
        } else {
            blockIndex = branchConditional.getFalseBlockIndex();
        }
        return new ProceduralValueVisitorResult.Builder().setResultValue(SimpleVoidValue.INSTANCE).setTerminated(true)
                .setTerminationType(BlockTerminationType.JUMPED).setJumpBlockIndex(blockIndex).build();
    }

    @Override
    public ProceduralValueVisitorResult visitBranch(Branch branch) {
        return new ProceduralValueVisitorResult.Builder().setResultValue(SimpleVoidValue.INSTANCE).setTerminated(true)
                .setTerminationType(BlockTerminationType.JUMPED).setJumpBlockIndex(branch.getBlockIndex()).build();
    }
}
