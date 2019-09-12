package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntOr - Instruction. Bitwise ors the two arguments together.
 * <p>
 * IntOr(Int left, Int right)
 */
public class IntOr implements IValue {
    private IValue left;
    private IValue right;

    private IntOr(IValue left, IValue right) {
        this.left = left;
        this.right = right;
    }

    public IValue getLeft() {
        return left;
    }

    public IValue getRight() {
        return right;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntOr(this);
    }

    public static IntOr create(IValue left,
                               IValue right) {
        if (left == null)
            throw new NullPointerException("Left cannot be null");
        if (right == null)
            throw new NullPointerException("Right cannot be null");
        return new IntOr(left, right);
    }

    public static class Builder {
        private IValue left;
        private IValue right;

        public Builder() {
        }

        public Builder(IValue left,
                       IValue right) {
            this.left = left;
            this.right = right;
        }

        public IntOr build() {
            if (left == null)
                throw new IllegalStateException("No left specified");
            if (right == null)
                throw new IllegalStateException("No right specified");
            return new IntOr(left, right);
        }

        public IValue getLeft() {
            return left;
        }

        public Builder setLeft(IValue left) {
            this.left = left;
            return this;
        }

        public IValue getRight() {
            return right;
        }

        public Builder setRight(IValue right) {
            this.right = right;
            return this;
        }
    }
}
