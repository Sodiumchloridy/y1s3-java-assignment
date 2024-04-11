package util;

import java.io.IOException;
import java.util.Scanner;

public class Util {
    Util(){};
    public static void clearConsole() throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            SystemMessage.errorMessage(9);
        }
        
    }

    public static void waitForEnter() {
        System.out.println("Press Enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Wait for the user to press Enter
    }
}
