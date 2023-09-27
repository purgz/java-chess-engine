import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/2r5/1P6/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "8/8/3p4/4P3/8/8/8/8 b KQkq - 0 1";
    private static final String enPassantFen = "rnbqkbnr/8/8/8/3Pp3/8/8/RNBQKBNR b KQkq d3 0 1";

    public static void main(String[] args) {


        Board board = new Board(enPassantFen);
        //board.createGameBoard(enPassantFen);
        System.out.println(board);

        //List<int[]> moves =  Moves.knightPseudoLegalMoves(board);

        //List<int[]> moves =  Moves.slidingPiecePseudoLegalMoves(board, 'b','b');
        // TODO: 25/09/2023 this methods need proper testing but it should work  
       // List<int[]> moves =  Moves.allSlidingPiecePseudoLegalMoves(board);

        //List<int[]> moves =  Moves.kingPseudoLegalMoves(board);

        List<int[]> moves = Moves.pawnPseudoLegalMoves(board);
        // TODO: 27/09/2023 method to get all the pseudo legal moves by combining all these methods 

        for (int[] move : moves){
            System.out.println(Arrays.toString(move));
        }

        board.prettyPrintBoard();
    }
}
