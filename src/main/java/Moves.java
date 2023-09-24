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

    final static int[] SLIDING_MOVE_VALUE = {1,-1,8,-8,7,9,-7,-9};

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

                //calculate all the move options
                int endSquare = knightSquare + moveOption;

                //ensure the move stays on the board
                if (endSquare < 64 && endSquare > -1){

                    //refactored to reduce if else
                    //if the end square is empty or opponent colour then add to moves
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


    public static List<int[]> allSlidingPiecePseudoLegalMoves(Board board){

        char colour = board.getSideToMove();

        List<int[]> allSlidingPiecePseudoLegalMoves = new ArrayList<>();

        for (int pieceIndex = 0; pieceIndex < board.getSquares().length; pieceIndex++){
// TODO: 24/09/2023 get locations of each sliding piece and add their move to the List 
        }
        
        return allSlidingPiecePseudoLegalMoves;
    }


    public static List<int[]> slidingPiecePseudoLegalMoves(Board board, char piece, char colour){

        //changes depending on the piece
        int moveValuesStartIndex;
        int moveValuesEndIndex;

        //select correct move options based on piece
        switch (piece)
        {
            case 'r':
            case 'R':
                moveValuesStartIndex = 0;
                moveValuesEndIndex = 3;
                break;
            case 'b':
            case 'B':
                moveValuesStartIndex = 4;
                moveValuesEndIndex = 7;
                break;
            default:
                moveValuesStartIndex = 0;
                moveValuesEndIndex = 7;
                break;
        }

        List<int[]> slidingPiecePseudoLegalMoves = new ArrayList<>();

        List<Integer> pieceSquares = new ArrayList<>();

        //find the squares for given piece i.e r/R
        for (int i = 0; i < board.getSquares().length; i++){

            if (board.getSquares()[i] == piece){
                pieceSquares.add(i);
            }
        }


        for (int pieceSquare : pieceSquares){

            for (int i = moveValuesStartIndex; i <= moveValuesEndIndex ; i++){

                int moveOption = SLIDING_MOVE_VALUE[i];

                //keep track of previous square to stop board wrapping
                int previousSquare = pieceSquare;
                //8 is the max iterations if a piece moves full length of board
                for (int moveOptionMultiplier = 1; moveOptionMultiplier <= 8; moveOptionMultiplier++){

                    int endSquare = pieceSquare + moveOption * moveOptionMultiplier;

                    if (endSquare < 64 && endSquare > -1 && !moveBoardWrap(previousSquare, endSquare)){

                        //if the take is valid then add to moves
                        if (checkEndSquareColour(board.getSquares(), colour, endSquare)){
                            int[] move = {pieceSquare, endSquare};
                            slidingPiecePseudoLegalMoves.add(move);
                        }

                        //if we either take or find our own piece then stop generating
                        if (board.getSquares()[endSquare] != 0){
                            break;
                        }

                        //otherwise, do next move and update previous square
                        previousSquare = endSquare;
                    } else {
                        //if leaving the board or wrapping the board then break loop
                        break;
                    }
                }
            }
        }

        return slidingPiecePseudoLegalMoves;
    }

    // TODO: 23/09/2023 king pseudo legal moves and pawn pseudo legal moves 


    //going to bed used for all piece calculation other than knights so refactored into its own method
    public static boolean moveBoardWrap(int startSquare, int endSquare){

        //if a move is wrapping across the board then return true, and it needs to be removed
        if (Util.isOnAFile(startSquare) && Util.isOnHFile(endSquare)){
            return true;
        }
        if (Util.isOnHFile(startSquare) && Util.isOnAFile(endSquare)){
            return true;
        }

        return false;
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
