package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * PointerGet - Instruction. Retrieves the data pointed to by the handle in the only argument.
 * <p>
 * PointerGet(Pointer(*) pointer)
 */
public class PointerGet implements IProceduralValue {
    private IProceduralValue pointer;

    private PointerGet(IProceduralValue pointer) {
        this.pointer = pointer;
    }

    public IProceduralValue getPointer() {
        return pointer;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitPointerGet(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitPointerGet(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("pointer", pointer)
                .toString();
    }

    public static PointerGet create(IProceduralValue pointer) {
        if (pointer == null)
            throw new NullPointerException("Pointer cannot be null");
        return new PointerGet(pointer);
    }

    public static class Builder {
        private IProceduralValue pointer;

        public Builder() {
        }

        public Builder(IProceduralValue pointer) {
            this.pointer = pointer;
        }

        public PointerGet build() {
            if (pointer == null)
                throw new IllegalStateException("No pointer specified");
            return new PointerGet(pointer);
        }

        public IProceduralValue getPointer() {
            return pointer;
        }

        public Builder setPointer(IProceduralValue pointer) {
            this.pointer = pointer;
            return this;
        }
    }
}
