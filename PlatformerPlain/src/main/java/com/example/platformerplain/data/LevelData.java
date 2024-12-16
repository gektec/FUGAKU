package com.example.platformerplain.data;

import com.example.platformerplain.model.GameModel;

/**
 * Class representing level state, including level layouts and related information.
 * @author Changyu Li, Zelin Xia
 * @date 2024/11/5
 */
public class LevelData {
    public static final String[][] Levels = new String[][]{
            {},
            {
                    "M000000000000MMMMMMMMMMMMM0000M",
                    "00000000000000DDCDCDCDDCD00000M",
                    "M000000000000MMMMMMMMMMMMM0000M",
                    "00000000H00E00000000000000000MM",
                    "M0000000HMMMM00000000000MM0000M",
                    "M0000000H0000000000000000M0000M",
                    "M0000000H0000000000000000M0C00M",
                    "M0000000H0000000000000000M0000M",
                    "M0000000H0000000000000000M0C00M",
                    "M0000000H0000000000000000M0000M",
                    "M0000000HMM00000000000000M0000M",
                    "M000CMC0H0000MMM000s0E000D0000M",
                    "M0PDDMMMMM000000000SMM0MMMM000G",
                    "MMMMMMMM00MMMM000MMMMM00MMMMMM0"
            },
            {
                    "000000000000000000000000000000000000000000000M",
                    "M0000000000000000000000000H000000000000000000M",
                    "MC000000000000000000000000HM00000000000000000M",
                    "MCC00000000000000000000MMMHM00000000000000000M",
                    "MCCCDDDDDDDD00000EEEE00000HM00000000000000000M",
                    "MMMMMMMMMMMMMMMMMMMMMM000EH000000000000000000M",
                    "0000000000000000000000000MHMMM00000MMMMH00000M",
                    "00000000000000000000000000HM00000000000H00000M",
                    "0000000000EC0CEC000M0000MMHM000E00E0000H00000M",
                    "00000000MMMMMMMMM00000M000HM00MMMMM0000H00000M",
                    "00000M00000000000000000000HM00000000000H00000M",
                    "00M00000000000000000000H00HM00000000MMMMM0000M",
                    "00H00000000000000000000HMMMM0000E0000000000EEM",
                    "00HMMM00M000M00EM0000E0H000M000MMM00000000MMMM",
                    "00H000E0M0MMM0MMM0000MMM000M00000000000000000M",
                    "P0H000MM0000000000000000000M000000MMMMMM0000GM",
                    "00HDD000sssssssssssssssssssMsssssssssssssssMMM",
                    "MMMM0000SSSSSSSSSSSSSSSSSSSMSSSSSSSSSSSSSSSMMM"
            },
            {
                    "M000M0000000000000000000000000000000000000000M",
                    "MC0CM000000000000000000000H000000000000000000M",
                    "MC0CM000000000000000000000HM00000000000000000M",
                    "MC00000000000000H000000MMMHM000E0000000000000M",
                    "MH0D00000MMMMMMMH00000MM00HM000M0000000000000M",
                    "MH0M000000000000H00000000EHM000000000E0000000M",
                    "MH00000000000000H00000000MHMMM00000MMMM000000M",
                    "M000MMM000000000H000000000HM00000000000000000M",
                    "M000M00000E0E0E0H0000000MMHM00E0E0E0000000000M",
                    "M000M000MMMMMMMMM000000000HM0MMMMMMM000000000M",
                    "M000MM00000000000000000000HM00000000D0D000000M",
                    "M000M0000M0000000000M00000HM000s0000MMMMM00ssM",
                    "M0000000000000000000M000MMMMC00S0E000000000SSM",
                    "M0C0MM0H00E0M00EMME00000M00MC00MMM00000000MMMM",
                    "M000M00HMMMMMMMMM0MMM000000MC00000000000E0000M",
                    "MP00M00H0000000000000000000MMMM000000MMMMM00GM",
                    "000DM0EHsssssssssssssssssssMsssssssssssssssMMM",
                    "MMMM0MMMSSSSSSSSSSSSSSSSSSSMSSSSSSSSSSSSSSSMMM"
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
