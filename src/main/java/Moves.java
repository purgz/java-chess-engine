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
        char knightChar = colourToCalculate == 'w' ? 'N' : 'n';

        //find square knight is on
        List<Integer> knightSquares = findPieceSquares(board, knightChar);

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
        char[] pieceOptions = colour == 'w' ? new char[]{'R', 'B', 'Q'} : new char[]{'r', 'b', 'q'};

        List<int[]> allSlidingPiecePseudoLegalMoves = new ArrayList<>();

        for (int pieceIndex = 0; pieceIndex < board.getSquares().length; pieceIndex++){

            //check if the current piece is a rook queen or bishop of correct colour
            if (board.getSquares()[pieceIndex] == pieceOptions[0] ||
                    board.getSquares()[pieceIndex] == pieceOptions[1] ||
                    board.getSquares()[pieceIndex] == pieceOptions[2]){

                char piece = board.getSquares()[pieceIndex];
                allSlidingPiecePseudoLegalMoves.addAll(slidingPiecePseudoLegalMoves(board, piece, colour));
            }
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

        List<Integer> pieceSquares = findPieceSquares(board, piece);

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

    // pawn pseudo-legal moves will require checks for diagonal takes, as well as en-passant which can use the board en-passant target square
    // pawn diagonal takes will also need to use the moveBoardWrap to stop overlapping board due to nature of board represented as a 64 length array

    public static List<int[]> pawnPseudoLegalMoves(Board board){

        char pawnChar = board.getSideToMove() == 'w' ? 'P' : 'p';

        List<int[]> pawnPseudoLegalMoves = new ArrayList<>();

        List<Integer> pawnSquares = findPieceSquares(board, pawnChar);

        for (Integer pieceSquare : pawnSquares){

            List<Integer> pieceMoveOptions = new ArrayList<>();

            //since pawn moves are completely different for each side need to add options separately
            if (board.getSideToMove() == 'w'){

                //check if it is the first time this pawn is moving
                //removes this from the generate move function as the squares for the starting pawn rank is hard to
                //calculate
                if (pieceSquare < 56 && pieceSquare > 47 && (pieceSquare - 16 != 0)){
                    pieceMoveOptions.add(-16);
                }
                generatePawnMoves(board, pieceSquare, pieceMoveOptions, -1);
            } else {

                if (pieceSquare < 16 && pieceSquare > 7 && (pieceSquare + 16 != 0)){
                    pieceMoveOptions.add(16);
                }
                generatePawnMoves(board, pieceSquare, pieceMoveOptions, 1);
            }

            for (Integer moveOption : pieceMoveOptions){
                int endSquare = pieceSquare + moveOption;

                if (endSquare > -1 && endSquare < 64 && !moveBoardWrap(pieceSquare, endSquare)){
                    int[] move = {pieceSquare, endSquare};
                    pawnPseudoLegalMoves.add(move);
                }
            }

        }
        return pawnPseudoLegalMoves;
    }

    //refactored to use the same code for black and white just by changing dir variable
    private static void generatePawnMoves(Board board, int pieceSquare, List<Integer> pieceMoveOptions, int dir){
        if (board.getSquares()[pieceSquare + dir * 8] == 0){
            pieceMoveOptions.add(dir * 8);
        }

        //if diagonal is not empty AND contains opponent piece then you can move there
        if (checkPawnDiagonal(board, pieceSquare,dir * 7)){
            pieceMoveOptions.add(dir * 7);
        }

        if (checkPawnDiagonal(board, pieceSquare, dir * 9)){
            pieceMoveOptions.add(dir * 9);
        }
    }

    public static boolean checkPawnDiagonal(Board board, int pieceSquare, int moveOption){

        //pretty easy way to check en-passant but the en-passant target square has to be updated on every move
        if (pieceSquare + moveOption == board.getEnPassantTargetSquare()){
            return true;
        }

        return (board.getSquares()[pieceSquare + moveOption] != 0) &&
                checkEndSquareColour(board.getSquares(), board.getSideToMove(), pieceSquare + moveOption);
    }

    public static List<int[]> kingPseudoLegalMoves(Board board){

        char kingChar = board.getSideToMove() == 'w' ? 'K' : 'k';

        //only one king square so list not really needed, but it means I can reuse the existing findPieceSquare function
        List<Integer> kingSquare = findPieceSquares(board, kingChar);

        List<int[]> kingPseudoLegalMoves = new ArrayList<>();

        for (Integer square : kingSquare){
            for (int i = 0; i < SLIDING_MOVE_VALUE.length; i ++){

                int moveOption = SLIDING_MOVE_VALUE[i];

                int endSquare = square + moveOption;

                if (endSquare < 64 && endSquare > -1 && !moveBoardWrap(square, endSquare)){

                    //if the take is valid then add to moves
                    if (checkEndSquareColour(board.getSquares(), board.getSideToMove(), endSquare)){
                        int[] move = {square, endSquare};
                        kingPseudoLegalMoves.add(move);
                    }
                }
            }
        }

        return kingPseudoLegalMoves;
    }


    public static List<Integer> findPieceSquares(Board board, char piece){
        List<Integer> pieceSquares = new ArrayList<>();

        for (int i = 0; i < board.getSquares().length; i ++){

            if (board.getSquares()[i] == piece){
                pieceSquares.add(i);
            }
        }

        return pieceSquares;
    }

    //going to be used for all piece calculation other than knights so refactored into its own method
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

    //all pseudo legal move methods are now complete
    // TODO: 27/09/2023 now need to make functions to update the game board for a given pseudo-legal move
    // TODO: 27/09/2023  on each update move the en-passant target square needs to be updated
    // TODO: 27/09/2023 need a method to check if the king is in check and a stack to undo moves to check for legality
}
