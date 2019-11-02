package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.ir.constant.BoolConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.IntConstant;
import com.kneelawk.kfractal.generator.api.ir.constant.RealConstant;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInputVisitor;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;

class PhiInputPrinter implements IPhiInputVisitor<Void> {
    private StringBuilder builder;

    PhiInputPrinter(StringBuilder builder) {
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
}
