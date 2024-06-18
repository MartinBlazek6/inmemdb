package org.example.atat;

import java.util.Scanner;

public class MainApp {
    static InMemoryDatabase db = new InMemoryDatabase();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String shouldContinue = "";
        while (!shouldContinue.equals("n")) {
            System.out.print("Insert command: ");

            String command = scanner.nextLine();

            System.out.println(db.executeInstructions(command));

            do {
                System.out.print("Do yo want to continue? (y/n): ");
                shouldContinue = scanner.nextLine();
            } while (!(shouldContinue.equals("n") || shouldContinue.equals("y")));
        }

    }
}
