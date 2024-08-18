package menu;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ScannerInputHandler {

    public static int getValidIntInput(Scanner scanner, String prompt, Collection<Integer> validInputValues) {
        int result = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println(prompt);
                result = scanner.nextInt();
                if (validInputValues.isEmpty()) {
                    validInput = true;
                }else{
                    if (validInputValues.contains(result)){
                            validInput = true;
                    }else{
                        System.out.println("Value must be one of " + validInputValues);
                    }
                }
            } catch (InputMismatchException ex) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }
        return result;
    }

    public static int getValidIntInput(Scanner scanner, String prompt) {
        return getValidIntInput(scanner,prompt, List.of());
    }

    public static double getValidDoubleInput(Scanner scanner, String prompt, Double minimumValue) {
        double result = 0.0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println(prompt);
                result = scanner.nextDouble();
                if (result >= minimumValue){
                    validInput = true;
                }else{
                    System.out.println("Value cannot be lower than " + minimumValue + ".");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Please enter a valid number (double).");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }

        return result;
    }

    public static String getValidStringInput(Scanner scanner, String prompt, Collection<String> validInputValues) {
        String result = "";
        boolean validInput = false;

        while (!validInput) {
            System.out.println(prompt);
            result = scanner.nextLine().trim();

            if (!result.isEmpty()) {
                if (validInputValues.isEmpty()) {
                    validInput = true;
                }else{
                    if (validInputValues.contains(result)){
                        validInput = true;
                    }else{
                        System.out.println("Value must be one of " + validInputValues);
                    }
                }

            } else {
                System.out.println("Input cannot be empty. Please enter a valid string.");
            }
        }

        return result;
    }

    public static String getValidStringInput(Scanner scanner, String prompt) {
        return getValidStringInput(scanner,prompt,List.of());
    }
}
