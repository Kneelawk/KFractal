package com.kneelawk.kfractal.generator.api.ir.phi;

import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PhiBranch {
    private IPhiInput value;
    private int previousBlockIndex;

    private PhiBranch(IPhiInput value, int previousBlockIndex) {
        this.value = value;
        this.previousBlockIndex = previousBlockIndex;
    }

    public IPhiInput getValue() {
        return value;
    }

    public int getPreviousBlockIndex() {
        return previousBlockIndex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("value", value)
                .append("previousBlockIndex", previousBlockIndex)
                .toString();
    }

    public static PhiBranch create(IPhiInput value, int previousBlockIndex) {
        if (value == null)
            throw new NullPointerException("Value cannot be null");
        return new PhiBranch(value, previousBlockIndex);
    }

    public static class Builder {
        private IPhiInput value;
        private int previousBlockIndex;

        public Builder() {
        }

        public Builder(IPhiInput value, int previousBlockIndex) {
            this.value = value;
            this.previousBlockIndex = previousBlockIndex;
        }

        public PhiBranch build() {
            if (value == null)
                throw new IllegalStateException("No value specified");
            return new PhiBranch(value, previousBlockIndex);
        }

        public IPhiInput getValue() {
            return value;
        }

        public Builder setValue(IPhiInput value) {
            this.value = value;
            return this;
        }

        public int getPreviousBlockIndex() {
            return previousBlockIndex;
        }

        public Builder setPreviousBlockIndex(int previousBlockIndex) {
            this.previousBlockIndex = previousBlockIndex;
            return this;
        }
    }
}
