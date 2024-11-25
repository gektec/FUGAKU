package com.example.platformerplain;

public class LevelData {
    public static final String[][] Levels = new String[][]{
            {},
            {
                    "M00M000000000MMMMMMMMMMMMM000M",
                    "00000000000000000000000000000M",
                    "M00M000000000MMMMMMMMMMMMM000M",
                    "0000000000000000E0E0000000000M",
                    "M00M000000000MMMMMMMMMMMMM000M",
                    "M00M000000000000000000000M000M",
                    "M00M000000000000000000000M000M",
                    "M00M000000000000000000000M000M",
                    "M00ME00000H00000000000000M000M",
                    "M00MMM0000H00000000000000M000M",
                    "M0PM0000MMM000000000000000000M",
                    "M000000000000MMM00000E0000000M",
                    "00000MMM00000000000MMM000MM00G",
                    "MMMMMMMM00MMMM000MMMMM00MMMMMM"
            },
            {
                    "00000000000G00000000000000000",
                    "000000000MM000000000000000000",
                    "00000MM000000M00000000000000M",
                    "0000000E00000M00000000000000M",
                    "0MMMM00000000M0000000E000000M",
                    "000000M000000000000000000000M",
                    "00000000M00000M0000000000M00M",
                    "00000000000M00000000000000M0M",
                    "000000000M000000MMMM00MM0000M",
                    "P0000000000M0E00000E00M00000M",
                    "00000000M00M0000000000M0000GM",
                    "MMMMMMM0MMMMMMMMMMMMMMMMMMMMM"
            }
    };

    public static class getLevelInformation {
        static int currentLevel = Main.currentLevel;
        public static int getLevelInformation() {
            return currentLevel;
        }
        public static int getLevelWidth() {
            return Levels[currentLevel][0].length() * Constants.TILE_SIZE;
        }

        public static int getLevelHeight() {
            return Levels[currentLevel].length * Constants.TILE_SIZE;
        }
    }
}
