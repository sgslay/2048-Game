package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

public class GameLogic {

    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        int currentValue = board[r][c];
        if (currentValue == 0 || r < minR) {
            return 0;
        }
        int newRow = r;
        boolean merged = false;

        for (int i = r - 1; i >= minR; i--) {
            if (board[i][c] == 0) {
                newRow = i;
            } else if (board[i][c] == currentValue && !merged) {
                board[i][c] *= 2;
                board[r][c] = 0;
                merged = true;
                return i + 1;
            } else {
                break;
            }
        }
        if (!merged) {
            if (newRow != r) {
                board[newRow][c] = currentValue;
                board[r][c] = 0;
            }
        }
        return 0;
    }

    public static void tiltColumn(int[][] board, int c) {
        int minR = 0;
        for (int r = 0; r < board.length; r++) {
            if (board[r][c] != 0) {
                int mergeRow = moveTileUpAsFarAsPossible(board, r, c, minR);
                if (mergeRow > 0) {
                    minR = mergeRow;
                }
            }
        }
    }

    public static void tiltUp(int[][] board) {
        for (int c = 0; c < board[0].length; c++) {
            tiltColumn(board, c);
        }
    }

    public static void tilt(int[][] board, Side side) {
        switch (side) {
            case NORTH:
                break;
            case EAST:
                rotateLeft(board);
                break;
            case SOUTH:
                rotateLeft(board);
                rotateLeft(board);
                break;
            case WEST:
                rotateRight(board);
                break;
            default:
                throw new IllegalArgumentException("Invalid side");
        }

        tiltUp(board);

        switch (side) {
            case NORTH:
                break;
            case EAST:
                rotateRight(board);
                break;
            case SOUTH:
                rotateLeft(board);
                rotateLeft(board);
                break;
            case WEST:
                rotateLeft(board);
                break;
            default:
                throw new IllegalArgumentException("Invalid side");

        }
    }
}
