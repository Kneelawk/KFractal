package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.ValueType;

interface TypeLookupResult {
    ValueType getType();

    ErrorType getErrorType();

    boolean isFound();

    class Error implements TypeLookupResult {
        private final ErrorType errorType;

        Error(ErrorType errorType) {
            this.errorType = errorType;
        }

        @Override
        public ValueType getType() {
            return null;
        }

        @Override
        public ErrorType getErrorType() {
            return errorType;
        }

        @Override
        public boolean isFound() {
            return false;
        }
    }

    class Found implements TypeLookupResult {
        private final ValueType type;

        Found(ValueType type) {
            this.type = type;
        }

        @Override
        public ValueType getType() {
            return type;
        }

        @Override
        public ErrorType getErrorType() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isFound() {
            return true;
        }
    }

    enum ErrorType {
        REFERENCE_LOOP,
        INVALID_TYPE,
        INVALID_INSTRUCTION
    }
}
