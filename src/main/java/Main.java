import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "8/8/2k5/5q2/8/8/5K2/8 w - - 0 1";
    private static final String enPassantFen = "rnbqkbnr/8/8/8/3Pp3/8/8/RNBQKBNR b KQkq d3 0 1";
    private static final String testFen2 = "8/8/8/q2RK3/8/8/8/8 w - - 0 1";

    public static void main(String[] args) {


        Board board = new Board(testFen2);
        //board.createGameBoard(enPassantFen);
        System.out.println(board);





        List<int[]> moves = board.boardLegalMoves();

        for (int[] move : moves){
            System.out.println(Arrays.toString(move));
        }

        System.out.println("************************************");
        board.prettyPrintBoard();
    }
}
