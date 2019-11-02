package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IntAdd - Instruction. Adds the two arguments as ints.
 * <p>
 * IntAdd(Int leftAddend, Int rightAddend)
 */
public class IntAdd implements IProceduralValue {
    private IProceduralValue leftAddend;
    private IProceduralValue rightAddend;

    private IntAdd(IProceduralValue leftAddend, IProceduralValue rightAddend) {
        this.leftAddend = leftAddend;
        this.rightAddend = rightAddend;
    }

    public IProceduralValue getLeftAddend() {
        return leftAddend;
    }

    public IProceduralValue getRightAddend() {
        return rightAddend;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntAdd(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitIntAdd(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("leftAddend", leftAddend)
                .append("rightAddend", rightAddend)
                .toString();
    }

    public static IntAdd create(IProceduralValue leftAddend,
                                IProceduralValue rightAddend) {
        if (leftAddend == null)
            throw new NullPointerException("LeftAddend cannot be null");
        if (rightAddend == null)
            throw new NullPointerException("RightAddend cannot be null");
        return new IntAdd(leftAddend, rightAddend);
    }

    public static class Builder {
        private IProceduralValue leftAddend;
        private IProceduralValue rightAddend;

        public Builder() {
        }

        public Builder(IProceduralValue leftAddend,
                       IProceduralValue rightAddend) {
            this.leftAddend = leftAddend;
            this.rightAddend = rightAddend;
        }

        public IntAdd build() {
            if (leftAddend == null)
                throw new IllegalStateException("No leftAddend specified");
            if (rightAddend == null)
                throw new IllegalStateException("No rightAddend specified");
            return new IntAdd(leftAddend, rightAddend);
        }

        public IProceduralValue getLeftAddend() {
            return leftAddend;
        }

        public Builder setLeftAddend(IProceduralValue leftAddend) {
            this.leftAddend = leftAddend;
            return this;
        }

        public IProceduralValue getRightAddend() {
            return rightAddend;
        }

        public Builder setRightAddend(IProceduralValue rightAddend) {
            this.rightAddend = rightAddend;
            return this;
        }
    }
}
