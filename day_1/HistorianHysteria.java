import java.io.*;
import java.util.*;

public class HistorianHysteria {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length >= 2) {
                left.add(Integer.parseInt(parts[0]));
                right.add(Integer.parseInt(parts[1]));
            }
        }

        int totalDistance = calculateTotalDistance(left, right);
        System.out.println("Total Distance (Part One): " + totalDistance);

        int similarityScore = calculateSimilarityScore(left, right);
        System.out.println("Similarity Score (Part Two): " + similarityScore);
    }

    private static int calculateTotalDistance(List<Integer> left, List<Integer> right) {
        List<Integer> sortedLeft = new ArrayList<>(left);
        List<Integer> sortedRight = new ArrayList<>(right);

        Collections.sort(sortedLeft);
        Collections.sort(sortedRight);

        int totalDistance = 0;
        for (int i = 0; i < sortedLeft.size(); i++) {
            totalDistance += Math.abs(sortedLeft.get(i) - sortedRight.get(i));
        }

        return totalDistance;
    }

    private static int calculateSimilarityScore(List<Integer> left, List<Integer> right) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : right) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        int similarityScore = 0;
        for (int num : left) {
            int frequency = frequencyMap.getOrDefault(num, 0);
            similarityScore += num * frequency;
        }

        return similarityScore;
    }
}