package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.Scope;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.IInstructionOutputVisitor;
import com.kneelawk.kfractal.generator.api.ir.instruction.io.VariableReference;
import com.kneelawk.kfractal.generator.util.FunctionScope;

class ValidatingInstructionOutputVisitor implements IInstructionOutputVisitor<ValueInfo> {
    private FunctionScope<ValueInfo> functionScope;

    public ValidatingInstructionOutputVisitor(
            FunctionScope<ValueInfo> functionScope) {
        this.functionScope = functionScope;
    }

    @Override
    public ValueInfo visitVariableReference(VariableReference reference) throws FractalIRException {
        int index = reference.getIndex();
        Scope scope = reference.getScope();

        ValueInfo valueInfo;
        if (index < functionScope.getScope(scope).size()) {
            valueInfo = functionScope.get(scope, index);
        } else {
            throw new MissingVariableReferenceException(
                    "Reference to missing variable: scope: " + scope + ", index: " + index);
        }

        // check for illegal output variable attributes (constant, or preallocated)
        if (valueInfo.getVariableAttributes().contains(IAttribute.PREALLOCATED)) {
            throw new IncompatibleVariableAttributeException(
                    "Variable: scope: " + scope + ", index: " + index + " has illegal output attribute PREALLOCATED");
        }

        return valueInfo;
    }

    @Override
    public ValueInfo visitVoid() {
        return new ValueInfo.Builder().setType(ValueTypes.VOID).build();
    }
}
