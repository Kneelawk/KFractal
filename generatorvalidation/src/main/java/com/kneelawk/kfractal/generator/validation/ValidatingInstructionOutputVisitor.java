package com.kneelawk.kfractal.generator.validation;

import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentScope;
import com.kneelawk.kfractal.generator.api.ir.ValueTypes;
import com.kneelawk.kfractal.generator.api.ir.GlobalDeclaration;
import com.kneelawk.kfractal.generator.api.ir.attribute.IGlobalAttribute;
import com.kneelawk.kfractal.generator.api.ir.reference.ArgumentReference;

import java.util.List;

class ValidatingInstructionOutputVisitor implements IInstructionOutputVisitor<ValueInfo> {
    private List<GlobalDeclaration> globalVariables;
    private List<GlobalDeclaration> contextVariables;
    private List<GlobalDeclaration> arguments;
    private List<GlobalDeclaration> localVariables;

    public ValidatingInstructionOutputVisitor(
            List<GlobalDeclaration> globalVariables,
            List<GlobalDeclaration> contextVariables,
            List<GlobalDeclaration> arguments,
            List<GlobalDeclaration> localVariables) {
        this.globalVariables = globalVariables;
        this.contextVariables = contextVariables;
        this.arguments = arguments;
        this.localVariables = localVariables;
    }

    @Override
    public ValueInfo visitVariableReference(ArgumentReference reference) throws FractalIRException {
        // find the variable
        // This might bes appropriate for its own class
        int index = reference.getIndex();
        ArgumentScope scope = reference.getScope();
        List<GlobalDeclaration> scopeList;
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
            GlobalDeclaration declaration = scopeList.get(index);
            valueInfo = new ValueInfo.Builder(true, declaration.getType(), declaration.getAttributes()).build();
        } else {
            throw new MissingVariableReferenceException(
                    "Reference to missing variable: scope: " + scope + ", index: " + index);
        }

        // check for illegal output variable attributes (constant, or preallocated)
        if (valueInfo.getVariableAttributes().contains(IGlobalAttribute.PREALLOCATED)) {
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
