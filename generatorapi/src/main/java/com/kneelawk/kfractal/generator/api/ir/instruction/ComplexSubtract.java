package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexSubtract - Instruction. Subtracts the second argument from the first argument.
 * <p>
 * ComplexSubtract(Complex minuend, Complex subtrahend)
 */
public class ComplexSubtract implements IValue {
    private IValue minuend;
    private IValue subtrahend;

    private ComplexSubtract(IValue minuend, IValue subtrahend) {
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
        return visitor.visitComplexSubtract(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("minuend", minuend)
                .append("subtrahend", subtrahend)
                .toString();
    }

    public static ComplexSubtract create(IValue minuend,
                                         IValue subtrahend) {
        if (minuend == null)
            throw new NullPointerException("Minuend cannot be null");
        if (subtrahend == null)
            throw new NullPointerException("Subtrahend cannot be null");
        return new ComplexSubtract(minuend, subtrahend);
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

        public ComplexSubtract build() {
            if (minuend == null)
                throw new IllegalStateException("No minuend specified");
            if (subtrahend == null)
                throw new IllegalStateException("No subtrahend specified");
            return new ComplexSubtract(minuend, subtrahend);
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
