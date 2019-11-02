package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.constant.NullPointer;
import com.kneelawk.kfractal.generator.api.ir.constant.VoidConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.kneelawk.kfractal.generator.validation.ValueTypeAsserts.*;
import static com.kneelawk.kfractal.generator.validation.ValueTypeUtils.createConstant;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InstructionValidationPointerTests {
    private static Stream<ValueType> notPointerValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().filter(v -> !ValueTypes.isPointer(v));
    }

    private static Stream<ValueType> invalidPointerValueTypes() {
        return Stream.of(ValueTypes.VOID, ValueTypes.NULL_FUNCTION, ValueTypes.NULL_POINTER);
    }

    private static Stream<ValueType> pointerValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().filter(ValueTypes::isPointer);
    }

    private static Stream<ImmutableList<ValueType>> notFirstPointerValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().filter(v -> !ValueTypes.isPointer(v))
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes().map(b -> ImmutableList.of(a, b)));
    }

    private static Stream<ImmutableList<ValueType>> notSecondPointerValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().filter(v -> !ValueTypes.isPointer(v))
                .flatMap(b -> VariableValueTypesProvider.variableValueTypes().map(a -> ImmutableList.of(a, b)));
    }

    private static Stream<ImmutableList<ValueType>> incompatibleValueAndPointerTypes() {
        return IncompatibleValueTypesProvider.incompatibleValueTypes()
                .map(p -> ImmutableList.of(p.getLeft(), ValueTypes.POINTER(p.getRight())));
    }

    private static Stream<ImmutableList<ValueType>> incompatiblePointerAndValueTypes() {
        return IncompatibleValueTypesProvider.incompatibleValueTypes()
                .map(p -> ImmutableList.of(ValueTypes.POINTER(p.getLeft()), p.getRight()));
    }

    private static Stream<ImmutableList<ValueType>> compatibleValueAndPointerTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes().map(b -> Pair.of(a, b)))
                .filter(p -> p.getLeft().isAssignableFrom(p.getRight()))
                .map(p -> ImmutableList.of(p.getLeft(), ValueTypes.POINTER(p.getRight())));
    }

    private static Stream<ImmutableList<ValueType>> compatiblePointerAndValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes().map(b -> Pair.of(a, b)))
                .filter(p -> p.getLeft().isAssignableFrom(p.getRight()))
                .map(p -> ImmutableList.of(ValueTypes.POINTER(p.getLeft()), p.getRight()));
    }

    private static Stream<ImmutableList<ValueType>> notTwoPointerValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes()
                        .map(b -> ImmutableList.of(a, b)))
                .filter(l -> !ValueTypes.isPointer(l.get(0)) || !ValueTypes.isPointer(l.get(1)));
    }

    private static Stream<ImmutableList<ValueType>> twoPointerValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().filter(ValueTypes::isPointer).flatMap(
                a -> VariableValueTypesProvider.variableValueTypes().filter(ValueTypes::isPointer)
                        .map(b -> ImmutableList.of(a, b)));
    }

    @ParameterizedTest(name = "testIncompatiblePointerAllocateTypes({arguments})")
    @MethodSource("invalidPointerValueTypes")
    void testIncompatiblePointerAllocateTypes(ValueType valueType) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(PointerAllocate.create(valueType));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testCompatiblePointerAllocateTypes({arguments})")
    @ArgumentsSource(VariableValueTypesProvider.class)
    void testCompatiblePointerAllocateTypes(ValueType valueType) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(PointerAllocate.create(valueType));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatiblePointerFreeTypes({arguments})")
    @MethodSource("notPointerValueTypes")
    void testIncompatiblePointerFreeTypes(ValueType valueType) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(PointerFree.create(createConstant(programBuilder, block, valueType)));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @Test
    void testIncompatiblePointerFreeNullPointerType() {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(PointerFree.create(NullPointer.INSTANCE));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testCompatiblePointerFreeTypes({arguments})")
    @MethodSource("pointerValueTypes")
    void testCompatiblePointerFreeTypes(ValueType valueType) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(PointerFree.create(createConstant(programBuilder, block, valueType)));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatiblePointerGetNotPointerTypes({arguments})")
    @MethodSource("notPointerValueTypes")
    void testIncompatiblePointerGetNotPointerTypes(ValueType valueTypes) {
        assertOneIncompatibleValueType(valueTypes, PointerGet::create);
    }

    @Test
    void testIncompatiblePointerGetNullPointerTypes() {
        assertOneIncompatibleValueType(ValueTypes.NULL_POINTER, PointerGet::create);
    }

    @ParameterizedTest(name = "testCompatiblePointerGetTypes({arguments})")
    @MethodSource("pointerValueTypes")
    void testCompatiblePointerGetTypes(ValueType valueTypes) {
        assertOneCompatibleValueType(valueTypes, PointerGet::create);
    }

    @ParameterizedTest(name = "testIncompatiblePointerSetNotPointerTypes({arguments})")
    @MethodSource("notFirstPointerValueTypes")
    void testIncompatiblePointerSetNotPointerTypes(List<ValueType> valueTypes) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(PointerSet
                .create(createConstant(programBuilder, block, valueTypes.get(0)),
                        createConstant(programBuilder, block, valueTypes.get(1))));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatiblePointerSetIncompatibleTypes({arguments})")
    @ArgumentsSource(IncompatibleValueTypesProvider.class)
    void testIncompatiblePointerSetIncompatibleTypes(Pair<ValueType, ValueType> valueTypes) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        var p = block.addValue(PointerAllocate.create(valueTypes.getLeft()));
        block.addValue(PointerSet
                .create(p, createConstant(programBuilder, block, valueTypes.getRight())));
        block.addValue(PointerFree.create(p));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatiblePointerSetNullPointerTypes({arguments})")
    @ArgumentsSource(VariableValueTypesProvider.class)
    void testIncompatiblePointerSetNullPointerTypes(ValueType valueType) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        block.addValue(
                PointerSet.create(NullPointer.INSTANCE, createConstant(programBuilder, block, valueType)));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testCompatiblePointerSetTypes({arguments})")
    @ArgumentsSource(CompatibleValueTypesProvider.class)
    void testCompatiblePointerSetTypes(Pair<ValueType, ValueType> valueTypes) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        BasicBlock.Builder block = new BasicBlock.Builder();
        var p = block.addValue(PointerAllocate.create(valueTypes.getLeft()));
        block.addValue(PointerSet
                .create(p, createConstant(programBuilder, block, valueTypes.getRight())));
        block.addValue(Return.create(VoidConstant.INSTANCE));
        function.addBlock(block.build());
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatiblePointerIsEqualTypes({arguments})")
    @MethodSource("notTwoPointerValueTypes")
    void testIncompatiblePointerIsEqualTypes(List<ValueType> valueTypes) {
        assertTwoIncompatibleValueTypes(valueTypes, PointerIsEqual::create);
    }

    @ParameterizedTest(name = "testCompatiblePointerIsEqualTypes({arguments})")
    @MethodSource("twoPointerValueTypes")
    void testCompatiblePointerIsEqualTypes(List<ValueType> valueTypes) {
        assertTwoCompatibleValueTypes(valueTypes, PointerIsEqual::create);
    }
}
