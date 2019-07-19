package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutputVisitor;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;
import com.kneelawk.kfractal.generator.simple.impl.ValueContainer;

import java.util.Map;

/**
 * Created by Kneelawk on 7/18/19.
 */
public class SimpleGeneratorInstructionOutputVisitor implements IInstructionOutputVisitor<Void> {
    private Map<String, ValueContainer> scope;
    private IEngineValue value;

    public SimpleGeneratorInstructionOutputVisitor(
            Map<String, ValueContainer> scope, IEngineValue value) {
        this.scope = scope;
        this.value = value;
    }

    @Override
    public Void visitVariableReference(VariableReference reference) throws FractalIRException {
        scope.get(reference.getName()).setValue(value);
        return null;
    }

    @Override
    public Void visitVoid() throws FractalIRException {
        return null;
    }
}
