package com.kneelawk.kfractal.generator.api.ir.constant;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInput;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInputVisitor;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IntConstant implements IProceduralValue, IPhiInput {
    private int value;

    private IntConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public <R> R accept(IPhiInputVisitor<R> visitor) throws FractalException {
        return visitor.visitIntConstant(this);
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntConstant(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitIntConstant(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("value", value)
                .toString();
    }

    public static IntConstant create(int value) {
        return new IntConstant(value);
    }

    public static class Builder {
        private int value;

        public Builder() {
        }

        public Builder(int value) {
            this.value = value;
        }

        public IntConstant build() {
            return new IntConstant(value);
        }

        public int getValue() {
            return value;
        }

        public Builder setValue(int value) {
            this.value = value;
            return this;
        }
    }
}
