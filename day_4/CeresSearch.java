import java.io.*;
import java.util.*;

public class CeresSearch {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<String> gridList = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            gridList.add(line.trim());
        }
        reader.close();

        if (gridList.isEmpty()) {
            System.out.println("0");
            return;
        }

        int numRows = gridList.size();
        int numCols = gridList.get(0).length();
        char[][] grid = new char[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String row = gridList.get(i);
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = row.charAt(j);
            }
        }

        int[][] diagonals = {
            {-1, -1}, 
            {-1, +1}, 
            {+1, -1}, 
            {+1, +1}  
        };

        int xmasCount = 0;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (grid[row][col] == 'A') {
                    boolean xmasFound = true;

                    boolean nw_se_match = false;
                    if (isValidPosition(row - 1, col - 1, numRows, numCols) && isValidPosition(row + 1, col + 1, numRows, numCols)) {
                        char nw = grid[row - 1][col - 1];
                        char se = grid[row + 1][col + 1];
                        if ((nw == 'M' && se == 'S') || (nw == 'S' && se == 'M')) {
                            nw_se_match = true;
                        }
                    }

                    boolean ne_sw_match = false;
                    if (isValidPosition(row - 1, col + 1, numRows, numCols) && isValidPosition(row + 1, col - 1, numRows, numCols)) {
                        char ne = grid[row - 1][col + 1];
                        char sw = grid[row + 1][col - 1];
                        if ((ne == 'M' && sw == 'S') || (ne == 'S' && sw == 'M')) {
                            ne_sw_match = true;
                        }
                    }

                    if (nw_se_match && ne_sw_match) {
                        xmasCount++;
                    }
                }
            }
        }

        System.out.println(xmasCount);
    }

    private static boolean isValidPosition(int row, int col, int numRows, int numCols) {
        return (row >= 0 && row < numRows && col >= 0 && col < numCols);
    }
}
