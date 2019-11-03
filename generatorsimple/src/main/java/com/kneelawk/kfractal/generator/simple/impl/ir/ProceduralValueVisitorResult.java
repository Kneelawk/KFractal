package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;

public class ProceduralValueVisitorResult {
    private final boolean terminated;
    private final BlockTerminationType terminationType;
    private final IEngineValue valueType;
    private final IEngineValue returnType;
    private final int jumpBlockIndex;

    private ProceduralValueVisitorResult(boolean terminated,
                                        BlockTerminationType terminationType,
                                        IEngineValue valueType,
                                        IEngineValue returnType, int jumpBlockIndex) {
        this.terminated = terminated;
        this.terminationType = terminationType;
        this.valueType = valueType;
        this.returnType = returnType;
        this.jumpBlockIndex = jumpBlockIndex;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public BlockTerminationType getTerminationType() {
        return terminationType;
    }

    public IEngineValue getValueType() {
        return valueType;
    }

    public IEngineValue getReturnType() {
        return returnType;
    }

    public int getJumpBlockIndex() {
        return jumpBlockIndex;
    }

    public static ProceduralValueVisitorResult create(
            IEngineValue valueType) {
        if (valueType == null)
            throw new NullPointerException("ValueType cannot be null");
        return new ProceduralValueVisitorResult(false, null, valueType, null, -1);
    }

    public static class Builder {
        private boolean terminated;
        private BlockTerminationType terminationType = null;
        private IEngineValue valueType;
        private IEngineValue returnType = null;
        private int jumpBlockIndex = -1;

        public Builder() {
        }

        public Builder(boolean terminated,
                       BlockTerminationType terminationType,
                       IEngineValue valueType,
                       IEngineValue returnType, int jumpBlockIndex) {
            this.terminated = terminated;
            this.terminationType = terminationType;
            this.valueType = valueType;
            this.returnType = returnType;
            this.jumpBlockIndex = jumpBlockIndex;
        }

        public ProceduralValueVisitorResult build() {
            if (valueType == null)
                throw new IllegalStateException("No valueType specified");
            return new ProceduralValueVisitorResult(terminated, terminationType, valueType, returnType, jumpBlockIndex);
        }

        public boolean isTerminated() {
            return terminated;
        }

        public Builder setTerminated(boolean terminated) {
            this.terminated = terminated;
            return this;
        }

        public BlockTerminationType getTerminationType() {
            return terminationType;
        }

        public Builder setTerminationType(
                BlockTerminationType terminationType) {
            this.terminationType = terminationType;
            return this;
        }

        public IEngineValue getValueType() {
            return valueType;
        }

        public Builder setValueType(IEngineValue valueType) {
            this.valueType = valueType;
            return this;
        }

        public IEngineValue getReturnType() {
            return returnType;
        }

        public Builder setReturnType(IEngineValue returnType) {
            this.returnType = returnType;
            return this;
        }

        public int getJumpBlockIndex() {
            return jumpBlockIndex;
        }

        public Builder setJumpBlockIndex(int jumpBlockIndex) {
            this.jumpBlockIndex = jumpBlockIndex;
            return this;
        }
    }
}
