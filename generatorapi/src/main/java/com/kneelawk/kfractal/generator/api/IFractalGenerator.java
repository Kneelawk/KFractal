package com.kneelawk.kfractal.generator.api;

import java.io.IOException;

public interface IFractalGenerator {
    /**
     * Gets the smallest amount of fractal data this generator can handle.
     *
     * @return the smallest size of fractal data this generator can generate.
     */
    int getMinimumRequestSize();

    /**
     * Gets the largest amount of fractal data this generator can handle.
     *
     * @return the largest size of fractal data this generator can generate.
     */
    int getMaximumRequestSize();

    /**
     * Sets up a fractal generator for generating fractals with the specified fractal info.
     * <p>
     * This is for doing things like allocating buffers, compiling bytecode, etc.
     *
     * @param info the FractalInfo describing the fractal to generate.
     */
    void setup(FractalInfo info) throws FractalException, IOException;

    /**
     * Generate a swath of pixel values. Each pixel is not colored, instead is an integer value between 0 and the setup
     * FractalInfo's iterations value.
     *
     * @param buffer          the buffer to generate pixels into.
     * @param bufferOffset    the offset within the buffer to start generating pixels at.
     * @param bufferStride    the number of elements in the buffer to skip for each pixel.
     * @param pixelStartX     the pixel x location of the area to generate the pixels of.
     * @param pixelStartY     the pixel y location of the area to generate the pixels of.
     * @param pixelAreaWidth  the pixel width of the area to generate the pixels of.
     * @param pixelAreaHeight the pixel height of the area to generate the pixels of.
     * @param pixelStride     the number of pixels in the fractal area to skip for each pixel generated.
     */
    void generatePixels(int[] buffer, int bufferOffset, int bufferStride, int pixelStartX, int pixelStartY,
                        int pixelAreaWidth, int pixelAreaHeight, int pixelStride) throws FractalException, IOException;

    /**
     * Cleans up the engine to prepare it to be setup() again.
     */
    void clean() throws FractalException, IOException;

    /**
     * Permanently frees the resources allocated by this fractal generator.
     */
    void destroy() throws FractalException, IOException;
}
