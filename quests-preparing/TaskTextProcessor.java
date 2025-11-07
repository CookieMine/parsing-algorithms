import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskTextProcessor {
    public static void main(String[] args) {
        // Замените "X:/pve.txt" на путь к вашему файлу
        String inputFileName = "X:/pve.txt";
        String outputFileName = "X:/pve_processed.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));

            int conditions = 0; // если 0, то это ещё первая часть
            String line;
            while ((line = reader.readLine()) != null) {
                if(conditions == 0) { // первая часть, название
                    // Если задание разбито по частям, заменяем "N2" и добавляем часть номер и общее количество частей
                    Matcher matcher = Pattern.compile(" N(\\d+)").matcher(line);
                    if (matcher.find()) {
                        int partNumber = Integer.parseInt(matcher.group(1));
                        line = line.replace(matcher.group(), ". Часть №" + partNumber + " из ?.");
                    }

                    // Заменяем цифры на знаки вопроса
                    String modifiedLine = line.replaceAll("\\d", "?");

                    // Если в конце названия нет знака препинания, добавляем точку
                    if (!line.matches(".*[.!?]$")) {
                        modifiedLine += ".";
                    }

                    writer.write(checkAndReplaceQuestions(modifiedLine));
                    writer.newLine();

                    conditions = 1;
                }else {
                    // вторая часть, здесь только суть задания
                    // Если в конце названия нет знака препинания, добавляем точку
                    if (!line.matches(".*[.!?]$")) {
                        line += ".";
                    }

                    writer.write(checkAndReplaceQuestions(line));
                    writer.newLine();

                    conditions = 0;
                }
            }

            reader.close();
            writer.close();

            System.out.println("Обработка завершена. Результат сохранен в " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String checkAndReplaceQuestions(String input) {
        if (input.length() < 4) {
            return input;
        }

        String firstFourChars = input.substring(0, 4);

        int questionCount = 0;
        for (int i = 0; i < firstFourChars.length(); i++) {
            if (firstFourChars.charAt(i) == '?') {
                questionCount++;
            }
        }

        if (questionCount > 1) {
            firstFourChars = firstFourChars.replaceAll("\\?+", "?");
        }

        return firstFourChars + input.substring(4);
    }
}
