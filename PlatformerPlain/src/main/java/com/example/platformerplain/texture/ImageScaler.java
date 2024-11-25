package com.example.platformerplain.texture;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;

public class ImageScaler {

    public static Image nearestNeighborScale(Image inputImage, int scale) {

        if(scale>1) {

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
