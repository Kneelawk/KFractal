package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.NullFunction;
import com.kneelawk.kfractal.generator.api.ir.constant.VoidConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionCall;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionCreate;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionIsEqual;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kneelawk.kfractal.generator.validation.ValueTypeAsserts.assertTwoCompatibleValueTypes;
import static com.kneelawk.kfractal.generator.validation.ValueTypeAsserts.assertTwoIncompatibleValueTypes;
import static com.kneelawk.kfractal.generator.validation.ValueTypeUtils.createConstant;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InstructionValidationFunctionTests {
    private static Stream<ValueType> shortVariableValueTypes() {
        return Stream.of(ValueTypes.BOOL, ValueTypes.REAL, ValueTypes.FUNCTION(ValueTypes.VOID, ValueTypes.INT),
                ValueTypes.FUNCTION(ValueTypes.POINTER(ValueTypes.COMPLEX), ValueTypes.INT),
                ValueTypes.POINTER(ValueTypes.REAL));
    }

    private static Stream<ImmutableList<ValueType>> argumentTypes() {
        return Streams.concat(Stream.of(ImmutableList.of()),
                shortVariableValueTypes().map(ImmutableList::of),
                shortVariableValueTypes()
                        .flatMap(
                                a -> shortVariableValueTypes().map(b -> ImmutableList.of(a, b))));
    }

    private static Stream<Pair<ValueType, ImmutableList<ValueType>>> incompatibleFunctionTypeAndArguments() {
        return shortVariableValueTypes()
                .flatMap(ret -> argumentTypes().map(args -> (ValueType) ValueTypes.FUNCTION(ret, args)))
                .flatMap(f -> argumentTypes().map(args -> Pair.of(f, args))).filter(p -> {
                    List<ValueType> argumentTypes = ValueTypes.toFunction(p.getLeft()).getArgumentTypes();
                    if (p.getRight().size() != argumentTypes.size())
                        return true;
                    int size = argumentTypes.size();
                    for (int i = 0; i < size; i++) {
                        if (!argumentTypes.get(i).isAssignableFrom(p.getRight().get(i)))
                            return true;
                    }
                    return false;
                });
    }

    private static Stream<Pair<ValueType, ImmutableList<ValueType>>> compatibleFunctionTypeAndArguments() {
        return Streams.concat(VariableValueTypesProvider.variableValueTypes()
                        .map(ret -> Pair.of(ValueTypes.FUNCTION(ret), ImmutableList.of())),
                VariableValueTypesProvider.variableValueTypes().flatMap(
                        ret -> CompatibleValueTypesProvider.compatibleValueTypes().map(p -> Pair
                                .of(ValueTypes.FUNCTION(ret, p.getLeft()), ImmutableList.of(p.getRight())))),
                VariableValueTypesProvider.variableValueTypes().flatMap(
                        ret -> CompatibleValueTypesProvider.compatibleValueTypes().flatMap(
                                a -> CompatibleValueTypesProvider.compatibleValueTypes().map(b -> Pair
                                        .of(ValueTypes.FUNCTION(ret, a.getLeft(), b.getLeft()),
                                                ImmutableList.of(a.getRight(), b.getRight()))))));
    }

    private static Stream<ImmutableList<ValueType>> notTwoFunctionValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes()
                        .map(b -> ImmutableList.of(a, b)))
                .filter(l -> !ValueTypes.isFunction(l.get(0)) || !ValueTypes.isFunction(l.get(1)));
    }

    private static Stream<ImmutableList<ValueType>> twoFunctionValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().filter(ValueTypes::isFunction)
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes().filter(ValueTypes::isFunction)
                        .map(b -> ImmutableList.of(a, b)));
    }

    @ParameterizedTest(name = "testIncompatibleFunctionCallFunctionArguments({arguments})")
    @MethodSource("incompatibleFunctionTypeAndArguments")
    void testIncompatibleFunctionCallFunctionArguments(Pair<ValueType, List<ValueType>> functionAndArgs) {
        ValueTypes.FunctionType functionType = ValueTypes.toFunction(functionAndArgs.getLeft());
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder otherFunction = new FunctionDefinition.Builder();
        otherFunction.setReturnType(functionType.getReturnType());
        functionType.getArgumentTypes().stream().map(ArgumentDeclaration::create)
                .forEachOrdered(otherFunction::addArgument);
        BasicBlock.Builder otherBlock = new BasicBlock.Builder();
        otherBlock.addValue(
                Return.create(createConstant(programBuilder, otherBlock, functionType.getReturnType())));
        otherFunction.addBlock(otherBlock.build());
        int gIndex = programBuilder.addFunction(otherFunction.build());

        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(FunctionCall
                .create(FunctionCreate.create(gIndex),
                        functionAndArgs.getRight().stream().map(v -> createConstant(programBuilder, block, v))
                                .collect(Collectors.toList())));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleFunctionArgumentException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @Test
    void testIncompatibleFunctionCallNullFunctionType() {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(FunctionCall.create(NullFunction.INSTANCE));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testCompatibleFunctionTypeAndArguments({arguments})")
    @MethodSource("compatibleFunctionTypeAndArguments")
    void testCompatibleFunctionCall(Pair<ValueType, List<ValueType>> functionAndArgs) {
        ValueTypes.FunctionType functionType = ValueTypes.toFunction(functionAndArgs.getLeft());
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder otherFunction = new FunctionDefinition.Builder();
        otherFunction.setReturnType(functionType.getReturnType());
        functionType.getArgumentTypes().stream().map(ArgumentDeclaration::create)
                .forEachOrdered(otherFunction::addArgument);
        BasicBlock.Builder otherBlock = new BasicBlock.Builder();
        otherBlock.addValue(
                Return.create(createConstant(programBuilder, otherBlock, functionType.getReturnType())));
        otherFunction.addBlock(otherBlock.build());
        int gIndex = programBuilder.addFunction(otherFunction.build());

        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(FunctionCall
                .create(FunctionCreate.create(gIndex),
                        functionAndArgs.getRight().stream().map(v -> createConstant(programBuilder, block, v))
                                .collect(Collectors.toList())));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @Test
    void testCompatibleFunctionCallVoidReturnType() {
        Program.Builder programBuilder = new Program.Builder();

        FunctionDefinition.Builder otherFunction = new FunctionDefinition.Builder();
        otherFunction.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder otherBlock = new BasicBlock.Builder();
        otherBlock.addValue(Return.create(VoidConstant.INSTANCE));
        otherFunction.addBlock(otherBlock.build());
        int gIndex = programBuilder.addFunction(otherFunction.build());

        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(FunctionCall
                .create(FunctionCreate.create(gIndex)));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatibleFunctionIsEqualTypes({arguments})")
    @MethodSource("notTwoFunctionValueTypes")
    void testIncompatibleFunctionIsEqualTypes(List<ValueType> valueTypes) {
        assertTwoIncompatibleValueTypes(valueTypes, FunctionIsEqual::create);
    }

    @ParameterizedTest(name = "testCompatibleFunctionIsEqualTypes({arguments})")
    @MethodSource("twoFunctionValueTypes")
    void testCompatibleFunctionIsEqualTypes(List<ValueType> valueTypes) {
        assertTwoCompatibleValueTypes(valueTypes, FunctionIsEqual::create);
    }
}
