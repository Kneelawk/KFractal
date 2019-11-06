package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableSet;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.generator.api.ir.constant.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionCreate;
import com.kneelawk.kfractal.generator.api.ir.instruction.GlobalGet;
import com.kneelawk.kfractal.generator.api.ir.instruction.PointerSet;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;
import org.apache.commons.math3.complex.Complex;

import java.util.Set;

public class ValueTypeUtils {
    private static String findFunctionName(Set<String> usedNames) {
        int index = 0;
        while (usedNames.contains("func_" + index)) {
            index++;
        }
        return "func_" + index;
    }

    private static String findGlobalName(Set<String> usedNames) {
        int index = 0;
        while (usedNames.contains("global_" + index)) {
            index++;
        }
        return "global_" + index;
    }

    static IProceduralValue createConstant(Program.Builder programBuilder,
                                           BasicBlock.Builder blockBuilder,
                                           Set<String> usedFunctionNames, Set<String> usedGlobalNames,
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
                FunctionDefinition.Builder newFunction = new FunctionDefinition.Builder();
                String functionName = findFunctionName(
                        ImmutableSet.<String>builder().addAll(programBuilder.getFunctions().keySet())
                                .addAll(usedFunctionNames).build());
                newFunction.setReturnType(functionType.getReturnType());
                for (ValueType argumentType : functionType.getArgumentTypes()) {
                    newFunction.addArgument(ArgumentDeclaration.create(argumentType));
                }

                BasicBlock.Builder newBlock = newFunction.addBlock();
                newBlock.addValue(Return.create(createConstant(programBuilder, newBlock,
                        ImmutableSet.<String>builder().addAll(usedFunctionNames).add(functionName).build(),
                        usedGlobalNames, functionType.getReturnType())));

                programBuilder.addFunction(functionName, newFunction.build());
                return FunctionCreate.create(functionName);
            }
        } else if (ValueTypes.isPointer(type)) {
            if (ValueTypes.isNullPointer(type)) {
                return NullPointer.INSTANCE;
            } else {
                // TODO: Stop relying on preallocated variables
                String globalName = findGlobalName(
                        ImmutableSet.<String>builder().addAll(programBuilder.getGlobalVariables().keySet())
                                .addAll(usedGlobalNames).build());
                programBuilder
                        .addGlobalVariable(globalName, GlobalDeclaration.create(type, IGlobalAttribute.PREALLOCATED));
                InstructionReference reference = blockBuilder.addValue(GlobalGet.create(globalName));
                blockBuilder.addValue(PointerSet.create(reference,
                        createConstant(programBuilder, blockBuilder, usedFunctionNames,
                                ImmutableSet.<String>builder().addAll(usedGlobalNames).add(globalName).build(),
                                ValueTypes.toPointer(type).getPointerType())));
                return reference;
            }
        } else {
            throw new IllegalArgumentException("Unknown ValueType: " + type);
        }
    }

    static IProceduralValue createConstant(Program.Builder programBuilder, BasicBlock.Builder blockBuilder,
                                           ValueType type) {
        return createConstant(programBuilder, blockBuilder, ImmutableSet.of(), ImmutableSet.of(), type);
    }
}
