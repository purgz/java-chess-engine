public class Move {

    private int startSquare;
    private int endSquare;
    private char piece;
    private char capturedPiece;
    private boolean isEnPassant;
    private int enPassantTargetSquare;
    boolean isDoublePawnMove;
    boolean isKingCastle;
    boolean isQueenCastle;

    public boolean isDoublePawnMove() {
        return isDoublePawnMove;
    }

    public boolean isKingCastle() {
        return isKingCastle;
    }

    public void setKingCastle(boolean kingCastle) {
        isKingCastle = kingCastle;
    }

    public boolean isQueenCastle() {
        return isQueenCastle;
    }

    public void setQueenCastle(boolean queenCastle) {
        isQueenCastle = queenCastle;
    }

    public void setDoublePawnMove(boolean doublePawnMove) {
        isDoublePawnMove = doublePawnMove;
    }

    public Move(int startSquare, int endSquare, char piece, char capturedPiece, boolean isEnPassant,
                int enPassantTargetSquare, boolean isDoublePawnMove, boolean isKingCastle, boolean isQueenCastle){
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.isEnPassant = isEnPassant;
        this.enPassantTargetSquare = enPassantTargetSquare;
        this.isDoublePawnMove = isDoublePawnMove;
        this.isKingCastle = isKingCastle;
        this.isQueenCastle = isQueenCastle;
    }

    public int getEnPassantTargetSquare() {
        return enPassantTargetSquare;
    }

    public void setEnPassantTargetSquare(int enPassantTargetSquare) {
        this.enPassantTargetSquare = enPassantTargetSquare;
    }

    @Override
    public boolean equals(Object obj){

        //allows a user to pass either a full move object or a int[] move to the do move method.
        if (obj instanceof int[]){

            int[] move = (int[]) obj;
            if (move[0] == startSquare && move[1] == endSquare){
                return true;
            } else {
                return false;
            }
        }
        Move move = (Move) obj;
        if (startSquare == move.getStartSquare() &&
                endSquare == move.getEndSquare() &&
                piece == move.getPiece() &&
                capturedPiece == move.getCapturedPiece() &&
                isEnPassant == move.isEnPassant() &&
                enPassantTargetSquare == move.enPassantTargetSquare &&
                isDoublePawnMove == move.isDoublePawnMove &&
                isKingCastle == move.isKingCastle &&
                isQueenCastle == move.isQueenCastle){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Move{" +
                "startSquare=" + startSquare +
                ", endSquare=" + endSquare +
                ", piece=" + piece +
                ", capturedPiece=" + capturedPiece +
                ", isEnPassant=" + isEnPassant +
                ", enPassantTargetSquare=" + enPassantTargetSquare +
                ", isDoublePawnMove=" + isDoublePawnMove +
                ", isKingCastle=" + isKingCastle +
                ", isQueenCastle=" + isQueenCastle +
                '}';
    }

    public int getStartSquare() {
        return startSquare;
    }

    public void setStartSquare(int startSquare) {
        this.startSquare = startSquare;
    }

    public int getEndSquare() {
        return endSquare;
    }

    public void setEndSquare(int endSquare) {
        this.endSquare = endSquare;
    }

    public char getPiece() {
        return piece;
    }

    public void setPiece(char piece) {
        this.piece = piece;
    }

    public char getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(char capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public void setEnPassant(boolean enPassant) {
        isEnPassant = enPassant;
    }
}
