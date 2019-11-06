package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentScope;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;

import java.util.List;
import java.util.function.Function;

class ValidatingProceduralValueVisitor implements IProceduralValueVisitor<ValidatingVisitorResult> {
    private final TypeCache cache;
    private final ValidatingVisitorContext context;

    ValidatingProceduralValueVisitor(TypeCache cache, ValidatingVisitorContext context) {
        this.cache = cache;
        this.context = context;
    }

    private void checkState(ValidatingVisitorResult result) throws FractalIRException {
        if (result.isTerminated()) {
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

    private void tryVisit(IProceduralValue parent, IProceduralValue child) throws FractalException {
        if (context.getTraversedNodes().contains(parent)) {
            throw new CyclicProceduralInstructionException("Attempting to traverse a node that is its own ancestor");
        } else {
            ValidatingVisitorContext newContext = context.builder().addTraversedNode(parent).build();
            ValidatingProceduralValueVisitor visitor = new ValidatingProceduralValueVisitor(cache, newContext);
            if (newContext.getTraversedNodes().contains(child)) {
                throw new CyclicProceduralInstructionException("Attempting to traverse a node with an ancestor as one of its children");
            } else {
                checkState(child.accept(visitor));
            }
        }
    }

    private void tryVisit(IProceduralValue parent, IProceduralValue child1, IProceduralValue child2)
            throws FractalException {
        if (context.getTraversedNodes().contains(parent)) {
            throw new CyclicProceduralInstructionException("Attempting to traverse a node that is its own ancestor");
        } else {
            ValidatingVisitorContext newContext = context.builder().addTraversedNode(parent).build();
            ValidatingProceduralValueVisitor visitor = new ValidatingProceduralValueVisitor(cache, newContext);
            if (newContext.getTraversedNodes().contains(child1)) {
                throw new CyclicProceduralInstructionException("Attempting to traverse a node with an ancestor as one of its children");
            } else {
                checkState(child1.accept(visitor));
            }
            if (newContext.getTraversedNodes().contains(child2)) {
                throw new CyclicProceduralInstructionException("Attempting to traverse a node with an ancestor as one of its children");
            } else {
                checkState(child2.accept(visitor));
            }
        }
    }

    private void tryVisit(IProceduralValue parent, Iterable<IProceduralValue> children) throws FractalException {
        if (context.getTraversedNodes().contains(parent)) {
            throw new CyclicProceduralInstructionException("Attempting to traverse a node that is its own ancestor");
        } else {
            ValidatingVisitorContext newContext = context.builder().addTraversedNode(parent).build();
            ValidatingProceduralValueVisitor visitor = new ValidatingProceduralValueVisitor(cache, newContext);
            for (IProceduralValue child : children) {
                if (!newContext.getTraversedNodes().contains(child)) {
                    checkState(child.accept(visitor));
                } else {
                    throw new CyclicProceduralInstructionException("Attempting to traverse a node with an ancestor as one of its children");
                }
            }
        }
    }

    @Override
    public ValidatingVisitorResult visitArgumentReference(ArgumentReference argumentReference) throws FractalException {
        ArgumentScope argumentScope = argumentReference.getScope();
        int argumentIndex = argumentReference.getIndex();

        List<ArgumentDeclaration> scope;
        switch (argumentScope) {
            case CONTEXT:
                scope = context.getFunction().getContextVariables();
                break;
            case ARGUMENTS:
                scope = context.getFunction().getArguments();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + argumentScope);
        }

        if (argumentIndex < 0 || argumentIndex >= scope.size()) {
            throw new FractalIRValidationException("Invalid argument reference index");
        }

        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitInstructionReference(InstructionReference instructionReference)
            throws FractalException {
        List<BasicBlock> blocks = context.getFunction().getBlocks();
        int blockIndex = instructionReference.getBlockIndex();

        if (blockIndex < 0 || blockIndex >= blocks.size()) {
            throw new MissingVariableReferenceException("Invalid instruction reference block index");
        }

        if (blockIndex > context.getBlockIndex()) {
            throw new MissingVariableReferenceException(
                    "Normal instructions cannot reference instructions from subsequent blocks");
        }

        BasicBlock block = blocks.get(blockIndex);
        int instructionIndex = instructionReference.getInstructionIndex();

        if (blockIndex == context.getBlockIndex() && instructionIndex > context.getInstructionIndex()) {
            throw new MissingVariableReferenceException("Normal instructions cannot reference subsequent instructions");
        }

        switch (instructionReference.getScope()) {
            case PHI:
                if (instructionIndex < 0 || instructionIndex >= block.getPhis().size()) {
                    throw new MissingVariableReferenceException("Invalid instruction reference instruction index");
                }
                break;
            case BODY:
                if (instructionIndex < 0 || instructionIndex >= block.getBody().size()) {
                    throw new MissingVariableReferenceException("Invalid instruction reference instruction index");
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + instructionReference.getScope());
        }

        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitBoolConstant(BoolConstant constant) {
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntConstant(IntConstant constant) {
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitRealConstant(RealConstant constant) {
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitComplexConstant(ComplexConstant constant) {
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitNullPointer() {
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitNullFunction() {
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitVoid() {
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitGlobalGet(GlobalGet globalGet) throws FractalException {
        if (!context.getProgram().getGlobalVariables().containsKey(globalGet.getGlobalName())) {
            throw new MissingVariableReferenceException("Invalid global name");
        }
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitGlobalSet(GlobalSet globalSet) throws FractalException {
        String globalName = globalSet.getGlobalName();
        if (!context.getProgram().getGlobalVariables().containsKey(globalName)) {
            throw new MissingVariableReferenceException("Invalid global name");
        }
        validValueTypeIfTrue(context.getProgram().getGlobalVariables().get(globalName).getType()
                        .isAssignableFrom(cache.getType(globalSet.getData(), context)),
                "GlobalSet global and data are of incompatible types");
        tryVisit(globalSet, globalSet.getData());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitReturn(Return aReturn) throws FractalException {
        ValueType returnValue = cache.getType(aReturn.getReturnValue(), context);
        validValueTypeIfTrue(context.getFunction().getReturnType().isAssignableFrom(returnValue),
                "Unable to return incompatible type");
        tryVisit(aReturn, aReturn.getReturnValue());
        return ValidatingVisitorResult.create(true);
    }

    @Override
    public ValidatingVisitorResult visitBoolNot(BoolNot boolNot) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isBool(cache.getType(boolNot.getInput(), context)),
                "BoolNot input is not a bool");
        tryVisit(boolNot, boolNot.getInput());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitBoolAnd(BoolAnd boolAnd) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isBool(cache.getType(boolAnd.getLeft(), context)),
                "BoolAnd left is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(cache.getType(boolAnd.getRight(), context)),
                "BoolAnd right is not a bool");
        tryVisit(boolAnd, boolAnd.getLeft(), boolAnd.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitBoolOr(BoolOr boolOr) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isBool(cache.getType(boolOr.getLeft(), context)),
                "BoolOr left is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(cache.getType(boolOr.getRight(), context)),
                "BoolOr right is not a bool");
        tryVisit(boolOr, boolOr.getLeft(), boolOr.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isBool(cache.getType(boolIsEqual.getLeft(), context)),
                "BoolIsEqual left is not a bool");
        validValueTypeIfTrue(ValueTypes.isBool(cache.getType(boolIsEqual.getRight(), context)),
                "BoolIsEqual right is not a bool");
        tryVisit(boolIsEqual, boolIsEqual.getLeft(), boolIsEqual.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntAdd(IntAdd intAdd) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intAdd.getLeftAddend(), context)),
                "IntAdd left addend is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intAdd.getRightAddend(), context)),
                "IntAdd right addend is not an Int");
        tryVisit(intAdd, intAdd.getLeftAddend(), intAdd.getRightAddend());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntSubtract(IntSubtract intSubtract) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intSubtract.getMinuend(), context)),
                "IntSubtract minuend is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intSubtract.getSubtrahend(), context)),
                "IntSubtract subtrahend is not an Int");
        tryVisit(intSubtract, intSubtract.getMinuend(), intSubtract.getSubtrahend());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntMultiply(IntMultiply intMultiply) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intMultiply.getLeftFactor(), context)),
                "IntMultiply left factor is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intMultiply.getRightFactor(), context)),
                "IntMultiply right factor is not an Int");
        tryVisit(intMultiply, intMultiply.getLeftFactor(), intMultiply.getRightFactor());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntDivide(IntDivide intDivide) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intDivide.getDividend(), context)),
                "IntDivide dividend is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intDivide.getDivisor(), context)),
                "IntDivide divisor is not an Int");
        tryVisit(intDivide, intDivide.getDivisor(), intDivide.getDividend());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntModulo(IntModulo intModulo) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intModulo.getLeft(), context)),
                "IntModulo left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intModulo.getRight(), context)),
                "IntModulo right is not an Int");
        tryVisit(intModulo, intModulo.getLeft(), intModulo.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntPower(IntPower intPower) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intPower.getBase(), context)),
                "IntPower base is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intPower.getExponent(), context)),
                "IntPower exponent is not an Int");
        tryVisit(intPower, intPower.getBase(), intPower.getExponent());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntNot(IntNot intNot) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intNot.getInput(), context)),
                "IntNot input is not an Int");
        tryVisit(intNot, intNot.getInput());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntAnd(IntAnd intAnd) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intAnd.getLeft(), context)),
                "IntAnd left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intAnd.getRight(), context)),
                "IntAnd right is not an Int");
        tryVisit(intAnd, intAnd.getLeft(), intAnd.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntOr(IntOr intOr) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intOr.getLeft(), context)),
                "IntOr left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intOr.getRight(), context)),
                "IntOr right is not an Int");
        tryVisit(intOr, intOr.getLeft(), intOr.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntXor(IntXor intXor) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intXor.getLeft(), context)),
                "IntXor left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intXor.getRight(), context)),
                "IntXor right is not an Int");
        tryVisit(intXor, intXor.getLeft(), intXor.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntIsEqual(IntIsEqual intIsEqual) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intIsEqual.getLeft(), context)),
                "IntIsEqual left is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intIsEqual.getRight(), context)),
                "IntIsEqual right is not an Int");
        tryVisit(intIsEqual, intIsEqual.getLeft(), intIsEqual.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntIsGreater(IntIsGreater intIsGreater) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intIsGreater.getSubject(), context)),
                "IntIsGreater subject is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intIsGreater.getBasis(), context)),
                "IntIsGreater basis is not an Int");
        tryVisit(intIsGreater, intIsGreater.getSubject(), intIsGreater.getBasis());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual)
            throws FractalException {
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intIsGreaterOrEqual.getSubject(), context)),
                "IntIsGreaterOrEqual subject is not an Int");
        validValueTypeIfTrue(ValueTypes.isInt(cache.getType(intIsGreaterOrEqual.getBasis(), context)),
                "IntIsGreaterOrEqual is not an Int");
        tryVisit(intIsGreaterOrEqual, intIsGreaterOrEqual.getSubject(), intIsGreaterOrEqual.getBasis());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitRealAdd(RealAdd realAdd) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realAdd.getLeftAddend(), context)),
                "RealAdd left addend is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realAdd.getRightAddend(), context)),
                "RealAdd right addend is not a Real");
        tryVisit(realAdd, realAdd.getLeftAddend(), realAdd.getRightAddend());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitRealSubtract(RealSubtract realSubtract) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realSubtract.getMinuend(), context)),
                "RealSubtract minuend is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realSubtract.getSubtrahend(), context)),
                "RealSubtract subtract is not a Real");
        tryVisit(realSubtract, realSubtract.getMinuend(), realSubtract.getSubtrahend());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitRealMultiply(RealMultiply realMultiply) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realMultiply.getLeftFactor(), context)),
                "RealMultiply left factor is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realMultiply.getRightFactor(), context)),
                "RealMultiply right factor is not a Real");
        tryVisit(realMultiply, realMultiply.getLeftFactor(), realMultiply.getRightFactor());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitRealDivide(RealDivide realDivide) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realDivide.getDividend(), context)),
                "RealDivide dividend is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realDivide.getDivisor(), context)),
                "RealDivide divisor is not a Real");
        tryVisit(realDivide, realDivide.getDividend(), realDivide.getDivisor());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitRealPower(RealPower realPower) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realPower.getBase(), context)),
                "RealPower base is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realPower.getExponent(), context)),
                "RealPower exponent is not a Real");
        tryVisit(realPower, realPower.getBase(), realPower.getExponent());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitRealIsEqual(RealIsEqual realIsEqual) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realIsEqual.getLeft(), context)),
                "RealIsEqual left is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realIsEqual.getRight(), context)),
                "RealIsEqual right is not a Real");
        tryVisit(realIsEqual, realIsEqual.getLeft(), realIsEqual.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitRealIsGreater(RealIsGreater realIsGreater) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realIsGreater.getSubject(), context)),
                "RealIsGreater subject is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realIsGreater.getBasis(), context)),
                "RealIsGreater basis is not a Real");
        tryVisit(realIsGreater, realIsGreater.getSubject(), realIsGreater.getBasis());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual)
            throws FractalException {
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realIsGreaterOrEqual.getSubject(), context)),
                "RealIsGreaterOrEqual subject is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realIsGreaterOrEqual.getBasis(), context)),
                "RealIsGreaterOrEqual basis is not a Real");
        tryVisit(realIsGreaterOrEqual, realIsGreaterOrEqual.getSubject(), realIsGreaterOrEqual.getBasis());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitRealComposeComplex(RealComposeComplex realComposeComplex)
            throws FractalException {
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realComposeComplex.getReal(), context)),
                "RealComposeComplex real is not a Real");
        validValueTypeIfTrue(ValueTypes.isReal(cache.getType(realComposeComplex.getImaginary(), context)),
                "RealComposeComplex imaginary is not a Real");
        tryVisit(realComposeComplex, realComposeComplex.getReal(), realComposeComplex.getImaginary());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitComplexAdd(ComplexAdd complexAdd) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexAdd.getLeftAddend(), context)),
                "ComplexAdd left addend is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexAdd.getRightAddend(), context)),
                "ComplexAdd right addend is not a Complex");
        tryVisit(complexAdd, complexAdd.getLeftAddend(), complexAdd.getRightAddend());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexSubtract.getMinuend(), context)),
                "ComplexSubtract minuend is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexSubtract.getSubtrahend(), context)),
                "ComplexSubtract subtrahend is not a Complex");
        tryVisit(complexSubtract, complexSubtract.getMinuend(), complexSubtract.getSubtrahend());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexMultiply.getLeftFactor(), context)),
                "ComplexMultiply left factor is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexMultiply.getRightFactor(), context)),
                "ComplexMultiply right factor is not a Complex");
        tryVisit(complexMultiply, complexMultiply.getLeftFactor(), complexMultiply.getRightFactor());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitComplexDivide(ComplexDivide complexDivide) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexDivide.getDividend(), context)),
                "ComplexDivide dividend is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexDivide.getDivisor(), context)),
                "ComplexDivide divisor is not a Complex");
        tryVisit(complexDivide, complexDivide.getDividend(), complexDivide.getDivisor());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitComplexPower(ComplexPower complexPower) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexPower.getBase(), context)),
                "ComplexPower base is not a Complex");
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexPower.getExponent(), context)),
                "ComplexPower exponent is not a Complex");
        tryVisit(complexPower, complexPower.getBase(), complexPower.getExponent());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexGetReal.getComplex(), context)),
                "ComplexGetReal complex is not a complex");
        tryVisit(complexGetReal, complexGetReal.getComplex());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary)
            throws FractalException {
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexGetImaginary.getComplex(), context)),
                "ComplexGetReal complex is not a complex");
        tryVisit(complexGetImaginary, complexGetImaginary.getComplex());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitComplexModulo(ComplexModulo complexModulo) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexModulo.getComplex(), context)),
                "ComplexGetReal complex is not a complex");
        tryVisit(complexModulo, complexModulo.getComplex());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitComplexIsEqual(ComplexIsEqual complexIsEqual) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexIsEqual.getLeft(), context)),
                "ComplexIsEqual left is not a complex");
        validValueTypeIfTrue(ValueTypes.isComplex(cache.getType(complexIsEqual.getRight(), context)),
                "ComplexIsEqual right is not a complex");
        tryVisit(complexIsEqual, complexIsEqual.getLeft(), complexIsEqual.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitFunctionCreate(FunctionCreate functionCreate) throws FractalException {
        String functionName = functionCreate.getFunctionName();
        if (!context.getProgram().getFunctions().containsKey(functionName)) {
            throw new MissingFunctionReferenceException("Invalid function create function index");
        }

        // compare context variable types
        FunctionDefinition target = context.getProgram().getFunctions().get(functionName);
        List<ArgumentDeclaration> targetContextVariables = target.getContextVariables();
        List<IProceduralValue> createContextVariables = functionCreate.getContextVariables();
        int targetContextVariablesSize = targetContextVariables.size();
        int createContextVariablesSize = createContextVariables.size();
        int size = Math.max(targetContextVariablesSize, createContextVariablesSize);
        for (int i = 0; i < size; i++) {
            if (i >= targetContextVariablesSize) {
                throw new IncompatibleFunctionContextException("FunctionContextConstant has extra context variables: " +
                        createContextVariables.subList(i, createContextVariablesSize));
            }
            if (i >= createContextVariablesSize) {
                throw new IncompatibleFunctionContextException(
                        "FunctionContextConstant is missing context variables: " +
                                targetContextVariables.subList(i, targetContextVariablesSize));
            }
            ArgumentDeclaration targetVariable = targetContextVariables.get(i);
            IValue constantInput = createContextVariables.get(i);
            if (!targetVariable.getType().isAssignableFrom(cache.getType(constantInput, context))) {
                throw new IncompatibleFunctionContextException(
                        "Function defines context variable: " + targetVariable + " but constant supplies: " +
                                constantInput);
            }
        }

        tryVisit(functionCreate, createContextVariables);
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitFunctionCall(FunctionCall functionCall) throws FractalException {

        // check the function argument
        ValueType functionArg = cache.getType(functionCall.getFunction(), context);
        validValueTypeIfTrue(ValueTypes.isFunction(functionArg),
                "FunctionCall function argument is not a Function");
        validValueTypeIfTrue(!ValueTypes.isNullFunction(functionArg),
                "FunctionCall cannot call the null-function");
        ValueTypes.FunctionType functionType = ValueTypes.toFunction(functionArg);

        // check the function's argument types
        List<ValueType> targetArguments = functionType.getArgumentTypes();
        List<IProceduralValue> instructionArguments = functionCall.getArguments();
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
            IProceduralValue instructionArgument = instructionArguments.get(i);
            validIfTrue(targetArgument.isAssignableFrom(cache.getType(instructionArgument, context)),
                    "Function defines argument: " + targetArgument + " but FunctionCall instruction supplies: " +
                            instructionArgument, IncompatibleFunctionArgumentException::new);
        }

        tryVisit(functionCall, instructionArguments);
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitFunctionIsEqual(FunctionIsEqual functionIsEqual) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isFunction(cache.getType(functionIsEqual.getLeft(), context)),
                "FunctionIsEqual left is not a function");
        validValueTypeIfTrue(ValueTypes.isFunction(cache.getType(functionIsEqual.getRight(), context)),
                "FunctionIsEqual right is not a function");
        tryVisit(functionIsEqual, functionIsEqual.getLeft(), functionIsEqual.getRight());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitPointerAllocate(PointerAllocate pointerAllocate)
            throws FractalException {
        ValueType pointerType = pointerAllocate.getType();
        if (ValueTypes.isVoid(pointerType)) {
            throw new IncompatibleValueTypeException("PointerAllocate cannot allocate a pointer of type VOID");
        }
        if (ValueTypes.isNullFunction(pointerType) || ValueTypes.isNullPointer(pointerType)) {
            throw new IncompatibleValueTypeException("PointerAllocate cannot allocate a pointer to a null-type");
        }
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitPointerFree(PointerFree pointerFree) throws FractalException {
        ValueType pointerType = cache.getType(pointerFree.getPointer(), context);
        validValueTypeIfTrue(ValueTypes.isPointer(pointerType),
                "PointerFree pointer argument is not a Pointer");
        validValueTypeIfTrue(!ValueTypes.isNullPointer(pointerType),
                "PointerFree cannot free the null-pointer");
        tryVisit(pointerFree, pointerFree.getPointer());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitPointerGet(PointerGet pointerGet) throws FractalException {
        ValueType pointerArg = cache.getType(pointerGet.getPointer(), context);
        validValueTypeIfTrue(ValueTypes.isPointer(pointerArg),
                "PointerGet pointer argument is not a Pointer");
        ValueTypes.PointerType pointerType = ValueTypes.toPointer(pointerArg);
        validValueTypeIfTrue(!ValueTypes.isNullPointer(pointerType),
                "PointerGet cannot get the value of the null-pointer");
        tryVisit(pointerGet, pointerGet.getPointer());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitPointerSet(PointerSet pointerSet) throws FractalException {
        ValueType pointerArg = cache.getType(pointerSet.getPointer(), context);
        validValueTypeIfTrue(ValueTypes.isPointer(pointerArg),
                "PointerSet pointer argument is not a Pointer");
        ValueTypes.PointerType pointerType = ValueTypes.toPointer(pointerArg);
        validValueTypeIfTrue(!ValueTypes.isNullPointer(pointerType),
                "PointerSet cannot set the value of the null-pointer");
        validValueTypeIfTrue(
                pointerType.getPointerType().isAssignableFrom(cache.getType(pointerSet.getData(), context)),
                "PointerSet pointer type and data type are incompatible");
        tryVisit(pointerSet, pointerSet.getPointer(), pointerSet.getData());
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalException {
        validValueTypeIfTrue(ValueTypes.isPointer(cache.getType(pointerIsEqual.getLeft(), context)),
                "PointerIsEqual left is not a Pointer");
        validValueTypeIfTrue(ValueTypes.isPointer(cache.getType(pointerIsEqual.getRight(), context)),
                "PointerIsEqual right is not a Pointer");
        return ValidatingVisitorResult.create();
    }

    @Override
    public ValidatingVisitorResult visitBranchConditional(BranchConditional branchConditional) {
        return ValidatingVisitorResult.create(true);
    }

    @Override
    public ValidatingVisitorResult visitBranch(Branch branch) {
        return ValidatingVisitorResult.create(true);
    }
}
