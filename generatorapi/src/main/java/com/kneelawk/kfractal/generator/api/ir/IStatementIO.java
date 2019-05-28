package com.kneelawk.kfractal.generator.api.ir;

/**
 * Created by Kneelawk on 5/26/19.
 */
public interface IStatementIO {
	void accept(IStatementIOVisitor visitor);
}
