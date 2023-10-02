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


}
