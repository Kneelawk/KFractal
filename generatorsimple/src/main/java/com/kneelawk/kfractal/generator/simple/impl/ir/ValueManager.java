package com.kneelawk.kfractal.generator.simple.impl.ir;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.kneelawk.kfractal.generator.api.FractalException;
import com.kneelawk.kfractal.generator.api.engine.FractalEngineException;
import com.kneelawk.kfractal.generator.api.engine.value.IEngineValue;
import com.kneelawk.kfractal.generator.api.ir.*;
import com.kneelawk.kfractal.generator.api.ir.phi.Phi;
import com.kneelawk.kfractal.generator.api.ir.phi.PhiBranch;
import com.kneelawk.kfractal.generator.simple.impl.SimpleProgramEngine;
import com.kneelawk.kfractal.generator.simple.impl.ValueContainer;

import java.util.List;
import java.util.Map;

public class ValueManager {
    private final Map<IValue, IEngineValue> values = Maps.newHashMap();

    public BasicBlockResult runBasicBlock(BasicBlock block, int previousBlockIndex, FunctionDefinition function,
                                   List<IEngineValue> contextVariables, List<IEngineValue> arguments, Program program,
                                   SimpleProgramEngine engine, List<ValueContainer> globalVariables)
            throws FractalException {
        PhiInputVisitorContext phiInputVisitorContext =
                PhiInputVisitorContext.create(function, contextVariables, arguments);
        ImmutableMap.Builder<Phi, IEngineValue> phiValues = ImmutableMap.builder();
        for (Phi phi : block.getPhis()) {
            phiValues.put(phi, generateValue(phi, previousBlockIndex, phiInputVisitorContext));
        }

        clearValues(block.getPhis());
        clearValues(block.getBody());

        values.putAll(phiValues.build());

        ProceduralValueVisitorContext proceduralValueVisitorContext = ProceduralValueVisitorContext
                .create(engine, program, globalVariables, function, contextVariables, arguments, block);
        for (IProceduralValue proceduralValue : block.getBody()) {
            ProceduralValueVisitorResult result = generateValue(proceduralValue, proceduralValueVisitorContext);
            if (result.isTerminated()) {
                return new BasicBlockResult.Builder().setTerminationType(result.getTerminationType())
                        .setReturnValue(result.getReturnValue()).setJumpBlockIndex(result.getJumpBlockIndex()).build();
            }
        }

        throw new FractalEngineException("BasicBlock did not terminate");
    }

    IEngineValue getValue(Phi phi) throws FractalEngineException {
        if (values.containsKey(phi)) {
            return values.get(phi);
        } else {
            throw new FractalEngineException("Cannot get the value of an unknown phi");
        }
    }

    IEngineValue getValue(IProceduralValue value) throws FractalEngineException {
        if (values.containsKey(value)) {
            return values.get(value);
        } else {
            throw new FractalEngineException("Cannot get the value of an unknown procedural value");
        }
    }

    ProceduralValueVisitorResult getOrGenerateValue(IProceduralValue value, ProceduralValueVisitorContext context)
            throws FractalException {
        if (values.containsKey(value)) {
            return ProceduralValueVisitorResult.create(values.get(value));
        } else {
            return generateValue(value, context);
        }
    }

    private IEngineValue generateValue(Phi phi, int previousBlockIndex, PhiInputVisitorContext context)
            throws FractalException {
        for (PhiBranch branch : phi.getBranches()) {
            if (branch.getPreviousBlockIndex() == previousBlockIndex) {
                return branch.getValue().accept(new PhiInputVisitor(this, context));
            }
        }
        throw new FractalEngineException("Invalid Phi previous block index");
    }

    private ProceduralValueVisitorResult generateValue(IProceduralValue value, ProceduralValueVisitorContext context)
            throws FractalException {
        ProceduralValueVisitorResult result = value.accept(new ProceduralValueVisitor(this, context));
        values.put(value, result.getResultValue());
        return result;
    }

    private void clearValues(Iterable<? extends IValue> values) throws FractalException {
        for (IValue value : values) {
            value.accept(new ValueScrubbingVisitor(this.values));
        }
    }
}
