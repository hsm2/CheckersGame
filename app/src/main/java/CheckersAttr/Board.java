package CheckersAttr;

/**
 * Created by harishmanikantan on 3/13/17.
 */
public class Board {

    public static final int DIMENSION = 8;
    public static final int MAXIMUM_PIECES_OF_PLAYER = 12;
    private String[][] boardElements = new String[DIMENSION][DIMENSION];

    public static final String RED_PIECE_NORMAL = "\033[31m" + "O";
    public static final String BLACK_PIECE_NORMAL = "\033[30m" + "O";
    public static final String RED_PIECE_KING = "\033[31m" + "K";
    public static final String BLACK_PIECE_KING = "\033[30m" + "K";
    public static final String FREE_POSITION = " ";
    public static final String INVALID_POSITION = "9,9";

    public static String redSide = "";
    public static String blackSide = "";

    private int blackPiecesLeft = MAXIMUM_PIECES_OF_PLAYER;
    private int redPiecesLeft = MAXIMUM_PIECES_OF_PLAYER;

    /**
     * This constructor calls the create board function
     */
    public Board() {
        createBoard();
    }

    /**
     * This is a copy constructor
     * @param copyBoard The board to copy
     */
    public Board(Board copyBoard) {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                boardElements[i][j] = copyBoard.boardElements[i][j];
            }
        }

        blackPiecesLeft = copyBoard.blackPiecesLeft;
        redPiecesLeft = copyBoard.redPiecesLeft;
    }

    /**
     * This method creates the board
     */
    private void createBoard() {

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if ((i + j) % 2 == 0 && i < (DIMENSION / 2) - 1) {
                    boardElements[i][j] = RED_PIECE_NORMAL;
                }
                else if ((i + j) % 2 == 0 && i > (DIMENSION / 2)) {
                    boardElements[i][j] = BLACK_PIECE_NORMAL;
                }
                else {
                    boardElements[i][j] = FREE_POSITION;
                }
            }
        }

    }

    /**
     * This method returns the piece at a position
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @return
     */
    public String getPosition(int x, int y) {
        if (x < 0 || x >= DIMENSION || y < 0 || y >= DIMENSION)
            return INVALID_POSITION;

        return boardElements[x][y];
    }

    /**
     * This method removes the piece from a particular position
     * @param x x coordinate of position
     * @param y y coordinate of position
     */
    public void removePiece(int x, int y) {

        if (boardElements[x][y].equals(RED_PIECE_NORMAL) || boardElements[x][y].equals(RED_PIECE_KING)) {
            redPiecesLeft -= 1;
        }
        else {
            blackPiecesLeft -= 1;
        }

        boardElements[x][y] = FREE_POSITION;
    }

    /**
     * This method sets a particular position to a piece
     * @param piece piece to be set
     * @param x x coordinate of position
     * @param y y coordinate of position
     */
    public void setPiece(String piece, int x, int y) {
        boardElements[x][y] = piece;
    }

    /**
     * This method makes a move on the board
     * @param move move to make
     */
    public void movePiece(Move move) {

        int currentX = move.getSourceX();
        int currentY = move.getSourceY();
        int targetX = move.getTargetX();
        int targetY = move.getTargetY();

        boardElements[targetX][targetY] = boardElements[currentX][currentY];
        boardElements[currentX][currentY] = FREE_POSITION;
    }

    /**
     * This method returns the number of black pieces left on the board
     * @return number of black pieces left
     */
    public int getBlackPiecesLeft() {
        return blackPiecesLeft;
    }

    /**
     * This method returns the number of red pieces left on the board
     * @return number of red pieces left
     */
    public int getRedPiecesLeft() {
        return redPiecesLeft;
    }
}
