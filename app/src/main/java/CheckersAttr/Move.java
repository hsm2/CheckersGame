package CheckersAttr;

/**
 * Created by harishmanikantan on 3/15/17.
 */
public class Move {

    private int sourceX = -1;
    private int sourceY = -1;
    private int targetX = -1;
    private int targetY = -1;

    /**
     * This constructor sets different values of a move
     * @param sourceX x coordinate of source position
     * @param sourceY y coordinate of source position
     * @param targetX x coordinate of target position
     * @param targetY y coordinate of target positon
     */
    public Move(int sourceX, int sourceY, int targetX, int targetY) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    /**
     * This method returns the x coordinate of the source position of move
     * @return
     */
    public int getSourceX() {
        return sourceX;
    }

    /**
     * This method returns the y coordinate of the source position of move
     * @return
     */
    public int getSourceY() {
        return sourceY;
    }

    /**
     * This method returns the x coordinate of the target position of move
     * @return
     */
    public int getTargetX() {
        return targetX;
    }

    /**
     * This method returns the y coordinate of the target position of move
     * @return
     */
    public int getTargetY() {
        return targetY;
    }
}
