package com.kneelawk.kfractal.generator.simple.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.IProgramEngine;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValueFactory;
import com.kneelawk.kfractal.generator.api.engine.value.IFunctionValue;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import com.kneelawk.kfractal.generator.simple.impl.ir.SimpleGeneratorInstructionVisitor;
import org.apache.commons.math3.complex.Complex;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Kneelawk on 7/17/19.
 */
public class SimpleProgramEngine implements IProgramEngine {
    private final SimpleEngineValueFactory factory = new SimpleEngineValueFactory(this);

    private Map<String, ValueContainer> globalScope;
    private Program program;

    @Override
    public void initialize(Program program) throws FractalEngineException {
        this.program = program;
        ImmutableMap.Builder<String, ValueContainer> globalVariablesBuilder = ImmutableMap.builder();
        addVariablesToScope(globalVariablesBuilder, program.getGlobalVariables().values());
        globalScope = globalVariablesBuilder.build();
    }

    private void addVariablesToScope(ImmutableMap.Builder<String, ValueContainer> scope,
                                     Iterable<VariableDeclaration> variables)
            throws FractalEngineException {
        for (VariableDeclaration v : variables) {
            IEngineValue value;
            if (ValueTypes.isPointer(v.getType()) && v.getAttributes().contains(IAttribute.PREALLOCATED)) {
                value = factory.newPointer(getDefaultValue(ValueTypes.toPointer(v.getType()).getPointerType()));
            } else {
                value = getDefaultValue(v.getType());
            }
            scope.put(v.getName(), new ValueContainer(v.getType(), value));
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
    public ValueType getGlobalValueType(String name) throws FractalEngineException {
        return globalScope.get(name).getType();
    }

    @Override
    public IEngineValue getGlobalValue(String name) throws FractalEngineException {
        return globalScope.get(name).getValue();
    }

    @Override
    public void setGlobalValue(String name, IEngineValue value) throws FractalEngineException {
        globalScope.get(name).setValue(value);
    }

    @Override
    public ValueTypes.FunctionType getFunctionSignature(String name) throws FractalEngineException {
        FunctionDefinition definition = program.getFunctions().get(name);
        return ValueTypes.FUNCTION(definition.getReturnType(),
                definition.getArgumentList().stream().map(VariableDeclaration::getType).collect(Collectors.toList()));
    }

    @Override
    public IFunctionValue getFunction(String name, List<IEngineValue> contextValues) throws FractalEngineException {
        if (program.getFunctions().containsKey(name)) {
            return new SimpleFunctionValue(this, name, ImmutableList.copyOf(contextValues));
        } else {
            throw new FractalEngineException("Unable to find function: " + name);
        }
    }

    IEngineValue invokeFunction(String name, List<IEngineValue> contextVariables, List<IEngineValue> arguments)
            throws FractalEngineException {
        FunctionDefinition definition = program.getFunctions().get(name);
        List<VariableDeclaration> contextVariableList = definition.getContextVariableList();
        List<VariableDeclaration> argumentList = definition.getArgumentList();

        // check arguments
        if (contextVariables.size() != contextVariableList.size())
            throw new FractalEngineException("Incompatible number of context variables");
        if (arguments.size() != argumentList.size())
            throw new FractalEngineException("Incompatible number of arguments");

        // setup scope
        ImmutableMap.Builder<String, ValueContainer> functionScope = ImmutableMap.builder();
        functionScope.putAll(globalScope);
        for (int i = 0; i < contextVariables.size(); i++) {
            functionScope.put(contextVariableList.get(i).getName(),
                    new ValueContainer(contextVariableList.get(i).getType(), contextVariables.get(i)));
        }
        for (int i = 0; i < arguments.size(); i++) {
            functionScope.put(argumentList.get(i).getName(),
                    new ValueContainer(argumentList.get(i).getType(), arguments.get(i)));
        }
        addVariablesToScope(functionScope, definition.getLocalVariables().values());

        // setup the instruction visitor
        SimpleGeneratorInstructionVisitor visitor = new SimpleGeneratorInstructionVisitor(this, functionScope.build());

        // visit the instructions
        try {
            for (IInstruction instruction : definition.getBody()) {
                boolean ret = instruction.accept(visitor);
                if (ret) {
                    return visitor.getReturnValue();
                }
            }
        } catch (FractalIRException e) {
            // rethrow this exception as something that's compatible with this interface
            throw new FractalEngineException("FractalIRException", e);
        }

        throw new FractalEngineException("Function failed to return a value");
    }
}
