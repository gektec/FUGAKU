package com.example.platformerplain.texture;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;

public class ImageScaler {

    public static ImagePattern nearestNeighborScale(Image inputImage) {
        final int scaleFactor = 5;
        
        int inputWidth = (int) inputImage.getWidth();
        int inputHeight = (int) inputImage.getHeight();

        int outputWidth = (int) (inputWidth * scaleFactor);
        int outputHeight = (int) (inputHeight * scaleFactor);

        WritableImage outputImage = new WritableImage(outputWidth, outputHeight);
        PixelReader pixelReader = inputImage.getPixelReader();
        PixelWriter pixelWriter = outputImage.getPixelWriter();

        for (int y = 0; y < outputHeight; y++) {
            for (int x = 0; x < outputWidth; x++) {
                // Calculate the nearest neighbor's coordinates in the input image
                int nearestX = (int) (x / scaleFactor);
                int nearestY = (int) (y / scaleFactor);

                // Get the color of the nearest neighbor
                Color color = pixelReader.getColor(nearestX, nearestY);

                // Set the color to the output image's pixel
                pixelWriter.setColor(x, y, color);
            }
        }

        return new ImagePattern(outputImage);
    }
}
