import java.io.*;
import java.util.*;

public class RedNosedReports {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int safeReportsCountPart1 = 0;
        int safeReportsCountPart2 = 0;
        String line;

        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            String[] parts = line.trim().split("\\s+");
            List<Integer> levels = new ArrayList<>();
            for (String part : parts) {
                try {
                    levels.add(Integer.parseInt(part));
                } catch (NumberFormatException e) {
                }
            }

            boolean isSafePart1 = isReportSafe(levels);
            if (isSafePart1) {
                safeReportsCountPart1++;
                safeReportsCountPart2++;
            } else {
                if (canBeMadeSafeByRemovingOneLevel(levels)) {
                    safeReportsCountPart2++;
                }
            }
        }

        System.out.println("Safe Reports (Part One): " + safeReportsCountPart1);
        System.out.println("Safe Reports (Part Two): " + safeReportsCountPart2);
    }

    private static boolean isReportSafe(List<Integer> levels) {
        if (levels.size() < 2) {
            return false;
        }

        int firstDiff = levels.get(1) - levels.get(0);

        if (firstDiff == 0) {
            return false;
        }

        boolean isIncreasing = firstDiff > 0;
        boolean isDecreasing = firstDiff < 0;

        for (int i = 0; i < levels.size() - 1; i++) {
            int currentLevel = levels.get(i);
            int nextLevel = levels.get(i + 1);
            int diff = nextLevel - currentLevel;
            int absDiff = Math.abs(diff);

            if (absDiff < 1 || absDiff > 3) {
                return false;
            }

            if (diff == 0) {
                return false;
            }

            if (isIncreasing && diff <= 0) {
                return false;
            }
            if (isDecreasing && diff >= 0) {
                return false;
            }
        }

        return true;
    }

    private static boolean canBeMadeSafeByRemovingOneLevel(List<Integer> levels) {
        for (int i = 0; i < levels.size(); i++) {
            List<Integer> modifiedLevels = new ArrayList<>(levels);
            modifiedLevels.remove(i);

            if (isReportSafe(modifiedLevels)) {
                return true;
            }
        }
        return false;
    }
}
