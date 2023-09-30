public class Move {

    private int startSquare;
    private int endSquare;
    private char piece;
    private char capturedPiece;
    private boolean isEnPassant;

    public Move(int startSquare, int endSquare, char piece, char capturedPiece, boolean isEnPassant){
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.isEnPassant = isEnPassant;
    }

    @Override
    public String toString() {
        return "Move{" +
                "startSquare=" + startSquare +
                ", endSquare=" + endSquare +
                ", piece=" + piece +
                ", capturedPiece=" + capturedPiece +
                ", isEnPassant=" + isEnPassant +
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
