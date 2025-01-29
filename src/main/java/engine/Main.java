package engine;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "8/ppp5/2k5/8/8/8/5KPP/8 w - - 0 1";
    private static final String enPassantFen = "4k3/8/8/8/3Pp3/8/8/4K3 b KQkq d3 0 1";
    private static final String testFen2 = "k7/8/8/8/8/8/rr6/7K b - - 0 1";

    private static final String fen4 = "r3k2r/8/8/8/8/8/8/R3K2R b KQq f6 0 1";

    public static int perfNodes(int depth, Board board){

        int nodes = 0;

        if (depth == 0){
            return 1;
        }
        List<Move> moves = board.boardLegalMoves();

        for (int i = 0; i < moves.size(); i++){

            board.doMove(moves.get(i));
            nodes += perfNodes(depth - 1, board);
            board.undoMove(moves.get(i));
        }
        return nodes;
    }

    public static void main(String[] args) {


        long startTime;
        long endTime;

        /*
          4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1 ;D1 26 ;D2 112 ;D3 3189 ;D4 17945 ;D5 532933 ;D6 2788982   yes !

          r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1



        */


        Board board = new Board(


        );


        System.out.println(board.boardLegalMoves().toArray().length);

        for (Move move : board.boardLegalMoves()){

            System.out.println(Util.convertBoardIndexToSquare(move.getStartSquare()) + " "  +
                    Util.convertBoardIndexToSquare(move.getEndSquare()) + " " + move.getPiece());
        }


      //  playGameInConsole(board);


        System.out.println("************************************");
        startTime = System.nanoTime();

        /*
        List<String> game = board.convertGameToFenList();
        for (String pos : game){
            System.out.println(pos);
        }
         */

        System.out.println("PERF test");
        System.out.println(perfNodes(5, board));


        endTime = System.nanoTime();
        System.out.println("************************************");

        double time = (double) (endTime - startTime) / 1000000;

        System.out.println("EXECUTION TIME " + time);

    }

    public static void playGameInConsole(Board board){

        Scanner scanner = new Scanner(System.in);

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
