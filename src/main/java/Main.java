import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "8/ppp5/2k5/8/8/8/5KPP/8 w - - 0 1";
    private static final String enPassantFen = "4k3/8/8/8/3Pp3/8/8/4K3 b KQkq d3 0 1";
    private static final String testFen2 = "k7/8/8/8/8/8/rr6/7K b - - 0 1";

    public static void main(String[] args) {


        long startTime;
        long endTime;

       // Board board = new Board(startingFen);
        Board board = new Board(testFen2);
        System.out.println("************************************");


        startTime = System.nanoTime();
        board.prettyPrintBoard();
        System.out.println("IS THIS CHECKMATE : " + board.checkMate());

        boolean move = board.doLegalMove(new int[] {Util.convertSquarePosToBoardIndex("b2"), Util.convertSquarePosToBoardIndex("b1")});
        board.prettyPrintBoard();
        System.out.println("IS THIS CHECKMATE : " + board.checkMate());


        /*
        int[] move1 = new int[] {Util.convertSquarePosToBoardIndex("a2"), Util.convertSquarePosToBoardIndex("a4")};
        board.doLegalMove(move1);
        System.out.println("************************************");
        board.prettyPrintBoard();
        System.out.println(board.convertBoardToFEN());
        System.out.println(board.getEnPassantTargetSquare());
        System.out.println(Util.convertBoardIndexToSquare(board.getEnPassantTargetSquare()));
        System.out.println(board.moveStack);

        int[] move2 = new int[] {Util.convertSquarePosToBoardIndex("a7"), Util.convertSquarePosToBoardIndex("a5")};
        board.doLegalMove(move2);
        System.out.println("************************************");
        board.prettyPrintBoard();
        System.out.println(board.convertBoardToFEN());

        int[] move3 = new int[] {Util.convertSquarePosToBoardIndex("a1"), Util.convertSquarePosToBoardIndex("a3")};
        board.doLegalMove(move3);
        System.out.println("************************************");
        board.prettyPrintBoard();
        System.out.println(board.convertBoardToFEN());


        board.undoLastMove();
        board.undoLastMove();
        board.undoLastMove();
        System.out.println("************************************");
        board.prettyPrintBoard();
        System.out.println(board.convertBoardToFEN());
        System.out.println(board.getEnPassantTargetSquare());
        */

        endTime = System.nanoTime();

        double time = (double) (endTime - startTime) / 1000000;

        System.out.println("EXECUTION TIME " + time);

        // TODO: 01/10/2023 ADD castling
        // TODO: 01/10/2023 ADD CHECKMATE

        /*
       Move enPassantTestMove =  new Move(36,43, 'p', 'P' , true, board.getEnPassantTargetSquare(), false);



       boolean moveWorked = board.doLegalMove(new int[] {36,43});

       board.prettyPrintBoard();
       System.out.println(board.moveStack);

       if (moveWorked){
           board.undoLastMove();
           board.prettyPrintBoard();
       }


       System.out.println(board.moveStack);
    */

    }
}
