package com.fugaku.platformer.model;

import java.io.*;
import java.util.*;

import static com.fugaku.platformer.data.Assets.SAVE_FILE_READER;
import static com.fugaku.platformer.data.Assets.SAVE_FILE_WRITER;

/**
 * Manages the ranking system for the game by keeping track of the highest scores.
 * It maintains a list to store the top scores and provides methods
 * to update and retrieve these scores.
 * The maximum number of scores stored is defined by {@link #MAX_SIZE}.
 *
 * @author Zelin Xia
 * @date 2024/12/15
 */
public class RankModel {
    private static final int MAX_SIZE = 3;
    private static List<Integer> scores = new ArrayList<>();  // List to store the top scores

    public static void initializeScores() {
        try {
            BufferedReader reader = SAVE_FILE_READER;
            String line;
            while ((line = reader.readLine()) != null && scores.size() < MAX_SIZE) {
                try {
                    scores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid score format: " + line);
                }
            }
            scores.sort(Collections.reverseOrder()); // Ensure scores are sorted
        } catch (IOException e) {
            System.err.println("Error reading score file: " + e.getMessage());
        }
    }

    /**
     * Updates the score list with the current final score from the game.
     */
    public static void saveScores(int finalScore) {
        if (scores.size() < MAX_SIZE) {
            scores.add(finalScore);
        } else {
            int minScore = Collections.min(scores);
            if (finalScore > minScore) {
                scores.remove(Integer.valueOf(minScore));
                scores.add(finalScore);
            }
        }

        scores.sort(Collections.reverseOrder());

        writeScoresToFile();
    }

    /**
     * Writes the current top scores to a file.
     */
    private static void writeScoresToFile() {
        try {
            BufferedWriter writer = SAVE_FILE_WRITER;
            for (Integer score : scores) {
                writer.write(score.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing score file: " + e.getMessage());
        }
    }

    /**
     * Returns an array containing the top scores.
     * @return An array with the top scores.
     */
    public static Integer[] getScores() {
        Integer[] topScores = new Integer[scores.size()];
        return scores.toArray(topScores);
    }

    /**
     * Gets the score with index.
     *
     */
    public static Integer getScore(int index) {
        Integer[] topScores = getScores();
        if(topScores.length >= index + 1) return topScores[index];
        else return 0;
    }

}
