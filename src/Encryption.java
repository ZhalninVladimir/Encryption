import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Encryption {
    static final String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ.,”:-—!? ";
    static final char[] chars = alphabet.toCharArray();
    public static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        encrypt();
    }

    public static void writeNewContentToFile(Path path, List<String> result) throws IOException {
        Path parent = path.getParent();
        System.out.println("Придумайте и введите имя файла c расширением txt, затем enter:");
        String nameOfFile = SCANNER.nextLine();
        Path name;
        if (nameOfFile.endsWith(".txt")) {
            name = Path.of(nameOfFile);
        } else {
            name = Path.of(nameOfFile + ".txt");
        }
        Path file = Files.createFile(parent.resolve(name));
        Files.write(file, result);
    }

    public static void encrypt() {
        while (true) {
            System.out.println("Добро пожаловать, дорогой гость!");
            System.out.println("Для шифрования текстого файла нажмите 1, затем enter.");
            System.out.println("Для расшифровки текстого файла нажмите 2, затем enter.");
            System.out.println("Для расшифровки текстого файла методом brut-force нажмите 3, затем enter.");
            System.out.println("Для расшифровки текстого файла методом статистического анализа нажмите 4, затем enter.");
            System.out.println("Для выхода из программы нажмите 5, затем enter.");
            int choice = Integer.parseInt(SCANNER.nextLine());
            Path path;
            List<String> lines;
            List<String> result;
            int kea;

            if (choice == 1) {
                System.out.println("Введите полный путь к файлу для зашифровки, затем enter:");
                path = Path.of(SCANNER.nextLine());
                System.out.println("Введите ключ - целое число (от 1 до 75), затем enter:");
                kea = Integer.parseInt(SCANNER.nextLine());
                try {
                    lines = Files.readAllLines(path);
                    result = encrypt(lines, kea);
                    writeNewContentToFile(path, result);
                    System.out.println("Файл создан в той же директории!");
                    System.out.println();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (choice == 2) {
                System.out.println("Введите полный путь к файлу для расшифровки, затем enter:");
                path = Path.of(SCANNER.nextLine());
                System.out.println("Введите ключ - целое число (от 1 до 75), затем enter:");
                kea = Integer.parseInt(SCANNER.nextLine());
                try {
                    lines = Files.readAllLines(path);
                    result = decrypt(lines, kea);
                    writeNewContentToFile(path, result);
                    System.out.println("Файл создан в той же директории!");
                    System.out.println();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (choice == 3) {
                Breaking.bruteForce();
            } else if (choice == 4) {
                Breaking.statisticAnalize();
            } else if (choice == 5) {
                System.out.println("До новых встреч!)");
                System.exit(0);
            } else {
                System.out.println("Invalid number! Попробуйте еще раз!");
                System.out.println();
            }
        }
    }

    public static List<String> encrypt(List<String> lines, int kea) {
        List<String> enryptLines = new ArrayList<>();
        for (String line : lines) {
            char[] strChars = line.toCharArray();
            char[] result = new char[strChars.length];
            for (int i = 0; i < strChars.length; i++) {
                char strChar = strChars[i];
                for (int j = 0; j < chars.length; j++) {
                    char ch = chars[j];
                    if (strChar == ch) {
                        result[i] = chars[(j + kea) % chars.length];
                    }
                }
            }
            enryptLines.add(new String(result));
        }

        return enryptLines;
    }

    public static List<String> decrypt(List<String> lines, int key) {
        List<String> decryptLines = new ArrayList<>();
        int kea = chars.length - key;
        for (String line : lines) {
            char[] strChars = line.toCharArray();
            char[] result = new char[strChars.length];
            for (int i = 0; i < strChars.length; i++) {
                char strChar = strChars[i];
                for (int j = 0; j < chars.length; j++) {
                    char ch = chars[j];
                    if (strChar == ch) {
                        result[i] = chars[(j + kea) % chars.length];
                    }
                }
            }
            decryptLines.add(new String(result));
        }
        return decryptLines;
    }

}
