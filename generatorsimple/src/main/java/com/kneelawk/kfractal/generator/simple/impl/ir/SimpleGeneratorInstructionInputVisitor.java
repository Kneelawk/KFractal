package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;
import com.kneelawk.kfractal.generator.simple.impl.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Kneelawk on 7/18/19.
 */
public class SimpleGeneratorInstructionInputVisitor implements IInstructionInputVisitor<IEngineValue> {
    private SimpleProgramEngine engine;
    private Map<String, ValueContainer> scope;

    public SimpleGeneratorInstructionInputVisitor(SimpleProgramEngine engine,
                                                  Map<String, ValueContainer> scope) {
        this.engine = engine;
        this.scope = scope;
    }

    @Override
    public IEngineValue visitVariableReference(VariableReference reference) throws FractalIRException {
        return scope.get(reference.getName()).getValue();
    }

    @Override
    public IEngineValue visitBoolConstant(BoolConstant constant) throws FractalIRException {
        return new SimpleBoolValue(constant.isValue());
    }

    @Override
    public IEngineValue visitIntConstant(IntConstant constant) throws FractalIRException {
        return new SimpleIntValue(constant.getValue());
    }

    @Override
    public IEngineValue visitRealConstant(RealConstant constant) throws FractalIRException {
        return new SimpleRealValue(constant.getValue());
    }

    @Override
    public IEngineValue visitComplexConstant(ComplexConstant constant) throws FractalIRException {
        return new SimpleComplexValue(constant.getValue());
    }

    @Override
    public IEngineValue visitFunctionContextConstant(FunctionContextConstant contextConstant)
            throws FractalException {
        List<IInstructionInput> contextVariables = contextConstant.getContextVariables();
        ImmutableList.Builder<IEngineValue> valueBuilder = ImmutableList.builder();
        for (IInstructionInput input : contextVariables) {
            valueBuilder.add(input.accept(this));
        }
        return engine.getFunction(contextConstant.getFunctionName(), valueBuilder.build());
    }

    @Override
    public IEngineValue visitNullPointer() throws FractalIRException {
        return SimpleNullPointerValue.INSTANCE;
    }

    @Override
    public IEngineValue visitNullFunction() throws FractalIRException {
        return SimpleNullFunctionValue.INSTANCE;
    }

    @Override
    public IEngineValue visitVoid() throws FractalIRException {
        return SimpleVoidValue.INSTANCE;
    }
}
