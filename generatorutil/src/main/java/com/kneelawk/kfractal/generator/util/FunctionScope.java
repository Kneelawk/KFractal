package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.ir.Scope;

import java.util.List;

public class FunctionScope<T> {
    private final List<T> globalScope;
    private final List<T> contextScope;
    private final List<T> argumentScope;
    private final List<T> localScope;

    public FunctionScope(List<T> globalScope, List<T> contextScope, List<T> argumentScope, List<T> localScope) {
        this.globalScope = globalScope;
        this.contextScope = contextScope;
        this.argumentScope = argumentScope;
        this.localScope = localScope;
    }

    public List<T> getScope(Scope scope) {
        switch (scope) {
            case GLOBAL:
                return globalScope;
            case CONTEXT:
                return contextScope;
            case ARGUMENTS:
                return argumentScope;
            case LOCAL:
                return localScope;
            default:
                throw new IllegalStateException("Unknown scope type: " + scope);
        }
    }

    public T get(Scope scope, int index) {
        return getScope(scope).get(index);
    }
}
