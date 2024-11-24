package com.example.platformerplain.texture;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;

import static com.example.platformerplain.Constants.SCALE_FACTOR;

public class ImageScaler {

    public static Image nearestNeighborScale(Image inputImage) {


        int inputWidth = (int) inputImage.getWidth();
        int inputHeight = (int) inputImage.getHeight();

        int outputWidth = (int) (inputWidth * SCALE_FACTOR);
        int outputHeight = (int) (inputHeight * SCALE_FACTOR);

        WritableImage outputImage = new WritableImage(outputWidth, outputHeight);
        PixelReader pixelReader = inputImage.getPixelReader();
        PixelWriter pixelWriter = outputImage.getPixelWriter();

        for (int y = 0; y < outputHeight; y++) {
            for (int x = 0; x < outputWidth; x++) {
                // Calculate the nearest neighbor's coordinates in the input image
                int nearestX = (int) (x / SCALE_FACTOR);
                int nearestY = (int) (y / SCALE_FACTOR);

                // Get the color of the nearest neighbor
                Color color = pixelReader.getColor(nearestX, nearestY);

                // Set the color to the output image's pixel
                pixelWriter.setColor(x, y, color);
            }
        }

        return outputImage;
    }
}
