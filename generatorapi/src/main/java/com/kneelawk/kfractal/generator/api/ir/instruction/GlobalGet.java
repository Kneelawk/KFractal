package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValue;
import com.kneelawk.kfractal.generator.api.ir.IProceduralValueVisitor;
import com.kneelawk.kfractal.generator.api.ir.IValueVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GlobalGet implements IProceduralValue {
    private String globalName;

    private GlobalGet(String globalName) {
        this.globalName = globalName;
    }

    public String getGlobalName() {
        return globalName;
    }

    @Override
    public <R> R accept(IProceduralValueVisitor<R> visitor) throws FractalException {
        return visitor.visitGlobalGet(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitGlobalGet(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("globalName", globalName)
                .toString();
    }

    public static GlobalGet create(String globalName) {
        if (globalName == null)
            throw new NullPointerException("GlobalName cannot be null");
        return new GlobalGet(globalName);
    }

    public static class Builder {
        private String globalName;

        public Builder() {
        }

        public Builder(String globalName) {
            this.globalName = globalName;
        }

        public GlobalGet build() {
            if (globalName == null)
                throw new IllegalStateException("No globalName specified");
            return new GlobalGet(globalName);
        }

        public String getGlobalName() {
            return globalName;
        }

        public Builder setGlobalName(String globalName) {
            this.globalName = globalName;
            return this;
        }
    }
}
