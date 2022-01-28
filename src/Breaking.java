import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Breaking {
    static void bruteForce() {
        Path path;
        System.out.println("Введите полный путь к файлу, затем enter:");
        path = Path.of(Encryption.SCANNER.nextLine());
        List<String> lines;
        int key = 0;
        List<String> result;
        try {
            lines = Files.readAllLines(path);
            for (int i = 1; i < 100; i++) {
                int count = 0;
                result = Encryption.decrypt(lines, i);
                for (String line : result) {
                    if (line.contains(", ") && line.contains(". ")) {
                        count++;
                    }
                }
                if (count > 2) {
                    key = i;
                    break;
                }
            }
            result = Encryption.decrypt(lines, key);
            Encryption.writeNewContentToFile(path, result);
            System.out.println("Файл создан в той же директории!");
            System.out.println("Ключ был - " + key);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<Character> sortedValues(Path pathOfEnrypted) throws IOException {
        List<String> encryptedLines = Files.readAllLines(pathOfEnrypted);
        HashMap<Character, Integer> encryptedMap = new HashMap<>();
        for (String encryptedLine : encryptedLines) {
            char[] encryptedChars = encryptedLine.toCharArray();
            for (char encryptedChar : encryptedChars) {
                if (!encryptedMap.containsKey(encryptedChar)) {
                    encryptedMap.put(encryptedChar, 1);
                } else {
                    encryptedMap.put(encryptedChar, encryptedMap.get(encryptedChar) + 1);
                }
            }
        }
        List<Map.Entry<Character, Integer>> encrList = new ArrayList<>(encryptedMap.entrySet());
        encrList.sort(Map.Entry.comparingByValue());

        List<Character> sortedList = new ArrayList<>();

        for (Map.Entry<Character, Integer> entry : encrList) {
            sortedList.add(entry.getKey());
        }
        Collections.reverse(sortedList);
        return sortedList;
    }

    static void statisticAnalize() {
        Path pathOfEnrypted;
        Path pathOfExampleText;
        System.out.println("Введите полный путь к зашифрованному файлу, затем enter:");
        pathOfEnrypted = Path.of(Encryption.SCANNER.nextLine());
        System.out.println("Введите полный путь к файлу с текстом для сравнения , затем enter:");
        pathOfExampleText = Path.of(Encryption.SCANNER.nextLine());
        try {

            List<Character> encrCharacters = sortedValues(pathOfEnrypted);
            List<Character> normCharacters = sortedValues(pathOfExampleText);

            HashMap<Character, Character> fusion = new HashMap<>();
            for (int i = 0; i < encrCharacters.size(); i++) {
                fusion.put(encrCharacters.get(i), normCharacters.get(i));
            }
            List<String> encryptedLines = Files.readAllLines(pathOfEnrypted);
            List<String> newLines = new ArrayList<>();
            for (String encryptedLine : encryptedLines) {
                char[] chars = encryptedLine.toCharArray();
                char[] newChars = new char[chars.length];
                for (int i = 0; i < chars.length; i++) {
                    newChars[i] = fusion.get(chars[i]);
                }
                newLines.add(new String(newChars));
            }
            Encryption.writeNewContentToFile(pathOfEnrypted, newLines);
            System.out.println("Файл создан в той же директории, что и зашифрованный файл");
            System.out.println();
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
