package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueType;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VoidConstant;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static com.kneelawk.kfractal.generator.validation.ValueTypeUtils.createConstant;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InstructionValidationReturnTests {
	@ParameterizedTest(name = "testIncompatibleReturnTypes({arguments})")
	@ArgumentsSource(IncompatibleValueTypesProvider.class)
	void testIncompatibleReturnTypes(Pair<ValueType, ValueType> valueTypes) {
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder function = new FunctionDefinition.Builder();
		function.setName("f");
		function.setReturnType(valueTypes.getLeft());
		function.addStatement(Return.create(createConstant(programBuilder, function, valueTypes.getRight())));
		programBuilder.addFunction(function.build());

		Program program = programBuilder.build();

		assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
				() -> ProgramPrinter.printProgram(program));
	}

	@ParameterizedTest(name = "testReturnTypes({arguments})")
	@ArgumentsSource(VariableValueTypesProvider.class)
	void testReturnTypes(ValueType valueType) {
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder function = new FunctionDefinition.Builder();
		function.setName("f");
		function.setReturnType(valueType);
		function.addStatement(Return.create(createConstant(programBuilder, function, valueType)));
		programBuilder.addFunction(function.build());

		Program program = programBuilder.build();

		assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
	}

	@Test
	void testVoidReturnType() {
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder function = new FunctionDefinition.Builder();
		function.setName("f");
		function.setReturnType(ValueTypes.VOID);
		function.addStatement(Return.create(VoidConstant.INSTANCE));
		programBuilder.addFunction(function.build());

		Program program = programBuilder.build();

		assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
	}
}
