package com.kneelawk.kfractal.generator.validation;

public class ValidatingVisitorResult {
    private final boolean terminated;

    private ValidatingVisitorResult(boolean terminated) {
        this.terminated = terminated;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public static ValidatingVisitorResult create() {
        return new ValidatingVisitorResult(false);
    }

    public static ValidatingVisitorResult create(boolean terminated) {
        return new ValidatingVisitorResult(terminated);
    }

    public static class Builder {
        private boolean terminated;

        public Builder() {
        }

        public Builder(boolean terminated) {
            this.terminated = terminated;
        }

        public ValidatingVisitorResult build() {
            return new ValidatingVisitorResult(terminated);
        }

        public boolean isTerminated() {
            return terminated;
        }

        public Builder setTerminated(boolean terminated) {
            this.terminated = terminated;
            return this;
        }
    }
}
