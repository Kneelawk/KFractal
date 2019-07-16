package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.generator.api.ir.instruction.PointerSet;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;
import org.apache.commons.math3.complex.Complex;

import java.util.Set;

public class ValueTypeUtils {
    private static final String variableNameFormat = "tmp%d";
    private static final String functionNameFormat = "f%d";

    private static String generateVariableName(Set<String> variables) {
        int i = 0;
        while (variables.contains(String.format(variableNameFormat, i))) {
            i++;
        }
        return String.format(variableNameFormat, i);
    }

    private static String generateFunctionName(Set<String> functions) {
        int i = 0;
        while (functions.contains(String.format(functionNameFormat, i))) {
            i++;
        }
        return String.format(functionNameFormat, i);
    }

    private static Set<String> getScope(Program.Builder programBuilder,
                                        FunctionDefinition.Builder functionBuilder, Iterable<String> extraVariables) {
        return ImmutableSet.<String>builder().addAll(programBuilder.getGlobalVariables().keySet())
                .addAll(functionBuilder.getContextVariables().keySet()).addAll(functionBuilder.getArguments().keySet())
                .addAll(functionBuilder.getLocalVariables().keySet()).addAll(extraVariables).build();
    }

    private static Set<String> getAllVariables(Program.Builder programBuilder,
                                               FunctionDefinition.Builder functionBuilder,
                                               Iterable<String> extraVariables) {
        ImmutableSet.Builder<String> scope = ImmutableSet.builder();

        // add global variables
        scope.addAll(programBuilder.getGlobalVariables().keySet());

        // add this function's variables
        scope.addAll(functionBuilder.getContextVariables().keySet());
        scope.addAll(functionBuilder.getArguments().keySet());
        scope.addAll(functionBuilder.getLocalVariables().keySet());

        // add the variables of every other function
        for (FunctionDefinition function : programBuilder.getFunctions().values()) {
            scope.addAll(function.getContextVariables().keySet());
            scope.addAll(function.getArguments().keySet());
            scope.addAll(function.getLocalVariables().keySet());
        }

        // add extra variables
        scope.addAll(extraVariables);

        return scope.build();
    }

    private static Set<String> getFunctionScope(Program.Builder programBuilder, Iterable<String> extraFunctions) {
        return ImmutableSet.<String>builder().addAll(programBuilder.getFunctions().keySet()).addAll(extraFunctions)
                .build();
    }

    static IInstructionInput createConstant(Program.Builder programBuilder,
                                            FunctionDefinition.Builder functionBuilder,
                                            Set<String> extraFunctions, Set<String> extraVariables,
                                            ValueType type) {
        if (ValueTypes.isVoid(type)) {
            return VoidConstant.INSTANCE;
        } else if (ValueTypes.isBool(type)) {
            return BoolConstant.create(false);
        } else if (ValueTypes.isInt(type)) {
            return IntConstant.create(0);
        } else if (ValueTypes.isReal(type)) {
            return RealConstant.create(0.0);
        } else if (ValueTypes.isComplex(type)) {
            return ComplexConstant.create(new Complex(0.0, 0.0));
        } else if (ValueTypes.isFunction(type)) {
            if (ValueTypes.isNullFunction(type)) {
                return NullFunction.INSTANCE;
            } else {
                ValueTypes.FunctionType functionType = ValueTypes.toFunction(type);
                String functionName = generateFunctionName(getFunctionScope(programBuilder, extraFunctions));
                FunctionDefinition.Builder newFunction = new FunctionDefinition.Builder();
                newFunction.setName(functionName);
                newFunction.setReturnType(functionType.getReturnType());
                for (ValueType argumentType : functionType.getArgumentTypes()) {
                    newFunction.addArgument(VariableDeclaration
                            .create(argumentType,
                                    generateVariableName(getScope(programBuilder, newFunction, extraVariables))));
                }
                newFunction.addStatement(
                        Return.create(createConstant(programBuilder, newFunction,
                                ImmutableSet.<String>builder().addAll(extraFunctions).add(functionName).build(),
                                ImmutableSet.<String>builder().addAll(extraVariables)
                                        .addAll(functionBuilder.getContextVariables().keySet())
                                        .addAll(functionBuilder.getArguments().keySet())
                                        .addAll(functionBuilder.getLocalVariables().keySet()).build(),
                                functionType.getReturnType())));
                programBuilder.addFunction(newFunction.build());
                return FunctionContextConstant.create(functionName, ImmutableList.of());
            }
        } else if (ValueTypes.isPointer(type)) {
            if (ValueTypes.isNullPointer(type)) {
                return NullPointer.INSTANCE;
            } else {
                String variableName =
                        generateVariableName(getAllVariables(programBuilder, functionBuilder, extraVariables));
                programBuilder.addGlobalVariable(VariableDeclaration.create(type, variableName, ImmutableList.of(
                        IAttribute.PREALLOCATED)));
                functionBuilder.addStatement(PointerSet.create(VariableReference.create(variableName),
                        createConstant(programBuilder, functionBuilder, extraFunctions, extraVariables,
                                ValueTypes.toPointer(type).getPointerType())));
                return VariableReference.create(variableName);
            }
        } else {
            throw new IllegalArgumentException("Unknown ValueType: " + type);
        }
    }

    static IInstructionInput createConstant(Program.Builder programBuilder, FunctionDefinition.Builder functionBuilder,
                                            ValueType type) {
        return createConstant(programBuilder, functionBuilder, ImmutableSet.of(), ImmutableSet.of(), type);
    }
}
