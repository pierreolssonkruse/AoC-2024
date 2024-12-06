import java.io.*;
import java.util.*;

public class PrintQueue {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<String> inputLines = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            inputLines.add(line.trim());
        }
        reader.close();

        List<String> rulesList = new ArrayList<>();
        List<String> updatesList = new ArrayList<>();
        boolean isRulesSection = true;

        for (String inputLine : inputLines) {
            if (inputLine.isEmpty()) {
                isRulesSection = false;
                continue;
            }

            if (isRulesSection) {
                rulesList.add(inputLine);
            } else {
                updatesList.add(inputLine);
            }
        }

        List<int[]> orderingRules = new ArrayList<>();
        for (String rule : rulesList) {
            String[] parts = rule.split("\\|");
            if (parts.length == 2) {
                try {
                    int X = Integer.parseInt(parts[0].trim());
                    int Y = Integer.parseInt(parts[1].trim());
                    orderingRules.add(new int[]{X, Y});
                } catch (NumberFormatException e) {
                    System.err.println("Invalid rule format: " + rule);
                }
            } else {
                System.err.println("Invalid rule format: " + rule);
            }
        }

        int totalMiddleSum = 0;

        for (String update : updatesList) {
            if (update.isEmpty()) {
                continue;
            }

            String[] pagesStr = update.split(",");
            List<Integer> pages = new ArrayList<>();
            Map<Integer, Integer> pagePositions = new HashMap<>();

            for (int i = 0; i < pagesStr.length; i++) {
                try {
                    int page = Integer.parseInt(pagesStr[i].trim());
                    pages.add(page);
                    pagePositions.put(page, i);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid page number in update: " + pagesStr[i]);
                }
            }

            boolean isCorrectOrder = true;

            for (int[] rule : orderingRules) {
                int X = rule[0];
                int Y = rule[1];

                if (pagePositions.containsKey(X) && pagePositions.containsKey(Y)) {
                    int posX = pagePositions.get(X);
                    int posY = pagePositions.get(Y);
                    if (posX >= posY) {
                        isCorrectOrder = false;
                        break;
                    }
                }
            }

            if (isCorrectOrder && !pages.isEmpty()) {
                int middleIndex = pages.size() / 2;
                int middlePage = pages.get(middleIndex);
                totalMiddleSum += middlePage;
            }
        }

        System.out.println(totalMiddleSum);
    }
}
