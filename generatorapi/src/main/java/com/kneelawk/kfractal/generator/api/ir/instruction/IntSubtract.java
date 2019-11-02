package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntSubtract - Instruction. Subtracts the second argument from the first argument.
 * <p>
 * IntSubtract(Int minuend, Int subtrahend)
 */
public class IntSubtract implements IProceduralValue {
    private IProceduralValue minuend;
    private IProceduralValue subtrahend;

    private IntSubtract(IProceduralValue minuend, IProceduralValue subtrahend) {
        this.minuend = minuend;
        this.subtrahend = subtrahend;
    }

    public IProceduralValue getMinuend() {
        return minuend;
    }

    public IProceduralValue getSubtrahend() {
        return subtrahend;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntSubtract(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitIntSubtract(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("minuend", minuend)
                .append("subtrahend", subtrahend)
                .toString();
    }

    public static IntSubtract create(IProceduralValue minuend,
                                     IProceduralValue subtrahend) {
        if (minuend == null)
            throw new NullPointerException("Minuend cannot be null");
        if (subtrahend == null)
            throw new NullPointerException("Subtrahend cannot be null");
        return new IntSubtract(minuend, subtrahend);
    }

    public static class Builder {
        private IProceduralValue minuend;
        private IProceduralValue subtrahend;

        public Builder() {
        }

        public Builder(IProceduralValue minuend,
                       IProceduralValue subtrahend) {
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

        public IProceduralValue getMinuend() {
            return minuend;
        }

        public Builder setMinuend(IProceduralValue minuend) {
            this.minuend = minuend;
            return this;
        }

        public IProceduralValue getSubtrahend() {
            return subtrahend;
        }

        public Builder setSubtrahend(IProceduralValue subtrahend) {
            this.subtrahend = subtrahend;
            return this;
        }
    }
}
