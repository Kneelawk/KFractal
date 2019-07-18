package com.kneelawk.kfractal.generator.api.ir.attribute;

public interface IAttribute {
    IAttribute PREALLOCATED = Preallocated.INSTANCE;

    void accept(IAttributeVisitor visitor);
}
