module com.kneelwak.kfractal.generator.api {
    exports com.kneelawk.kfractal.generator.api;
    exports com.kneelawk.kfractal.generator.api.engine;
    exports com.kneelawk.kfractal.generator.api.engine.value;
    exports com.kneelawk.kfractal.generator.api.ir;
    exports com.kneelawk.kfractal.generator.api.ir.attribute;
    exports com.kneelawk.kfractal.generator.api.ir.instruction;
    exports com.kneelawk.kfractal.generator.api.ir.instruction.io;

    requires com.kneelawk.kfractal.util;
    requires com.google.common;
    requires org.apache.commons.lang3;

    requires transitive commons.math3;
}
