package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Board {

    public static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private String boardStartingFen;

    private final char[] squares = new char[64];

    private char sideToMove;

    private boolean whiteKingCastle;
    private boolean whiteQueenCastle;
    private boolean blackKingCastle;
    private boolean blackQueenCastle;

    //need to check fen for en passant target square
    private int enPassantTargetSquare;

    private int initialEnPassantSquare;
    private boolean[] initialCastlingRights;

    public Stack<Move> moveStack = new Stack<>();

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

    public Board(){
        this.boardStartingFen = startingFen;
        createGameBoard(startingFen);
    }

    private void createGameBoard(String fen){

        String[] fenParts = fen.split(" ");

        populateBoard(fenParts[0]);
        sideToMove = fenParts[1].charAt(0);
        setCastlingRights(fenParts[2]);
        setEnpassantTargetSquare(fenParts[3]);
        this.initialEnPassantSquare = enPassantTargetSquare;
        this.initialCastlingRights = new boolean[] {whiteKingCastle, whiteQueenCastle, blackKingCastle, blackQueenCastle};
    }

    public String convertBoardToFEN(){

        StringBuilder boardFen = new StringBuilder();

        int emptySquareCount = 0;
        for (int i = 0; i < squares.length; i++){

            //add / for each rank
            if (i % 8 == 0 && i != 0){
                if (emptySquareCount != 0){
                    boardFen.append(emptySquareCount);
                }
                boardFen.append("/");

                emptySquareCount = 0;
            }

            if (squares[i] != 0){

                if (emptySquareCount != 0){
                    boardFen.append(emptySquareCount);
                }

                emptySquareCount = 0;

                boardFen.append(squares[i]);
            } else {

                emptySquareCount++;
            }
        }

        //add side to move to fen
        boardFen.append(" ").append(sideToMove);

        //castling rights format whiteKingchar,whitequeenchar,blackkingchar,blackqueenchar
        String castlingRights = "";
        if (whiteKingCastle){
            castlingRights += 'K';
        }
        if (whiteQueenCastle) {
            castlingRights += 'Q';
        }
        if (blackKingCastle) {
            castlingRights += 'k';
        }
        if (blackQueenCastle) {
            castlingRights += 'q';
        }
        if (castlingRights.equals("")){
            castlingRights = "-";
        }
        boardFen.append(" ").append(castlingRights);

        //en passant must be converted into board cell
        if (enPassantTargetSquare == -1){
            boardFen.append(" -");
        } else {
            String enPassantSquare = Util.convertBoardIndexToSquare(enPassantTargetSquare);
            boardFen.append(" ").append(enPassantSquare);
        }

        //I don't really know what this last part of the fen string means, I will implement it, at some point, maybe....
        boardFen.append(" 0 1");

        return boardFen.toString();
    }

    @Override
    public String toString() {
        return "engine.Board{" +
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

    public void setCastlingRights(String castlingRights) {

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

    public void prettyPrintBoard(){

        StringBuilder board = new StringBuilder();
        for (int i = 0; i < squares.length; i ++){

            if (i % 8 == 0){
                board.append("\n");
                board.append(9 - (i / 8 + 1));
                board.append(" | ");
            }

            if (squares[i] == 0){
                board.append(" ");
            } else{
                board.append(squares[i]);
            }
            board.append(" | ");
        }

        board.append("\n    a   b   c   d   e   f   g   h  ");
        System.out.println(board);
    }

    public boolean isCurrentPlayerInCheck(){

        List<Move> opponentPseudoLegalMoves = PseudoMoves.opponentPseudoLegalMoves(this);

        char kingChar = sideToMove == 'w' ? 'K' : 'k';

        List<Integer> kingPos = PseudoMoves.findPieceSquares(this, kingChar);

        boolean inCheck = false;
        for (Move opMove : opponentPseudoLegalMoves){

            if (opMove.getEndSquare() == kingPos.get(0)){
                inCheck = true;
            }
        }
        return inCheck;
    }

    //this just move piece ignoring all else - iterate through all pseudo moves to check
    public boolean doMove(Move move){

        int startSquare = move.getStartSquare();
        int endSquare = move.getEndSquare();


        int dir = sideToMove == 'w' ? 1 : -1;
        if (move.isEnPassant()){

            squares[endSquare] = squares[startSquare];
            squares[startSquare] = 0;
            squares[endSquare + dir * 8] = 0;
        } else if (move.isQueenCastle()){
            squares[endSquare] = squares[startSquare];
            squares[startSquare] = 0;
            squares[startSquare - 1] = squares[startSquare - 4];
            squares[startSquare - 4] = 0;
        } else if (move.isKingCastle()){
            squares[endSquare] = squares[startSquare];
            squares[startSquare] = 0;
            squares[startSquare + 1] = squares[startSquare + 3];
            squares[startSquare + 3] = 0;
        } else {
            squares[endSquare] = squares[startSquare];
            squares[startSquare] = 0;
        }

        if (move.isDoublePawnMove()){
            enPassantTargetSquare = startSquare - dir * 8;
            move.setEnPassantTargetSquare(enPassantTargetSquare);
        } else {
            enPassantTargetSquare = -1;
            move.setEnPassantTargetSquare(enPassantTargetSquare);
        }

        boolean[] castlingRights = new boolean[] {whiteKingCastle, whiteQueenCastle, blackKingCastle, blackQueenCastle};

        if (move.getPiece() == 'K'){
            castlingRights[0] = false;
            castlingRights[1] = false;
            move.setCastlingRights(castlingRights);
        } else if (move.getPiece() == 'k'){
            castlingRights[2] = false;
            castlingRights[3] = false;
        } else if (move.getPiece() == 'R'){
            if (startSquare == 63){
                castlingRights[0] = false;
            } else if (startSquare == 56){
                castlingRights[1] = false;
            }
        } else if (move.getPiece() == 'r'){
            if (startSquare == 7){
                castlingRights[2] = false;
            } else if (startSquare == 0){
                castlingRights[3] = false;
            }
        }

        //should deal with the rooks being captures before a castle move is made
        if (move.getCapturedPiece() == 'r'){
            if (move.getEndSquare() == 7) {
                castlingRights[2] = false;
            } else if (move.getEndSquare() == 0){
                castlingRights[3] = false;
            }
        } else if (move.getCapturedPiece() == 'R'){
            if (move.getEndSquare() == 63){
                castlingRights[0] = false;
            } else if (move.getEndSquare() == 56){
                castlingRights[1] = false;
            }
        }

        move.setCastlingRights(castlingRights);

        whiteKingCastle = castlingRights[0];
        whiteQueenCastle = castlingRights[1];
        blackKingCastle = castlingRights[2];
        blackQueenCastle = castlingRights[3];

        moveStack.push(move);
        sideToMove = sideToMove == 'w' ? 'b' : 'w';
        return false;
    }


    public boolean doLegalMove(int[] move){

        List<Move> legalMoves = boardLegalMoves();

        for (Move legalMove : legalMoves){

            if (legalMove.equals(move)){
                doMove(legalMove);

                if (legalMove.getPiece() == 'P' && legalMove.getEndSquare() < 8 && legalMove.getEndSquare() > -1){
                    squares[legalMove.getEndSquare()] = 'Q';
                } else if (legalMove.getPiece() == 'p' && legalMove.getEndSquare() < 64 && legalMove.getEndSquare() > 55){
                    squares[legalMove.getEndSquare()] = 'q';
                }

                return true;
            }

        }

        return false;
    }


    public boolean undoMove(Move move){

        if (move.isEnPassant()){
            int dir = sideToMove == 'w' ? 1 : -1;
            squares[move.getStartSquare()] = move.getPiece();
            squares[move.getEndSquare()] = 0;
            squares[move.getEndSquare() - dir * 8] = move.getCapturedPiece();

        } else if (move.isKingCastle()){
            squares[move.getStartSquare()] = move.getPiece();
            squares[move.getEndSquare()] = 0;
            squares[move.getStartSquare() + 3] = squares[move.getStartSquare() + 1];
            squares[move.getStartSquare() + 1] = 0;
        } else if (move.isQueenCastle()){
            squares[move.getStartSquare()] = move.getPiece();
            squares[move.getEndSquare()] = 0;
            squares[move.getStartSquare() - 4] = squares[move.getStartSquare() - 1];
            squares[move.getStartSquare() - 1] = 0;
        } else {
            squares[move.getEndSquare()] = move.getCapturedPiece();
            squares[move.getStartSquare()] = move.getPiece();
        }

        moveStack.pop();
        if (moveStack.size() != 0){

            enPassantTargetSquare = moveStack.peek().getEnPassantTargetSquare();
            whiteKingCastle = moveStack.peek().getCastlingRights()[0];
            whiteQueenCastle = moveStack.peek().getCastlingRights()[1];
            blackKingCastle = moveStack.peek().getCastlingRights()[2];
            blackQueenCastle = moveStack.peek().getCastlingRights()[3];
        } else {

            //going to cache the starting values when the board is initialized
            enPassantTargetSquare = initialEnPassantSquare;
            whiteKingCastle = initialCastlingRights[0];
            whiteQueenCastle = initialCastlingRights[1];
            blackQueenCastle = initialCastlingRights[2];
            blackKingCastle = initialCastlingRights[3];
        }
        //alternate side
        sideToMove = sideToMove == 'w' ? 'b' : 'w';
        return false;
    }

    public void undoLastMove(){
        if (moveStack.size() != 0){
            undoMove(moveStack.peek());
        }
    }

    public List<Move> boardLegalMoves(){

        List<Move> allPseudoLegalMoves = PseudoMoves.allPseudoLegalMoves(this);

        //need to add this here to stop stack overflow ...
        List<Move> legalMoves = new ArrayList<>(PseudoMoves.castlingPseudoLegalMoves(this));

        //do and undo each move
        for (Move move : allPseudoLegalMoves){

            doMove(move);

            sideToMove = sideToMove == 'w' ? 'b' : 'w';
            if (!isCurrentPlayerInCheck()){
                if (!legalMoves.contains(move))
                    legalMoves.add(move);
            }
            sideToMove = sideToMove == 'w' ? 'b' : 'w';
            //restore position and remove move from stack
            undoMove(move);
        }

        return legalMoves;
    }

    public boolean checkMate(){
        if (boardLegalMoves().size() == 0){
            return true;
        }

        return false;
    }

    //method to convert the game into a series of fen strings for saving later
    public List<String> convertGameToFenList(){
        List<String> allPositions = new ArrayList<>();

        Board board = new Board(getBoardStartingFen());
        allPositions.add(getBoardStartingFen());

        for (int i = 0; i < moveStack.size(); i++){
            board.doMove(moveStack.get(i));

            allPositions.add(board.convertBoardToFEN());
        }

        return allPositions;
    }
}
