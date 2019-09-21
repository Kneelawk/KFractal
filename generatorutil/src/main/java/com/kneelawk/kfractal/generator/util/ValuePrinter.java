package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;

class ValuePrinter implements IValueVisitor<Void> {
    private final StringBuilder builder;

    ValuePrinter(StringBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Void visitArgumentReference(ArgumentReference argumentReference) {
        builder.append("ArgumentReference(").append(argumentReference.getScope()).append(", ")
                .append(argumentReference.getIndex()).append(")");
        return null;
    }

    @Override
    public Void visitInstructionReference(InstructionReference instructionReference) {
        builder.append("InstructionReference(").append(instructionReference.getBlockIndex()).append(", ")
                .append(instructionReference.getScope()).append(", ").append(instructionReference.getInstructionIndex())
                .append(")");
        return null;
    }

    @Override
    public Void visitBoolConstant(BoolConstant constant) {
        builder.append("BoolConstant(").append(constant.isValue()).append(")");
        return null;
    }

    @Override
    public Void visitIntConstant(IntConstant constant) {
        builder.append("IntConstant(").append(constant.getValue()).append(")");
        return null;
    }

    @Override
    public Void visitRealConstant(RealConstant constant) {
        builder.append("RealConstant(").append(constant.getValue()).append(")");
        return null;
    }

    @Override
    public Void visitComplexConstant(ComplexConstant constant) {
        builder.append("ComplexConstant(").append(constant.getValue()).append(")");
        return null;
    }

    @Override
    public Void visitNullPointer() {
        builder.append("NullPointer");
        return null;
    }

    @Override
    public Void visitNullFunction() {
        builder.append("NullFunction");
        return null;
    }

    @Override
    public Void visitVoid() {
        builder.append("Void");
        return null;
    }

    @Override
    public Void visitReturn(Return aReturn) throws FractalException {
        builder.append("Return(");
        aReturn.getReturnValue().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitGlobalGet(GlobalGet globalGet) {
        builder.append("GlobalGet(").append(globalGet.getGlobalIndex()).append(")");
        return null;
    }

    @Override
    public Void visitGlobalSet(GlobalSet globalSet) throws FractalException {
        builder.append("GlobalSet(").append(globalSet.getGlobalIndex()).append(", ");
        globalSet.getData().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitBoolNot(BoolNot boolNot) throws FractalException {
        builder.append("BoolNot(");
        boolNot.getInput().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitBoolAnd(BoolAnd boolAnd) throws FractalException {
        builder.append("BoolAnd(");
        boolAnd.getLeft().accept(this);
        builder.append(", ");
        boolAnd.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitBoolOr(BoolOr boolOr) throws FractalException {
        builder.append("BoolOr(");
        boolOr.getLeft().accept(this);
        builder.append(", ");
        boolOr.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalException {
        builder.append("BoolIsEqual(");
        boolIsEqual.getLeft().accept(this);
        builder.append(", ");
        boolIsEqual.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntAdd(IntAdd intAdd) throws FractalException {
        builder.append("IntAdd(");
        intAdd.getLeftAddend().accept(this);
        builder.append(", ");
        intAdd.getRightAddend().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntSubtract(IntSubtract intSubtract) throws FractalException {
        builder.append("IntSubtract(");
        intSubtract.getMinuend().accept(this);
        builder.append(", ");
        intSubtract.getSubtrahend().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntMultiply(IntMultiply intMultiply) throws FractalException {
        builder.append("IntMultiply(");
        intMultiply.getLeftFactor().accept(this);
        builder.append(", ");
        intMultiply.getRightFactor().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntDivide(IntDivide intDivide) throws FractalException {
        builder.append("IntDivide(");
        intDivide.getDividend().accept(this);
        builder.append(", ");
        intDivide.getDivisor().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntModulo(IntModulo intModulo) throws FractalException {
        builder.append("IntModulo(");
        intModulo.getLeft().accept(this);
        builder.append(", ");
        intModulo.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntPower(IntPower intPower) throws FractalException {
        builder.append("IntPower(");
        intPower.getBase().accept(this);
        builder.append(", ");
        intPower.getExponent().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntNot(IntNot intNot) throws FractalException {
        builder.append("IntNot(");
        intNot.getInput().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntAnd(IntAnd intAnd) throws FractalException {
        builder.append("IntAnd(");
        intAnd.getLeft().accept(this);
        builder.append(", ");
        intAnd.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntOr(IntOr intOr) throws FractalException {
        builder.append("IntOr(");
        intOr.getLeft().accept(this);
        builder.append(", ");
        intOr.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntXor(IntXor intXor) throws FractalException {
        builder.append("IntXor(");
        intXor.getLeft().accept(this);
        builder.append(", ");
        intXor.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntIsEqual(IntIsEqual intIsEqual) throws FractalException {
        builder.append("IntIsEqual(");
        intIsEqual.getLeft().accept(this);
        builder.append(", ");
        intIsEqual.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntIsGreater(IntIsGreater intIsGreater) throws FractalException {
        builder.append("IntIsGreater(");
        intIsGreater.getSubject().accept(this);
        builder.append(", ");
        intIsGreater.getBasis().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalException {
        builder.append("IntIsGreaterOrEqual(");
        intIsGreaterOrEqual.getSubject().accept(this);
        builder.append(", ");
        intIsGreaterOrEqual.getBasis().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealAdd(RealAdd realAdd) throws FractalException {
        builder.append("RealAdd(");
        realAdd.getLeftAddend().accept(this);
        builder.append(", ");
        realAdd.getRightAddend().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealSubtract(RealSubtract realSubtract) throws FractalException {
        builder.append("RealSubtract(");
        realSubtract.getMinuend().accept(this);
        builder.append(", ");
        realSubtract.getSubtrahend().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealMultiply(RealMultiply realMultiply) throws FractalException {
        builder.append("RealMultiply(");
        realMultiply.getLeftFactor().accept(this);
        builder.append(", ");
        realMultiply.getRightFactor().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealDivide(RealDivide realDivide) throws FractalException {
        builder.append("RealDivide(");
        realDivide.getDividend().accept(this);
        builder.append(", ");
        realDivide.getDivisor().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealPower(RealPower realPower) throws FractalException {
        builder.append("RealPower(");
        realPower.getBase().accept(this);
        builder.append(", ");
        realPower.getExponent().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealIsEqual(RealIsEqual realIsEqual) throws FractalException {
        builder.append("RealIsEqual(");
        realIsEqual.getLeft().accept(this);
        builder.append(", ");
        realIsEqual.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealIsGreater(RealIsGreater realIsGreater) throws FractalException {
        builder.append("RealIsGreater(");
        realIsGreater.getSubject().accept(this);
        builder.append(", ");
        realIsGreater.getBasis().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalException {
        builder.append("RealIsGreaterOrEqual(");
        realIsGreaterOrEqual.getSubject().accept(this);
        builder.append(", ");
        realIsGreaterOrEqual.getBasis().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalException {
        builder.append("RealComposeComplex(");
        realComposeComplex.getReal().accept(this);
        builder.append(", ");
        realComposeComplex.getImaginary().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexAdd(ComplexAdd complexAdd) throws FractalException {
        builder.append("ComplexAdd(");
        complexAdd.getLeftAddend().accept(this);
        builder.append(", ");
        complexAdd.getRightAddend().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalException {
        builder.append("ComplexSubtract(");
        complexSubtract.getMinuend().accept(this);
        builder.append(", ");
        complexSubtract.getSubtrahend().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalException {
        builder.append("ComplexMultiply(");
        complexMultiply.getLeftFactor().accept(this);
        builder.append(", ");
        complexMultiply.getRightFactor().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexDivide(ComplexDivide complexDivide) throws FractalException {
        builder.append("ComplexDivide(");
        complexDivide.getDividend().accept(this);
        builder.append(", ");
        complexDivide.getDivisor().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexPower(ComplexPower complexPower) throws FractalException {
        builder.append("ComplexPower(");
        complexPower.getBase().accept(this);
        builder.append(", ");
        complexPower.getExponent().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalException {
        builder.append("ComplexGetReal(");
        complexGetReal.getComplex().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalException {
        builder.append("ComplexGetImaginary(");
        complexGetImaginary.getComplex().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexModulo(ComplexModulo complexModulo) throws FractalException {
        builder.append("ComplexModulo(");
        complexModulo.getComplex().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexIsEqual(ComplexIsEqual complexIsEqual) throws FractalException {
        builder.append("ComplexIsEqual(");
        complexIsEqual.getLeft().accept(this);
        builder.append(", ");
        complexIsEqual.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitFunctionCreate(FunctionCreate functionCreate) throws FractalException {
        builder.append("FunctionCreate(").append(functionCreate.getFunctionIndex()).append(", [ ");
        boolean first = true;
        for (IValue input : functionCreate.getContextVariables()) {
            if (!first)
                builder.append(", ");
            input.accept(this);
            first = false;
        }
        builder.append(" ])");
        return null;
    }

    @Override
    public Void visitFunctionCall(FunctionCall functionCall) throws FractalException {
        builder.append("FunctionCall(");
        functionCall.getFunction().accept(this);
        builder.append(", [ ");
        boolean first = true;
        for (IValue input : functionCall.getArguments()) {
            if (!first)
                builder.append(", ");
            input.accept(this);
            first = false;
        }
        builder.append(" ])");
        return null;
    }

    @Override
    public Void visitFunctionIsEqual(FunctionIsEqual functionIsEqual) throws FractalException {
        builder.append("FunctionIsEqual(");
        functionIsEqual.getLeft().accept(this);
        builder.append(", ");
        functionIsEqual.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitPointerAllocate(PointerAllocate pointerAllocate) {
        builder.append("PointerAllocate(").append(pointerAllocate.getType()).append(")");
        return null;
    }

    @Override
    public Void visitPointerFree(PointerFree pointerFree) throws FractalException {
        builder.append("PointerFree(");
        pointerFree.getPointer().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitPointerGet(PointerGet pointerGet) throws FractalException {
        builder.append("PointerGet(");
        pointerGet.getPointer().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitPointerSet(PointerSet pointerSet) throws FractalException {
        builder.append("PointerSet(");
        pointerSet.getPointer().accept(this);
        builder.append(", ");
        pointerSet.getData().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalException {
        builder.append("PointerIsEqual(");
        pointerIsEqual.getLeft().accept(this);
        builder.append(", ");
        pointerIsEqual.getRight().accept(this);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitBranchConditional(BranchConditional branchConditional) throws FractalException {
        builder.append("BranchConditional(");
        branchConditional.getCondition().accept(this);
        builder.append(", ").append(branchConditional.getTrueBlockIndex()).append(", ")
                .append(branchConditional.getFalseBlockIndex()).append(")");
        return null;
    }

    @Override
    public Void visitBranch(Branch branch) {
        builder.append("Branch(").append(branch.getBlockIndex()).append(")");
        return null;
    }
}
