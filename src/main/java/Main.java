import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "8/8/2k5/5q2/8/8/5K2/8 w - - 0 1";
    private static final String enPassantFen = "4k3/8/8/8/3Pp3/8/8/4K3 b KQkq d3 0 1";
    private static final String testFen2 = "8/8/8/q2RK3/8/8/8/8 w - - 0 1";

    public static void main(String[] args) {


        Board board = new Board(enPassantFen);
        //board.createGameBoard(enPassantFen);
        System.out.println(board);


        //List<Move> moves = board.boardLegalMoves();

        List<Move> moves = PseudoMoves.allPseudoLegalMoves(board);
        for (Move move : moves){
            System.out.println(move);
        }


        System.out.println("************************************");
        board.prettyPrintBoard();

        Move enPassantTestMove =  new Move(36,43, 'p', 'P' , true);
        board.doMove(enPassantTestMove);
        board.prettyPrintBoard();

        board.undoMove(enPassantTestMove);
        board.prettyPrintBoard();

       // board.doMove(new int[]{36,43});
        //board.prettyPrintBoard();
    }
}
