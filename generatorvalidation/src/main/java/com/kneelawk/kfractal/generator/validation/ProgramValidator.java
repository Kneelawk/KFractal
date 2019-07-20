package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProgramValidator {
    public static void checkValidity(Program program) throws FractalException {
        checkVariables(program.getGlobalVariables().values());
        Map<String, ValueInfo> globalVariables = ImmutableMap.copyOf(program.getGlobalVariables().values().stream()
                .map(v -> new ImmutablePair<>(v.getName(),
                        new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));

        for (FunctionDefinition function : program.getFunctions().values()) {
            // setup local variable scope
            Map<String, ValueInfo> localScope = Maps.newHashMap();
            localScope.putAll(globalVariables);
            checkArguments(function.getContextVariableList());
            checkVariableNameConflicts(localScope, function.getContextVariableList());
            localScope.putAll(function.getContextVariableList().stream()
                    .map(v -> new ImmutablePair<>(v.getName(),
                            new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
            checkArguments(function.getArgumentList());
            checkVariableNameConflicts(localScope, function.getArgumentList());
            localScope.putAll(function.getArgumentList().stream().map(v -> new ImmutablePair<>(v.getName(),
                    new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
            checkVariables(function.getLocalVariables().values());
            checkVariableNameConflicts(localScope, function.getLocalVariables().values());
            localScope.putAll(function.getLocalVariables().values().stream().map(v -> new ImmutablePair<>(v.getName(),
                    new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));

            ValidatingInstructionVisitor visitor =
                    new ValidatingInstructionVisitor(program.getFunctions(), localScope, function.getReturnType());

            for (IInstruction instruction : function.getBody()) {
                instruction.accept(visitor);
            }

            if (!visitor.isReturned()) {
                throw new FractalIRValidationException("Function is lacking a return statement");
            }
        }
    }

    private static void checkArguments(Collection<VariableDeclaration> arguments) throws FractalIRValidationException {
        for (VariableDeclaration v : arguments) {
            ValueType type = v.getType();
            Set<IAttribute> attributes = v.getAttributes();

            // check variable types
            checkVariableType(type);

            // check variable attributes
            if (attributes.contains(IAttribute.PREALLOCATED)) {
                throw new IllegalVariableAttributeException("Illegal argument attribute: PREALLOCATED");
            }
        }
    }

    private static void checkVariables(Collection<VariableDeclaration> variables)
            throws FractalIRValidationException {
        for (VariableDeclaration v : variables) {
            ValueType type = v.getType();
            Set<IAttribute> attributes = v.getAttributes();

            // check variable types
            checkVariableType(type);

            // check variable attributes
            if (!ValueTypes.isPointer(type)) {
                if (attributes.contains(IAttribute.PREALLOCATED)) {
                    throw new IllegalVariableAttributeException("Illegal variable attribute: PREALLOCATED");
                }
            }
        }
    }

    private static void checkVariableType(ValueType type) throws FractalIRValidationException {
        if (ValueTypes.isVoid(type)) {
            throw new IllegalVariableTypeException("Illegal variable type: VOID");
        } else if (ValueTypes.isFunction(type)) {
            if (ValueTypes.isNullFunction(type)) {
                throw new IllegalVariableTypeException("Illegal variable type: NULL_FUNCTION");
            }
        } else if (ValueTypes.isPointer(type)) {
            if (ValueTypes.isNullPointer(type)) {
                throw new IllegalVariableTypeException("Illegal variable type: NULL_POINTER");
            }
        }
    }

    private static void checkVariableNameConflicts(Map<String, ValueInfo> scope,
                                                   Collection<VariableDeclaration> newVariables)
            throws NameConflictException {
        for (VariableDeclaration v : newVariables) {
            if (scope.containsKey(v.getName())) {
                throw new NameConflictException("Cannot redefine variable: " + v.getName());
            }
        }
    }
}
