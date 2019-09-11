package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ComplexAdd - Instruction. Adds the two complex numbers.
 * <p>
 * ComplexAdd(Complex leftAddend, Complex rightAddend)
 */
public class ComplexAdd implements IValue {
    private IValue leftAddend;
    private IValue rightAddend;

    private ComplexAdd(IValue leftAddend, IValue rightAddend) {
        this.leftAddend = leftAddend;
        this.rightAddend = rightAddend;
    }

    public IValue getLeftAddend() {
        return leftAddend;
    }

    public IValue getRightAddend() {
        return rightAddend;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitComplexAdd(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("leftAddend", leftAddend)
                .append("rightAddend", rightAddend)
                .toString();
    }

    public static ComplexAdd create(IValue leftAddend,
                                    IValue rightAddend) {
        if (leftAddend == null)
            throw new NullPointerException("LeftAddend cannot be null");
        if (rightAddend == null)
            throw new NullPointerException("RightAddend cannot be null");
        return new ComplexAdd(leftAddend, rightAddend);
    }

    public static class Builder {
        private IValue leftAddend;
        private IValue rightAddend;

        public Builder() {
        }

        public Builder(IValue leftAddend,
                       IValue rightAddend) {
            this.leftAddend = leftAddend;
            this.rightAddend = rightAddend;
        }

        public ComplexAdd build() {
            if (leftAddend == null)
                throw new IllegalStateException("No leftAddend specified");
            if (rightAddend == null)
                throw new IllegalStateException("No rightAddend specified");
            return new ComplexAdd(leftAddend, rightAddend);
        }

        public IValue getLeftAddend() {
            return leftAddend;
        }

        public Builder setLeftAddend(IValue leftAddend) {
            this.leftAddend = leftAddend;
            return this;
        }

        public IValue getRightAddend() {
            return rightAddend;
        }

        public Builder setRightAddend(IValue rightAddend) {
            this.rightAddend = rightAddend;
            return this;
        }
    }
}
