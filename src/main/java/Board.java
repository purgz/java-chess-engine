import java.util.Arrays;

public class Board {

    private String boardStartingFen;

    private final char[] squares = new char[64];

    private char sideToMove;

    private boolean whiteKingCastle;
    private boolean whiteQueenCastle;
    private boolean blackKingCastle;
    private boolean blackQueenCastle;

    //need to check fen for en passant target square
    private int enPassantTargetSquare;

    //no args constructor if wanting to manually call the methods to set board
    public Board(){};

    public String getBoardStartingFen() {
        return boardStartingFen;
    }

    public void setBoardStartingFen(String boardStartingFen) {
        this.boardStartingFen = boardStartingFen;
    }

    public char[] getSquares() {
        return squares;
    }

    public char getSideToMove() {
        return sideToMove;
    }

    public void setSideToMove(char sideToMove) {
        this.sideToMove = sideToMove;
    }

    public boolean isWhiteKingCastle() {
        return whiteKingCastle;
    }

    public void setWhiteKingCastle(boolean whiteKingCastle) {
        this.whiteKingCastle = whiteKingCastle;
    }

    public boolean isWhiteQueenCastle() {
        return whiteQueenCastle;
    }

    public void setWhiteQueenCastle(boolean whiteQueenCastle) {
        this.whiteQueenCastle = whiteQueenCastle;
    }

    public boolean isBlackKingCastle() {
        return blackKingCastle;
    }

    public void setBlackKingCastle(boolean blackKingCastle) {
        this.blackKingCastle = blackKingCastle;
    }

    public boolean isBlackQueenCastle() {
        return blackQueenCastle;
    }

    public void setBlackQueenCastle(boolean blackQueenCastle) {
        this.blackQueenCastle = blackQueenCastle;
    }

    public int getEnPassantTargetSquare() {
        return enPassantTargetSquare;
    }

    public void setEnPassantTargetSquare(int enPassantTargetSquare) {
        this.enPassantTargetSquare = enPassantTargetSquare;
    }

    //can be used to initialize a board with a given fen
    public Board(String fen) {

        this.boardStartingFen = fen;
        createGameBoard(fen);
    }

    private void createGameBoard(String fen){

        String[] fenParts = fen.split(" ");

        populateBoard(fenParts[0]);
        sideToMove = fenParts[1].charAt(0);
        setCastlingRights(fenParts[2]);
        setEnpassantTargetSquare(fenParts[3]);
    }

    @Override
    public String toString() {
        return "Board{" +
                "boardStartingFen='" + boardStartingFen + '\'' +
                ", squares=" + Arrays.toString(squares) +
                ", sideToMove=" + sideToMove +
                ", whiteKingCastle=" + whiteKingCastle +
                ", whiteQueenCastle=" + whiteQueenCastle +
                ", blackKingCastle=" + blackKingCastle +
                ", blackQueenCastle=" + blackQueenCastle +
                ", enPassantTargetSquare=" + enPassantTargetSquare +
                '}';
    }

    private void setEnpassantTargetSquare(String targetSquare){

        if (targetSquare.equals("-")){
            enPassantTargetSquare = -1;
        } else {
            enPassantTargetSquare = Util.convertSquarePosToBoardIndex(targetSquare);
        }
    }

    private void setCastlingRights(String castlingRights) {

        if (castlingRights.equals("-")){
            // '-' means neither side has castling rights
            whiteKingCastle = false;
            whiteQueenCastle = false;
            blackKingCastle = false;
            blackQueenCastle = false;
        } else {

            for (int i = 0; i < castlingRights.length(); i++){

                char currentChar = castlingRights.charAt(i);

                switch(currentChar)
                {
                    case 'K':
                        whiteKingCastle = true;
                        break;
                    case 'Q':
                        whiteQueenCastle = true;
                        break;
                    case 'k':
                        blackKingCastle = true;
                        break;
                    case 'q':
                        blackQueenCastle = true;
                        break;
                    default:
                        break;
                }
            }
        }


    }

    private void populateBoard(String pieces){

        int boardIndex = 0;

        for (int i = 0; i < pieces.length(); i++){

            char currentChar = pieces.charAt(i);

            if (Character.isDigit(currentChar)){
                boardIndex += currentChar - '0';
            } else {
                if (currentChar != '/'){
                    //System.out.println(boardIndex);
                    squares[boardIndex] = currentChar;
                    boardIndex++;
                }
            }
        }
    }

}
