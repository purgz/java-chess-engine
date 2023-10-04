import com.sun.source.tree.AssertTree;
import org.junit.*;
public class MoveTest {

    private static Board startBoard;

    @Before
    public void setupDefaultBoard(){

        startBoard = new Board(); //no fen string just uses starting position fen
    }

    @Test
    public void TestConvertBoardToFEN(){
        //creating board with default fen and then generating a fen from the board object
        Assert.assertEquals(Board.startingFen, startBoard.convertBoardToFEN());

        int[] move = {Util.convertSquarePosToBoardIndex("a2"),Util.convertSquarePosToBoardIndex("a4")};
        startBoard.doLegalMove(move);
        String fen = "rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR b KQkq a3 0 1";
        Assert.assertEquals(startBoard.convertBoardToFEN(), fen);
    }

    @Test
    public void TestCheckMate(){

        final String checkMateInOneFen = "k7/8/8/8/8/8/rr6/7K b - - 0 1";
        Board board = new Board(checkMateInOneFen);

        Assert.assertFalse(board.checkMate());

        boolean move = board.doLegalMove(new int[] {Util.convertSquarePosToBoardIndex("b2"), Util.convertSquarePosToBoardIndex("b1")});

        Assert.assertTrue(board.checkMate());
    }

    @Test
    public void TestUndoMove(){

        //do 4 moves and undo 4 then check we have the starting fen - this verifies that the undo is properly restoring previous positions

        int[] move1 = Util.convertMoveToBoardIndex("e2e4");
        int[] move2 = Util.convertMoveToBoardIndex("c7c5");

        int[] move3 = Util.convertMoveToBoardIndex("g1f3");
        int[] move4 = Util.convertMoveToBoardIndex("d7d6");
        startBoard.doLegalMove(move1);
        startBoard.doLegalMove(move2);
        startBoard.doLegalMove(move3);
        startBoard.doLegalMove(move4);
        startBoard.prettyPrintBoard();

        startBoard.undoLastMove();
        startBoard.undoLastMove();
        startBoard.undoLastMove();
        startBoard.undoLastMove();

        Assert.assertEquals(startBoard.convertBoardToFEN(), Board.startingFen);

    }

    @Test
    public void testCastling(){

        String fen = "r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1";
        Board castleBoard = new Board(fen);

        System.out.println(PseudoMoves.castlingPseudoLegalMoves(castleBoard));

    }


}
