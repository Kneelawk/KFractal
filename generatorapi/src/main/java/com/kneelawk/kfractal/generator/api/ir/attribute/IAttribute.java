package com.kneelawk.kfractal.generator.api.ir.attribute;

public interface IAttribute {
    IAttribute CONSTANT = Constant.INSTANCE;
    IAttribute PREALLOCATED = Preallocated.INSTANCE;

    void accept(IAttributeVisitor visitor);
}
