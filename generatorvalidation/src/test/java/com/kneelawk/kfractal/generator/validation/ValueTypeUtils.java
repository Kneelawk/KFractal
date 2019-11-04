package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.generator.api.ir.constant.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionCreate;
import com.kneelawk.kfractal.generator.api.ir.instruction.GlobalGet;
import com.kneelawk.kfractal.generator.api.ir.instruction.PointerSet;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.reference.InstructionReference;
import org.apache.commons.math3.complex.Complex;

public class ValueTypeUtils {
    static IProceduralValue createConstant(Program.Builder programBuilder,
                                           BasicBlock.Builder blockBuilder,
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
                    newFunction.addArgument(ArgumentDeclaration.create(argumentType));
                }

                BasicBlock.Builder newBlock = new BasicBlock.Builder();
                newBlock.addValue(
                        Return.create(createConstant(programBuilder, newBlock,
                                functionOffset, globalVariableOffset, 0,
                                functionType.getReturnType())));

                newFunction.addBlock(newBlock.build());
                int functionIndex = programBuilder.addFunction(newFunction.build());
                return FunctionCreate.create(functionIndex + functionOffset);
            }
        } else if (ValueTypes.isPointer(type)) {
            if (ValueTypes.isNullPointer(type)) {
                return NullPointer.INSTANCE;
            } else {
                // TODO: Stop relying on preallocated variables
                int pointer =
                        programBuilder.addGlobalVariable(GlobalDeclaration.create(type, IGlobalAttribute.PREALLOCATED))
                                + globalVariableOffset;
                InstructionReference reference = blockBuilder.addValue(GlobalGet.create(pointer));
                blockBuilder.addValue(PointerSet.create(reference,
                        createConstant(programBuilder, blockBuilder, functionOffset, globalVariableOffset,
                                localVariableOffset, ValueTypes.toPointer(type).getPointerType())));
                return reference;
            }
        } else {
            throw new IllegalArgumentException("Unknown ValueType: " + type);
        }
    }

    static IProceduralValue createConstant(Program.Builder programBuilder, BasicBlock.Builder blockBuilder,
                                           ValueType type) {
        return createConstant(programBuilder, blockBuilder, 0, 0, 0, type);
    }
}
