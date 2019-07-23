package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionCall;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionIsEqual;
import com.kneelawk.kfractal.generator.api.ir.instruction.FunctionIsNotEqual;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.FunctionContextConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.NullFunction;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VoidConstant;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kneelawk.kfractal.generator.validation.ValueTypeAsserts.assertThreeCompatibleValueTypes;
import static com.kneelawk.kfractal.generator.validation.ValueTypeAsserts.assertThreeIncompatibleValueTypes;
import static com.kneelawk.kfractal.generator.validation.ValueTypeUtils.createConstant;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InstructionValidationFunctionTests {
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

    private static Stream<ImmutableList<ValueType>> notOneBoolAndTwoFunctionValueTypes() {
        return VariableValueTypesProvider.variableValueTypes()
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes()
                        .flatMap(b -> VariableValueTypesProvider.variableValueTypes()
                                .map(c -> ImmutableList.of(a, b, c))))
                .filter(l -> !ValueTypes.isBool(l.get(0)) || !ValueTypes.isFunction(l.get(1)) ||
                        !ValueTypes.isFunction(l.get(2)));
    }

    private static Stream<ImmutableList<ValueType>> oneBoolAndTwoFunctionValueTypes() {
        return VariableValueTypesProvider.variableValueTypes().filter(ValueTypes::isFunction)
                .flatMap(a -> VariableValueTypesProvider.variableValueTypes().filter(ValueTypes::isFunction)
                        .map(b -> ImmutableList.of(ValueTypes.BOOL, a, b)));
    }

    @ParameterizedTest(name = "testIncompatibleFunctionCallFunctionArguments({arguments})")
    @MethodSource("incompatibleFunctionTypeAndArguments")
    void testIncompatibleFunctionCallFunctionArguments(Pair<ValueType, List<ValueType>> functionAndArgs) {
        ValueTypes.FunctionType functionType = ValueTypes.toFunction(functionAndArgs.getLeft());
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder otherFunction = new FunctionDefinition.Builder();
        otherFunction.setReturnType(functionType.getReturnType());
        functionType.getArgumentTypes().stream().map(VariableDeclaration::create)
                .forEachOrdered(otherFunction::addArgument);
        otherFunction.addStatement(
                Return.create(createConstant(programBuilder, otherFunction, functionType.getReturnType())));
        int gIndex = programBuilder.addFunction(otherFunction.build());

        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var ret = function.addLocalVariable(VariableDeclaration.create(functionType.getReturnType()));
        function.addStatement(FunctionCall
                .create(ret, FunctionContextConstant.create(gIndex),
                        functionAndArgs.getRight().stream().map(v -> createConstant(programBuilder, function, v))
                                .collect(Collectors.toList())));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleFunctionArgumentException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatibleFunctionCallReturnType({arguments})")
    @ArgumentsSource(IncompatibleValueTypesProvider.class)
    void testIncompatibleFunctionCallReturnType(Pair<ValueType, ValueType> valueTypes) {
        Program.Builder programBuilder = new Program.Builder();

        FunctionDefinition.Builder otherFunction = new FunctionDefinition.Builder();
        otherFunction.setReturnType(valueTypes.getRight());
        otherFunction.addStatement(Return.create(createConstant(programBuilder, otherFunction, valueTypes.getRight())));
        int gIndex = programBuilder.addFunction(otherFunction.build());

        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var ret = function.addLocalVariable(VariableDeclaration.create(valueTypes.getLeft()));
        function.addStatement(FunctionCall
                .create(ret, FunctionContextConstant.create(gIndex),
                        ImmutableList.of()));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @Test
    void testIncompatibleFunctionCallVoidReturnType() {
        Program.Builder programBuilder = new Program.Builder();

        FunctionDefinition.Builder otherFunction = new FunctionDefinition.Builder();
        otherFunction.setReturnType(ValueTypes.VOID);
        otherFunction.addStatement(Return.create(VoidConstant.INSTANCE));
        int gIndex = programBuilder.addFunction(otherFunction.build());

        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var ret = function.addLocalVariable(VariableDeclaration.create(ValueTypes.COMPLEX));
        function.addStatement(FunctionCall
                .create(ret, FunctionContextConstant.create(gIndex, ImmutableList.of()),
                        ImmutableList.of()));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @Test
    void testIncompatibleFunctionCallNullFunctionType() {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        function.addStatement(FunctionCall.create(VoidConstant.INSTANCE, NullFunction.INSTANCE, ImmutableList.of()));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
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
        int size = functionType.getArgumentTypes().size();
        functionType.getArgumentTypes().stream().map(VariableDeclaration::create)
                .forEachOrdered(otherFunction::addArgument);
        otherFunction.addStatement(
                Return.create(createConstant(programBuilder, otherFunction, functionType.getReturnType())));
        int gIndex = programBuilder.addFunction(otherFunction.build());

        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var ret = function.addLocalVariable(VariableDeclaration.create(functionType.getReturnType()));
        function.addStatement(FunctionCall
                .create(ret, FunctionContextConstant.create(gIndex, ImmutableList.of()),
                        functionAndArgs.getRight().stream().map(v -> createConstant(programBuilder, function, v))
                                .collect(Collectors.toList())));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
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
        otherFunction.addStatement(Return.create(VoidConstant.INSTANCE));
        int gIndex = programBuilder.addFunction(otherFunction.build());

        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        function.addStatement(FunctionCall
                .create(VoidConstant.INSTANCE, FunctionContextConstant.create(gIndex, ImmutableList.of()),
                        ImmutableList.of()));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatibleFunctionIsEqualTypes({arguments})")
    @MethodSource("notOneBoolAndTwoFunctionValueTypes")
    void testIncompatibleFunctionIsEqualTypes(List<ValueType> valueTypes) {
        assertThreeIncompatibleValueTypes(valueTypes, FunctionIsEqual::create);
    }

    @ParameterizedTest(name = "testCompatibleFunctionIsEqualTypes({arguments})")
    @MethodSource("oneBoolAndTwoFunctionValueTypes")
    void testCompatibleFunctionIsEqualTypes(List<ValueType> valueTypes) {
        assertThreeCompatibleValueTypes(valueTypes, FunctionIsEqual::create);
    }

    @ParameterizedTest(name = "testIncompatibleFunctionIsNotEqualTypes({arguments})")
    @MethodSource("notOneBoolAndTwoFunctionValueTypes")
    void testIncompatibleFunctionIsNotEqualTypes(List<ValueType> valueTypes) {
        assertThreeIncompatibleValueTypes(valueTypes, FunctionIsNotEqual::create);
    }

    @ParameterizedTest(name = "testCompatibleFunctionIsNotEqualTypes({arguments})")
    @MethodSource("oneBoolAndTwoFunctionValueTypes")
    void testCompatibleFunctionIsNotEqualTypes(List<ValueType> valueTypes) {
        assertThreeCompatibleValueTypes(valueTypes, FunctionIsNotEqual::create);
    }
}
