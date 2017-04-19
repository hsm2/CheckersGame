package CheckersAttr;

/**
 * Created by harishmanikantan on 3/15/17.
 */
public class Move {

    private int sourceX = -1;
    private int sourceY = -1;
    private int targetX = -1;
    private int targetY = -1;


    public Move(int sourceX, int sourceY, int targetX, int targetY) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public int getSourceX() {
        return sourceX;
    }

    public int getSourceY() {
        return sourceY;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }
}
