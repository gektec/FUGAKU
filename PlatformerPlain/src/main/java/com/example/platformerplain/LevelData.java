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
                    "G00000000000000000000000000000000000000000000M",
                    "H00000000000000000000000000000000000000000000M",
                    "H0000000000000000000000000HM00000000000000000M",
                    "H0000000000000000000000MMMHM00000000000000000M",
                    "H00DDDDDDDDD00000EEEEE0000HM00000000000000000M",
                    "MMMMMMMMMMMMMMMMMMMMMM000EH000000000000000000M",
                    "0000000000000000000000000MHMMM00000MMMMH00000M",
                    "00000000000000000000000000HM00000000000H00000M",
                    "0000000000E0E0E0000M0000MMHM00EEEEE0000H00000M",
                    "00000000MMMMMMMMM00000M000HM00MMMMM0000H00000M",
                    "00000M00000000000000000000HM00000000000H00000M",
                    "00M00000000000000000000H00HM00000000MMMMM0000M",
                    "00H00000000000000000000HMMMM000EEE00000000EEEM",
                    "00HMMM00M0E0M00EM0000E0H000M000MMM00000000MMMM",
                    "00H000E0M0MMM0MMM0000MMM000M00000000000000000M",
                    "P0H000MM0000000000000000000M000000000MMM0000GM",
                    "00HDD000sssssssssssssssssssMssssssssssssssssss",
                    "MMMM0000SSSSSSSSSSSSSSSSSSSMSSSSSSSSSSSSSSSSSS"
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
