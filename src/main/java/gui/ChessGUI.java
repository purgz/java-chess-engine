package gui;

import engine.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.net.URL;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ChessGUI {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private final JLabel message = new JLabel(
            "Chess Champ is ready to play!");
    private static final String COLS = "ABCDEFGH";
    public static final int KING = 0, QUEEN = 1,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = {
            ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK
    };
    public static final int BLACK = 0, WHITE = 1;

    private int startSquare = -1;
    private int endSquare = -1;
    private Board board;

    ChessGUI() {
        initializeGui();
    }

    public final void initializeGui() {
        // create the images for the chess pieces
        createImages();

        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        Action newGameAction = new AbstractAction("New") {

            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewGame();
            }
        };
        tools.add(newGameAction);
        tools.add(new JButton("Save")); // TODO - add functionality!
        tools.add(new JButton("Restore")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(new JButton("Resign")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(message);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);

        chessBoard = new JPanel(new GridLayout(0, 9)) {

            /**
             * Override the preferred size to return the largest it can, in
             * a square shape.  Must (must, must) be added to a GridBagLayout
             * as the only component (it uses the parent as a guide to size)
             * with no GridBagConstaint (so it is centered).
             */
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int)d.getWidth(),(int)d.getHeight());
                } else if (c!=null &&
                        c.getWidth()>d.getWidth() &&
                        c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w>h ? h : w);
                return new Dimension(s,s);
            }
        };
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8,8,8,8),
                new LineBorder(Color.BLACK)
        ));
        // Set the BG to be ochre
        Color ochre = new Color(204,119,34);
        chessBoard.setBackground(ochre);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(ochre);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        // create the chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }

                final int index = ii * 8 + jj;
                b.setName(Integer.toString(index));
                chessBoardSquares[jj][ii] = b;

                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JButton target = (JButton) e.getSource();

                        System.out.println(startSquare + " square " + endSquare);

                        if (startSquare == -1){
                            startSquare = Integer.parseInt(b.getName());
                        } else {
                            if (Integer.parseInt(b.getName()) != startSquare){
                                endSquare = Integer.parseInt(b.getName());
                            }
                        }

                        if (startSquare != -1 && endSquare != -1){
                            int[] move = {startSquare, endSquare};

                            if (board.doLegalMove(move)){
                                //if the board move is accepted then render the new updated board
                                renderBoard();
                            }

                            //System.out.println(board.convertBoardToFEN());
                            //board.prettyPrintBoard();
                            startSquare = -1;
                            endSquare = -1;
                        }

                    }
                });
            }
        }

        /*
         * fill the chess board
         */
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (9-(ii + 1)),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }

    private void renderBoard(){
        for (int i = 0; i < board.getSquares().length; i ++) {
            int currentCol = i % 8;
            int currentRow = i / 8;

            switch (board.getSquares()[i]) {
                case 0:
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(
                            new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)));
                    break;
                case 'P':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[WHITE][PAWN]));
                    break;
                case 'N':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[WHITE][KNIGHT]));
                    break;
                case 'B':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[WHITE][BISHOP]));
                    break;
                case 'K':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[WHITE][KING]));
                    break;
                case 'Q':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[WHITE][QUEEN]));
                    break;
                case 'R':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[WHITE][ROOK]));
                    break;
                case 'p':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[BLACK][PAWN]));
                    break;
                case 'n':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[BLACK][KNIGHT]));
                    break;
                case 'b':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[BLACK][BISHOP]));
                    break;
                case 'k':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[BLACK][KING]));
                    break;
                case 'q':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[BLACK][QUEEN]));
                    break;
                case 'r':
                    chessBoardSquares[currentCol][currentRow].setIcon(new ImageIcon(chessPieceImages[BLACK][ROOK]));
                    break;
            }
        }
    }

    public final JComponent getGui() {
        return gui;
    }

    private final void createImages() {
        try {
            URL url = new URL("https://i.stack.imgur.com/memI0.png");
            BufferedImage bi = ImageIO.read(url);
            for (int ii = 0; ii < 2; ii++) {
                for (int jj = 0; jj < 6; jj++) {
                    chessPieceImages[ii][jj] = bi.getSubimage(
                            jj * 64, ii * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Initializes the icons of the initial chess board piece places
     */
    private final void setupNewGame() {
        message.setText("Make your move!");
        board = new Board();
        // set up the black pieces
        renderBoard();
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                ChessGUI cg = new ChessGUI();

                JFrame f = new JFrame("ChessChamp");
                f.add(cg.getGui());
                // Ensures JVM closes after frame(s) closed and
                // all non-daemon threads are finished
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // See https://stackoverflow.com/a/7143398/418556 for demo.
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}
