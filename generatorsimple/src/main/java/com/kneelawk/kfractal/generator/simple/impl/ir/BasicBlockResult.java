package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;

public class BasicBlockResult {
    private final BlockTerminationType terminationType;
    private final IEngineValue returnValue;
    private final int jumpBlockIndex;

    private BasicBlockResult(BlockTerminationType terminationType,
                            IEngineValue returnValue, int jumpBlockIndex) {
        this.terminationType = terminationType;
        this.returnValue = returnValue;
        this.jumpBlockIndex = jumpBlockIndex;
    }

    public BlockTerminationType getTerminationType() {
        return terminationType;
    }

    public IEngineValue getReturnValue() {
        return returnValue;
    }

    public int getJumpBlockIndex() {
        return jumpBlockIndex;
    }

    public static BasicBlockResult create(IEngineValue returnValue) {
        if (returnValue == null)
            throw new NullPointerException("ReturnValue cannot be null");
        // this should throw an ArrayIndexOutOfBoundsException if misused
        return new BasicBlockResult(BlockTerminationType.RETURNED, returnValue, -1);
    }

    public static BasicBlockResult create(int jumpBlockIndex) {
        return new BasicBlockResult(BlockTerminationType.JUMPED, null, jumpBlockIndex);
    }

    public static BasicBlockResult create(
            BlockTerminationType terminationType,
            IEngineValue returnValue, int jumpBlockIndex) {
        if (terminationType == null)
            throw new NullPointerException("TerminationType cannot be null");
        if (returnValue == null)
            throw new NullPointerException("ReturnValue cannot be null");
        return new BasicBlockResult(terminationType, returnValue, jumpBlockIndex);
    }

    public static class Builder {
        private BlockTerminationType terminationType;
        private IEngineValue returnValue = null;
        private int jumpBlockIndex = -1;

        public Builder() {
        }

        public Builder(BlockTerminationType terminationType,
                       IEngineValue returnValue, int jumpBlockIndex) {
            this.terminationType = terminationType;
            this.returnValue = returnValue;
            this.jumpBlockIndex = jumpBlockIndex;
        }

        public BasicBlockResult build() {
            if (terminationType == null)
                throw new IllegalStateException("No terminationType specified");
            return new BasicBlockResult(terminationType, returnValue, jumpBlockIndex);
        }

        public BlockTerminationType getTerminationType() {
            return terminationType;
        }

        public Builder setTerminationType(
                BlockTerminationType terminationType) {
            this.terminationType = terminationType;
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
