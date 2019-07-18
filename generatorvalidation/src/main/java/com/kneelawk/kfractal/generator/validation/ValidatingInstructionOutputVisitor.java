package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutputVisitor;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;

import java.util.Map;

class ValidatingInstructionOutputVisitor implements IInstructionOutputVisitor<ValueInfo> {
    private final Map<String, ValueInfo> variables;

    ValidatingInstructionOutputVisitor(
            Map<String, ValueInfo> variables) {
        this.variables = variables;
    }

    @Override
    public ValueInfo visitVariableReference(VariableReference reference) throws FractalIRException {
        // find the variable
        // This might bes appropriate for its own class
        String referenceName = reference.getName();
        ValueInfo valueInfo;
        if (variables.containsKey(referenceName)) {
            valueInfo = variables.get(referenceName);
        } else {
            throw new MissingVariableReferenceException("Reference to missing variable: '" + referenceName + '\'');
        }

        // check for illegal output variable attributes (constant, or preallocated)
        if (valueInfo.getVariableAttributes().contains(IAttribute.PREALLOCATED)) {
            throw new IncompatibleVariableAttributeException(
                    "Variable: '" + referenceName + "' has illegal output attribute PREALLOCATED");
        }

        return valueInfo;
    }

    @Override
    public ValueInfo visitVoid() {
        return new ValueInfo.Builder().setType(ValueTypes.VOID).build();
    }
}
