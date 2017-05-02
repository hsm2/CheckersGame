package CheckersAttr;

import java.util.ArrayList;

/**
 * Created by harishmanikantan on 3/17/17.
 */
/*public class EasyPlayer extends Player implements PlayerStrategy {

    private final int NORTH_WEST = 0;
    private final int NORTH_EAST = 1;
    private final int SOUTH_EAST = 2;
    private final int SOUTH_WEST = 3;

    public EasyPlayer(int playerType) {
        super(playerType);
    }

    @Override
    public Move doMove(Board board, String positionToMove) {
        Move move = null;
        if (!positionToMove.equals(Board.INVALID_POSITION)) {
            //System.out.println("If1");
            ArrayList<Move> moves = getJumpMove(board, positionToMove);
            //System.out.println("If2");
            if (moves.size() == 1) {
                return moves.get(0);
            }
            else if (moves.size() > 1) {
                return getBestMove(moves, board);
            }
            return null;
        }
        else {
            //System.out.println("Else1");
            ArrayList<ArrayList<Move>> moves = getJump(board);
            //System.out.println("Else2");
            if (moves.size() == 1) {
                if (moves.get(0).size() == 1) {
                    return moves.get(0).get(0);
                }
            }
            else if (moves.size() > 1) {
                return getBestMove(board, moves);
            }
        }

        ArrayList<Move> possibleMoves = getPossibleMoves(board);

        Move m = getBestMove(possibleMoves, board);
        //System.out.println(m == null);
        //System.out.println(m.getSourceX());
        //System.out.println("\nSource X = " + m.getSourceX() + " Source Y = " + m.getSourceY());
        //System.out.println("Target X = " + m.getTargetX() + " Target Y = " + m.getTargetY());

        return m;
    }

    private ArrayList<Move> getPossibleMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        for (int i = 0; i < Board.DIMENSION; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
                if (getPlayerType() == Checkers.BLACK &&
                        (board.getPosition(i, j).equals(Board.BLACK_PIECE_KING)
                        || board.getPosition(i, j).equals(Board.BLACK_PIECE_NORMAL))) {
                    moves.addAll(getPossibleMoves(board, i, j));
                }
            }
        }

        return moves;
    }

    private ArrayList<Move> getPossibleMoves(Board board, int i, int j) {
        ArrayList<Move> moves = new ArrayList<>();

        if (board.getPosition(i + 1, j + 1).equals(Board.FREE_POSITION)
                && getPlayerType() == Checkers.RED) {
            moves.add(new Move(i, j, i + 1, j + 1));
        }
        if (board.getPosition(i + 1, j + 1).equals(Board.FREE_POSITION)
                && board.getPosition(i, j).equals(Board.BLACK_PIECE_KING)) {
            moves.add(new Move(i, j, i + 1, j + 1));
        }
        if (board.getPosition(i + 1, j - 1).equals(Board.FREE_POSITION)
                && getPlayerType() == Checkers.RED) {
            moves.add(new Move(i, j, i + 1, j - 1));
        }
        if (board.getPosition(i + 1, j - 1).equals(Board.FREE_POSITION)
                && board.getPosition(i, j).equals(Board.BLACK_PIECE_KING)) {
            moves.add(new Move(i, j, i + 1, j - 1));
        }
        if (board.getPosition(i - 1, j + 1).equals(Board.FREE_POSITION)
                && getPlayerType() == Checkers.BLACK) {
            moves.add(new Move(i, j, i - 1, j + 1));
        }
        if (board.getPosition(i - 1, j + 1).equals(Board.FREE_POSITION)
                && board.getPosition(i, j).equals(Board.RED_PIECE_KING)) {
            moves.add(new Move(i, j, i - 1, j + 1));
        }
        if (board.getPosition(i - 1, j - 1).equals(Board.FREE_POSITION)
                && getPlayerType() == Checkers.BLACK) {
            moves.add(new Move(i, j, i - 1, j - 1));
        }
        if (board.getPosition(i - 1, j - 1).equals(Board.FREE_POSITION)
                && board.getPosition(i, j).equals(Board.RED_PIECE_KING)) {
            moves.add(new Move(i, j, i - 1, j - 1));
        }

        return moves;
    }

    private Move getBestMove(Board board, ArrayList<ArrayList<Move>> moves) {
        ArrayList<Move> allMoves = new ArrayList<>();

        for (int i = 0; i < moves.size(); i++) {
            for (int j = 0; j < moves.get(i).size(); j++) {
                allMoves.add(moves.get(i).get(j));
            }
        }

        return getBestMove(allMoves, board);
    }

    private Move getBestMove(ArrayList<Move> moves, Board board) {
        int max = Integer.MIN_VALUE;
        Move move = null;

        for (int i = 0; i < moves.size(); i++) {
            int score = getMoveScore(moves.get(i), board, 0);

            if (score > max) {
                max = score;
                move = moves.get(i);
            }
        }

        return move;
    }

    private int getMoveScore(Move move, Board board, int level) {
        int currentScore = 0;
        board = new Board(board);

        if (move == null) {
            return -1;
        }
        if (Math.abs(move.getSourceX() - move.getTargetX()) == 2 && level == 1) {
            currentScore += 3;
        }
        if (Math.abs(move.getSourceX() - move.getTargetX()) == 2 && level == 0) {
            currentScore += 5;
        }
        if (canBeJumped(board, move) && level == 0) {
            currentScore -= 5;
        }
        if (canBeJumped(board, move) && level == 1) {
            currentScore -= 3;
        }

        if (level != 0) {
            return currentScore;
        }

        board.movePiece(move);

        Board northWestBoard = new Board(board);
        Move northWestMove = buildMove(northWestBoard, move.getTargetX(), move.getTargetY(), NORTH_WEST);
        System.out.println("NorthWest is null" + (northWestMove == null));
        int northWestScore = currentScore;

        if (northWestMove != null) {
            //northWestBoard.printBoard();
            northWestBoard.movePiece(northWestMove);

            northWestScore = northWestScore + getMoveScore(northWestMove, northWestBoard, level + 1);
            if (northWestScore > currentScore && isInterferingMove(northWestBoard, northWestMove, NORTH_WEST)) {
                northWestScore = currentScore;
            }
            System.out.println("North West score " + northWestScore);
        }

        Board northEastBoard = new Board(board);
        Move northEastMove = buildMove(northEastBoard, move.getTargetX(), move.getTargetY(), NORTH_EAST);
        System.out.println("NorthEast is null" + (northEastMove == null));
        int northEastScore = currentScore;

        if (northEastMove != null) {
            northEastBoard.movePiece(northEastMove);

            northEastScore = northEastScore + getMoveScore(northEastMove, northEastBoard, level + 1);
            if (northEastScore > currentScore && isInterferingMove(northEastBoard, northEastMove, NORTH_EAST)) {
                northEastScore = currentScore;
            }
            System.out.println("North East score " + northEastScore);
        }

        Board southEastBoard = new Board(board);
        Move southEastMove = buildMove(southEastBoard, move.getTargetX(), move.getTargetY(), SOUTH_EAST);
        System.out.println("SouthEast is null" + (southEastMove == null));
        int southEastScore = currentScore;

        if (southEastMove != null) {
            southEastBoard.movePiece(southEastMove);

            southEastScore = southEastScore + getMoveScore(southEastMove, southEastBoard, level + 1);
            if (southEastScore > currentScore && isInterferingMove(southEastBoard, southEastMove, SOUTH_EAST)) {
                southEastScore = currentScore;
            }
            System.out.println("South East score " + southEastScore);
        }

        Board southWestBoard = new Board(board);
        Move southWestMove = buildMove(southWestBoard, move.getTargetX(), move.getTargetY(), SOUTH_WEST);
        System.out.println("SouthWest is null" + (southWestMove == null));
        int southWestScore = currentScore;

        if (southWestMove != null) {
            southWestBoard.movePiece(southWestMove);

            southWestScore = southWestScore + getMoveScore(southWestMove, southWestBoard, level + 1);
            if (southWestScore > currentScore && isInterferingMove(southWestBoard, southWestMove, SOUTH_WEST)) {
                southWestScore = currentScore;
            }
            System.out.println("South West score " + southWestScore);
        }

        int maxScore = Math.max(northWestScore, northEastScore);
        maxScore = Math.max(maxScore, southWestScore);
        maxScore = Math.max(maxScore, southEastScore);

        return maxScore;
    }

    private boolean canBeJumped(Board board, Move move) {
        int x = move.getTargetX();
        int y = move.getTargetY();

        String nwPosition = board.getPosition(x - 1, y - 1);
        String nePosition = board.getPosition(x - 1, y + 1);
        String swPosition = board.getPosition(x + 1, y - 1);
        String sePosition = board.getPosition(x + 1, y + 1);

        if (getPlayerType() == Checkers.RED && nwPosition.equals(Board.BLACK_PIECE_KING)) {
            return true;
        }
        if (getPlayerType() == Checkers.BLACK && (nwPosition.equals(Board.RED_PIECE_NORMAL)
                || nwPosition.equals(Board.RED_PIECE_KING))) {
            return true;
        }
        if (getPlayerType() == Checkers.RED && nePosition.equals(Board.BLACK_PIECE_KING)) {
            return true;
        }
        if (getPlayerType() == Checkers.BLACK && (nePosition.equals(Board.RED_PIECE_NORMAL)
                || nePosition.equals(Board.RED_PIECE_KING))) {
            return true;
        }
        if (getPlayerType() == Checkers.RED && (swPosition.equals(Board.BLACK_PIECE_KING)
                || swPosition.equals(Board.BLACK_PIECE_NORMAL))) {
            return true;
        }
        if (getPlayerType() == Checkers.BLACK && swPosition.equals(Board.RED_PIECE_KING)) {
            return true;
        }
        if (getPlayerType() == Checkers.RED && (sePosition.equals(Board.BLACK_PIECE_KING)
                || sePosition.equals(Board.BLACK_PIECE_NORMAL))) {
            return true;
        }
        if (getPlayerType() == Checkers.BLACK && sePosition.equals(Board.RED_PIECE_KING)) {
            return true;
        }

        return false;
    }

    private boolean isInterferingMove(Board board, Move move, int direction) {
        int x = move.getTargetX();
        int y = move.getTargetY();

        String currentPosition = board.getPosition(move.getSourceX(), move.getSourceY());

        if (direction == NORTH_EAST || direction == NORTH_WEST) {
            if (currentPosition.equals(Board.RED_PIECE_KING)
                    && (board.getPosition(x + 1, y - 1).equals(Board.BLACK_PIECE_NORMAL)
                    || board.getPosition(x + 1, y - 1).equals(Board.BLACK_PIECE_KING))) {
                return true;
            }
            if (currentPosition.equals(Board.BLACK_PIECE_NORMAL) || currentPosition.equals(Board.BLACK_PIECE_KING)
                    && board.getPosition(x + 1, y - 1).equals(Board.RED_PIECE_KING)) {
                return true;
            }
            if (currentPosition.equals(Board.RED_PIECE_KING)
                    && (board.getPosition(x + 1, y + 1).equals(Board.BLACK_PIECE_NORMAL)
                    || board.getPosition(x + 1, y + 1).equals(Board.BLACK_PIECE_KING))) {
                return true;
            }
            if (currentPosition.equals(Board.BLACK_PIECE_NORMAL) || currentPosition.equals(Board.BLACK_PIECE_KING)
                    && board.getPosition(x + 1, y + 1).equals(Board.RED_PIECE_KING)) {
                return true;
            }
            if (currentPosition.equals(Board.RED_PIECE_KING)
                    && board.getPosition(x - 1, y + 1).equals(Board.BLACK_PIECE_KING)) {
                return true;
            }
            if ((currentPosition.equals(Board.BLACK_PIECE_NORMAL) || currentPosition.equals(Board.BLACK_PIECE_KING))
                && (board.getPosition(x - 1, y + 1).equals(Board.RED_PIECE_NORMAL)
                || board.getPosition(x - 1, y + 1).equals(Board.RED_PIECE_KING))) {
                return true;
            }
            if (currentPosition.equals(Board.RED_PIECE_KING)
                    && board.getPosition(x - 1, y - 1).equals(Board.BLACK_PIECE_KING)) {
                return true;
            }
            if ((currentPosition.equals(Board.BLACK_PIECE_NORMAL) || currentPosition.equals(Board.BLACK_PIECE_KING))
                && (board.getPosition(x - 1, y - 1).equals(Board.RED_PIECE_KING)
                || board.getPosition(x - 1, y - 1).equals(Board.RED_PIECE_NORMAL))) {
                return true;
            }

            return false;
        }
        else {
            if (currentPosition.equals(Board.BLACK_PIECE_KING)
                    && (board.getPosition(x - 1, y - 1).equals(Board.RED_PIECE_KING)
                    || board.getPosition(x - 1, y - 1).equals(Board.RED_PIECE_NORMAL))) {
                return true;
            }
            if ((currentPosition.equals(Board.RED_PIECE_NORMAL) || currentPosition.equals(Board.RED_PIECE_KING))
                    && board.getPosition(x - 1, y - 1).equals(Board.BLACK_PIECE_KING)) {
                return true;
            }
            if (currentPosition.equals(Board.BLACK_PIECE_KING)
                    && (board.getPosition(x - 1, y + 1).equals(Board.RED_PIECE_KING)
                    || board.getPosition(x - 1, y + 1).equals(Board.RED_PIECE_NORMAL))) {
                return true;
            }
            if ((currentPosition.equals(Board.RED_PIECE_KING) || currentPosition.equals(Board.RED_PIECE_NORMAL))
                    && board.getPosition(x - 1, y + 1).equals(Board.BLACK_PIECE_KING)) {
                return true;
            }
            if ((currentPosition.equals(Board.RED_PIECE_KING) || currentPosition.equals(Board.RED_PIECE_NORMAL))
                    && (board.getPosition(x + 1, y + 1).equals(Board.BLACK_PIECE_KING)
                    || board.getPosition(x + 1, y + 1).equals(Board.BLACK_PIECE_NORMAL))) {
                return true;
            }
            if (currentPosition.equals(Board.BLACK_PIECE_KING)
                    && board.getPosition(x + 1, y + 1).equals(Board.RED_PIECE_KING)) {
                return true;
            }
            if ((currentPosition.equals(Board.RED_PIECE_KING) || currentPosition.equals(Board.RED_PIECE_NORMAL))
                    && (board.getPosition(x + 1, y - 1).equals(Board.BLACK_PIECE_KING)
                    || board.getPosition(x + 1, y - 1).equals(Board.BLACK_PIECE_NORMAL))) {
                return true;
            }
            if (currentPosition.equals(Board.BLACK_PIECE_KING)
                    && board.getPosition(x + 1, y - 1).equals(Board.RED_PIECE_KING)) {
                return true;
            }

            return false;
        }
    }

    private Move buildMove(Board board, int sourceX, int sourceY, int direction) {
        String piece = board.getPosition(sourceX, sourceY);

        int changeX = 0;
        int changeY = 0;

        if (direction == NORTH_WEST) {
            changeX = -1;
            changeY = -1;
        }
        else if (direction == NORTH_EAST) {
            changeX = -1;
            changeY = 1;
        }
        else if (direction == SOUTH_EAST) {
            changeX = 1;
            changeY = 1;
        }
        else {
            changeX = 1;
            changeY = -1;
        }


        if (piece.equals(Board.RED_PIECE_KING)) {
            if (board.getPosition(sourceX + changeX, sourceY + changeY).equals(Board.BLACK_PIECE_KING)
                    || board.getPosition(sourceX + changeX, sourceY + changeY).equals(Board.BLACK_PIECE_NORMAL)) {
                if (board.getPosition(sourceX + changeX * 2, sourceY + changeY * 2).equals(Board.FREE_POSITION)) {
                    return new Move(sourceX, sourceY, sourceX + changeX * 2, sourceY + changeY * 2);
                }
                else {
                    return null;
                }
            }
            if ((sourceX + changeX) >= 0 && (sourceX + changeX) < Board.DIMENSION
                    && (sourceY + changeY) >= 0 && (sourceY + changeY) < Board.DIMENSION) {
                return new Move(sourceX, sourceY, sourceX + changeX, sourceY + changeY);
            }
        }
        else if (piece.equals(Board.BLACK_PIECE_NORMAL) || piece.equals(Board.BLACK_PIECE_KING)) {
            System.out.println("SourceX = " + sourceX + " SourceY = " + sourceY);
            if (board.getPosition(sourceX + changeX, sourceY + changeY).equals(Board.RED_PIECE_NORMAL)
                    || board.getPosition(sourceX + changeX, sourceY + changeY).equals(Board.RED_PIECE_KING)) {
                if (board.getPosition(sourceX + changeX * 2, sourceY + changeY * 2).equals(Board.FREE_POSITION)) {
                    System.out.println("TargetX = " + (sourceX + changeX * 2) + " TargetY = " + (sourceY + changeY * 2));
                    return new Move(sourceX, sourceY, sourceX + changeX * 2, sourceY + changeY * 2);
                }
                else {
                    return null;
                }
            }
            if ((sourceX + changeX) >= 0 && (sourceX + changeX) < Board.DIMENSION
                    && (sourceY + changeY) >= 0 && (sourceY + changeY) < Board.DIMENSION) {
                System.out.println("TargetX = " + (sourceX + changeX) + " TargetY = " + (sourceY + changeY));
                return new Move(sourceX, sourceY, sourceX + changeX, sourceY + changeY);
            }
        }

        return null;
    }
}
*/