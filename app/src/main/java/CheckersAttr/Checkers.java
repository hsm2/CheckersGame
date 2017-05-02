package CheckersAttr;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by harishmanikantan on 3/13/17.
 */
public class Checkers {

    public Board board;

    public static String PLAYER_RED_NAME = "Player Red";
    public static String PLAYER_BLACK_NAME = "Player Black";
    public static final int RED = 0;
    public static final int BLACK = 1;

    private String winner = "";

    private int currentPlayer = BLACK;

    private String positionToMove = board.INVALID_POSITION;

    /**
     * This constructor creates a new board
     */
    public Checkers() {

        board = new Board();
        board.redSide = PLAYER_RED_NAME;
        board.blackSide = PLAYER_BLACK_NAME;

    }

    /**
     * This method plays a move on the board
     * @param move
     */
    public void play(Move move) {
        boolean pieceWon = false;
        do {
            if (pieceWon) {
                ArrayList<Move> moves = getJump(board, positionToMove);
                if (!moves.isEmpty())
                    move = moves.get(0);
                else
                    move = null;
            }
            updateBoard(move);
            pieceWon = pieceWonOnMove(move);
            updateCurrentPlayer(pieceWon);
        }
        while (pieceWon);
    }

    /**
     * This method checks if a piece was won on the move
     * @param move
     * @return true if piece was won, else false
     */
    private boolean pieceWonOnMove(Move move) {
        if (move == null || Math.abs(move.getTargetY() - move.getSourceY()) != 2) {
            positionToMove = Board.INVALID_POSITION;
            return false;
        }
        positionToMove = move.getTargetX() + "," + move.getTargetY();
        return true;
    }

    /**
     * This method updates the current player based on if a piece was won
     * @param pieceWon
     */
    private void updateCurrentPlayer(boolean pieceWon) {
        if (pieceWon) {
            return;
        }

        if (currentPlayer == RED) {
            currentPlayer = BLACK;
        }
        else {
            currentPlayer = RED;
        }
    }

    /**
     * This method updates the board on a move
     * @param move
     */
    public void updateBoard(Move move) {
        if (move == null)
            return;

        board.movePiece(move);
        if (Math.abs(move.getTargetY() - move.getSourceY()) == 2) {
            board.removePiece((move.getSourceX() + move.getTargetX()) / 2, (move.getTargetY() + move.getSourceY()) / 2);
        }
        if (currentPlayer == RED && move.getTargetX() == board.DIMENSION - 1) {
            board.setPiece(board.RED_PIECE_KING, move.getTargetX(), move.getTargetY());
        }
        else if (currentPlayer == BLACK && move.getTargetX() == 0) {
            board.setPiece(board.BLACK_PIECE_KING, move.getTargetX(), move.getTargetY());
        }
    }

    /**
     * This method checks if a move is valid
     * @param move
     * @return true if move is valid, else false
     */
    public boolean isValidMove(Move move) {

        if (move.getSourceX() >= board.DIMENSION || move.getSourceX() < 0 || move.getSourceY() >= board.DIMENSION || move.getSourceY() < 0)
            return false;

        int sourceX = move.getSourceX();
        int sourceY = move.getSourceY();
        int targetX = move.getTargetX();
        int targetY = move.getTargetY();

        String sourcePiece = board.getPosition(sourceX, sourceY);
        String targetPiece = board.getPosition(targetX, targetY);

        if ((sourcePiece.equals(board.RED_PIECE_NORMAL) || sourcePiece.equals(board.RED_PIECE_KING)) && currentPlayer == RED) {
            if (sourcePiece.equals(board.RED_PIECE_NORMAL)) {
                if (targetX - sourceX == 1 && Math.abs(targetY - sourceY) == 1) {
                    if (targetPiece.equals(board.FREE_POSITION)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (targetX - sourceX == 2 && Math.abs(targetY - sourceY) == 2) {
                    if (board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.BLACK_PIECE_NORMAL)
                            || board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.BLACK_PIECE_KING)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            else {
                if (Math.abs(targetX - sourceX) == 1 && Math.abs(targetY - sourceY) == 1) {
                    if (targetPiece.equals(board.FREE_POSITION)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (Math.abs(targetX - sourceX) == 2 && Math.abs(targetY - sourceY) == 2) {
                    if (board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.BLACK_PIECE_NORMAL)
                            || board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.BLACK_PIECE_KING)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        if ((sourcePiece.equals(board.BLACK_PIECE_NORMAL) || sourcePiece.equals(board.BLACK_PIECE_KING)) && currentPlayer == BLACK) {
            if (sourcePiece.equals(board.BLACK_PIECE_NORMAL)) {
                if (targetX - sourceX == -1 && Math.abs(targetY - sourceY) == 1) {
                    if (targetPiece.equals(board.FREE_POSITION)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (targetX - sourceX == -2 && Math.abs(targetY - sourceY) == 2) {
                    if (board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.RED_PIECE_NORMAL)
                            || board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.RED_PIECE_KING)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            else {
                if (Math.abs(targetX - sourceX) == 1 && Math.abs(targetY - sourceY) == 1) {
                    if (targetPiece.equals(board.FREE_POSITION)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (Math.abs(targetX - sourceX) == 2 && Math.abs(targetY - sourceY) == 2) {
                    if (board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.RED_PIECE_NORMAL)
                            || board.getPosition((targetX + sourceX) / 2, (targetY + sourceY) / 2).equals(board.RED_PIECE_KING)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        else {
            return false;
        }

    }

    /**
     * This method checks if the game is on
     * @return true if game is on, else false
     */
    public boolean isGameOn() {

        if (board.getRedPiecesLeft() == 0) {
            winner = PLAYER_BLACK_NAME;
            return false;
        }
        else if (board.getBlackPiecesLeft() == 0) {
            winner = PLAYER_RED_NAME;
            return false;
        }

        return true;
    }

    /**
     * This method checks if the piece selected is valid
     * @param targetX x coordinate of position
     * @param targetY y coordinate of position
     * @return true if valid position, else false
     */
    public boolean isValidPieceSelection(int targetX, int targetY) {
        String position = board.getPosition(targetX, targetY);

        if (currentPlayer == Checkers.BLACK
                && (position.equals(Board.BLACK_PIECE_NORMAL) || position.equals(Board.BLACK_PIECE_KING)))
            return true;

        if (currentPlayer == Checkers.RED
                && (position.equals(Board.RED_PIECE_NORMAL) || position.equals(Board.RED_PIECE_KING)))
            return true;

        return false;
    }

    /**
     * This method returns an arraylist of possible jumps from a particular position on the board
     * @param board board of the game
     * @param positionToMove position to move
     * @return arraylist of jump moves
     */
    protected ArrayList<Move> getJump(Board board, String positionToMove) {
        ArrayList<Move> moves = new ArrayList<>();

        int currentX = Integer.parseInt(positionToMove.substring(0, 1));
        int currentY = Integer.parseInt(positionToMove.substring(2));

        String piece = board.getPosition(currentX, currentY);

        //System.out.println("Get jump move 1");

        if (piece.equals(Board.RED_PIECE_NORMAL) || piece.equals(Board.RED_PIECE_KING)) {
            if (board.getPosition(currentX + 1, currentY + 1).equals(Board.BLACK_PIECE_NORMAL)
                    || board.getPosition(currentX + 1, currentY + 1).equals(Board.BLACK_PIECE_KING)) {
                if (board.getPosition(currentX + 2, currentY + 2).equals(Board.FREE_POSITION)) {
                    moves.add(new Move(currentX, currentY, currentX + 2, currentY + 2));
                }
            }
            else if (board.getPosition(currentX + 1, currentY - 1).equals(Board.BLACK_PIECE_NORMAL)
                    || board.getPosition(currentX + 1, currentY - 1).equals(Board.BLACK_PIECE_KING)) {
                if (board.getPosition(currentX + 2, currentY - 2).equals(Board.FREE_POSITION)) {
                    moves.add(new Move(currentX, currentY, currentX + 2, currentY - 2));
                }
            }
        }
        if (piece.equals(Board.RED_PIECE_KING)) {
            if (board.getPosition(currentX - 1, currentY - 1).equals(Board.BLACK_PIECE_NORMAL)
                    || board.getPosition(currentX - 1, currentY - 1).equals(Board.BLACK_PIECE_KING)) {
                if (board.getPosition(currentX - 2, currentY - 2).equals(Board.FREE_POSITION)) {
                    moves.add(new Move(currentX, currentY, currentX - 2, currentY - 2));
                }
            }
            else if (board.getPosition(currentX - 1, currentY + 1).equals(Board.BLACK_PIECE_NORMAL)
                    || board.getPosition(currentX - 1, currentY + 1).equals(Board.BLACK_PIECE_KING)) {
                if (board.getPosition(currentX - 2, currentY + 2).equals(Board.FREE_POSITION)) {
                    moves.add(new Move(currentX, currentY, currentX - 2, currentY + 2));
                }
            }
        }
        if (piece.equals(Board.BLACK_PIECE_NORMAL) || piece.equals(Board.BLACK_PIECE_KING)) {
            if (board.getPosition(currentX - 1, currentY - 1).equals(Board.RED_PIECE_NORMAL)
                    || board.getPosition(currentX - 1, currentY - 1).equals(Board.RED_PIECE_KING)) {
                if (board.getPosition(currentX - 2, currentY - 2).equals(Board.FREE_POSITION)) {
                    moves.add(new Move(currentX, currentY, currentX - 2, currentY - 2));
                }
            }
            else if (board.getPosition(currentX - 1, currentY + 1).equals(Board.RED_PIECE_NORMAL)
                    || board.getPosition(currentX - 1, currentY + 1).equals(Board.RED_PIECE_KING)) {
                if (board.getPosition(currentX - 2, currentY + 2).equals(Board.FREE_POSITION)) {
                    moves.add(new Move(currentX, currentY, currentX - 2, currentY + 2));
                }
            }
        }
        if (piece.equals(Board.BLACK_PIECE_KING)) {
            if (board.getPosition(currentX + 1, currentY + 1).equals(Board.RED_PIECE_NORMAL)
                    || board.getPosition(currentX + 1, currentY + 1).equals(Board.RED_PIECE_KING)) {
                if (board.getPosition(currentX + 2, currentY + 2).equals(Board.FREE_POSITION)) {
                    moves.add(new Move(currentX, currentY, currentX + 2, currentY + 2));
                }
            }
            else if (board.getPosition(currentX + 1, currentY - 1).equals(Board.RED_PIECE_NORMAL)
                    || board.getPosition(currentX + 1, currentY - 1).equals(Board.RED_PIECE_KING)) {
                if (board.getPosition(currentX + 2, currentY - 2).equals(Board.FREE_POSITION)) {
                    moves.add(new Move(currentX, currentY, currentX + 2, currentY - 2));
                }
            }
        }

        return moves;
    }

    /**
     * This method returns the winner of the game
     * @return
     */
    public String getWinner() {
        return winner;
    }
}
