import java.io.*;
import java.util.regex.*;

public class MullItOver {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder memory = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            memory.append(line);
        }

        String input = memory.toString();

        Pattern pattern = Pattern.compile(
            "mul\\s*\\(\\s*([0-9]{1,3})\\s*,\\s*([0-9]{1,3})\\s*\\)" +
            "|do\\(\\)" +
            "|don't\\(\\)" +
            "|undo\\(\\)"
        );

        Matcher matcher = pattern.matcher(input);

        boolean isMulEnabled = true;
        int total = 0;

        while (matcher.find()) {
            if (matcher.group(1) != null && matcher.group(2) != null) {
                if (isMulEnabled) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    total += x * y;
                }
            } else {
                String instruction = matcher.group();
                if (instruction.equals("do()") || instruction.equals("undo()")) {
                    isMulEnabled = true;
                } else if (instruction.equals("don't()")) {
                    isMulEnabled = false;
                }
            }
        }

        System.out.println(total);
    }
}
