package com.example.platformerplain.model;

public class RankModel {
    private static RankModel instance;

    public static int[] highestArray = new int[3];

    public static void highestScore(){
            int i  = GameModel.getFinalScore();
            if(i > highestArray[2]){
                if(i > highestArray[1]){
                    if(i > highestArray[0]){
                        highestArray[0] = i;
                    }
                    highestArray[1] = i;
                }
            }highestArray[2] = i;
    }

    public static int getHighestScore(){;
        return highestArray[0];
    }

    public static int getSecondScore(){
        return highestArray[1];
    }

    public static int getThirdScore(){
        return highestArray[2];
    }

}
