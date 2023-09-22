import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Moves {

    //generate psudo legal moves for knights
    //if the knight is on the outer two files then some moves need to be removed
    //probably a much smarter way of doing this but hard coding the options was easy
    final static int[] KNIGHT_MOVE_VALUES = {6,-6,10,-10,15,-15,17,-17};
    final static int[] KNIGHT_MOVE_VALUES_A_FILE = {-6,10,-15,17};
    final static int[] KNIGHT_MOVE_VALUES_B_FILE = {-6,10,15,-15,17,-17};
    final static int[] KNIGHT_MOVE_VALUES_H_FILE = {6,-10,15,-17};
    final static int[] KNIGHT_MOVE_VALUES_G_FILE = {6,-10,15,-15,17,-17};

    //returns list of int[] length 2 with start square and end square for each move given as board index 0 - 63
    public static List<int[]> knightPseudoLegalMoves(Board board){


        List<int[]> allKnightPseudoLegalMoves = new ArrayList<>();

        char colourToCalculate = board.getSideToMove();
        char knightChar;

        //find the correct night char
        if (colourToCalculate == 'w'){
            knightChar = 'N';
        } else {
            knightChar = 'n';
        }

        List<Integer> knightSquares = new ArrayList<>();

        //find squares with knights
        for (int i = 0; i < board.getSquares().length; i++){

            if (board.getSquares()[i] == knightChar){
                knightSquares.add(i);
            }
        }

        for (Integer knightSquare : knightSquares){

            int[] knightMoveOptions;

            //check if knight is on a / b / g / h file as set knight move accordingly
            if (Util.isOnAFile(knightSquare)){
                knightMoveOptions = KNIGHT_MOVE_VALUES_A_FILE;
            } else if (Util.isOnBFile(knightSquare)){
                knightMoveOptions = KNIGHT_MOVE_VALUES_B_FILE;
            } else if (Util.isOnGFile(knightSquare)){
                knightMoveOptions = KNIGHT_MOVE_VALUES_G_FILE;
            } else if (Util.isOnHFile(knightSquare)){
                knightMoveOptions = KNIGHT_MOVE_VALUES_H_FILE;
            } else {
                knightMoveOptions = KNIGHT_MOVE_VALUES;
            }

            for (int moveOption : knightMoveOptions){

                int endSquare = knightSquare + moveOption;

                if (endSquare < 64 && endSquare > -1){

                    //refactored to reduce if else
                    if (checkEndSquareColour(board.getSquares(), colourToCalculate, endSquare)){
                        int[] move = {knightSquare, endSquare};
                        allKnightPseudoLegalMoves.add(move);
                    }
                }

            }
        }

        //return list of moves
        return allKnightPseudoLegalMoves;
    }


    //checks the end square for current colour of if empty
    public static boolean checkEndSquareColour(char[] squares, char colour, int squareIndex){

        if (colour == 'w'){
            return !Character.isUpperCase(squares[squareIndex]) || squares[squareIndex] == 0;
        } else {
            return !Character.isLowerCase(squares[squareIndex]) || squares[squareIndex] == 0;
        }
    }
}
