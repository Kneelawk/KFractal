package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * BoolNot - Instruction. Gets the not value of the argument.
 * <p>
 * BoolNot(Bool input)
 */
public class BoolNot implements IProceduralValue {
    private IProceduralValue input;

    private BoolNot(IProceduralValue input) {
        this.input = input;
    }

    public IProceduralValue getInput() {
        return input;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitBoolNot(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitBoolNot(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("input", input)
                .toString();
    }

    public static BoolNot create(IProceduralValue input) {
        if (input == null)
            throw new NullPointerException("Input cannot be null");
        return new BoolNot(input);
    }

    public static class Builder {
        private IProceduralValue input;

        public Builder() {
        }

        public Builder(IProceduralValue input) {
            this.input = input;
        }

        public BoolNot build() {
            if (input == null)
                throw new IllegalStateException("No input specified");
            return new BoolNot(input);
        }

        public IProceduralValue getInput() {
            return input;
        }

        public Builder setInput(IProceduralValue input) {
            this.input = input;
            return this;
        }
    }
}
