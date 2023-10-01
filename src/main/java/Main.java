import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "8/ppp5/2k5/8/8/8/5KPP/8 w - - 0 1";
    private static final String enPassantFen = "4k3/8/8/8/3Pp3/8/8/4K3 b KQkq d3 0 1";
    private static final String testFen2 = "8/8/8/q2RK3/8/8/8/8 w - - 0 1";

    public static void main(String[] args) {


        Board board = new Board(enPassantFen);

        /*
        List<Move> moves = board.boardLegalMoves();
        //List<Move> pseudomoves = PseudoMoves.allPseudoLegalMoves(board);
        for (Move move : moves){
            System.out.println(move);
        }

         */

        System.out.println("************************************");
        board.prettyPrintBoard();

        Move enPassantTestMove =  new Move(36,43, 'p', 'P' , true, board.getEnPassantTargetSquare());

      //  List<Move> legalMoves = board.boardLegalMoves();
        //System.out.println("LEGAL MOVES" + legalMoves);
        //System.out.println(legalMoves.contains(enPassantTestMove));

        boolean moveWorked = board.doLegalMove(new int[] {36,43});

        board.prettyPrintBoard();
        System.out.println(board.moveStack);

        if (moveWorked){
            board.undoLastMove();
            board.prettyPrintBoard();
        }


        System.out.println(board.moveStack);


    }
}
