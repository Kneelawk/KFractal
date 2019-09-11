package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * PointerAllocate - Instruction. Allocates data for a pointer on the language-backed heap. All allocations that have
 * not been freed should be freed when the program exits.
 * <p>
 * PointerAllocate(type)
 */
public class PointerAllocate implements IValue {
    private ValueType type;

    private PointerAllocate(ValueType type) {
        this.type = type;
    }

    public ValueType getType() {
        return type;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitPointerAllocate(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("type", type)
                .toString();
    }

    public static PointerAllocate create(ValueType type) {
        if (type == null)
            throw new NullPointerException("Type cannot be null");
        return new PointerAllocate(type);
    }

    public static class Builder {
        private ValueType type;

        public Builder() {
        }

        public Builder(ValueType type) {
            this.type = type;
        }

        public PointerAllocate build() {
            if (type == null)
                throw new IllegalStateException("No type specified");
            return new PointerAllocate(type);
        }

        public ValueType getType() {
            return type;
        }

        public Builder setType(ValueType type) {
            this.type = type;
            return this;
        }
    }
}
