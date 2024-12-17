package com.fugaku.platformer.data;

import com.fugaku.platformer.model.GameModel;

/**
 * Class representing level state, including level layouts and related information.
 * @author Changyu Li, Zelin Xia
 * @date 2024/11/5
 */
public class LevelData {
    public static final String[][] Levels = new String[][]{
            // Test level
            {
                    "0000000000000000000M",
                    "0000000000000000H00M",
                    "0000000000000000H00M",
                    "P000000000000000H00M",
                    "0000000000000000HM0M",
                    "MMMMMMMMMMMMMMMMMM0M",
                    "0000000000000000000M",
                    "DDDDDDDDD00G0000000M",
                    "MMMMMMMMMMMMMMMMMMMM"
            },

            // Level 1
            {
                    "0000000000000000000000000000000",
                    "00000000000000CDCDCDCECDC000000",
                    "0000000000000MMMMMMMMMMMMM00000",
                    "0000000000000S00000000000000000",
                    "0000000000000s0000000000000C000",
                    "00000000H00D0000000000000000000",
                    "00000000HMMMM00000000000000C000",
                    "00000000H0000000000000000000000",
                    "00000000H000000000000000000C000",
                    "00000000H0000000000000000000000",
                    "00000C00HMM00000000000000000000",
                    "0000CMC0H0000MMM000s0E000D00000",
                    "0P00CMMMMM000000000SMM00MMM000G",
                    "MMMMMMMMMMMMMM000MMMMM00MMMMMMM"
            },

            // Level 2
            {
                    "000000000000000000000000000000000000000000000",
                    "00000000000000000000000000000000000000000H00G",
                    "0C00000000000000000000000E000000000000000HMMM",
                    "0CC000000000s0000000000MMMMM00000000000000000",
                    "0CCCDDDDDDDDS0000EEEE000000000000000000000000",
                    "MMMMMMMMMMMMMMMMMMMMMM0000000000000DDDE000000",
                    "00000000000000000000000000000000000MMMMH00000",
                    "000000000000000000000000000000000000000H00000",
                    "0000000000EC0CEC000M000000M0000E00E0000H00000",
                    "00000000MMMMMMMMM00000M00000000MMMM0000H00000",
                    "000000000000000000000000000000000000000H00000",
                    "00000000000000000000000H000000000000MMMMM00EE",
                    "000000000000000EM000000HMMMM0000E000000000000",
                    "00000000M000M0MMM0000E0H000M000MMM00000000MMM",
                    "000000E0M0MMM00000000MMM000M0000000DDD0000000",
                    "P0000MMMM000000000000000000M000000MMMMMM00000",
                    "00DDDMsssssssssssMsssssssssMsssssssssssssssMM",
                    "MMMMMMSSSSSSSSSSSMSSSSSSSSSMSSSSSSSSSSSSSSSMM"
            },

            // Level 3
            {
                    "0C0C0C0C0000000000000000000000000000000000000",
                    "00000000000000000000000000MM00000000000000000",
                    "0C0C0C000000000000000000000M00000000000MM0000",
                    "000000000000000000000000000M00000000000000000",
                    "0C0C0000000000000000000000HM00000000000000000",
                    "00000000000000000000000000HM00000000000000000",
                    "0C000000000000000000000000HM00000000000000000",
                    "00000000000DDD00H000000000HM00000000000000000",
                    "0H0D00000MMMMMMMH000000000HM00000000000000000",
                    "0H0M000000000000H00000000EHM0E0000000E0000000",
                    "0H00DD0000000000H00000000MHMMMMM000MMMM000000",
                    "MMMMMMM000000000H000000000HM0000000000S000000",
                    "0000M00000E0D0E0H0000000MMHM00ECECE000s000000",
                    "0000M000MMMMMMMMM000000000HM00MMMMMM000000000",
                    "0000MM00000000000000000000HM000000000DDD00sss",
                    "000000000M00000000000MM000HM000s0000MMMMM0SSS",
                    "000000000000000000000MM00MMMC00S0E00000000MMM",
                    "0000MM0H00E0M0DDMME00000000MC00MMM00000000000",
                    "0000M00HMMMMMMMMMMMMMMM0000MC00000000DDDE0000",
                    "0P00M00H00000000000000000000000MM000MMMMM000G",
                    "000DM0EHssssssMsssssssss0000000ssssssssssssMM",
                    "MMMMNMMMSSSSSSMSSSSSSSSS0000000SSSSSSSSSSSSMM"
            }
    };

    /**
     * Inner class for retrieving information about the current level.
     */
    public static class getLevelInformation {

        /**
         * Returns the current level number.
         * @return the current level number.
         */
        public static int getLevelNumber() {
            return GameModel.getCurrentLevel();
        }

        /**
         * Calculates the width of the current level.
         * @return the width of the current level in pixels.
         */
        public static int getLevelWidth() {
            return Levels[GameModel.getCurrentLevel()][0].length() * Constants.TILE_SIZE;
        }

        /**
         * Calculates the height of the current level.
         * @return the height of the current level in pixels.
         */
        public static int getLevelHeight() {
            return Levels[GameModel.getCurrentLevel()].length * Constants.TILE_SIZE;
        }
    }
}
