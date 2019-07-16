package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutputVisitor;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;

class InstructionOutputPrinter implements IInstructionOutputVisitor<Void> {
    private final StringBuilder builder;

    InstructionOutputPrinter(StringBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Void visitVariableReference(VariableReference reference) {
        builder.append("VariableReference(\"").append(reference.getName()).append("\")");
        return null;
    }

    @Override
    public Void visitVoid() {
        builder.append("Void");
        return null;
    }
}
