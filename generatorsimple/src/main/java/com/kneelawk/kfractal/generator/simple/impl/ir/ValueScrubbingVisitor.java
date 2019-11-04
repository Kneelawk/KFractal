package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.phi.Phi;
import com.kneelawk.kfractal.generator.api.ir.phi.PhiBranch;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;

import java.util.Map;

public class ValueScrubbingVisitor implements IValueVisitor<Void> {
    private final Map<IValue, IEngineValue> values;

    public ValueScrubbingVisitor(
            Map<IValue, IEngineValue> values) {
        this.values = values;
    }

    private void remove(IValue value) {
        values.remove(value);
    }

    private void remove(IValue parent, IValue child) throws FractalException {
        if (values.containsKey(parent)) {
            values.remove(parent);
            child.accept(this);
        }
    }

    private void remove(IValue parent, IValue child1, IValue child2) throws FractalException {
        if (values.containsKey(parent)) {
            values.remove(parent);
            child1.accept(this);
            child2.accept(this);
        }
    }

    private void remove(IValue parent, Iterable<? extends IValue> children) throws FractalException {
        if (values.containsKey(parent)) {
            values.remove(parent);
            for (IValue child : children) {
                child.accept(this);
            }
        }
    }

    private void remove(IValue parent, IValue child1, Iterable<? extends IValue> children) throws FractalException {
        if (values.containsKey(parent)) {
            values.remove(parent);
            child1.accept(this);
            for (IValue child : children) {
                child.accept(this);
            }
        }
    }

    @Override
    public Void visitArgumentReference(ArgumentReference argumentReference) throws FractalException {
        remove(argumentReference);
        return null;
    }

    @Override
    public Void visitInstructionReference(InstructionReference instructionReference) throws FractalException {
        remove(instructionReference);
        return null;
    }

    @Override
    public Void visitBoolConstant(BoolConstant constant) throws FractalException {
        // no need to remove constants
        return null;
    }

    @Override
    public Void visitIntConstant(IntConstant constant) throws FractalException {
        // no need to remove constants
        return null;
    }

    @Override
    public Void visitRealConstant(RealConstant constant) throws FractalException {
        // no need to remove constants
        return null;
    }

    @Override
    public Void visitComplexConstant(ComplexConstant constant) throws FractalException {
        // no need to remove constants
        return null;
    }

    @Override
    public Void visitNullPointer() throws FractalException {
        // no need to remove constants
        return null;
    }

    @Override
    public Void visitNullFunction() throws FractalException {
        // no need to remove constants
        return null;
    }

    @Override
    public Void visitVoid() throws FractalException {
        // no need to remove constants
        return null;
    }

    @Override
    public Void visitReturn(Return aReturn) throws FractalException {
        remove(aReturn, aReturn.getReturnValue());
        return null;
    }

    @Override
    public Void visitGlobalGet(GlobalGet globalGet) throws FractalException {
        remove(globalGet);
        return null;
    }

    @Override
    public Void visitGlobalSet(GlobalSet globalSet) throws FractalException {
        remove(globalSet, globalSet.getData());
        return null;
    }

    @Override
    public Void visitBoolNot(BoolNot boolNot) throws FractalException {
        remove(boolNot, boolNot.getInput());
        return null;
    }

    @Override
    public Void visitBoolAnd(BoolAnd boolAnd) throws FractalException {
        remove(boolAnd, boolAnd.getLeft(), boolAnd.getRight());
        return null;
    }

    @Override
    public Void visitBoolOr(BoolOr boolOr) throws FractalException {
        remove(boolOr, boolOr.getLeft(), boolOr.getRight());
        return null;
    }

    @Override
    public Void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalException {
        remove(boolIsEqual, boolIsEqual.getLeft(), boolIsEqual.getRight());
        return null;
    }

    @Override
    public Void visitIntAdd(IntAdd intAdd) throws FractalException {
        remove(intAdd, intAdd.getLeftAddend(), intAdd.getRightAddend());
        return null;
    }

    @Override
    public Void visitIntSubtract(IntSubtract intSubtract) throws FractalException {
        remove(intSubtract, intSubtract.getMinuend(), intSubtract.getSubtrahend());
        return null;
    }

    @Override
    public Void visitIntMultiply(IntMultiply intMultiply) throws FractalException {
        remove(intMultiply, intMultiply.getLeftFactor(), intMultiply.getRightFactor());
        return null;
    }

    @Override
    public Void visitIntDivide(IntDivide intDivide) throws FractalException {
        remove(intDivide, intDivide.getDividend(), intDivide.getDivisor());
        return null;
    }

    @Override
    public Void visitIntModulo(IntModulo intModulo) throws FractalException {
        remove(intModulo, intModulo.getLeft(), intModulo.getRight());
        return null;
    }

    @Override
    public Void visitIntPower(IntPower intPower) throws FractalException {
        remove(intPower, intPower.getBase(), intPower.getExponent());
        return null;
    }

    @Override
    public Void visitIntNot(IntNot intNot) throws FractalException {
        remove(intNot, intNot.getInput());
        return null;
    }

    @Override
    public Void visitIntAnd(IntAnd intAnd) throws FractalException {
        remove(intAnd, intAnd.getLeft(), intAnd.getRight());
        return null;
    }

    @Override
    public Void visitIntOr(IntOr intOr) throws FractalException {
        remove(intOr, intOr.getLeft(), intOr.getRight());
        return null;
    }

    @Override
    public Void visitIntXor(IntXor intXor) throws FractalException {
        remove(intXor, intXor.getLeft(), intXor.getRight());
        return null;
    }

    @Override
    public Void visitIntIsEqual(IntIsEqual intIsEqual) throws FractalException {
        remove(intIsEqual, intIsEqual.getLeft(), intIsEqual.getRight());
        return null;
    }

    @Override
    public Void visitIntIsGreater(IntIsGreater intIsGreater) throws FractalException {
        remove(intIsGreater, intIsGreater.getSubject(), intIsGreater.getBasis());
        return null;
    }

    @Override
    public Void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalException {
        remove(intIsGreaterOrEqual, intIsGreaterOrEqual.getSubject(), intIsGreaterOrEqual.getBasis());
        return null;
    }

    @Override
    public Void visitRealAdd(RealAdd realAdd) throws FractalException {
        remove(realAdd, realAdd.getLeftAddend(), realAdd.getRightAddend());
        return null;
    }

    @Override
    public Void visitRealSubtract(RealSubtract realSubtract) throws FractalException {
        remove(realSubtract, realSubtract.getMinuend(), realSubtract.getSubtrahend());
        return null;
    }

    @Override
    public Void visitRealMultiply(RealMultiply realMultiply) throws FractalException {
        remove(realMultiply, realMultiply.getLeftFactor(), realMultiply.getRightFactor());
        return null;
    }

    @Override
    public Void visitRealDivide(RealDivide realDivide) throws FractalException {
        remove(realDivide, realDivide.getDividend(), realDivide.getDivisor());
        return null;
    }

    @Override
    public Void visitRealPower(RealPower realPower) throws FractalException {
        remove(realPower, realPower.getBase(), realPower.getExponent());
        return null;
    }

    @Override
    public Void visitRealIsEqual(RealIsEqual realIsEqual) throws FractalException {
        remove(realIsEqual, realIsEqual.getLeft(), realIsEqual.getRight());
        return null;
    }

    @Override
    public Void visitRealIsGreater(RealIsGreater realIsGreater) throws FractalException {
        remove(realIsGreater, realIsGreater.getSubject(), realIsGreater.getBasis());
        return null;
    }

    @Override
    public Void visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalException {
        remove(realIsGreaterOrEqual, realIsGreaterOrEqual.getSubject(), realIsGreaterOrEqual.getBasis());
        return null;
    }

    @Override
    public Void visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalException {
        remove(realComposeComplex, realComposeComplex.getReal(), realComposeComplex.getImaginary());
        return null;
    }

    @Override
    public Void visitComplexAdd(ComplexAdd complexAdd) throws FractalException {
        remove(complexAdd, complexAdd.getLeftAddend(), complexAdd.getRightAddend());
        return null;
    }

    @Override
    public Void visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalException {
        remove(complexSubtract, complexSubtract.getMinuend(), complexSubtract.getSubtrahend());
        return null;
    }

    @Override
    public Void visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalException {
        remove(complexMultiply, complexMultiply.getLeftFactor(), complexMultiply.getRightFactor());
        return null;
    }

    @Override
    public Void visitComplexDivide(ComplexDivide complexDivide) throws FractalException {
        remove(complexDivide, complexDivide.getDividend(), complexDivide.getDivisor());
        return null;
    }

    @Override
    public Void visitComplexPower(ComplexPower complexPower) throws FractalException {
        remove(complexPower, complexPower.getBase(), complexPower.getExponent());
        return null;
    }

    @Override
    public Void visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalException {
        remove(complexGetReal, complexGetReal.getComplex());
        return null;
    }

    @Override
    public Void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalException {
        remove(complexGetImaginary, complexGetImaginary.getComplex());
        return null;
    }

    @Override
    public Void visitComplexModulo(ComplexModulo complexModulo) throws FractalException {
        remove(complexModulo, complexModulo.getComplex());
        return null;
    }

    @Override
    public Void visitComplexIsEqual(ComplexIsEqual complexIsEqual) throws FractalException {
        remove(complexIsEqual, complexIsEqual.getLeft(), complexIsEqual.getRight());
        return null;
    }

    @Override
    public Void visitFunctionCreate(FunctionCreate functionCreate) throws FractalException {
        remove(functionCreate, functionCreate.getContextVariables());
        return null;
    }

    @Override
    public Void visitFunctionCall(FunctionCall functionCall) throws FractalException {
        remove(functionCall, functionCall.getFunction(), functionCall.getArguments());
        return null;
    }

    @Override
    public Void visitFunctionIsEqual(FunctionIsEqual functionIsEqual) throws FractalException {
        remove(functionIsEqual, functionIsEqual.getLeft(), functionIsEqual.getRight());
        return null;
    }

    @Override
    public Void visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalException {
        remove(pointerAllocate);
        return null;
    }

    @Override
    public Void visitPointerFree(PointerFree pointerFree) throws FractalException {
        remove(pointerFree, pointerFree.getPointer());
        return null;
    }

    @Override
    public Void visitPointerGet(PointerGet pointerGet) throws FractalException {
        remove(pointerGet, pointerGet.getPointer());
        return null;
    }

    @Override
    public Void visitPointerSet(PointerSet pointerSet) throws FractalException {
        remove(pointerSet, pointerSet.getPointer(), pointerSet.getData());
        return null;
    }

    @Override
    public Void visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalException {
        remove(pointerIsEqual, pointerIsEqual.getLeft(), pointerIsEqual.getRight());
        return null;
    }

    @Override
    public Void visitPhi(Phi phi) throws FractalException {
        remove(phi, phi.getBranches().stream().map(PhiBranch::getValue).collect(ImmutableList.toImmutableList()));
        return null;
    }

    @Override
    public Void visitBranchConditional(BranchConditional branchConditional) throws FractalException {
        remove(branchConditional, branchConditional.getCondition());
        return null;
    }

    @Override
    public Void visitBranch(Branch branch) throws FractalException {
        remove(branch);
        return null;
    }
}
