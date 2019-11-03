package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;

public class ProceduralValueVisitorResult {
    private final boolean terminated;
    private final BlockTerminationType terminationType;
    private final IEngineValue resultValue;
    private final IEngineValue returnValue;
    private final int jumpBlockIndex;

    private ProceduralValueVisitorResult(boolean terminated,
                                         BlockTerminationType terminationType,
                                         IEngineValue resultValue,
                                         IEngineValue returnValue, int jumpBlockIndex) {
        this.terminated = terminated;
        this.terminationType = terminationType;
        this.resultValue = resultValue;
        this.returnValue = returnValue;
        this.jumpBlockIndex = jumpBlockIndex;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public BlockTerminationType getTerminationType() {
        return terminationType;
    }

    public IEngineValue getResultValue() {
        return resultValue;
    }

    public IEngineValue getReturnValue() {
        return returnValue;
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
        private IEngineValue resultValue;
        private IEngineValue returnValue = null;
        private int jumpBlockIndex = -1;

        public Builder() {
        }

        public Builder(boolean terminated,
                       BlockTerminationType terminationType,
                       IEngineValue resultValue,
                       IEngineValue returnValue, int jumpBlockIndex) {
            this.terminated = terminated;
            this.terminationType = terminationType;
            this.resultValue = resultValue;
            this.returnValue = returnValue;
            this.jumpBlockIndex = jumpBlockIndex;
        }

        public ProceduralValueVisitorResult build() {
            if (resultValue == null)
                throw new IllegalStateException("No valueType specified");
            return new ProceduralValueVisitorResult(terminated, terminationType, resultValue, returnValue, jumpBlockIndex);
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

        public IEngineValue getResultValue() {
            return resultValue;
        }

        public Builder setResultValue(IEngineValue resultValue) {
            this.resultValue = resultValue;
            return this;
        }

        public IEngineValue getReturnValue() {
            return returnValue;
        }

        public Builder setReturnValue(IEngineValue returnValue) {
            this.returnValue = returnValue;
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
