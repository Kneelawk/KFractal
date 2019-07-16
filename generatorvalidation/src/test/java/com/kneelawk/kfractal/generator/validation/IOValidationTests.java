package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableList;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.VariableDeclaration;
import com.kneelawk.kfractal.generator.api.ir.instruction.Assign;
import com.kneelawk.kfractal.generator.api.ir.instruction.ComplexAdd;
import com.kneelawk.kfractal.generator.api.ir.instruction.Return;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.ComplexConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.FunctionContextConstant;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VoidConstant;
import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IOValidationTests {
	@Test
	void testInvalidInputVariableReference() {
		// create the program
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
		functionBuilder.setName("f");
		functionBuilder.setReturnType(ValueTypes.VOID);
		functionBuilder.addLocalVariable(VariableDeclaration.create(ValueTypes.COMPLEX, "a"));
		functionBuilder.addStatement(Assign.create(VariableReference.create("a"), VariableReference.create("missing")));
		functionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
		programBuilder.addFunction(functionBuilder.build());

		// test the validator
		assertThrows(MissingVariableReferenceException.class,
				() -> ProgramValidator.checkValidity(programBuilder.build()));
	}

	@Test
	void testValidInputVariableReference() {
		// create the program
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
		functionBuilder.setName("f");
		functionBuilder.setReturnType(ValueTypes.VOID);
		functionBuilder.addLocalVariable(VariableDeclaration.create(ValueTypes.COMPLEX, "a"));
		functionBuilder.addLocalVariable(VariableDeclaration.create(ValueTypes.COMPLEX, "b"));
		functionBuilder.addStatement(Assign.create(VariableReference.create("a"), VariableReference.create("b")));
		functionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
		programBuilder.addFunction(functionBuilder.build());

		// test the validator
		assertDoesNotThrow(() -> ProgramValidator.checkValidity(programBuilder.build()));
	}

	@Test
	void testInvalidOutputVariableReference() {
		// create the program
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
		functionBuilder.setName("f");
		functionBuilder.setReturnType(ValueTypes.VOID);
		functionBuilder.addStatement(
				Assign.create(VariableReference.create("missing"), ComplexConstant.create(new Complex(1, 1))));
		functionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
		programBuilder.addFunction(functionBuilder.build());

		// test the validator
		assertThrows(MissingVariableReferenceException.class,
				() -> ProgramValidator.checkValidity(programBuilder.build()));
	}

	@Test
	void testValidOutputVariableReference() {
		// create the program
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
		functionBuilder.setName("f");
		functionBuilder.setReturnType(ValueTypes.VOID);
		functionBuilder.addLocalVariable(VariableDeclaration.create(ValueTypes.COMPLEX, "a"));
		functionBuilder
				.addStatement(Assign.create(VariableReference.create("a"), ComplexConstant.create(new Complex(1, 1))));
		functionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
		programBuilder.addFunction(functionBuilder.build());

		// test the validator
		assertDoesNotThrow(() -> ProgramValidator.checkValidity(programBuilder.build()));
	}

	@Test
	void testMissingFunctionReference() {
		// create the program
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder functionBuilder = new FunctionDefinition.Builder();
		functionBuilder.setName("f");
		functionBuilder.setReturnType(ValueTypes.VOID);
		functionBuilder.addLocalVariable(
				VariableDeclaration.create(ValueTypes.FUNCTION(ValueTypes.VOID, ImmutableList.of()), "a"));
		functionBuilder.addStatement(Assign.create(VariableReference.create("a"),
				FunctionContextConstant.create("missing", ImmutableList.of())));
		functionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
		programBuilder.addFunction(functionBuilder.build());

		// test the validator
		assertThrows(MissingFunctionReferenceException.class,
				() -> ProgramValidator.checkValidity(programBuilder.build()));
	}

	@Test
	void testIncompatibleFunctionContextVariables() {
		// create the program
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder gFunctionBuilder = new FunctionDefinition.Builder();
		gFunctionBuilder.setName("g");
		gFunctionBuilder.setReturnType(ValueTypes.COMPLEX);
		gFunctionBuilder.addContextVariable(VariableDeclaration.create(ValueTypes.COMPLEX, "a"));
		gFunctionBuilder.addLocalVariable(VariableDeclaration.create(ValueTypes.COMPLEX, "tmp0"));
		gFunctionBuilder.addStatement(ComplexAdd.create(VariableReference.create("tmp0"), VariableReference.create("a"),
				ComplexConstant.create(new Complex(0, 2))));
		gFunctionBuilder.addStatement(Return.create(VariableReference.create("tmp0")));
		programBuilder.addFunction(gFunctionBuilder.build());

		FunctionDefinition.Builder fFunctionBuilder = new FunctionDefinition.Builder();
		fFunctionBuilder.setName("f");
		fFunctionBuilder.setReturnType(ValueTypes.VOID);
		fFunctionBuilder.addLocalVariable(
				VariableDeclaration.create(ValueTypes.FUNCTION(ValueTypes.COMPLEX, ImmutableList.of()), "a"));
		fFunctionBuilder.addStatement(
				Assign.create(VariableReference.create("a"), FunctionContextConstant.create("g", ImmutableList.of())));
		fFunctionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
		programBuilder.addFunction(fFunctionBuilder.build());

		// test the validator
		assertThrows(IncompatibleFunctionContextException.class,
				() -> ProgramValidator.checkValidity(programBuilder.build()));
	}

	@Test
	void testValidFunctionReference() {
		// create the program
		Program.Builder programBuilder = new Program.Builder();
		FunctionDefinition.Builder gFunctionBuilder = new FunctionDefinition.Builder();
		gFunctionBuilder.setName("g");
		gFunctionBuilder.setReturnType(ValueTypes.COMPLEX);
		gFunctionBuilder.addContextVariable(VariableDeclaration.create(ValueTypes.COMPLEX, "a"));
		gFunctionBuilder.addLocalVariable(VariableDeclaration.create(ValueTypes.COMPLEX, "tmp0"));
		gFunctionBuilder.addStatement(ComplexAdd.create(VariableReference.create("tmp0"), VariableReference.create("a"),
				ComplexConstant.create(new Complex(0, 2))));
		gFunctionBuilder.addStatement(Return.create(VariableReference.create("tmp0")));
		programBuilder.addFunction(gFunctionBuilder.build());

		FunctionDefinition.Builder fFunctionBuilder = new FunctionDefinition.Builder();
		fFunctionBuilder.setName("f");
		fFunctionBuilder.setReturnType(ValueTypes.VOID);
		fFunctionBuilder.addLocalVariable(
				VariableDeclaration.create(ValueTypes.FUNCTION(ValueTypes.COMPLEX, ImmutableList.of()), "a"));
		fFunctionBuilder.addStatement(Assign.create(VariableReference.create("a"),
				FunctionContextConstant.create("g", ImmutableList.of(ComplexConstant.create(new Complex(2, 0))))));
		fFunctionBuilder.addStatement(Return.create(VoidConstant.INSTANCE));
		programBuilder.addFunction(fFunctionBuilder.build());

		// test the validator
		assertDoesNotThrow(() -> ProgramValidator.checkValidity(programBuilder.build()));
	}
}
