package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.util.StringUtils;

class InstructionPrinter implements IInstructionVisitor<Void> {
    private final StringBuilder builder;
    private final int indent;

    private final InstructionInputPrinter inputPrinter;
    private final InstructionOutputPrinter outputPrinter;

    InstructionPrinter(StringBuilder builder, int indent) {
        this.builder = builder;
        this.indent = indent;

        inputPrinter = new InstructionInputPrinter(builder);
        outputPrinter = new InstructionOutputPrinter(builder);
    }

    @Override
    public Void visitAssign(Assign assign) throws FractalIRException {
        builder.append("Assign(");
        assign.getDest().accept(outputPrinter);
        builder.append(", ");
        assign.getSource().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitReturn(Return aReturn) throws FractalIRException {
        builder.append("Return(");
        aReturn.getReturnValue().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitBoolNot(BoolNot boolNot) throws FractalIRException {
        builder.append("BoolNot(");
        boolNot.getOutput().accept(outputPrinter);
        builder.append(", ");
        boolNot.getInput().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitBoolAnd(BoolAnd boolAnd) throws FractalIRException {
        builder.append("BoolAnd(");
        boolAnd.getResult().accept(outputPrinter);
        builder.append(", ");
        boolAnd.getLeft().accept(inputPrinter);
        builder.append(", ");
        boolAnd.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitBoolOr(BoolOr boolOr) throws FractalIRException {
        builder.append("BoolOr(");
        boolOr.getResult().accept(outputPrinter);
        builder.append(", ");
        boolOr.getLeft().accept(inputPrinter);
        builder.append(", ");
        boolOr.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitBoolIsEqual(BoolIsEqual boolIsEqual) throws FractalIRException {
        builder.append("BoolIsEqual(");
        boolIsEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        boolIsEqual.getLeft().accept(inputPrinter);
        builder.append(", ");
        boolIsEqual.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitBoolIsNotEqual(BoolIsNotEqual boolIsNotEqual) throws FractalIRException {
        builder.append("BoolIsNotEqual(");
        boolIsNotEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        boolIsNotEqual.getLeft().accept(inputPrinter);
        builder.append(", ");
        boolIsNotEqual.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntAdd(IntAdd intAdd) throws FractalIRException {
        builder.append("IntAdd(");
        intAdd.getSum().accept(outputPrinter);
        builder.append(", ");
        intAdd.getLeftAddend().accept(inputPrinter);
        builder.append(", ");
        intAdd.getRightAddend().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntSubtract(IntSubtract intSubtract) throws FractalIRException {
        builder.append("IntSubtract(");
        intSubtract.getDifference().accept(outputPrinter);
        builder.append(", ");
        intSubtract.getMinuend().accept(inputPrinter);
        builder.append(", ");
        intSubtract.getSubtrahend().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntMultiply(IntMultiply intMultiply) throws FractalIRException {
        builder.append("IntMultiply(");
        intMultiply.getProduct().accept(outputPrinter);
        builder.append(", ");
        intMultiply.getLeftFactor().accept(inputPrinter);
        builder.append(", ");
        intMultiply.getRightFactor().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntDivide(IntDivide intDivide) throws FractalIRException {
        builder.append("IntDivide(");
        intDivide.getQuotient().accept(outputPrinter);
        builder.append(", ");
        intDivide.getDividend().accept(inputPrinter);
        builder.append(", ");
        intDivide.getDivisor().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntModulo(IntModulo intModulo) throws FractalIRException {
        builder.append("IntModulo(");
        intModulo.getResult().accept(outputPrinter);
        builder.append(", ");
        intModulo.getLeft().accept(inputPrinter);
        builder.append(", ");
        intModulo.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntPower(IntPower intPower) throws FractalIRException {
        builder.append("IntPower(");
        intPower.getResult().accept(outputPrinter);
        builder.append(", ");
        intPower.getBase().accept(inputPrinter);
        builder.append(", ");
        intPower.getExponent().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntNot(IntNot intNot) throws FractalIRException {
        builder.append("IntNot(");
        intNot.getOutput().accept(outputPrinter);
        builder.append(", ");
        intNot.getInput().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntAnd(IntAnd intAnd) throws FractalIRException {
        builder.append("IntAnd(");
        intAnd.getResult().accept(outputPrinter);
        builder.append(", ");
        intAnd.getLeft().accept(inputPrinter);
        builder.append(", ");
        intAnd.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntOr(IntOr intOr) throws FractalIRException {
        builder.append("IntOr(");
        intOr.getResult().accept(outputPrinter);
        builder.append(", ");
        intOr.getLeft().accept(inputPrinter);
        builder.append(", ");
        intOr.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntXor(IntXor intXor) throws FractalIRException {
        builder.append("IntXor(");
        intXor.getResult().accept(outputPrinter);
        builder.append(", ");
        intXor.getLeft().accept(inputPrinter);
        builder.append(", ");
        intXor.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntIsEqual(IntIsEqual intIsEqual) throws FractalIRException {
        builder.append("IntIsEqual(");
        intIsEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        intIsEqual.getLeft().accept(inputPrinter);
        builder.append(", ");
        intIsEqual.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntIsNotEqual(IntIsNotEqual intIsNotEqual) throws FractalIRException {
        builder.append("IntIsNotEqual(");
        intIsNotEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        intIsNotEqual.getLeft().accept(inputPrinter);
        builder.append(", ");
        intIsNotEqual.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntIsGreater(IntIsGreater intIsGreater) throws FractalIRException {
        builder.append("IntIsGreater(");
        intIsGreater.getResult().accept(outputPrinter);
        builder.append(", ");
        intIsGreater.getSubject().accept(inputPrinter);
        builder.append(", ");
        intIsGreater.getBasis().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIntIsGreaterOrEqual(IntIsGreaterOrEqual intIsGreaterOrEqual) throws FractalIRException {
        builder.append("IntIsGreaterOrEqual(");
        intIsGreaterOrEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        intIsGreaterOrEqual.getSubject().accept(inputPrinter);
        builder.append(", ");
        intIsGreaterOrEqual.getBasis().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealAdd(RealAdd realAdd) throws FractalIRException {
        builder.append("RealAdd(");
        realAdd.getSum().accept(outputPrinter);
        builder.append(", ");
        realAdd.getLeftAddend().accept(inputPrinter);
        builder.append(", ");
        realAdd.getRightAddend().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealSubtract(RealSubtract realSubtract) throws FractalIRException {
        builder.append("RealSubtract(");
        realSubtract.getDifference().accept(outputPrinter);
        builder.append(", ");
        realSubtract.getMinuend().accept(inputPrinter);
        builder.append(", ");
        realSubtract.getSubtrahend().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealMultiply(RealMultiply realMultiply) throws FractalIRException {
        builder.append("RealMultiply(");
        realMultiply.getProduct().accept(outputPrinter);
        builder.append(", ");
        realMultiply.getLeftFactor().accept(inputPrinter);
        builder.append(", ");
        realMultiply.getRightFactor().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealDivide(RealDivide realDivide) throws FractalIRException {
        builder.append("RealDivide(");
        realDivide.getQuotient().accept(outputPrinter);
        builder.append(", ");
        realDivide.getDividend().accept(inputPrinter);
        builder.append(", ");
        realDivide.getDivisor().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealPower(RealPower realPower) throws FractalIRException {
        builder.append("RealPower(");
        realPower.getResult().accept(outputPrinter);
        builder.append(", ");
        realPower.getBase().accept(inputPrinter);
        builder.append(", ");
        realPower.getExponent().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealIsEqual(RealIsEqual realIsEqual) throws FractalIRException {
        builder.append("RealIsEqual(");
        realIsEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        realIsEqual.getLeft().accept(inputPrinter);
        builder.append(", ");
        realIsEqual.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealIsNotEqual(RealIsNotEqual realIsNotEqual) throws FractalIRException {
        builder.append("RealIsNotEqual(");
        realIsNotEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        realIsNotEqual.getLeft().accept(inputPrinter);
        builder.append(", ");
        realIsNotEqual.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealIsGreater(RealIsGreater realIsGreater) throws FractalIRException {
        builder.append("RealIsGreater(");
        realIsGreater.getResult().accept(outputPrinter);
        builder.append(", ");
        realIsGreater.getSubject().accept(inputPrinter);
        builder.append(", ");
        realIsGreater.getBasis().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealIsGreaterOrEqual(RealIsGreaterOrEqual realIsGreaterOrEqual) throws FractalIRException {
        builder.append("RealIsGreaterOrEqual(");
        realIsGreaterOrEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        realIsGreaterOrEqual.getSubject().accept(inputPrinter);
        builder.append(", ");
        realIsGreaterOrEqual.getBasis().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitRealComposeComplex(RealComposeComplex realComposeComplex) throws FractalIRException {
        builder.append("RealComposeComplex(");
        realComposeComplex.getComplex().accept(outputPrinter);
        builder.append(", ");
        realComposeComplex.getReal().accept(inputPrinter);
        builder.append(", ");
        realComposeComplex.getImaginary().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexAdd(ComplexAdd complexAdd) throws FractalIRException {
        builder.append("ComplexAdd(");
        complexAdd.getSum().accept(outputPrinter);
        builder.append(", ");
        complexAdd.getLeftAddend().accept(inputPrinter);
        builder.append(", ");
        complexAdd.getRightAddend().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexSubtract(ComplexSubtract complexSubtract) throws FractalIRException {
        builder.append("ComplexSubtract(");
        complexSubtract.getDifference().accept(outputPrinter);
        builder.append(", ");
        complexSubtract.getMinuend().accept(inputPrinter);
        builder.append(", ");
        complexSubtract.getSubtrahend().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexMultiply(ComplexMultiply complexMultiply) throws FractalIRException {
        builder.append("ComplexMultiply(");
        complexMultiply.getProduct().accept(outputPrinter);
        builder.append(", ");
        complexMultiply.getLeftFactor().accept(inputPrinter);
        builder.append(", ");
        complexMultiply.getRightFactor().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexDivide(ComplexDivide complexDivide) throws FractalIRException {
        builder.append("ComplexDivide(");
        complexDivide.getQuotient().accept(outputPrinter);
        builder.append(", ");
        complexDivide.getDividend().accept(inputPrinter);
        builder.append(", ");
        complexDivide.getDivisor().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexPower(ComplexPower complexPower) throws FractalIRException {
        builder.append("ComplexPower(");
        complexPower.getResult().accept(outputPrinter);
        builder.append(", ");
        complexPower.getBase().accept(inputPrinter);
        builder.append(", ");
        complexPower.getExponent().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexGetReal(ComplexGetReal complexGetReal) throws FractalIRException {
        builder.append("ComplexGetReal(");
        complexGetReal.getReal().accept(outputPrinter);
        builder.append(", ");
        complexGetReal.getComplex().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexGetImaginary(ComplexGetImaginary complexGetImaginary) throws FractalIRException {
        builder.append("ComplexGetImaginary(");
        complexGetImaginary.getImaginary().accept(outputPrinter);
        builder.append(", ");
        complexGetImaginary.getComplex().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitComplexModulo(ComplexModulo complexModulo) throws FractalIRException {
        builder.append("ComplexModulo(");
        complexModulo.getModulus().accept(outputPrinter);
        builder.append(", ");
        complexModulo.getComplex().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitFunctionCall(FunctionCall functionCall) throws FractalIRException {
        builder.append("FunctionCall(");
        functionCall.getResult().accept(outputPrinter);
        builder.append(", ");
        functionCall.getFunction().accept(inputPrinter);
        builder.append(", [ ");
        boolean first = true;
        for (IInstructionInput input : functionCall.getArguments()) {
            if (!first)
                builder.append(", ");
            input.accept(inputPrinter);
            first = false;
        }
        builder.append(" ])");
        return null;
    }

    @Override
    public Void visitFunctionIsEqual(FunctionIsEqual functionIsEqual) throws FractalIRException {
        builder.append("FunctionIsEqual(");
        functionIsEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        functionIsEqual.getLeft().accept(inputPrinter);
        builder.append(", ");
        functionIsEqual.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitFunctionIsNotEqual(FunctionIsNotEqual functionIsNotEqual) throws FractalIRException {
        builder.append("FunctionIsNotEqual(");
        functionIsNotEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        functionIsNotEqual.getLeft().accept(inputPrinter);
        builder.append(", ");
        functionIsNotEqual.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitPointerAllocate(PointerAllocate pointerAllocate) throws FractalIRException {
        builder.append("PointerAllocate(");
        pointerAllocate.getPointer().accept(outputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitPointerFree(PointerFree pointerFree) throws FractalIRException {
        builder.append("PointerFree(");
        pointerFree.getPointer().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitPointerGet(PointerGet pointerGet) throws FractalIRException {
        builder.append("PointerGet(");
        pointerGet.getData().accept(outputPrinter);
        builder.append(", ");
        pointerGet.getPointer().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitPointerSet(PointerSet pointerSet) throws FractalIRException {
        builder.append("PointerSet(");
        pointerSet.getPointer().accept(inputPrinter);
        builder.append(", ");
        pointerSet.getData().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitPointerIsEqual(PointerIsEqual pointerIsEqual) throws FractalIRException {
        builder.append("PointerIsEqual(");
        pointerIsEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        pointerIsEqual.getLeft().accept(inputPrinter);
        builder.append(", ");
        pointerIsEqual.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitPointerIsNotEqual(PointerIsNotEqual pointerIsNotEqual) throws FractalIRException {
        builder.append("PointerIsNotEqual(");
        pointerIsNotEqual.getResult().accept(outputPrinter);
        builder.append(", ");
        pointerIsNotEqual.getLeft().accept(inputPrinter);
        builder.append(", ");
        pointerIsNotEqual.getRight().accept(inputPrinter);
        builder.append(")");
        return null;
    }

    @Override
    public Void visitIf(If anIf) throws FractalIRException {
        InstructionPrinter internalPrinter = new InstructionPrinter(builder, indent + 1);

        builder.append("If(");
        anIf.getCondition().accept(inputPrinter);
        builder.append(",").append(System.lineSeparator());
        StringUtils.indent(builder, indent);
        builder.append("[");

        boolean first = true;
        for (IInstruction instruction : anIf.getIfTrue()) {
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, indent + 1);
            instruction.accept(internalPrinter);
            first = false;
        }

        if (first) {
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, indent);
        }
        builder.append("],").append(System.lineSeparator());
        StringUtils.indent(builder, indent);
        builder.append("[");

        first = true;
        for (IInstruction instruction : anIf.getIfFalse()) {
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, indent + 1);
            instruction.accept(internalPrinter);
            first = false;
        }

        if (!first) {
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, indent);
        }
        builder.append("])");
        return null;
    }

    @Override
    public Void visitWhile(While aWhile) throws FractalIRException {
        InstructionPrinter internalPrinter = new InstructionPrinter(builder, indent + 1);

        builder.append("While(");
        aWhile.getCondition().accept(inputPrinter);
        builder.append(",").append(System.lineSeparator());
        StringUtils.indent(builder, indent);
        builder.append("[");

        boolean first = true;
        for (IInstruction instruction : aWhile.getWhileTrue()) {
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, indent + 1);
            instruction.accept(internalPrinter);
            first = false;
        }

        if (!first) {
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, indent);
        }
        builder.append("])");
        return null;
    }
}
