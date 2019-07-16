package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;

class InstructionInputPrinter implements IInstructionInputVisitor<Void> {
    private final StringBuilder builder;

    InstructionInputPrinter(StringBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Void visitVariableReference(VariableReference reference) {
        builder.append("VariableReference(\"").append(reference.getName()).append("\")");
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
    public Void visitFunctionContextConstant(FunctionContextConstant contextConstant) throws FractalIRException {
        builder.append("FunctionContextConstant(\"").append(contextConstant.getFunctionName()).append("\", [ ");
        boolean first = true;
        for (IInstructionInput input : contextConstant.getContextVariables()) {
            if (!first)
                builder.append(", ");
            input.accept(this);
            first = false;
        }
        builder.append(" ])");
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
