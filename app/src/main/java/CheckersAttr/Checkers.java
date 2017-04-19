package CheckersAttr;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by harishmanikantan on 3/13/17.
 */
public class Checkers {

    public Board board;

    private String PLAYER_RED_NAME = "Player Red";
    private String PLAYER_BLACK_NAME = "Player Black";
    public static final int RED = 0;
    public static final int BLACK = 1;

    private String winner = "";

    private Player redPlayer;
    private Player blackPlayer;

    private int currentPlayer = BLACK;

    private String positionToMove = board.INVALID_POSITION;

    public Checkers() {

        board = new Board();
        board.redSide = PLAYER_RED_NAME;
        board.blackSide = PLAYER_BLACK_NAME;

    }

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

    private boolean pieceWonOnMove(Move move) {
        if (move == null || Math.abs(move.getTargetY() - move.getSourceY()) != 2) {
            positionToMove = Board.INVALID_POSITION;
            return false;
        }
        positionToMove = move.getTargetX() + "," + move.getTargetY();
        return true;
    }

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

    private Move getNextMove(String pieceToMove) {
        Move move = null;

        do {
            if (currentPlayer == RED) {
                move = redPlayer.doMove(board, pieceToMove);
            }
            else {
                //System.out.println("Black");
                move = blackPlayer.doMove(board, pieceToMove);
                //System.out.println(move.getSourceX() + "," + move.getSourceY());
                //System.out.println(move.getTargetX() + "," + move.getTargetY());
            }
        }
        while (pieceToMove.equals(Board.INVALID_POSITION) && !isValidMove(move));

        return move;
    }

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

    public boolean isGameOn() {
        if (board.getRedPiecesLeft() == 0 || board.getBlackPiecesLeft() == 0) {
            return false;
        }

        if (board.getRedPiecesLeft() == 0) {
            winner = PLAYER_BLACK_NAME;
        }
        else {
            winner = PLAYER_RED_NAME;
        }

        return true;
    }

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

    public String getWinner() {
        return winner;
    }
}
