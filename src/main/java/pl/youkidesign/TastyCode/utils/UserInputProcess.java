package pl.youkidesign.TastyCode.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserInputProcess {

    public Scanner scanner = new Scanner(System.in);

    public boolean ifOrder() {
        String input = scanner.next().toLowerCase();

        boolean responseIf = Boolean.parseBoolean(input) ||
                input.equals("y") ||
                input.equals("1");
        scanner.nextLine();

        return responseIf;
    }

    public void ifExit(long choice) {
        if (choice == 0) {
            System.out.println("See you next time!");
            System.exit(200);
        }

    }
}
