package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.generator.api.ir.constant.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionCreate;
import com.kneelawk.kfractal.generator.api.ir.instruction.PointerSet;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.*;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;
import org.apache.commons.math3.complex.Complex;

public class ValueTypeUtils {
    static IInstructionInput createConstant(Program.Builder programBuilder,
                                            FunctionDefinition.Builder functionBuilder,
                                            int functionOffset, int globalVariableOffset, int localVariableOffset,
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
                newFunction.setReturnType(functionType.getReturnType());
                for (ValueType argumentType : functionType.getArgumentTypes()) {
                    newFunction.addArgument(GlobalDeclaration.create(argumentType));
                }
                newFunction.addStatement(
                        Return.create(createConstant(programBuilder, newFunction,
                                functionOffset, globalVariableOffset, 0,
                                functionType.getReturnType())));
                int functionIndex = programBuilder.addFunction(newFunction.build());
                return FunctionCreate.create(functionIndex + functionOffset);
            }
        } else if (ValueTypes.isPointer(type)) {
            if (ValueTypes.isNullPointer(type)) {
                return NullPointer.INSTANCE;
            } else {
                ArgumentReference pointer =
                        programBuilder.addGlobalVariable(GlobalDeclaration.create(type, IGlobalAttribute.PREALLOCATED))
                                .offset(globalVariableOffset);
                functionBuilder.addStatement(PointerSet.create(pointer,
                        createConstant(programBuilder, functionBuilder, functionOffset, globalVariableOffset,
                                localVariableOffset, ValueTypes.toPointer(type).getPointerType())));
                return pointer;
            }
        } else {
            throw new IllegalArgumentException("Unknown ValueType: " + type);
        }
    }

    static IInstructionInput createConstant(Program.Builder programBuilder, FunctionDefinition.Builder functionBuilder,
                                            ValueType type) {
        return createConstant(programBuilder, functionBuilder, 0, 0, 0, type);
    }
}
