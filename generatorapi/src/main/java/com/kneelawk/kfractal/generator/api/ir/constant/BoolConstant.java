package com.kneelawk.kfractal.generator.api.ir.constant;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInput;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInputVisitor;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Kneelawk on 5/27/19.
 */
public class BoolConstant implements IProceduralValue, IPhiInput {
    private boolean value;

    private BoolConstant(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    @Override
    public <R> R accept(IPhiInputVisitor<R> visitor) throws FractalException {
        return visitor.visitBoolConstant(this);
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitBoolConstant(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitBoolConstant(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("value", value)
                .toString();
    }

    public static BoolConstant create(boolean value) {
        return new BoolConstant(value);
    }

    public static class Builder {
        private boolean value;

        public Builder() {
        }

        public Builder(boolean value) {
            this.value = value;
        }

        public BoolConstant build() {
            return new BoolConstant(value);
        }

        public boolean isValue() {
            return value;
        }

        public Builder setValue(boolean value) {
            this.value = value;
            return this;
        }
    }
}
