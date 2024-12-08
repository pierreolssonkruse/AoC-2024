import java.io.*;
import java.util.*;

public class BridgeRepair {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
            lines.add(line.trim());
        }

        long totalCalibrationResult = 0;

        for (String eq : lines) {
            String[] parts = eq.split(":");
            if (parts.length != 2) {
                continue;
            }

            long testValue;
            try {
                testValue = Long.parseLong(parts[0].trim());
            } catch (NumberFormatException e) {
                continue;
            }

            String[] numStrs = parts[1].trim().split("\\s+");
            List<Long> numbers = new ArrayList<>();
            boolean validNumbers = true;
            for (String numStr : numStrs) {
                try {
                    numbers.add(Long.parseLong(numStr));
                } catch (NumberFormatException e) {
                    validNumbers = false;
                    break;
                }
            }
            if (!validNumbers || numbers.isEmpty()) {
                continue;
            }

            Set<Long> currentValues = new HashSet<>();
            currentValues.add(numbers.get(0));

            for (int i = 1; i < numbers.size(); i++) {
                long nextNum = numbers.get(i);
                Set<Long> nextValues = new HashSet<>();

                for (long val : currentValues) {
                    nextValues.add(val + nextNum);
                    nextValues.add(val * nextNum);
                }

                currentValues = nextValues;
            }

            if (currentValues.contains(testValue)) {
                totalCalibrationResult += testValue;
            }
        }

        System.out.println(totalCalibrationResult);
    }
}
