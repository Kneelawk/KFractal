package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * PointerSet - Instruction. Sets the value of the data pointed to by the handle in the first argument to the data in
 * the second argument.
 * <p>
 * PointerSet(Pointer(*) pointer, * data)
 */
public class PointerSet implements IProceduralValue {
    private IProceduralValue pointer;
    private IProceduralValue data;

    private PointerSet(IProceduralValue pointer, IProceduralValue data) {
        this.pointer = pointer;
        this.data = data;
    }

    public IProceduralValue getPointer() {
        return pointer;
    }

    public IProceduralValue getData() {
        return data;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitPointerSet(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitPointerSet(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("pointer", pointer)
                .append("data", data)
                .toString();
    }

    public static PointerSet create(IProceduralValue pointer,
                                    IProceduralValue data) {
        if (pointer == null)
            throw new NullPointerException("Pointer cannot be null");
        if (data == null)
            throw new NullPointerException("Data cannot be null");
        return new PointerSet(pointer, data);
    }

    public static class Builder {
        private IProceduralValue pointer;
        private IProceduralValue data;

        public Builder() {
        }

        public Builder(IProceduralValue pointer,
                       IProceduralValue data) {
            this.pointer = pointer;
            this.data = data;
        }

        public PointerSet build() {
            if (pointer == null)
                throw new IllegalStateException("No pointer specified");
            if (data == null)
                throw new IllegalStateException("No data specified");
            return new PointerSet(pointer, data);
        }

        public IProceduralValue getPointer() {
            return pointer;
        }

        public Builder setPointer(IProceduralValue pointer) {
            this.pointer = pointer;
            return this;
        }

        public IProceduralValue getData() {
            return data;
        }

        public Builder setData(IProceduralValue data) {
            this.data = data;
            return this;
        }
    }
}
