package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.GlobalDeclaration;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.util.StringUtils;

public class ProgramPrinter {
    public static String printProgram(Program program) {
        StringBuilder builder = new StringBuilder();
        builder.append("Program(").append(System.lineSeparator());
        builder.append("[");
        boolean first = true;
        for (int i = 0; i < program.getGlobalVariables().size(); i++) {
            GlobalDeclaration variable = program.getGlobalVariables().get(i);
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 1);
            builder.append(i).append("=");
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
        for (int i = 0; i < program.getFunctions().size(); i++) {
            FunctionDefinition function = program.getFunctions().get(i);
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 1);
            builder.append(i).append("=");
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
        builder.append(function.getReturnType().toString()).append(",").append(System.lineSeparator());
        StringUtils.indent(builder, 2);
        builder.append("[");
        boolean first = true;
        for (int i = 0; i < function.getContextVariables().size(); i++) {
            GlobalDeclaration variable = function.getContextVariables().get(i);
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 3);
            builder.append(i).append("=");
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
        for (int i = 0; i < function.getArguments().size(); i++) {
            GlobalDeclaration argument = function.getArguments().get(i);
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 3);
            builder.append(i).append("=");
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
        for (int i = 0; i < function.getLocalVariables().size(); i++) {
            GlobalDeclaration variable = function.getLocalVariables().get(i);
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 3);
            builder.append(i).append("=");
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
        for (IInstruction instruction : function.getBlocks()) {
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 3);
            try {
                instruction.accept(printer);
            } catch (FractalException e) {
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

    private static void printVariableDeclaration(StringBuilder builder, GlobalDeclaration declaration) {
        builder.append("VariableDeclaration(");
        builder.append(declaration.getType().toString());
        if (declaration.getAttributes().isEmpty()) {
            builder.append(")");
        } else {
            builder.append(", [ ");
            GlobalAttributePrinter attributePrinter = new GlobalAttributePrinter(builder);
            boolean first = true;
            for (IGlobalAttribute attribute : declaration.getAttributes()) {
                if (!first)
                    builder.append(", ");
                try {
                    attribute.accept(attributePrinter);
                } catch (FractalException e) {
                    e.printStackTrace();
                }
                first = false;
            }
            builder.append(" ])");
        }
    }
}
