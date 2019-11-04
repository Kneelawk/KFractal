package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttributeVisitor;

class GlobalAttributePrinter implements IGlobalAttributeVisitor<Void> {
    private final StringBuilder builder;

    GlobalAttributePrinter(StringBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Void visitPreallocated() {
        builder.append("Preallocated");
        return null;
    }
}
