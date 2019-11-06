package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GlobalSet implements IProceduralValue {
    private String globalName;
    private IProceduralValue data;

    private GlobalSet(String globalName, IProceduralValue data) {
        this.globalName = globalName;
        this.data = data;
    }

    public String getGlobalName() {
        return globalName;
    }

    public IProceduralValue getData() {
        return data;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitGlobalSet(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitGlobalSet(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("globalName", globalName)
                .append("data", data)
                .toString();
    }

    public static GlobalSet create(String globalName,
                                   IProceduralValue data) {
        if (globalName == null)
            throw new NullPointerException("GlobalName cannot be null");
        if (data == null)
            throw new NullPointerException("Data cannot be null");
        return new GlobalSet(globalName, data);
    }

    public static class Builder {
        private String globalName;
        private IProceduralValue data;

        public Builder() {
        }

        public Builder(String globalName, IProceduralValue data) {
            this.globalName = globalName;
            this.data = data;
        }

        public GlobalSet build() {
            if (globalName == null)
                throw new IllegalStateException("No globalName specified");
            if (data == null)
                throw new IllegalStateException("No data specified");
            return new GlobalSet(globalName, data);
        }

        public String getGlobalName() {
            return globalName;
        }

        public Builder setGlobalName(String globalName) {
            this.globalName = globalName;
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
