package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.reference.VariableScope;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.VariableDeclaration;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.generator.api.ir.reference.VariableReference;

import java.util.List;

class ValidatingInstructionOutputVisitor implements IInstructionOutputVisitor<ValueInfo> {
    private List<VariableDeclaration> globalVariables;
    private List<VariableDeclaration> contextVariables;
    private List<VariableDeclaration> arguments;
    private List<VariableDeclaration> localVariables;

    public ValidatingInstructionOutputVisitor(
            List<VariableDeclaration> globalVariables,
            List<VariableDeclaration> contextVariables,
            List<VariableDeclaration> arguments,
            List<VariableDeclaration> localVariables) {
        this.globalVariables = globalVariables;
        this.contextVariables = contextVariables;
        this.arguments = arguments;
        this.localVariables = localVariables;
    }

    @Override
    public ValueInfo visitVariableReference(VariableReference reference) throws FractalIRException {
        // find the variable
        // This might bes appropriate for its own class
        int index = reference.getIndex();
        VariableScope scope = reference.getScope();
        List<VariableDeclaration> scopeList;
        switch (scope) {
            case GLOBAL:
                scopeList = globalVariables;
                break;
            case CONTEXT:
                scopeList = contextVariables;
                break;
            case ARGUMENTS:
                scopeList = arguments;
                break;
            case LOCAL:
                scopeList = localVariables;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + scope);
        }

        ValueInfo valueInfo;
        if (index < scopeList.size()) {
            VariableDeclaration declaration = scopeList.get(index);
            valueInfo = new ValueInfo.Builder(true, declaration.getType(), declaration.getAttributes()).build();
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
