package com.kneelawk.kfractal.generator.api.ir.reference;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInput;
import com.kneelawk.kfractal.generator.api.ir.phi.IPhiInputVisitor;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Kneelawk on 5/26/19.
 */
public class ArgumentReference implements IValue, IPhiInput {
    private ArgumentScope scope;
    private int index;

    private ArgumentReference(ArgumentScope scope, int index) {
        this.scope = scope;
        this.index = index;
    }

    public ArgumentScope getScope() {
        return scope;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public <R> R accept(IPhiInputVisitor<R> visitor) throws FractalException {
        return visitor.visitArgumentReference(this);
    }

    @Override
    public <R> R accept(IValueVisitor<R> visitor) throws FractalException {
        return visitor.visitArgumentReference(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("scope", scope)
                .append("index", index)
                .toString();
    }

    public ArgumentReference offset(int offset) {
        if (index + offset < 0)
            throw new IndexOutOfBoundsException("VariableReference index cannot be less than 0");
        return new ArgumentReference(scope, index + offset);
    }

    public static ArgumentReference create(ArgumentScope scope, int index) {
        if (scope == null)
            throw new NullPointerException("Scope cannot be null");
        if (index < 0)
            throw new IndexOutOfBoundsException("VariableReference index cannot be less than 0");
        return new ArgumentReference(scope, index);
    }

    public static class Builder {
        private ArgumentScope scope;
        private int index;

        public Builder() {
        }

        public Builder(ArgumentScope scope, int index) {
            this.scope = scope;
            this.index = index;
        }

        public ArgumentReference build() {
            if (scope == null)
                throw new IllegalStateException("No scope specified");
            if (index < 0)
                throw new IndexOutOfBoundsException("VariableReference index cannot be less than 0");
            return new ArgumentReference(scope, index);
        }

        public ArgumentScope getScope() {
            return scope;
        }

        public Builder setScope(ArgumentScope scope) {
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
