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

        char[][] grid = new char[rows][cols];
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        int guardX = -1, guardY = -1;
        int direction = -1;

        for (int r = 0; r < rows; r++) {
            String row = lines.get(r);
            for (int c = 0; c < cols; c++) {
                char ch = row.charAt(c);
                if (ch == '^') {
                    guardX = c;
                    guardY = r;
                    direction = 0;
                    grid[r][c] = '.';
                } else if (ch == '>') {
                    guardX = c;
                    guardY = r;
                    direction = 1;
                    grid[r][c] = '.';
                } else if (ch == 'v') {
                    guardX = c;
                    guardY = r;
                    direction = 2;
                    grid[r][c] = '.';
                } else if (ch == '<') {
                    guardX = c;
                    guardY = r;
                    direction = 3;
                    grid[r][c] = '.';
                } else {
                    grid[r][c] = ch;
                }
            }
        }

        if (guardX == -1 || guardY == -1 || direction == -1) {
            System.out.println(0);
            return;
        }

        boolean[][] visited = new boolean[rows][cols];
        int visitedCount = 0;

        visited[guardY][guardX] = true;
        visitedCount++;

        while (true) {
            int nx = guardX + dx[direction];
            int ny = guardY + dy[direction];
            boolean inBounds = (nx >= 0 && nx < cols && ny >= 0 && ny < rows);
            boolean isObstacle = inBounds && grid[ny][nx] == '#';

            if (isObstacle) {
                direction = (direction + 1) % 4;
            } else {
                guardX = nx;
                guardY = ny;

                if (guardX < 0 || guardX >= cols || guardY < 0 || guardY >= rows) {
                    break;
                }

                if (!visited[guardY][guardX]) {
                    visited[guardY][guardX] = true;
                    visitedCount++;
                }
            }
        }

        System.out.println(visitedCount);
    }
}
