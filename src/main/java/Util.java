import java.util.Arrays;

public class Util {

    //some moves need to check if they are on the edge of the board
    //stops overlapping the board at edges since the board is stored in a 1d array
    private static final int[] hFile = {7,15,23,31,39,47,55,63};
    private static final int[] gFile = {6,14,22,30,38,46,54,62};
    private static final int[] aFile = {0,8,16,24,32,40,48,56};
    private static final int[] bFile = {1,9,17,25,33,41,49,57};

    public static int convertSquarePosToBoardIndex(String square){

        char fileChar = square.charAt(0);
        char rankChar = square.charAt(1);

        // - 'a' will give correct rank corresponding to the letter a - h
        int file = fileChar - 'a';

        //- '0' will give the digit char as an int
        // 8 - num as the board works from top to bottom
        int rank = 8 - (rankChar - '0');

        int boardIndex = (rank * 8) + file;

        return boardIndex;
    }

    public static boolean isOnHFile(int boardIndex){

        return arrayContains(hFile, boardIndex);
    }

    public static boolean isOnGFile(int boardIndex){

        return arrayContains(gFile, boardIndex);
    }

    public static boolean isOnAFile(int boardIndex){

        return arrayContains(aFile, boardIndex);
    }

    public static boolean isOnBFile(int boardIndex){

        return arrayContains(bFile, boardIndex);
    }

    public static boolean arrayContains(int[] arr, int value){

        for (int num : arr){
            if (num == value){
                return true;
            }
        }

        return  false;
    }
}
