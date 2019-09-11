package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * PointerSet - Instruction. Sets the value of the data pointed to by the handle in the first argument to the data in
 * the second argument.
 * <p>
 * PointerSet(Pointer(*) pointer, * data)
 */
public class PointerSet implements IValue {
    private IValue pointer;
    private IValue data;

    private PointerSet(IValue pointer, IValue data) {
        this.pointer = pointer;
        this.data = data;
    }

    public IValue getPointer() {
        return pointer;
    }

    public IValue getData() {
        return data;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitPointerSet(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("pointer", pointer)
                .append("data", data)
                .toString();
    }

    public static PointerSet create(IValue pointer,
                                    IValue data) {
        if (pointer == null)
            throw new NullPointerException("Pointer cannot be null");
        if (data == null)
            throw new NullPointerException("Data cannot be null");
        return new PointerSet(pointer, data);
    }

    public static class Builder {
        private IValue pointer;
        private IValue data;

        public Builder() {
        }

        public Builder(IValue pointer,
                       IValue data) {
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

        public IValue getPointer() {
            return pointer;
        }

        public Builder setPointer(IValue pointer) {
            this.pointer = pointer;
            return this;
        }

        public IValue getData() {
            return data;
        }

        public Builder setData(IValue data) {
            this.data = data;
            return this;
        }
    }
}
