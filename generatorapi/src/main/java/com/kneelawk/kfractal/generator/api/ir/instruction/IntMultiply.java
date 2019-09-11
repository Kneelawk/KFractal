package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntMultiply - Instruction. Multiplies the two arguments together.
 * <p>
 * IntMultiply(Int leftFactor, Int rightFactor)
 */
public class IntMultiply implements IValue {
    private IValue leftFactor;
    private IValue rightFactor;

    private IntMultiply(IValue leftFactor, IValue rightFactor) {
        this.leftFactor = leftFactor;
        this.rightFactor = rightFactor;
    }

    public IValue getLeftFactor() {
        return leftFactor;
    }

    public IValue getRightFactor() {
        return rightFactor;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntMultiply(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("leftFactor", leftFactor)
                .append("rightFactor", rightFactor)
                .toString();
    }

    public static IntMultiply create(IValue leftFactor,
                                     IValue rightFactor) {
        if (leftFactor == null)
            throw new NullPointerException("LeftFactor cannot be null");
        if (rightFactor == null)
            throw new NullPointerException("RightFactor cannot be null");
        return new IntMultiply(leftFactor, rightFactor);
    }

    public static class Builder {
        private IValue leftFactor;
        private IValue rightFactor;

        public Builder() {
        }

        public Builder(IValue leftFactor,
                       IValue rightFactor) {
            this.leftFactor = leftFactor;
            this.rightFactor = rightFactor;
        }

        public IntMultiply build() {
            if (leftFactor == null)
                throw new IllegalStateException("No leftFactor specified");
            if (rightFactor == null)
                throw new IllegalStateException("No rightFactor specified");
            return new IntMultiply(leftFactor, rightFactor);
        }

        public IValue getLeftFactor() {
            return leftFactor;
        }

        public Builder setLeftFactor(IValue leftFactor) {
            this.leftFactor = leftFactor;
            return this;
        }

        public IValue getRightFactor() {
            return rightFactor;
        }

        public Builder setRightFactor(IValue rightFactor) {
            this.rightFactor = rightFactor;
            return this;
        }
    }
}
