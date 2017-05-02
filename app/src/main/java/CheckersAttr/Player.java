package CheckersAttr;

import java.util.ArrayList;

/**
 * Created by harishmanikantan on 3/15/17.
 */
/*public class Player {

    private int playerType;

    public Player(int playerType) {
        this.playerType = playerType;
    }

    public int getPlayerType() {
        return playerType;
    }

    public Move doMove(Board board, String pieceToMove) {
        return null;
    }

    public String[] getMovablePositions(Board board) {


        return new String[1];
    }

    protected ArrayList<Move> getJumpMove(Board board, String positionToMove) {
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

    protected ArrayList<ArrayList<Move>> getJump(Board board) {
        //System.out.println("GetJump1");
        ArrayList<Move> temp = null;
        ArrayList<ArrayList<Move>> moves = new ArrayList<>();

        for (int i = 0; i < Board.DIMENSION; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
                if (getPlayerType() == Checkers.RED && (board.getPosition(i, j).equals(Board.RED_PIECE_NORMAL)
                        || board.getPosition(i, j).equals(Board.RED_PIECE_KING))) {
                    temp = getJumpMove(board, i + "," + j);
                    //System.out.println("Get jump move 2");
                    if (!temp.isEmpty()) {
                        moves.add(temp);
                    }
                    else {
                        //System.out.println("Null");
                    }
                }
                else if (getPlayerType() == Checkers.BLACK && (board.getPosition(i, j).equals(board.BLACK_PIECE_NORMAL)
                        || board.getPosition(i, j).equals(board.BLACK_PIECE_KING))) {
                    temp = getJumpMove(board, i + "," + j);
                    //System.out.println("Get jump move 2");
                    if (!temp.isEmpty()) {
                        moves.add(temp);
                    }
                    else {
                        //System.out.println("Null");
                    }
                }
            }
        }
        //System.out.println("Here");
        return moves;
    }
}
*/