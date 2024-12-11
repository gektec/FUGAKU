package com.example.platformerplain;

import com.example.platformerplain.texture.ImageScaler;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AssetManager {
    public static void preloadAssets() {
        Field[] fields = Assets.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                // Access the static field
                if (field.get(null) instanceof GameMediaPlayer gameMediaPlayer) {
                    gameMediaPlayer.play();
                    gameMediaPlayer.stop();
                }
            }catch(IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }

    static final Map<Integer, int[]> adjacencyCodePlatform;
    static final Map<Integer, int[]> adjacencyCodeSpike;

    static {
        adjacencyCodePlatform = new HashMap<>();
        // Populating the map with adjacencyCode as keys and sprite row/column as values
        // Example entries (these are illustrative and should be adjusted according to your actual sprite sheet)
        adjacencyCodePlatform.put(0, new int[]{3, 3}); // No adjacency
        adjacencyCodePlatform.put(1, new int[]{3, 2}); // Up
        adjacencyCodePlatform.put(2, new int[]{0, 3}); // Right
        adjacencyCodePlatform.put(3, new int[]{0, 2}); // Up + Right
        adjacencyCodePlatform.put(4, new int[]{3, 0}); // Down
        adjacencyCodePlatform.put(5, new int[]{3, 1}); // Up + Down
        adjacencyCodePlatform.put(6, new int[]{0, 0}); // Right + Down
        adjacencyCodePlatform.put(7, new int[]{0, 1}); // Up + Right + Down
        adjacencyCodePlatform.put(8, new int[]{2, 3}); // Left
        adjacencyCodePlatform.put(9, new int[]{2, 2}); // Up + Left
        adjacencyCodePlatform.put(10, new int[]{1, 3}); // Right + Left
        adjacencyCodePlatform.put(11, new int[]{1, 2}); // Up + Right + Left
        adjacencyCodePlatform.put(12, new int[]{2, 0}); // Down + Left
        adjacencyCodePlatform.put(13, new int[]{2, 1}); // Up + Down + Left
        adjacencyCodePlatform.put(14, new int[]{1, 0}); // Right + Down + Left
        adjacencyCodePlatform.put(15, new int[]{1, 1}); // All sides (Up + Right + Down + Left)


        adjacencyCodeSpike = new HashMap<>();
        adjacencyCodeSpike.put(1, new int[]{6, 1}); // Body is on top
        adjacencyCodeSpike.put(20, new int[]{6, 0}); // Peak is on bottom
        adjacencyCodeSpike.put(2, new int[]{4, 0}); // Body is on right
        adjacencyCodeSpike.put(24, new int[]{5, 0}); // Peak is on left
        adjacencyCodeSpike.put(4, new int[]{7, 0}); // Body is on bottom
        adjacencyCodeSpike.put(17, new int[]{7, 1}); // Peak is on top
        adjacencyCodeSpike.put(8, new int[]{6, 2}); // Body is on left
        adjacencyCodeSpike.put(18, new int[]{5, 1}); // Peak is on right
    }


    public static int[] getPlatformPosition(int adjacencyCode) {
        return adjacencyCodePlatform.get(adjacencyCode);
    }

    public static int[] getSpikePosition(int adjacencyCode) {
        return adjacencyCodeSpike.get(adjacencyCode);
    }

    public static class GameMediaPlayer {
        private MediaPlayer mediaPlayer;

        public GameMediaPlayer (String mediaFile) {
            Media media = loadSound(mediaFile);
            this.mediaPlayer = new MediaPlayer(media);
        }

        public void play() {
            if (mediaPlayer.getStatus() != MediaPlayer.Status.STOPPED) {
                mediaPlayer.stop();
            }
            mediaPlayer.play();
        }

        public void stop() {
            mediaPlayer.stop();
        }

        public void cycle() {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                }
            });
        }

        public void stopCycle() {
            mediaPlayer.setOnEndOfMedia(null); // Remove the looping behavior
        }
    }

    public static Media loadSound(String s) {
        try {
            String mediaPath = Objects.requireNonNull(
                    Assets.class.getResource(s)
            ).toURI().toString();
            return new Media(mediaPath);
        } catch (URISyntaxException e) {
            System.err.println("Error loading sound file: " + e.getMessage());
            return null;
        } catch (NullPointerException e) {
            System.err.println("Sound resource not found: " + s);
            return null;
        }
    }

    public static Image[][] loadImage(String s, int w, int h, int scale) {
        Image[][] ret;
        try {
            InputStream inputStream = Objects.requireNonNull(Assets.class.getResourceAsStream(s));
            Image spritesheet = new Image(inputStream);
            int width = (int) (spritesheet.getWidth() / w);
            int height = (int) (spritesheet.getHeight() / h);
            ret = new Image[height][width];

            PixelReader pixelReader = spritesheet.getPixelReader();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    ret[i][j] = ImageScaler.nearestNeighborScale((new WritableImage(pixelReader, j * w, i * h, w, h)), scale);
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading graphics.");
            System.exit(0);
        }
        return null;
    }

    public static Image loadImage(String s, int scale) {
        try {
            InputStream inputStream = Objects.requireNonNull(Assets.class.getResourceAsStream(s));
            Image image = new Image(inputStream);
            return ImageScaler.nearestNeighborScale(image, scale);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading graphics.");
            System.exit(0);
        }
        return null;
    }

    //Overload
    public static Image loadImage(String s) {
        try {
            InputStream inputStream = Objects.requireNonNull(Assets.class.getResourceAsStream(s));
            Image image = new Image(inputStream);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading graphics.");
            System.exit(0);
        }
        return null;
    }

}
