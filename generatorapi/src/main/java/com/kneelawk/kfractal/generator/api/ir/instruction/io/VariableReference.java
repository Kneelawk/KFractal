package com.kneelawk.kfractal.generator.api.ir.instruction.io;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.Scope;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Kneelawk on 5/26/19.
 */
public class VariableReference implements IInstructionInput, IInstructionOutput {
    private Scope scope;
    private int index;

    private VariableReference(Scope scope, int index) {
        this.scope = scope;
        this.index = index;
    }

    public Scope getScope() {
        return scope;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public <R> R accept(IInstructionInputVisitor<R> visitor) throws FractalException {
        return visitor.visitVariableReference(this);
    }

    @Override
    public <R> R accept(IInstructionOutputVisitor<R> visitor) throws FractalException {
        return visitor.visitVariableReference(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("scope", scope)
                .append("index", index)
                .toString();
    }

    public VariableReference offset(int offset) {
        if (index + offset < 0)
            throw new IndexOutOfBoundsException("VariableReference index cannot be less than 0");
        return new VariableReference(scope, index + offset);
    }

    public static VariableReference create(Scope scope, int index) {
        if (scope == null)
            throw new NullPointerException("Scope cannot be null");
        if (index < 0)
            throw new IndexOutOfBoundsException("VariableReference index cannot be less than 0");
        return new VariableReference(scope, index);
    }

    public static class Builder {
        private Scope scope;
        private int index;

        public Builder() {
        }

        public Builder(Scope scope, int index) {
            this.scope = scope;
            this.index = index;
        }

        public VariableReference build() {
            if (scope == null)
                throw new IllegalStateException("No scope specified");
            if (index < 0)
                throw new IndexOutOfBoundsException("VariableReference index cannot be less than 0");
            return new VariableReference(scope, index);
        }

        public Scope getScope() {
            return scope;
        }

        public Builder setScope(Scope scope) {
            this.scope = scope;
            return this;
        }

        public int getIndex() {
            return index;
        }

        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }
    }
}
