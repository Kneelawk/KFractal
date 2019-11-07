package com.kneelawk.kfractal.generator.api.ir.attribute;

import com.kneelawk.kfractal.generator.api.FractalException;

/**
 * Preallocated - Variables declared with the preallocated attribute must be pointer type and cannot be reassigned. When
 * a pointer is preallocated, it means that it starts as a valid memory handle to memory who's lifetime is the same as
 * this variable. When a preallocated pointer goes out of scope, its memory is released.
 */
public class Preallocated implements IGlobalAttribute {
    static final Preallocated INSTANCE = new Preallocated();

    private Preallocated() {
    }

    @Override
    public <R> R accept(IGlobalAttributeVisitor<R> visitor) throws FractalException {
        return visitor.visitPreallocated();
    }

    @Override
    public String toString() {
        return "Preallocated";
    }
}
