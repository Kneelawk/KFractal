package com.kneelawk.kfractal.generator.api.ir.instruction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionInput;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutput;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.List;

/**
 * FunctionCall - Instruction. Calls the function represented by the function context that is the second argument with
 * arguments in the list that is the third argument and stores the function's return value in the variable referenced by
 * the first argument. The variable that the return value is stored in must be of the same type as the function's return
 * value and each of the arguments in the specified argument list must be the same types as the arguments in the
 * function.
 * <p>
 * FunctionCall(* result, Function(*, [ ** ]) function, [ ** arguments ])
 */
public class FunctionCall implements IInstruction {
    private IInstructionOutput result;
    private IInstructionInput function;
    private List<IInstructionInput> arguments;

    private FunctionCall(IInstructionOutput result,
                         IInstructionInput function,
                         List<IInstructionInput> arguments) {
        this.result = result;
        this.function = function;
        this.arguments = arguments;
    }

    public IInstructionOutput getResult() {
        return result;
    }

    public IInstructionInput getFunction() {
        return function;
    }

    public List<IInstructionInput> getArguments() {
        return arguments;
    }

    @Override
    public <R> R accept(IInstructionVisitor<R> visitor) throws FractalException {
        return visitor.visitFunctionCall(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("result", result)
                .append("function", function)
                .append("arguments", arguments)
                .toString();
    }

    public static FunctionCall create(IInstructionOutput result,
                                      IInstructionInput function,
                                      Iterable<IInstructionInput> arguments) {
        return new FunctionCall(result, function, ImmutableList.copyOf(arguments));
    }

    public static class Builder {
        private IInstructionOutput result;
        private IInstructionInput function;
        private List<IInstructionInput> arguments = Lists.newArrayList();

        public Builder() {
        }

        public Builder(IInstructionOutput result,
                       IInstructionInput function,
                       Collection<IInstructionInput> arguments) {
            this.result = result;
            this.function = function;
            this.arguments.addAll(arguments);
        }

        public FunctionCall build() {
            return new FunctionCall(result, function, ImmutableList.copyOf(arguments));
        }

        public IInstructionOutput getResult() {
            return result;
        }

        public Builder setResult(IInstructionOutput result) {
            this.result = result;
            return this;
        }

        public IInstructionInput getFunction() {
            return function;
        }

        public Builder setFunction(IInstructionInput function) {
            this.function = function;
            return this;
        }

        public List<IInstructionInput> getArguments() {
            return arguments;
        }

        public Builder setArguments(
                Collection<IInstructionInput> arguments) {
            this.arguments.clear();
            this.arguments.addAll(arguments);
            return this;
        }

        public Builder addArgument(IInstructionInput argument) {
            this.arguments.add(argument);
            return this;
        }

        public Builder addArguments(Collection<IInstructionInput> arguments) {
            this.arguments.addAll(arguments);
            return this;
        }
    }
}
