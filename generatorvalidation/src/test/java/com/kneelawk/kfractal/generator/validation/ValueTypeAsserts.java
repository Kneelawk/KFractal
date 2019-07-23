package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VoidConstant;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;

import java.util.List;

import static com.kneelawk.kfractal.generator.validation.ValueTypeUtils.createConstant;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValueTypeAsserts {
    static void assertThreeIncompatibleValueTypes(List<ValueType> argumentTypes, InstructionCreator3 creator) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var res = function.addLocalVariable(VariableDeclaration.create(argumentTypes.get(0)));
        function.addStatement(
                creator.create(res,
                        createConstant(programBuilder, function, argumentTypes.get(1)),
                        createConstant(programBuilder, function, argumentTypes.get(2))));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    static void assertThreeCompatibleValueTypes(List<ValueType> argumentTypes, InstructionCreator3 creator) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var res = function.addLocalVariable(VariableDeclaration.create(argumentTypes.get(0)));
        function.addStatement(
                creator.create(res,
                        createConstant(programBuilder, function, argumentTypes.get(1)),
                        createConstant(programBuilder, function, argumentTypes.get(2))));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }

    static void assertTwoIncompatibleValueTypes(List<ValueType> argumentTypes, InstructionCreator2 creator) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var output = function.addLocalVariable(VariableDeclaration.create(argumentTypes.get(0)));
        function.addStatement(creator.create(output,
                createConstant(programBuilder, function, argumentTypes.get(1))));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
                () -> ProgramPrinter.printProgram(program));
    }

    static void assertTwoCompatibleValueTypes(List<ValueType> argumentTypes, InstructionCreator2 creator) {
        Program.Builder programBuilder = new Program.Builder();
        FunctionDefinition.Builder function = new FunctionDefinition.Builder();
        function.setReturnType(ValueTypes.VOID);
        var output = function.addLocalVariable(VariableDeclaration.create(argumentTypes.get(0)));
        function.addStatement(creator.create(output,
                createConstant(programBuilder, function, argumentTypes.get(1))));
        function.addStatement(Return.create(VoidConstant.INSTANCE));
        programBuilder.addFunction(function.build());

        Program program = programBuilder.build();

        assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
    }
}
