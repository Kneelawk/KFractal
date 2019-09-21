package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GlobalGet implements IValue {
    private int globalIndex;

    private GlobalGet(int globalIndex) {
        this.globalIndex = globalIndex;
    }

    public int getGlobalIndex() {
        return globalIndex;
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitGlobalGet(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("globalIndex", globalIndex)
                .toString();
    }

    public static GlobalGet create(int globalIndex) {
        return new GlobalGet(globalIndex);
    }

    public static class Builder {
        private int globalIndex;

        public Builder() {
        }

        public Builder(int globalIndex) {
            this.globalIndex = globalIndex;
        }

        public GlobalGet build() {
            return new GlobalGet(globalIndex);
        }

        public int getGlobalIndex() {
            return globalIndex;
        }

        public Builder setGlobalIndex(int globalIndex) {
            this.globalIndex = globalIndex;
            return this;
        }
    }
}
