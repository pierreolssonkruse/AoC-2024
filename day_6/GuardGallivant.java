import java.io.*;
import java.util.*;

public class GuardGallivant {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            lines.add(line);
        }

        int rows = lines.size();
        if (rows == 0) {
            System.out.println(0);
            return;
        }
        int cols = lines.get(0).length();
        char[][] originalGrid = new char[rows][cols];
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        int guardX = -1, guardY = -1;
        int initialDirection = -1;

        for (int r = 0; r < rows; r++) {
            String currentRow = lines.get(r);
            for (int c = 0; c < cols; c++) {
                char ch = currentRow.charAt(c);
                if (ch == '^') {
                    guardX = c;
                    guardY = r;
                    initialDirection = 0;
                    originalGrid[r][c] = '.';
                } else if (ch == '>') {
                    guardX = c;
                    guardY = r;
                    initialDirection = 1;
                    originalGrid[r][c] = '.';
                } else if (ch == 'v') {
                    guardX = c;
                    guardY = r;
                    initialDirection = 2;
                    originalGrid[r][c] = '.';
                } else if (ch == '<') {
                    guardX = c;
                    guardY = r;
                    initialDirection = 3;
                    originalGrid[r][c] = '.';
                } else {
                    originalGrid[r][c] = ch;
                }
            }
        }

        if (guardX == -1 || guardY == -1 || initialDirection == -1) {
            System.out.println(0);
            return;
        }

        List<int[]> candidatePositions = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (originalGrid[r][c] != '#' && !(c == guardX && r == guardY)) {
                    candidatePositions.add(new int[]{c, r});
                }
            }
        }

        int validObstructionCount = 0;

        for (int[] pos : candidatePositions) {
            int obsX = pos[0];
            int obsY = pos[1];
            char[][] gridWithObstruction = new char[rows][cols];
            for (int r = 0; r < rows; r++) {
                gridWithObstruction[r] = Arrays.copyOf(originalGrid[r], cols);
            }
            gridWithObstruction[obsY][obsX] = '#';

            if (isGuardStuckInLoop(gridWithObstruction, guardX, guardY, initialDirection, dx, dy, rows, cols)) {
                validObstructionCount++;
            }
        }

        System.out.println(validObstructionCount);
    }

    private static boolean isGuardStuckInLoop(char[][] grid, int startX, int startY, int startDir,
                                              int[] dx, int[] dy, int rows, int cols) {
        int guardX = startX;
        int guardY = startY;
        int direction = startDir;
        int totalStates = rows * cols * 4;
        boolean[] visitedStates = new boolean[totalStates];

        while (true) {
            if (guardX < 0 || guardX >= cols || guardY < 0 || guardY >= rows) {
                return false;
            }
            int state = guardY * cols * 4 + guardX * 4 + direction;
            if (visitedStates[state]) {
                return true;
            }
            visitedStates[state] = true;

            int nextX = guardX + dx[direction];
            int nextY = guardY + dy[direction];
            boolean inBounds = (nextX >= 0 && nextX < cols && nextY >= 0 && nextY < rows);
            boolean isObstacle = inBounds && grid[nextY][nextX] == '#';

            if (isObstacle) {
                direction = (direction + 1) % 4;
            } else {
                guardX = nextX;
                guardY = nextY;
            }
        }
    }
}
