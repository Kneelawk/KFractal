package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.instruction.Assign;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VoidConstant;
import com.kneelawk.kfractal.generator.util.ProgramPrinter;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static com.kneelawk.kfractal.generator.validation.ValueTypeUtils.createConstant;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InstructionValidationAssignTests {
	@ParameterizedTest(name = "testIncompatibleAssignTypes({arguments})")
	@ArgumentsSource(IncompatibleValueTypesProvider.class)
	void testIncompatibleAssignTypes(Pair<ValueType, ValueType> assignTypes) {
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder function = new FunctionDefinition.Builder();
		function.setName("f");
		function.setReturnType(ValueTypes.VOID);
		function.addLocalVariable(VariableDeclaration.create(assignTypes.getLeft(), "res"));
		function.addStatement(Assign.create(VariableReference.create("res"),
				createConstant(programBuilder, function, assignTypes.getRight())));
		function.addStatement(Return.create(VoidConstant.INSTANCE));
		programBuilder.addFunction(function.build());

		Program program = programBuilder.build();

		assertThrows(IncompatibleValueTypeException.class, () -> ProgramValidator.checkValidity(program),
				() -> ProgramPrinter.printProgram(program));
	}

	@ParameterizedTest(name = "testCompatibleAssignTypes({arguments})")
	@ArgumentsSource(CompatibleValueTypesProvider.class)
	void testCompatibleAssignTypes(Pair<ValueType, ValueType> assignTypes) {
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder function = new FunctionDefinition.Builder();
		function.setName("f");
		function.setReturnType(ValueTypes.VOID);
		function.addLocalVariable(VariableDeclaration.create(assignTypes.getLeft(), "res"));
		function.addStatement(
				Assign.create(VariableReference.create("res"),
						createConstant(programBuilder, function, assignTypes.getRight())));
		function.addStatement(Return.create(VoidConstant.INSTANCE));
		programBuilder.addFunction(function.build());

		Program program = programBuilder.build();

		assertDoesNotThrow(() -> ProgramValidator.checkValidity(program), () -> ProgramPrinter.printProgram(program));
	}
}
