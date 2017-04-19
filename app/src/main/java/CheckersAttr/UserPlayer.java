package CheckersAttr;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by harishmanikantan on 3/15/17.
 */
public class UserPlayer extends Player implements PlayerStrategy {

    public UserPlayer(int playerType) {
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
                System.out.println("Choose target");
                for (int i = 0; i < moves.size(); i++) {
                    System.out.println((i + 1) + ". " + moves.get(i).getTargetX() + "," + moves.get(i).getTargetY());
                }
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextInt()) {
                    int option = scanner.nextInt() - 1;
                    if (option >= 0 && option < moves.size()) {
                        return moves.get(option);
                    }
                    System.out.println("Invalid choice \nChoose target");
                }
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
                else {

                }
            }
            else if (moves.size() > 1) {
                System.out.println("Choose piece");
                for (int i = 0; i < moves.size(); i++) {
                    System.out.println((i + 1) + ". " + moves.get(i).get(0).getSourceX() + "," + moves.get(i).get(0).getSourceY());
                }
                Scanner scanner = new Scanner(System.in);
                int option = -1;
                while (scanner.hasNextInt()) {
                    option = scanner.nextInt() - 1;
                    if (option >= 0 && option < moves.size()) {
                        break;
                    }
                    System.out.println("Invalid choice \nChoose piece");
                }

                if (moves.get(option).size() == 1) {
                    return moves.get(option).get(0);
                }
                else {
                    System.out.println("Choose target");
                    for (int i = 0; i < moves.get(option).size(); i++) {
                        System.out.println((i + 1) + ". " + moves.get(option).get(i).getTargetX() + "," + moves.get(option).get(i).getTargetY());
                    }
                    int option2 = -1;
                    while (scanner.hasNextInt()) {
                        option2 = scanner.nextInt() - 1;
                        if (option2 >= 0 && option2 < moves.get(option).size()) {
                            return moves.get(option).get(option2);
                        }
                        System.out.println("Invalid choice \nChoose target");
                    }
                }
            }
        }

        Scanner scanner = new Scanner(System.in);

        boolean validCurrentPosition = false;
        String current = "";

        if (positionToMove.equals(board.INVALID_POSITION)) {
            //System.out.println("Get current");
            while (!validCurrentPosition) {
                System.out.println("Enter position of piece you want to move x,y");
                current = scanner.next();
                validCurrentPosition = isValidInput(current);
            }
        }
        else {
            current = positionToMove;
        }

        boolean validTargetPosition = false;
        String target = "";

        while (!validTargetPosition) {
            System.out.println("Enter position of board you want to move it to x,y");
            target = scanner.next();
            validTargetPosition = isValidInput(target);
        }

        int currentX = Integer.parseInt(current.substring(0, 1));
        int currentY = Integer.parseInt(current.substring(2));

        int targetX = Integer.parseInt(target.substring(0, 1));
        int targetY = Integer.parseInt(target.substring(2));

        move = new Move(currentX, currentY, targetX, targetY);

        return move;
    }

    private boolean isValidInput(String current) {
        current = current.trim();
        if (current.length() != 3)
            return false;
        if (!Character.isDigit(current.charAt(0)))
            return false;
        if (current.charAt(1) != ',')
            return false;
        if (!Character.isDigit(current.charAt(2)))
            return false;
        return true;
    }

}
