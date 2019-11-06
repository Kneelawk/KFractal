package com.kneelawk.kfractal.generator.util;

import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.generator.api.ir.phi.Phi;
import com.kneelawk.kfractal.generator.api.ir.phi.PhiBranch;
import com.kneelawk.kfractal.util.StringUtils;

import java.util.List;
import java.util.Map;

public class ProgramPrinter {
    public static String printProgram(Program program) {
        StringBuilder builder = new StringBuilder();
        builder.append("Program(").append(System.lineSeparator());
        builder.append("[");
        boolean first = true;
        for (Map.Entry<String, GlobalDeclaration> entry : program.getGlobalVariables().entrySet()) {
            GlobalDeclaration variable = entry.getValue();
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 1);
            builder.append('"').append(entry.getKey()).append("\"=");
            printGlobalDeclaration(builder, variable);
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
        for (Map.Entry<String, FunctionDefinition> entry : program.getFunctions().entrySet()) {
            FunctionDefinition function = entry.getValue();
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 1);
            builder.append('"').append(entry.getKey()).append("\"=");
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

        // print return type
        StringUtils.indent(builder, 2);
        builder.append(function.getReturnType().toString()).append(",").append(System.lineSeparator());

        // print context variables
        StringUtils.indent(builder, 2);
        builder.append("[");
        List<ArgumentDeclaration> arguments = function.getContextVariables();
        int size = arguments.size();
        int index;
        for (index = 0; index < function.getContextVariables().size(); index++) {
            ArgumentDeclaration variable = arguments.get(index);
            if (index > 0)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 3);
            builder.append(index).append("=");
            printArgumentDeclaration(builder, variable);
        }
        if (size < 1) {
            builder.append("  ");
        } else {
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 2);
        }
        builder.append("],").append(System.lineSeparator());

        // print arguments
        StringUtils.indent(builder, 2);
        builder.append("[");
        arguments = function.getArguments();
        size = arguments.size();
        for (index = 0; index < size; index++) {
            ArgumentDeclaration argument = arguments.get(index);
            if (index > 0)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 3);
            builder.append(index).append("=");
            printArgumentDeclaration(builder, argument);
        }
        if (size < 1) {
            builder.append("  ");
        } else {
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 2);
        }
        builder.append("],").append(System.lineSeparator());

        // print basic blocks
        StringUtils.indent(builder, 2);
        builder.append("[");
        List<BasicBlock> blocks = function.getBlocks();
        size = blocks.size();
        for (index = 0; index < size; index++) {
            BasicBlock block = blocks.get(index);
            if (index > 0)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 3);
            printBasicBlock(builder, block);
        }
        if (size < 1) {
            builder.append("  ");
        } else {
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 2);
        }
        builder.append("]").append(System.lineSeparator());

        StringUtils.indent(builder, 1);
        builder.append(")");
    }

    private static void printGlobalDeclaration(StringBuilder builder, GlobalDeclaration declaration) {
        builder.append("GlobalDeclaration(");
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

    private static void printArgumentDeclaration(StringBuilder builder, ArgumentDeclaration declaration) {
        builder.append("ArgumentDeclaration(");
        builder.append(declaration.getType().toString());
        builder.append(")");
    }

    private static void printBasicBlock(StringBuilder builder, BasicBlock basicBlock) {
        builder.append("BasicBlock(").append(System.lineSeparator());

        // print phis
        StringUtils.indent(builder, 4);
        builder.append("[").append(System.lineSeparator());
        int index;
        int size = basicBlock.getPhis().size();
        for (index = 0; index < size; index++) {
            Phi phi = basicBlock.getPhis().get(index);
            if (index > 0)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 5);
            builder.append(index).append("=");
            printPhi(builder, phi);
        }
        if (size > 0) {
            builder.append("  ");
        } else {
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 4);
        }
        builder.append("],").append(System.lineSeparator());

        // print instructions
        ProceduralValuePrinter printer = new ProceduralValuePrinter(builder);

        StringUtils.indent(builder, 4);
        builder.append("[");
        List<IProceduralValue> body = basicBlock.getBody();
        size = body.size();
        for (index = 0; index < size; index++) {
            IProceduralValue value = body.get(index);
            if (index > 0)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 5);
            builder.append(index).append("=");
            try {
                value.accept(printer);
            } catch (FractalException e) {
                e.printStackTrace();
            }
        }
        if (size < 1) {
            builder.append("  ");
        } else {
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 4);
        }
        builder.append("]").append(System.lineSeparator());

        StringUtils.indent(builder, 3);
        builder.append(")");
    }

    private static void printPhi(StringBuilder builder, Phi phi) {
        builder.append("Phi([").append(System.lineSeparator());

        // print branches
        boolean first = true;
        for (PhiBranch branch : phi.getBranches()) {
            if (!first)
                builder.append(",");
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 6);
            printPhiBranch(builder, branch);
            first = false;
        }
        if (first) {
            builder.append("  ");
        } else {
            builder.append(System.lineSeparator());
            StringUtils.indent(builder, 5);
        }
        builder.append("])");
    }

    private static void printPhiBranch(StringBuilder builder, PhiBranch phiBranch) {
        builder.append("PhiBranch(");

        // print value
        try {
            phiBranch.getValue().accept(new PhiInputPrinter(builder));
        } catch (FractalException e) {
            e.printStackTrace();
        }

        builder.append(", ").append(phiBranch.getPreviousBlockIndex()).append(")");
    }
}
