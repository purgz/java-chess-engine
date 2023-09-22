import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2 ";
    private static final String enPassantFen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq c6 0 1";

    public static void main(String[] args) {


        Board board = new Board(startingFen);
        //board.createGameBoard(enPassantFen);
        System.out.println(board);

        List<int[]> moves =  Moves.knightPseudoLegalMoves(board);

        for (int[] move : moves){
            System.out.println(Arrays.toString(move));
        }
    }
}
