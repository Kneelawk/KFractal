package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.VariableDeclaration;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import com.kneelawk.kfractal.util.StringUtils;

public class ProgramPrinter {
	public static String printProgram(Program program) {
		StringBuilder builder = new StringBuilder();
		builder.append("Program(").append(System.lineSeparator());
		builder.append("[");
		boolean first = true;
		for (VariableDeclaration variable : program.getGlobalVariables().values()) {
			if (!first)
				builder.append(",");
			builder.append(System.lineSeparator());
			StringUtils.indent(builder, 1);
			builder.append("\"").append(variable.getName()).append("\"=");
			printVariableDeclaration(builder, variable);
			first = false;
		}
		if (first) {
			builder.append("  ");
		} else {
			builder.append(System.lineSeparator());
		}
		builder.append("],").append(System.lineSeparator());
		builder.append("[");
		first = true;
		for (FunctionDefinition function : program.getFunctions().values()) {
			if (!first)
				builder.append(",");
			builder.append(System.lineSeparator());
			StringUtils.indent(builder, 1);
			builder.append("\"").append(function.getName()).append("\"=");
			printFunctionDefinition(builder, function);
			first = false;
		}
		if (first) {
			builder.append("  ");
		} else {
			builder.append(System.lineSeparator());
		}
		builder.append("]").append(System.lineSeparator());
		builder.append(")");

		return builder.toString();
	}

	private static void printFunctionDefinition(StringBuilder builder, FunctionDefinition function) {
		builder.append("FunctionDefinition(").append(System.lineSeparator());
		StringUtils.indent(builder, 2);
		builder.append("\"").append(function.getName()).append("\",").append(System.lineSeparator());
		StringUtils.indent(builder, 2);
		builder.append(function.getReturnType().toString()).append(",").append(System.lineSeparator());
		StringUtils.indent(builder, 2);
		builder.append("[");
		boolean first = true;
		for (VariableDeclaration variable : function.getContextVariableList()) {
			if (!first)
				builder.append(",");
			builder.append(System.lineSeparator());
			StringUtils.indent(builder, 3);
			builder.append("\"").append(variable.getName()).append("\"=");
			printVariableDeclaration(builder, variable);
			first = false;
		}
		if (first) {
			builder.append("  ");
		} else {
			builder.append(System.lineSeparator());
			StringUtils.indent(builder, 2);
		}
		builder.append("],").append(System.lineSeparator());
		StringUtils.indent(builder, 2);
		builder.append("[");
		first = true;
		for (VariableDeclaration argument : function.getArgumentList()) {
			if (!first)
				builder.append(",");
			builder.append(System.lineSeparator());
			StringUtils.indent(builder, 3);
			builder.append("\"").append(argument.getName()).append("\"=");
			printVariableDeclaration(builder, argument);
			first = false;
		}
		if (first) {
			builder.append("  ");
		} else {
			builder.append(System.lineSeparator());
			StringUtils.indent(builder, 2);
		}
		builder.append("],").append(System.lineSeparator());
		StringUtils.indent(builder, 2);
		builder.append("[");
		first = true;
		for (VariableDeclaration variable : function.getLocalVariables().values()) {
			if (!first)
				builder.append(",");
			builder.append(System.lineSeparator());
			StringUtils.indent(builder, 3);
			builder.append("\"").append(variable.getName()).append("\"=");
			printVariableDeclaration(builder, variable);
			first = false;
		}
		if (first) {
			builder.append("  ");
		} else {
			builder.append(System.lineSeparator());
			StringUtils.indent(builder, 2);
		}
		builder.append("],").append(System.lineSeparator());
		StringUtils.indent(builder, 2);
		builder.append("[");

		InstructionPrinter printer = new InstructionPrinter(builder, 3);

		first = true;
		for (IInstruction instruction : function.getBody()) {
			if (!first)
				builder.append(",");
			builder.append(System.lineSeparator());
			StringUtils.indent(builder, 3);
			try {
				instruction.accept(printer);
			} catch (FractalIRException e) {
				e.printStackTrace();
			}
			first = false;
		}
		if (first) {
			builder.append("  ");
		} else {
			builder.append(System.lineSeparator());
			StringUtils.indent(builder, 2);
		}
		builder.append("]").append(System.lineSeparator());
		StringUtils.indent(builder, 1);
		builder.append(")");
	}

	private static void printVariableDeclaration(StringBuilder builder, VariableDeclaration declaration) {
		builder.append("VariableDeclaration(");
		builder.append(declaration.getType().toString());
		builder.append(", \"");
		builder.append(declaration.getName());
		if (declaration.getAttributes().isEmpty()) {
			builder.append("\")");
		} else {
			builder.append("\", [ ");
			AttributePrinter attributePrinter = new AttributePrinter(builder);
			boolean first = true;
			for (IAttribute attribute : declaration.getAttributes()) {
				if (!first)
					builder.append(", ");
				attribute.accept(attributePrinter);
				first = false;
			}
			builder.append(" ])");
		}
	}
}
