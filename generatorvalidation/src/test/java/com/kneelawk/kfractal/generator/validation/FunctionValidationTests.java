package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static com.kneelawk.kfractal.generator.validation.ValueTypeUtils.createConstant;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FunctionValidationTests {
    @Test
    void testIllegalMissingReturnType() {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setName("f");
        function.setReturnType(ValueTypes.VOID);
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(FractalIRValidationException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter
                        .printProgram(program));
    }

    @ParameterizedTest(name = "testIncompatibleFunctionReturnType({arguments})")
    @ArgumentsSource(IncompatibleValueTypesProvider.class)
    void testIncompatibleFunctionReturnType(ImmutablePair<ValueType, ValueType> valueTypes) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setName("f");
        function.setReturnType(valueTypes.left);
        function.addStatement(Return.create(createConstant(programBuilder, function, valueTypes.right)));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }
}
