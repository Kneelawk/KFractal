package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GlobalSet implements IProceduralValue {
    private int globalIndex;
    private IProceduralValue data;

    private GlobalSet(int globalIndex, IProceduralValue data) {
        this.globalIndex = globalIndex;
        this.data = data;
    }

    public int getGlobalIndex() {
        return globalIndex;
    }

    public IProceduralValue getData() {
        return data;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitGlobalSet(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor)
            throws FractalException {
        return visitor.visitGlobalSet(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("globalIndex", globalIndex)
                .append("data", data)
                .toString();
    }

    public static GlobalSet create(int globalIndex, IProceduralValue data) {
        if (data == null)
            throw new NullPointerException("Data cannot be null");
        return new GlobalSet(globalIndex, data);
    }

    public static class Builder {
        private int globalIndex;
        private IProceduralValue data;

        public Builder() {
        }

        public Builder(int globalIndex, IProceduralValue data) {
            this.globalIndex = globalIndex;
            this.data = data;
        }

        public GlobalSet build() {
            if (data == null)
                throw new IllegalStateException("No data specified");
            return new GlobalSet(globalIndex, data);
        }

        public int getGlobalIndex() {
            return globalIndex;
        }

        public Builder setGlobalIndex(int globalIndex) {
            this.globalIndex = globalIndex;
            return this;
        }

        public IProceduralValue getData() {
            return data;
        }

        public Builder setData(IProceduralValue data) {
            this.data = data;
            return this;
        }
    }
}
