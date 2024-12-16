package com.example.platformerplain.texture;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * This class provides functionality to scale images using the nearest neighbor algorithm.
 *
 * @author Changyu Li
 * @date 2024/11/13
 */
public class ImageScaler {

    /**
     * Scales the provided input image using the nearest neighbor algorithm.
     *
     * <p>
     * If the scale factor is greater than 1, the method creates a new image that
     * is larger than the input image, maintaining the original pixel colors
     * without interpolation. If the scale factor is 1 or less, the input image
     * is returned unchanged.
     * </p>
     *
     * @param inputImage The image to be scaled.
     * @param scale The scaling factor. Must be greater than 0.
     * @return A new scaled image if scale is greater than 1; otherwise, the original input image.
     */
    public static Image nearestNeighborScale(Image inputImage, int scale) {
        if (scale > 1) {
            int inputWidth = (int) inputImage.getWidth();
            int inputHeight = (int) inputImage.getHeight();

            int outputWidth = (int) (inputWidth * scale);
            int outputHeight = (int) (inputHeight * scale);

            WritableImage outputImage = new WritableImage(outputWidth, outputHeight);
            PixelReader pixelReader = inputImage.getPixelReader();
            PixelWriter pixelWriter = outputImage.getPixelWriter();

            for (int y = 0; y < outputHeight; y++) {
                for (int x = 0; x < outputWidth; x++) {
                    // Calculate the nearest neighbor's coordinates in the input image
                    int nearestX = (int) (x / scale);
                    int nearestY = (int) (y / scale);

                    // Get the color of the nearest neighbor
                    Color color = pixelReader.getColor(nearestX, nearestY);

                    // Set the color to the output image's pixel
                    pixelWriter.setColor(x, y, color);
                }
            }

            return outputImage;
        }
        return inputImage;
    }
}
