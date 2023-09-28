import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "8/8/2k5/5q2/5n2/8/5K2/8 b - - 0 1";
    private static final String enPassantFen = "rnbqkbnr/8/8/8/3Pp3/8/8/RNBQKBNR b KQkq d3 0 1";

    public static void main(String[] args) {


        Board board = new Board(enPassantFen);
        //board.createGameBoard(enPassantFen);
        System.out.println(board);

        //List<int[]> moves =  Moves.knightPseudoLegalMoves(board);
        //List<int[]> moves =  Moves.slidingPiecePseudoLegalMoves(board, 'b','b');
        //List<int[]> moves =  Moves.allSlidingPiecePseudoLegalMoves(board);
        //List<int[]> moves =  Moves.kingPseudoLegalMoves(board);
        //List<int[]> moves = Moves.pawnPseudoLegalMoves(board);

        List<int[]> moves= Moves.allPseudoLegalMoves(board);

        for (int[] move : moves){
            System.out.println(Arrays.toString(move));
        }

        board.prettyPrintBoard();

        System.out.println("***********************************");
        System.out.println(board.convertBoardToFEN());
    }
}
