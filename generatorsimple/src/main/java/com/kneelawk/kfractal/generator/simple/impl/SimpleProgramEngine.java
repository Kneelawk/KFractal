package com.kneelawk.kfractal.generator.simple.impl;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.IProgramEngine;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValueFactory;
import com.kneelawk.kfractal.generator.api.engine.value.IFunctionValue;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.generator.simple.impl.ir.BasicBlockResult;
import com.kneelawk.kfractal.generator.simple.impl.ir.BlockTerminationType;
import com.kneelawk.kfractal.generator.simple.impl.ir.ValueManager;
import org.apache.commons.math3.complex.Complex;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kneelawk on 7/17/19.
 */
public class SimpleProgramEngine implements IProgramEngine {
    private static final int STARTING_BLOCK_INDEX = -1;

    private final SimpleEngineValueFactory factory = new SimpleEngineValueFactory(this);

    private List<ValueContainer> globalScope;
    private Program program;

    @Override
    public void initialize(Program program) throws FractalEngineException {
        this.program = program;
        ImmutableList.Builder<ValueContainer> globalVariablesBuilder = ImmutableList.builder();
        addVariablesToScope(globalVariablesBuilder, program.getGlobalVariables());
        globalScope = globalVariablesBuilder.build();
    }

    private void addVariablesToScope(ImmutableList.Builder<ValueContainer> scope,
                                     Iterable<GlobalDeclaration> variables)
            throws FractalEngineException {
        for (GlobalDeclaration v : variables) {
            IEngineValue value;
            if (ValueTypes.isPointer(v.getType()) && v.getAttributes().contains(IGlobalAttribute.PREALLOCATED)) {
                value = factory.newPointer(getDefaultValue(ValueTypes.toPointer(v.getType()).getPointerType()));
            } else {
                value = getDefaultValue(v.getType());
            }
            scope.add(new ValueContainer(v.getType(), value));
        }
    }

    private IEngineValue getDefaultValue(ValueType type) throws FractalEngineException {
        if (ValueTypes.isVoid(type)) {
            throw new RuntimeException("Unable to create a value type for void");
        } else if (ValueTypes.isBool(type)) {
            return factory.newBool(false);
        } else if (ValueTypes.isInt(type)) {
            return factory.newInt(0);
        } else if (ValueTypes.isReal(type)) {
            return factory.newReal(0);
        } else if (ValueTypes.isComplex(type)) {
            return factory.newComplex(new Complex(0, 0));
        } else if (ValueTypes.isFunction(type)) {
            return SimpleNullFunctionValue.INSTANCE;
        } else if (ValueTypes.isPointer(type)) {
            return factory.nullPointer();
        } else {
            throw new RuntimeException("Unknown value type: " + type);
        }
    }

    @Override
    public IEngineValueFactory getValueFactory() throws FractalEngineException {
        return factory;
    }

    @Override
    public ValueType getGlobalValueType(int index) throws FractalEngineException {
        return globalScope.get(index).getType();
    }

    @Override
    public IEngineValue getGlobalValue(int index) throws FractalEngineException {
        return globalScope.get(index).getValue();
    }

    @Override
    public void setGlobalValue(int index, IEngineValue value) throws FractalEngineException {
        globalScope.get(index).setValue(value);
    }

    @Override
    public ValueTypes.FunctionType getFunctionSignature(int index) throws FractalEngineException {
        FunctionDefinition definition = program.getFunctions().get(index);
        return ValueTypes.FUNCTION(definition.getReturnType(),
                definition.getArguments().stream().map(ArgumentDeclaration::getType).collect(Collectors.toList()));
    }

    @Override
    public IFunctionValue getFunction(int index, List<IEngineValue> contextValues) throws FractalEngineException {
        if (index < program.getFunctions().size()) {
            return new SimpleFunctionValue(this, index, ImmutableList.copyOf(contextValues));
        } else {
            throw new FractalEngineException("Unable to find function: index: " + index);
        }
    }

    IEngineValue invokeFunction(int index, List<IEngineValue> contextVariables, List<IEngineValue> arguments)
            throws FractalException {
        FunctionDefinition definition = program.getFunctions().get(index);
        List<ArgumentDeclaration> contextVariableList = definition.getContextVariables();
        List<ArgumentDeclaration> argumentList = definition.getArguments();

        // check arguments
        if (contextVariables.size() != contextVariableList.size())
            throw new FractalEngineException("Incompatible number of context variables");
        if (arguments.size() != argumentList.size())
            throw new FractalEngineException("Incompatible number of arguments");

        // setup scope
        ImmutableList.Builder<IEngineValue> contextScope = ImmutableList.builder();
        for (int i = 0; i < contextVariables.size(); i++) {
            contextScope.add(contextVariables.get(i));
        }

        ImmutableList.Builder<IEngineValue> argumentScope = ImmutableList.builder();
        for (int i = 0; i < arguments.size(); i++) {
            argumentScope.add(arguments.get(i));
        }

        List<BasicBlock> blocks = definition.getBlocks();

        ValueManager manager = new ValueManager();
        BasicBlockResult result = BasicBlockResult.create(0);
        int lastBlockIndex = STARTING_BLOCK_INDEX;
        while (result.getTerminationType() != BlockTerminationType.RETURNED) {
            int targetBlock = result.getJumpBlockIndex();
            BasicBlock block = blocks.get(targetBlock);
            result =
                    manager.runBasicBlock(block, lastBlockIndex, definition, contextVariables, arguments, program, this,
                            globalScope);
            lastBlockIndex = targetBlock;
        }

        return result.getReturnValue();
    }
}
