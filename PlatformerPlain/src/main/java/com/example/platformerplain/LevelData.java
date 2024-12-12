package com.example.platformerplain;

import com.example.platformerplain.model.GameModel;

public class LevelData {
    public static final String[][] Levels = new String[][]{
            {},
            {
                    "M00M000000000MMMMMMMMMMMMM000M",
                    "00000000000000DDDDDDDDDD00000M",
                    "M00M000000000MMMMMMMMMMMMM000M",
                    "0000000000000000E0E0000000000M",
                    "M00M000000000MMMMMMMMMMMMM000M",
                    "M00M000000000000000000000M000M",
                    "M00M000000000000000000000M000M",
                    "M00M000000000000000000000M000M",
                    "M00ME00000H00000000000000M000M",
                    "M00MMM0000H00000000000000M000M",
                    "M0PM0000MMM000000000000000000M",
                    "M000000000000MMM000s0E0000000M",
                    "00DDDMMM00000000000SMM000MM00G",
                    "MMMMMMMM00MMMM000MMMMM00MMMMMM"
            },
            {
                    "0000000000000000000000000000000",
                    "000000000000000000000000000000G",
                    "0000000000E0E0E0000M0000000M00M",
                    "00000000MMMMMMMMM00000M0000000M",
                    "00000M000000000000000000000000M",
                    "00M000000000M00000000000000000M",
                    "0000000000E000E000MM0E00000000M",
                    "00MMM000M0MM00M000000MMM000000M",
                    "000H00E0M00000000000000000000GM",
                    "P00H00MM0000000000000000000000M",
                    "00DH0000sssssssssssssssssssssss",
                    "MMMM0000SSSSSSSSSSSSSSSSSSSSSSS"
            }
    };

    public static class getLevelInformation {

        public static int getLevelNumber() {
            return GameModel.currentLevel;
        }
        public static int getLevelWidth() {
            return Levels[GameModel.currentLevel][0].length() * Constants.TILE_SIZE;
        }

        public static int getLevelHeight() {
            return Levels[GameModel.currentLevel].length * Constants.TILE_SIZE;
        }
    }
}
