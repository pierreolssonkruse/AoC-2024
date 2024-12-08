import java.io.*;
import java.util.*;

public class ResonantCollinearity {

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point p = (Point) o;
            return this.x == p.x && this.y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
            lines.add(line.trim());
        }

        if (lines.isEmpty()) {
            System.out.println(0);
            return;
        }

        int rows = lines.size();
        int cols = lines.get(0).length();
        Map<Character, List<Point>> freqToPoints = new HashMap<>();

        for (int y = 0; y < rows; y++) {
            String currentRow = lines.get(y);
            for (int x = 0; x < cols; x++) {
                char ch = currentRow.charAt(x);
                if (ch != '.') {
                    freqToPoints.computeIfAbsent(ch, k -> new ArrayList<>()).add(new Point(x, y));
                }
            }
        }

        Set<Point> antinodeSet = new HashSet<>();

        for (Map.Entry<Character, List<Point>> entry : freqToPoints.entrySet()) {
            char freq = entry.getKey();
            List<Point> antennas = entry.getValue();

            if (antennas.size() < 2) {
                continue;
            }

            int n = antennas.size();

            for (int i = 0; i < n; i++) {
                Point A = antennas.get(i);
                for (int j = i + 1; j < n; j++) {
                    Point B = antennas.get(j);

                    int antinode1_x = 2 * B.x - A.x;
                    int antinode1_y = 2 * B.y - A.y;

                    if (isWithinGrid(antinode1_x, antinode1_y, cols, rows)) {
                        antinodeSet.add(new Point(antinode1_x, antinode1_y));
                    }

                    int antinode2_x = 2 * A.x - B.x;
                    int antinode2_y = 2 * A.y - B.y;

                    if (isWithinGrid(antinode2_x, antinode2_y, cols, rows)) {
                        antinodeSet.add(new Point(antinode2_x, antinode2_y));
                    }
                }
            }
        }

        System.out.println(antinodeSet.size());
    }

    private static boolean isWithinGrid(int x, int y, int cols, int rows) {
        return x >= 0 && x < cols && y >= 0 && y < rows;
    }
}
