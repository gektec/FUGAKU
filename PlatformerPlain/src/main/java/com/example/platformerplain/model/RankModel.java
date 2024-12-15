package com.example.platformerplain.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class RankModel {
    private static RankModel instance;
    private static final int MAX_SIZE = 3;
    private static PriorityQueue<Integer> scores = new PriorityQueue<>(MAX_SIZE, Collections.reverseOrder());
    private int first,second,third;


    public static void highestScore(){
        int score  = GameModel.getFinalScore();
        if (scores.size() < MAX_SIZE) {
            scores.offer(score);
        } else {
            if (score > scores.peek()) {
                scores.poll();
                scores.offer(score);
            }
        }
    }

    private static Integer[] getScores() {
        PriorityQueue<Integer> scoreCopy = new PriorityQueue<>(RankModel.scores);
        return new Integer[]{scoreCopy.poll(), scoreCopy.poll(), scoreCopy.poll()};
    }

    public static Integer getFirstScore() {
        if(getScores()[0] == null){
            return 0;
        }
        return getScores()[0];
    }

    public static Integer getSecondScore() {
        if(getScores()[1] == null){
            return 0;
        }
        return getScores()[1];
    }

    public static Integer getThirdScore() {
        if(getScores()[2] == null){
            return 0;
        }
        return getScores()[2];
    }

}
