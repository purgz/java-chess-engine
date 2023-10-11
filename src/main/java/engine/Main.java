package engine;

import java.util.Scanner;

public class Main {

    private static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "8/ppp5/2k5/8/8/8/5KPP/8 w - - 0 1";
    private static final String enPassantFen = "4k3/8/8/8/3Pp3/8/8/4K3 b KQkq d3 0 1";
    private static final String testFen2 = "k7/8/8/8/8/8/rr6/7K b - - 0 1";

    public static void main(String[] args) {


        long startTime;
        long endTime;

        System.out.println("************************************");
        startTime = System.nanoTime();
       // String fen4 = "r3k2r/8/8/8/8/7B/8/R3K2R b KQkq - 0 1";
       // engine.Board board = new engine.Board(fen4);
        playGameInConsole();



       // System.out.println(engine.PseudoMoves.castlingPseudoLegalMoves(board));

        //engine.Board board = new engine.Board(fen4);
        //board.prettyPrintBoard();

        /*
        engine.PseudoMoves.castlingPseudoLegalMoves(board);

        engine.Move blackKingCastle = new engine.Move (4, 6, 'k', (char) 0, false, board.getEnPassantTargetSquare(), false, true, false, board.getCastlingRights());
        engine.Move blackQueenCastle = new engine.Move (4, 2, 'k', (char) 0, false, board.getEnPassantTargetSquare(), false, false, true, board.getCastlingRights());

        board.doMove(blackQueenCastle);
        board.prettyPrintBoard();
        board.undoMove(blackQueenCastle);
        board.prettyPrintBoard();
        */

        endTime = System.nanoTime();
        System.out.println("************************************");

        double time = (double) (endTime - startTime) / 1000000;

        System.out.println("EXECUTION TIME " + time);

    }

    public static void playGameInConsole(){
        String fen4 = "r3k2r/8/8/8/8/8/8/R3K2R b KQq f6 0 1";
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();
        while (!board.checkMate()){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            board.prettyPrintBoard();
            System.out.println(board.convertBoardToFEN());
           // System.out.println(board.moveStack);
            String moveString = scanner.nextLine();

            if (moveString.equals("undo")){
                board.undoLastMove();
                continue;
            }

            int[] move = Util.convertMoveToBoardIndex(moveString);
            //if the move is invalid then empty array is returned
            if (move.length > 0){
                //valid move
                board.doLegalMove(move);

            }
        }


        board.prettyPrintBoard();
        System.out.println("CHECKMATE " + board.getSideToMove() + " loses");
    }


}
