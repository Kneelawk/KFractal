package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.Scope;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutputVisitor;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;
import com.kneelawk.kfractal.generator.simple.impl.ValueContainer;

import java.util.List;

/**
 * Created by Kneelawk on 7/18/19.
 */
public class SimpleGeneratorInstructionOutputVisitor implements IInstructionOutputVisitor<Void> {
    private List<ValueContainer> globalScope;
    private List<ValueContainer> contextScope;
    private List<ValueContainer> argumentScope;
    private List<ValueContainer> localScope;
    private IEngineValue value;

    public SimpleGeneratorInstructionOutputVisitor(
            List<ValueContainer> globalScope,
            List<ValueContainer> contextScope,
            List<ValueContainer> argumentScope,
            List<ValueContainer> localScope, IEngineValue value) {
        this.globalScope = globalScope;
        this.contextScope = contextScope;
        this.argumentScope = argumentScope;
        this.localScope = localScope;
        this.value = value;
    }

    @Override
    public Void visitVariableReference(VariableReference reference) throws FractalIRException {
        int index = reference.getIndex();
        Scope scope = reference.getScope();
        List<ValueContainer> scopeList;
        switch (scope) {
            case GLOBAL:
                scopeList = globalScope;
                break;
            case CONTEXT:
                scopeList = contextScope;
                break;
            case ARGUMENTS:
                scopeList = argumentScope;
                break;
            case LOCAL:
                scopeList = localScope;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + scope);
        }

        scopeList.get(index).setValue(value);
        return null;
    }

    @Override
    public Void visitVoid() throws FractalIRException {
        return null;
    }
}
