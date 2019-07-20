package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.ir.attribute.IAttributeVisitor;

class AttributePrinter implements IAttributeVisitor<Void> {
    private final StringBuilder builder;

    AttributePrinter(StringBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Void visitPreallocated() {
        builder.append("Preallocated");
        return null;
    }
}
