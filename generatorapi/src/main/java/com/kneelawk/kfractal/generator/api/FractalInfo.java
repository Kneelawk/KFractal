package com.kneelawk.kfractal.generator.api;

import com.kneelawk.kfractal.generator.api.ir.Program;
import com.kneelawk.kfractal.util.KFractalToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FractalInfo {
    private int imageWidth;
    private int imageHeight;
    private double planeWidth;
    private double planeHeight;
    private double planeStartX;
    private double planeStartY;
    private boolean mandelbrot;
    private int iterations;
    private double cReal;
    private double cImaginary;
    private Program generatorFunction;

    public FractalInfo(int imageWidth, int imageHeight, double planeWidth, double planeHeight, double planeStartX,
                       double planeStartY, boolean mandelbrot, int iterations, double cReal, double cImaginary,
                       Program generatorFunction) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.planeWidth = planeWidth;
        this.planeHeight = planeHeight;
        this.planeStartX = planeStartX;
        this.planeStartY = planeStartY;
        this.mandelbrot = mandelbrot;
        this.iterations = iterations;
        this.cReal = cReal;
        this.cImaginary = cImaginary;
        this.generatorFunction = generatorFunction;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public double getPlaneWidth() {
        return planeWidth;
    }

    public double getPlaneHeight() {
        return planeHeight;
    }

    public double getPlaneStartX() {
        return planeStartX;
    }

    public double getPlaneStartY() {
        return planeStartY;
    }

    public boolean isMandelbrot() {
        return mandelbrot;
    }

    public int getIterations() {
        return iterations;
    }

    public double getcReal() {
        return cReal;
    }

    public double getcImaginary() {
        return cImaginary;
    }

    public Program getGeneratorFunction() {
        return generatorFunction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, KFractalToStringStyle.KFRACTAL_TO_STRING_STYLE)
                .append("imageWidth", imageWidth)
                .append("imageHeight", imageHeight)
                .append("planeWidth", planeWidth)
                .append("planeHeight", planeHeight)
                .append("planeStartX", planeStartX)
                .append("planeStartY", planeStartY)
                .append("mandelbrot", mandelbrot)
                .append("iterations", iterations)
                .append("cReal", cReal)
                .append("cImaginary", cImaginary)
                .append("generatorFunction", generatorFunction)
                .toString();
    }

    public static class Builder {
        private int imageWidth = 1000;
        private int imageHeight = 1000;
        private double planeWidth = 1;
        private double planeHeight = 1;
        private double planeStartX = -0.5;
        private double planeStartY = -0.5;
        private boolean mandelbrot = false;
        private int iterations = 100;
        private double cReal = 0;
        private double cImaginary = 0;
        private Program generatorFunction;

        public Builder() {
        }

        public Builder(int imageWidth, int imageHeight, double planeWidth, double planeHeight, double planeStartX,
                       double planeStartY, boolean mandelbrot, int iterations, double cReal, double cImaginary,
                       Program generatorFunction) {
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
            this.planeWidth = planeWidth;
            this.planeHeight = planeHeight;
            this.planeStartX = planeStartX;
            this.planeStartY = planeStartY;
            this.mandelbrot = mandelbrot;
            this.iterations = iterations;
            this.cReal = cReal;
            this.cImaginary = cImaginary;
            this.generatorFunction = generatorFunction;
        }

        public FractalInfo build() {
            return new FractalInfo(imageWidth, imageHeight, planeWidth, planeHeight, planeStartX, planeStartY,
                    mandelbrot, iterations, cReal, cImaginary, generatorFunction);
        }

        public int getImageWidth() {
            return imageWidth;
        }

        public Builder setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public int getImageHeight() {
            return imageHeight;
        }

        public Builder setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public double getPlaneWidth() {
            return planeWidth;
        }

        public Builder setPlaneWidth(double planeWidth) {
            this.planeWidth = planeWidth;
            return this;
        }

        public double getPlaneHeight() {
            return planeHeight;
        }

        public Builder setPlaneHeight(double planeHeight) {
            this.planeHeight = planeHeight;
            return this;
        }

        public double getPlaneStartX() {
            return planeStartX;
        }

        public Builder setPlaneStartX(double planeStartX) {
            this.planeStartX = planeStartX;
            return this;
        }

        public double getPlaneStartY() {
            return planeStartY;
        }

        public Builder setPlaneStartY(double planeStartY) {
            this.planeStartY = planeStartY;
            return this;
        }

        public boolean isMandelbrot() {
            return mandelbrot;
        }

        public Builder setMandelbrot(boolean mandelbrot) {
            this.mandelbrot = mandelbrot;
            return this;
        }

        public int getIterations() {
            return iterations;
        }

        public Builder setIterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        public double getcReal() {
            return cReal;
        }

        public Builder setcReal(double cReal) {
            this.cReal = cReal;
            return this;
        }

        public double getcImaginary() {
            return cImaginary;
        }

        public Builder setcImaginary(double cImaginary) {
            this.cImaginary = cImaginary;
            return this;
        }

        public Program getGeneratorFunction() {
            return generatorFunction;
        }

        public Builder setGeneratorFunction(Program generatorFunction) {
            this.generatorFunction = generatorFunction;
            return this;
        }
    }
}
