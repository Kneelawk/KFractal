package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntMultiply - Instruction. Multiplies the two arguments together.
 * <p>
 * IntMultiply(Int leftFactor, Int rightFactor)
 */
public class IntMultiply implements IProceduralValue {
    private IProceduralValue leftFactor;
    private IProceduralValue rightFactor;

    private IntMultiply(IProceduralValue leftFactor, IProceduralValue rightFactor) {
        this.leftFactor = leftFactor;
        this.rightFactor = rightFactor;
    }

    public IProceduralValue getLeftFactor() {
        return leftFactor;
    }

    public IProceduralValue getRightFactor() {
        return rightFactor;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntMultiply(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitIntMultiply(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("leftFactor", leftFactor)
                .append("rightFactor", rightFactor)
                .toString();
    }

    public static IntMultiply create(IProceduralValue leftFactor,
                                     IProceduralValue rightFactor) {
        if (leftFactor == null)
            throw new NullPointerException("LeftFactor cannot be null");
        if (rightFactor == null)
            throw new NullPointerException("RightFactor cannot be null");
        return new IntMultiply(leftFactor, rightFactor);
    }

    public static class Builder {
        private IProceduralValue leftFactor;
        private IProceduralValue rightFactor;

        public Builder() {
        }

        public Builder(IProceduralValue leftFactor,
                       IProceduralValue rightFactor) {
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

        public IProceduralValue getLeftFactor() {
            return leftFactor;
        }

        public Builder setLeftFactor(IProceduralValue leftFactor) {
            this.leftFactor = leftFactor;
            return this;
        }

        public IProceduralValue getRightFactor() {
            return rightFactor;
        }

        public Builder setRightFactor(IProceduralValue rightFactor) {
            this.rightFactor = rightFactor;
            return this;
        }
    }
}
