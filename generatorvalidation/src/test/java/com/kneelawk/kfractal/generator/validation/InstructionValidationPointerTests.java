package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.NullPointer;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VoidConstant;
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

    private static Stream<ImmutableList<ValueType>> notOneBoolAndTwoPointerValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes()
                        .flatMap(b -> VariableValueTypesProvider.variableValueTypes()
                                .map(c -> ImmutableList.of(a, b, c))))
                .filter(l -> !ValueTypes.isBool(l.get(0)) || !ValueTypes.isPointer(l.get(1)) ||
                        !ValueTypes.isPointer(l.get(2)));
    }

    private static Stream<ImmutableList<ValueType>> oneBoolAndTwoPointerValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().filter(ValueTypes::isPointer).flatMap(
                a -> VariableValueTypesProvider.variableValueTypes().filter(ValueTypes::isPointer)
                        .map(b -> ImmutableList.of(ValueTypes.BOOL, a, b)));
    }

    @ParameterizedTest(name = "testIncompatiblePointerAllocateTypes({arguments})")
    @MethodSource("notPointerValueTypes")
    void testIncompatiblePointerAllocateTypes(ValueType valueType) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var p = function.addLocalVariable(VariableDeclaration.create(valueType));
        function.addStatement(PointerAllocate.create(p));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testCompatiblePointerAllocateTypes({arguments})")
    @MethodSource("pointerValueTypes")
    void testCompatiblePointerAllocateTypes(ValueType valueType) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var p = function.addLocalVariable(VariableDeclaration.create(valueType));
        function.addStatement(PointerAllocate.create(p));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
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
        var p = function.addLocalVariable(VariableDeclaration.create(valueType));
        function.addStatement(PointerFree.create(p));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
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
        function.addStatement(PointerFree.create(NullPointer.INSTANCE));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
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
        var p = function.addLocalVariable(VariableDeclaration.create(valueType));
        function.addStatement(PointerFree.create(p));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatiblePointerGetNotPointerTypes({arguments})")
    @MethodSource("notSecondPointerValueTypes")
    void testIncompatiblePointerGetNotPointerTypes(List<ValueType> valueTypes) {
        assertTwoIncompatibleValueTypes(valueTypes, PointerGet::create);
    }

    @ParameterizedTest(name = "testIncompatiblePointerGetIncompatibleTypes({arguments})")
    @MethodSource("incompatibleValueAndPointerTypes")
    void testIncompatiblePointerGetIncompatibleTypes(List<ValueType> valueTypes) {
        assertTwoIncompatibleValueTypes(valueTypes, PointerGet::create);
    }

    @ParameterizedTest(name = "testIncompatiblePointerGetNullPointerTypes({arguments})")
    @ArgumentsSource(VariableValueTypesProvider.class)
    void testIncompatiblePointerGetNullPointerTypes(ValueType valueType) {
        assertTwoIncompatibleValueTypes(ImmutableList.of(valueType, ValueTypes.NULL_POINTER), PointerGet::create);
    }

    @ParameterizedTest(name = "testCompatiblePointerGetTypes({arguments})")
    @MethodSource("compatibleValueAndPointerTypes")
    void testCompatiblePointerGetTypes(List<ValueType> valueTypes) {
        assertTwoCompatibleValueTypes(valueTypes, PointerGet::create);
    }

    @ParameterizedTest(name = "testIncompatiblePointerSetNotPointerTypes({arguments})")
    @MethodSource("notFirstPointerValueTypes")
    void testIncompatiblePointerSetNotPointerTypes(List<ValueType> valueTypes) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var p = function.addLocalVariable(VariableDeclaration.create(valueTypes.get(0)));
        function.addStatement(PointerSet
                .create(p, createConstant(programBuilder, function, valueTypes.get(1))));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatiblePointerSetIncompatibleTypes({arguments})")
    @MethodSource("incompatiblePointerAndValueTypes")
    void testIncompatiblePointerSetIncompatibleTypes(List<ValueType> valueTypes) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var p = function.addLocalVariable(VariableDeclaration.create(valueTypes.get(0)));
        function.addStatement(PointerSet
                .create(p, createConstant(programBuilder, function, valueTypes.get(1))));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
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
        function.addStatement(
                PointerSet.create(NullPointer.INSTANCE, createConstant(programBuilder, function, valueType)));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testCompatiblePointerSetTypes({arguments})")
    @MethodSource("compatiblePointerAndValueTypes")
    void testCompatiblePointerSetTypes(List<ValueType> valueTypes) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var p = function.addLocalVariable(VariableDeclaration.create(valueTypes.get(0)));
        function.addStatement(PointerSet
                .create(p, createConstant(programBuilder, function, valueTypes.get(1))));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatiblePointerIsEqualTypes({arguments})")
    @MethodSource("notOneBoolAndTwoPointerValueTypes")
    void testIncompatiblePointerIsEqualTypes(List<ValueType> valueTypes) {
        assertThreeIncompatibleValueTypes(valueTypes, PointerIsEqual::create);
    }

    @ParameterizedTest(name = "testCompatiblePointerIsEqualTypes({arguments})")
    @MethodSource("oneBoolAndTwoPointerValueTypes")
    void testCompatiblePointerIsEqualTypes(List<ValueType> valueTypes) {
        assertThreeCompatibleValueTypes(valueTypes, PointerIsEqual::create);
    }

    @ParameterizedTest(name = "testIncompatiblePointerIsNotEqualTypes({arguments})")
    @MethodSource("notOneBoolAndTwoPointerValueTypes")
    void testIncompatiblePointerIsNotEqualTypes(List<ValueType> valueTypes) {
        assertThreeIncompatibleValueTypes(valueTypes, PointerIsNotEqual::create);
    }

    @ParameterizedTest(name = "testCompatiblePointerIsNotEqualTypes({arguments})")
    @MethodSource("oneBoolAndTwoPointerValueTypes")
    void testCompatiblePointerIsNotEqualTypes(List<ValueType> valueTypes) {
        assertThreeCompatibleValueTypes(valueTypes, PointerIsNotEqual::create);
    }
}
