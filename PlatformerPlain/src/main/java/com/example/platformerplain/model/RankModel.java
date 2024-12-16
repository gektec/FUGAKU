package com.example.platformerplain.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Manages the ranking system for the game by keeping track of the highest scores.
 * It maintains a priority queue to store the top scores and provides methods
 * to update and retrieve these scores.
 *
 * The maximum number of scores stored is defined by {@link #MAX_SIZE}.
 *
 * @author Zelin Xia
 * @date 2024/12/15
 */
public class RankModel {
    private static RankModel instance;
    private static final int MAX_SIZE = 3;
    private static PriorityQueue<Integer> scores = new PriorityQueue<>(MAX_SIZE, Collections.reverseOrder());
    private int first, second, third;

    /**
     * Updates the score list with the current final score from the game.
     * If the score list is not full, adds the score. If it is full,
     * replaces the lowest score if the new score is higher.
     */
    public static void highestScore() {
        int score = GameModel.getFinalScore();
        if (scores.size() < MAX_SIZE) {
            scores.offer(score);
        } else {
            if (score > scores.peek()) {
                scores.poll();
                scores.offer(score);
            }
        }
    }

    /**
     * Returns an array containing the top scores.
     *
     * @return An array with the top three scores, may contain null for empty spots.
     */
    private static Integer[] getScores() {
        PriorityQueue<Integer> scoreCopy = new PriorityQueue<>(RankModel.scores);
        return new Integer[]{scoreCopy.poll(), scoreCopy.poll(), scoreCopy.poll()};
    }

    /**
     * Gets the highest score.
     *
     * @return The highest score, or 0 if there are no scores.
     */
    public static Integer getFirstScore() {
        if (getScores()[0] == null) {
            return 0;
        }
        return getScores()[0];
    }

    /**
     * Gets the second highest score.
     *
     * @return The second highest score, or 0 if there are fewer than two scores.
     */
    public static Integer getSecondScore() {
        if (getScores()[1] == null) {
            return 0;
        }
        return getScores()[1];
    }

    /**
     * Gets the third highest score.
     *
     * @return The third highest score, or 0 if there are fewer than three scores.
     */
    public static Integer getThirdScore() {
        if (getScores()[2] == null) {
            return 0;
        }
        return getScores()[2];
    }
}
