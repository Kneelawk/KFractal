package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.attribute.IAttribute;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProgramValidator {
	public static void checkValidity(Program program) throws FractalIRException {
		checkVariables(program.getGlobalVariables().values());
		Map<String, ValueInfo> globalVariables = ImmutableMap.copyOf(program.getGlobalVariables().values().stream()
				.map(v -> new ImmutablePair<>(v.getName(),
						new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue)));

		for (FunctionDefinition function : program.getFunctions().values()) {
			// setup local variable scope
			Map<String, ValueInfo> localScope = Maps.newHashMap();
			localScope.putAll(globalVariables);
			checkArguments(function.getContextVariableList());
			checkVariableNameConflicts(localScope, function.getContextVariableList());
			localScope.putAll(function.getContextVariableList().stream()
					.map(v -> new ImmutablePair<>(v.getName(),
							new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
					.collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
			checkArguments(function.getArgumentList());
			checkVariableNameConflicts(localScope, function.getArgumentList());
			localScope.putAll(function.getArgumentList().stream().map(v -> new ImmutablePair<>(v.getName(),
					new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
					.collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
			checkVariables(function.getLocalVariables().values());
			checkVariableNameConflicts(localScope, function.getLocalVariables().values());
			localScope.putAll(function.getLocalVariables().values().stream().map(v -> new ImmutablePair<>(v.getName(),
					new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
					.collect(Collectors.toMap(Pair::getKey, Pair::getValue)));

			ValidatingInstructionVisitor visitor =
					new ValidatingInstructionVisitor(program.getFunctions(), localScope, function.getReturnType());

			for (IInstruction instruction : function.getBody()) {
				instruction.accept(visitor);
			}
		}
	}

	private static void checkArguments(Collection<VariableDeclaration> arguments) throws FractalIRValidationException {
		for (VariableDeclaration v : arguments) {
			ValueType type = v.getType();
			Set<IAttribute> attributes = v.getAttributes();

			// check variable types
			checkVariableType(type);

			// check variable attributes
			if (attributes.contains(IAttribute.PREALLOCATED)) {
				throw new FractalIRValidationException("Illegal argument attribute: PREALLOCATED");
			}
		}
	}

	private static void checkVariables(Collection<VariableDeclaration> variables)
			throws FractalIRValidationException {
		for (VariableDeclaration v : variables) {
			ValueType type = v.getType();
			Set<IAttribute> attributes = v.getAttributes();

			// check variable types
			checkVariableType(type);

			// check variable attributes
			if (!ValueTypes.isPointer(type)) {
				if (attributes.contains(IAttribute.PREALLOCATED)) {
					throw new FractalIRValidationException("Illegal variable attribute: PREALLOCATED");
				}
			}

			if (attributes.containsAll(ImmutableSet.of(IAttribute.CONSTANT, IAttribute.PREALLOCATED))) {
				throw new FractalIRValidationException("Redundant variable attributes: CONSTANT and PREALLOCATED");
			}
		}
	}

	private static void checkVariableType(ValueType type) throws FractalIRValidationException {
		if (ValueTypes.isVoid(type)) {
			throw new FractalIRValidationException("Illegal variable type: VOID");
		} else if (ValueTypes.isFunction(type)) {
			if (ValueTypes.toFunction(type).isNullFunction()) {
				throw new FractalIRValidationException("Illegal variable type: NULL_FUNCTION");
			}
		} else if (ValueTypes.isPointer(type)) {
			if (ValueTypes.toPointer(type).isNullPointer()) {
				throw new FractalIRValidationException("Illegal variable type: NULL_POINTER");
			}
		}
	}

	private static void checkVariableNameConflicts(Map<String, ValueInfo> scope,
												   Collection<VariableDeclaration> newVariables)
			throws NameConflictException {
		for (VariableDeclaration v : newVariables) {
			if (scope.containsKey(v.getName())) {
				throw new NameConflictException("Cannot redefine variable: " + v.getName());
			}
		}
	}
}
