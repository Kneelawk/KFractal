package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * BoolNot - Instruction. Gets the not value of the argument.
 * <p>
 * BoolNot(Bool input)
 */
public class BoolNot implements IValue {
    private IValue input;

    private BoolNot(IValue input) {
        this.input = input;
    }

    public IValue getInput() {
        return input;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitBoolNot(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("input", input)
                .toString();
    }

    public static BoolNot create(IValue input) {
        if (input == null)
            throw new NullPointerException("Input cannot be null");
        return new BoolNot(input);
    }

    public static class Builder {
        private IValue input;

        public Builder() {
        }

        public Builder(IValue input) {
            this.input = input;
        }

        public BoolNot build() {
            if (input == null)
                throw new IllegalStateException("No input specified");
            return new BoolNot(input);
        }

        public IValue getInput() {
            return input;
        }

        public Builder setInput(IValue input) {
            this.input = input;
            return this;
        }
    }
}
