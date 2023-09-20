

public class Board {

    private static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String testFen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2 ";

    //want to convert any fen position into board representation

    private static final char[] squares = new char[64];

    private char sideToMove;

    private boolean whiteKingCastle;
    private boolean whiteQueenCastle;
    private boolean blackKingCastle;
    private boolean blackQueenCastle;

    private static void populateBoard(String fen){

        String[] fenParts = fen.split(" ");

        int boardIndex = 0;

        for (int i = 0; i < fenParts[0].length(); i++){
            char currentChar = fenParts[0].charAt(i);

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

    public static void main(String[] args) {


        populateBoard(startingFen);
        System.out.println(squares);

        System.out.println(squares[20] == 0);
    }
}
