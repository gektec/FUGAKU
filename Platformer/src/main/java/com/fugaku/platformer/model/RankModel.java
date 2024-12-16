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

    /**
     * Updates the score list with the current final score from the game.
     * If the score list is not full, adds the score. If it is full,
     * replaces the lowest score if the new score is higher.
     */
    public static void saveScores(int finalScore) {

        try {
            BufferedReader reader = SAVE_FILE_READER;
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    scores.add(Integer.parseInt(line.trim())); // Add existing scores
                } catch (NumberFormatException e) {
                    System.err.println("Invalid score format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading score file: " + e.getMessage());
        }


        // Add the new finalScore
        if (scores.size() < MAX_SIZE) {
            scores.add(finalScore);  // Directly add the score if we have fewer than 3 scores
        } else {
            // There are already 3 scores, check if the new score should replace the lowest one
            int minScore = Collections.min(scores);  // Find the minimum score
            if (finalScore > minScore) {
                scores.remove(Integer.valueOf(minScore));  // Remove the minimum score
                scores.add(finalScore);  // Add the new score
            }
        }

        // Sort the list in descending order
        scores.sort(Collections.reverseOrder());

        // Write the updated scores back to the file
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
     *
     * @return An array with the top three scores.
     */
    public static Integer[] getScores() {
        Integer[] topScores = new Integer[MAX_SIZE];

        // Copy the top 3 scores from the list
        for (int i = 0; i < scores.size(); i++) {
            topScores[i] = scores.get(i);
        }

        return topScores;
    }

    /**
     * Gets the highest score.
     *
     * @return The highest score, or 0 if there are no scores.
     */
    public static Integer getFirstScore() {
        Integer[] topScores = getScores();
        return topScores[0];  // Return the highest score (first element)
    }

    /**
     * Gets the second highest score.
     *
     * @return The second highest score, or 0 if there are fewer than two scores.
     */
    public static Integer getSecondScore() {
        Integer[] topScores = getScores();
        return topScores[1];  // Return the second highest score (second element)
    }

    /**
     * Gets the third highest score.
     *
     * @return The third highest score, or 0 if there are fewer than three scores.
     */
    public static Integer getThirdScore() {
        Integer[] topScores = getScores();
        return topScores[2];  // Return the third highest score (third element)
    }
}
