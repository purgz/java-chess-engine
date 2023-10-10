import engine.Board;
import engine.Move;
import engine.PseudoMoves;
import engine.Util;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

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
    @DisplayName("Castling should work correctly")
    public void testCastling(){

        String fen = "r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1";
        String fen2 = "r3k2r/8/8/8/8/8/8/R3K2R b KQkq - 0 1";
        String fen3 = "r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1";
        String fen4 = "r3k2r/8/8/8/8/7B/8/R3K2R b KQkq - 0 1";

        Board whiteCastleBoard = new Board(fen);
        Board blackCastleBoard = new Board(fen2);
        Board blackCastleBoard2 = new Board(fen4);

        Move whiteKingCastle = new Move (60, 62, 'K', (char) 0, false, whiteCastleBoard.getEnPassantTargetSquare(), false, true, false);
        Move whiteQueenCastle = new Move(60, 58, 'K', (char) 0, false, whiteCastleBoard.getEnPassantTargetSquare(), false, false, true);

        Move blackKingCastle = new Move (4, 6, 'k', (char) 0, false, blackCastleBoard.getEnPassantTargetSquare(), false, true, false);
        Move blackQueenCastle = new Move (4, 2, 'k', (char) 0, false, blackCastleBoard.getEnPassantTargetSquare(), false, false, true);

        List<Move> castlingPseudoMoves = PseudoMoves.castlingPseudoLegalMoves(whiteCastleBoard);
        List<Move> expected = new ArrayList<>();
        expected.add(whiteKingCastle);
        expected.add(whiteQueenCastle);

        List<Move> blackCastlingPseudoMoves = PseudoMoves.castlingPseudoLegalMoves(blackCastleBoard);
        List<Move> expected2 = new ArrayList<>();
        expected2.add(blackKingCastle);
        expected2.add(blackQueenCastle);

        //should have a piece blocking the queen side castle
        List<Move> blackCastlingPseudoMoves2 = PseudoMoves.castlingPseudoLegalMoves(blackCastleBoard2);
        System.out.println(blackCastlingPseudoMoves2);
        List<Move> expected3 = new ArrayList<>();
        expected3.add(blackKingCastle);

        Assert.assertArrayEquals("Testing if white king castling pseudo legal moves are being found correctly", expected.toArray(), castlingPseudoMoves.toArray());
        Assert.assertArrayEquals("Testing if black king castling pseudo legal moves are being found correctly", expected2.toArray(), blackCastlingPseudoMoves.toArray());
        Assert.assertArrayEquals("Testing if a piece blocking the queen side castle", expected3.toArray(), blackCastlingPseudoMoves2.toArray());



    }


}
