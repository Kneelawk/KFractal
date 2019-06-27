package com.kneelawk.kfractal.generator.validation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.kneelawk.kfractal.generator.api.ir.FractalIRException;
import com.kneelawk.kfractal.generator.api.ir.FunctionDefinition;
import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.generator.api.ir.VariableDeclaration;
import com.kneelawk.kfractal.generator.api.ir.instruction.IInstruction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramValidator {
	public static void checkValidity(Program program) throws FractalIRException {
		Map<String, ValueInfo> globalVariables = ImmutableMap.copyOf(program.getGlobalVariables().values().stream()
				.map(v -> new ImmutablePair<>(v.getName(),
						new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue)));

		for (FunctionDefinition function : program.getFunctions().values()) {
			// setup local variable scope
			Map<String, ValueInfo> localScope = Maps.newHashMap();
			localScope.putAll(globalVariables);
			checkVariableNameConflicts(localScope, function.getContextVariableList());
			localScope.putAll(function.getContextVariableList().stream()
					.map(v -> new ImmutablePair<>(v.getName(),
							new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
					.collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
			checkVariableNameConflicts(localScope, function.getArgumentList());
			localScope.putAll(function.getArgumentList().stream().map(v -> new ImmutablePair<>(v.getName(),
					new ValueInfo.Builder(true, v.getType(), v.getAttributes()).build()))
					.collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
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
