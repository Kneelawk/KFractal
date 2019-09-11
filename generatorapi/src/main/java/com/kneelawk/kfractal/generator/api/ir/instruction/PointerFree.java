package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * PointerFree - Instruction. Frees the data pointed to by the handle in the only argument if the current language
 * backend supports explicitly freeing pointers.
 * <p>
 * PointerFree(Pointer(*) pointer)
 */
public class PointerFree implements IValue {
    private IValue pointer;

    private PointerFree(IValue pointer) {
        this.pointer = pointer;
    }

    public IValue getPointer() {
        return pointer;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitPointerFree(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("pointer", pointer)
                .toString();
    }

    public static PointerFree create(IValue pointer) {
        if (pointer == null)
            throw new NullPointerException("Pointer cannot be null");
        return new PointerFree(pointer);
    }

    public static class Builder {
        private IValue pointer;

        public Builder() {
        }

        public Builder(IValue pointer) {
            this.pointer = pointer;
        }

        public PointerFree build() {
            if (pointer == null)
                throw new IllegalStateException("No pointer specified");
            return new PointerFree(pointer);
        }

        public IValue getPointer() {
            return pointer;
        }

        public Builder setPointer(IValue pointer) {
            this.pointer = pointer;
            return this;
        }
    }
}
