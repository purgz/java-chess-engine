import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String startingFen = "rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2 ";
    private static final String enPassantFen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq c6 0 1";

    public static void main(String[] args) {


        Board board = new Board(startingFen);
        //board.createGameBoard(enPassantFen);
        System.out.println(board);

      //  List<int[]> moves =  Moves.knightPseudoLegalMoves(board);

        //List<int[]> moves =  Moves.slidingPiecePseudoLegalMoves(board, 'b','b');
        // TODO: 25/09/2023 this methods need proper testing but it should work  
        List<int[]> moves =  Moves.allSlidingPiecePseudoLegalMoves(board);

        for (int[] move : moves){
            System.out.println(Arrays.toString(move));
        }

        board.prettyPrintBoard();
    }
}
