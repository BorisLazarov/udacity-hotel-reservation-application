package Utilities;

import java.util.*;

public class ScannerInputHandler {

    public static int getValidIntInput(Scanner scanner, String prompt, Collection<Integer> validInputValues) {
        int result;
        while (true) {
            try {
                System.out.println(prompt);
                result = Integer.parseInt(scanner.nextLine());
                if (validInputValues.isEmpty() || validInputValues.contains(result)) {
                    return result;
                }else{
                    System.out.println("Value must be one of " + validInputValues);
                }
            } catch (InputMismatchException | NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
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
                result = Double.parseDouble(scanner.nextLine());
                if (result >= minimumValue){
                    validInput = true;
                }else{
                    System.out.println("Value cannot be lower than " + minimumValue + ".");
                }
            } catch (InputMismatchException | NumberFormatException ex) {
                System.out.println("Please enter a valid number (double).");
            }
        }
        return result;
    }

    public static String getValidStringInput(Scanner scanner, String prompt, Collection<String> validInputValues) {
        String result;

        while (true) {
            System.out.println(prompt);
            result = scanner.nextLine().trim();

            if (!result.isEmpty()) {
                if (validInputValues.isEmpty() || validInputValues.contains(result)) {
                    return result;
                }else{
                    System.out.println("Value must be one of " + validInputValues);
                }
            } else {
                System.out.println("Input cannot be empty. Please enter a valid string.");
            }
        }
    }

    public static String getValidStringInput(Scanner scanner, String prompt) {
        return getValidStringInput(scanner,prompt,List.of());
    }

    public static boolean getValidBooleanYNInput(Scanner scanner, String prompt) {
        List<String> validInputValues = List.of("y","n");
        String result;

        while (true) {
            System.out.println(prompt);
            result = scanner.nextLine().trim().toLowerCase();

            if (!result.isEmpty()) {
                if (validInputValues.contains(result)) {
                    return result.equals("y");
                }else{
                    System.out.println("Value must be one of " + validInputValues);
                }
            } else {
                System.out.println("Input cannot be empty. Please enter a valid string.");
            }
        }
    }
}
