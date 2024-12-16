package com.example.platformerplain.data;

import com.example.platformerplain.model.GameModel;

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
                    "00000000000000DDCDCDCDDCD000000",
                    "0000000000000MMMMMMMMMMMMM00000",
                    "0000000000000S00000000000000000",
                    "0000000000000s00000000000000000",
                    "00000000H0000000000000000000000",
                    "00000000HMMMM00000000000000C000",
                    "00000000H0000000000000000000000",
                    "00000000H000000000000000000C000",
                    "00000000H0000000000000000000000",
                    "00000000HMM00000000000000000000",
                    "0000CMC0H0000MMM000s0E000D00000",
                    "0P0DDMMMMM000000000SMM00MMM000G",
                    "MMMMMMMMMMMMMM000MMMMM00MMMMMMM"
            },

            // Level 2
            {
                    "000000000000000000000000000000000000000000000",
                    "00000000000000000000000000H000000000000000000",
                    "0C000000000000000000000000HM00000000000000000",
                    "0CC00000000000000000000MMMHM00000000000000000",
                    "0CCCDDDDDDDD00000EEEE00000HM00000000000000000",
                    "MMMMMMMMMMMMMMMMMMMMMM000EH000000000000000000",
                    "0000000000000000000000000MHMMM00000MMMMH00000",
                    "00000000000000000000000000HM00000000000H00000",
                    "0000000000EC0CEC000M0000MMHM000E00E0000H00000",
                    "00000000MMMMMMMMM00000M000HM00MMMMM0000H00000",
                    "00000M00000000000000000000HM00000000000H00000",
                    "00M00000000000000000000H00HM00000000MMMMM0000",
                    "00H00000000000000000000HMMMM0000E0000000000EE",
                    "00HMMM00M000M00EM0000E0H000M000MMM00000000MMM",
                    "00H000E0M0MMM0MMM0000MMM000M00000000000000000",
                    "P0H000MM0000000000000000000M000000MMMMMM0000G",
                    "00HDD000sssssssssssssssssssMsssssssssssssssMM",
                    "MMMM0000SSSSSSSSSSSSSSSSSSSMSSSSSSSSSSSSSSSMM"
            },

            // Level 3
            {
                    "0000M0000000000000000000000000000000000000000",
                    "0C0CM000000000000000000000H000000000000000000",
                    "0C0CM000000000000000000000HM00000000000000000",
                    "0C00000000000000H000000MMMHM000E0000000000000",
                    "0H0D00000MMMMMMMH00000MM00HM000M0000000000000",
                    "0H0M000000000000H00000000EHM000000000E0000000",
                    "0H00000000000000H00000000MHMMM00000MMMM000000",
                    "0000MMM000000000H000000000HM00000000000000000",
                    "0000M00000E0E0E0H0000000MMHM00E0E0E0000000000",
                    "0000M000MMMMMMMMM000000000HM0MMMMMMM000000000",
                    "0000MM00000000000000000000HM00000000D0D000000",
                    "0000M0000M0000000000M00000HM000s0000MMMMM00ss",
                    "00000000000000000000M000MMMMC00S0E000000000SS",
                    "00C0MM0H00E0M00EMME00000M00MC00MMM00000000MMM",
                    "0000M00HMMMMMMMM00MMM000000MC00000000000E0000",
                    "0P00M00H0000000000000000000MMMM000000MMMMM00G",
                    "000DM0EHssssssMssssssssssssMsssssssssssssssMM",
                    "MMMM0MMMSSSSSSMSSSSSSSSSSSSMSSSSSSSSSSSSSSSMM"
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
