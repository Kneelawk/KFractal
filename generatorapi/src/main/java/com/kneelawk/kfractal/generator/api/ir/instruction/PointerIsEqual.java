package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * PointerIsEqual - Instruction. Checks to see if the two pointer arguments hold handles to the same data.
 * <p>
 * PointerIsEqual(Pointer(*) left, Pointer(*) right)
 */
public class PointerIsEqual implements IProceduralValue {
    private IProceduralValue left;
    private IProceduralValue right;

    private PointerIsEqual(IProceduralValue left, IProceduralValue right) {
        this.left = left;
        this.right = right;
    }

    public IProceduralValue getLeft() {
        return left;
    }

    public IProceduralValue getRight() {
        return right;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitPointerIsEqual(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitPointerIsEqual(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("left", left)
                .append("right", right)
                .toString();
    }

    public static PointerIsEqual create(IProceduralValue left,
                                        IProceduralValue right) {
        if (left == null)
            throw new NullPointerException("Left cannot be null");
        if (right == null)
            throw new NullPointerException("Right cannot be null");
        return new PointerIsEqual(left, right);
    }

    public static class Builder {
        private IProceduralValue left;
        private IProceduralValue right;

        public Builder() {
        }

        public Builder(IProceduralValue left,
                       IProceduralValue right) {
            this.left = left;
            this.right = right;
        }

        public PointerIsEqual build() {
            if (left == null)
                throw new IllegalStateException("No left specified");
            if (right == null)
                throw new IllegalStateException("No right specified");
            return new PointerIsEqual(left, right);
        }

        public IProceduralValue getLeft() {
            return left;
        }

        public Builder setLeft(IProceduralValue left) {
            this.left = left;
            return this;
        }

        public IProceduralValue getRight() {
            return right;
        }

        public Builder setRight(IProceduralValue right) {
            this.right = right;
            return this;
        }
    }
}
