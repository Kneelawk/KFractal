package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntSubtract - Instruction. Subtracts the second argument from the first argument.
 * <p>
 * IntSubtract(Int minuend, Int subtrahend)
 */
public class IntSubtract implements IValue {
    private IValue minuend;
    private IValue subtrahend;

    private IntSubtract(IValue minuend, IValue subtrahend) {
        this.minuend = minuend;
        this.subtrahend = subtrahend;
    }

    public IValue getMinuend() {
        return minuend;
    }

    public IValue getSubtrahend() {
        return subtrahend;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntSubtract(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("minuend", minuend)
                .append("subtrahend", subtrahend)
                .toString();
    }

    public static IntSubtract create(IValue minuend,
                                     IValue subtrahend) {
        if (minuend == null)
            throw new NullPointerException("Minuend cannot be null");
        if (subtrahend == null)
            throw new NullPointerException("Subtrahend cannot be null");
        return new IntSubtract(minuend, subtrahend);
    }

    public static class Builder {
        private IValue minuend;
        private IValue subtrahend;

        public Builder() {
        }

        public Builder(IValue minuend,
                       IValue subtrahend) {
            this.minuend = minuend;
            this.subtrahend = subtrahend;
        }

        public IntSubtract build() {
            if (minuend == null)
                throw new IllegalStateException("No minuend specified");
            if (subtrahend == null)
                throw new IllegalStateException("No subtrahend specified");
            return new IntSubtract(minuend, subtrahend);
        }

        public IValue getMinuend() {
            return minuend;
        }

        public Builder setMinuend(IValue minuend) {
            this.minuend = minuend;
            return this;
        }

        public IValue getSubtrahend() {
            return subtrahend;
        }

        public Builder setSubtrahend(IValue subtrahend) {
            this.subtrahend = subtrahend;
            return this;
        }
    }
}
