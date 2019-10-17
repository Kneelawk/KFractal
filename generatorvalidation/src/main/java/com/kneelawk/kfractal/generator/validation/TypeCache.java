package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.IValue;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueType;

import java.util.Map;

class TypeCache {
    private final Map<IValue, ValueType> types = Maps.newHashMap();

    ValueType getType(IValue instruction, ValidatingVisitorContext context) throws FractalException {
        return getType(instruction, context.getProgram(), context.getFunction());
    }

    private ValueType getType(IValue instruction, Program program, FunctionDefinition functionDefinition)
            throws FractalException {
        TypeLookupVisitorContext context =
                TypeLookupVisitorContext.create(ImmutableSet.of(), program, functionDefinition);
        TypeLookupResult result = getType(instruction, context);
        if (result.isFound()) {
            return result.getType();
        } else {
            throw new FractalIRValidationException("Unable to determine instruction type, " + result.getErrorType());
        }
    }

    TypeLookupResult getType(IValue instruction, TypeLookupVisitorContext context)
            throws FractalException {
        if (types.containsKey(instruction)) {
            return new TypeLookupResult.Found(types.get(instruction));
        }

        TypeLookupValueVisitor visitor = new TypeLookupValueVisitor(this, context);
        TypeLookupResult result = instruction.accept(visitor);
        if (result.isFound()) {
            types.put(instruction, result.getType());
        }
        return result;

    }
}
