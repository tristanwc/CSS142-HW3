package com.company;

import java.util.Random;
import java.util.Scanner;


public class Main {
    //Global variables
    public static int userPoints = 25;
    public static int allowedAttempts = 5;

    //While loop that acts as the games' main menu
    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter choice (h)elp , (p)lay, (c)onfigure, (r)eport, (q)uit");
            Scanner input = new Scanner(System.in);
            char choice = input.next().charAt(0);
            switch (choice) {
                case 'h':
                    helpMenu();
                    break;
                case 'c':
                    allowedAttempts = configure(input);
                    System.out.println("Pass Value: " + allowedAttempts);
                    break;
                case 'p':
                    play(input);
                    break;
                case 'r':
                    report();
                    break;
                case 'q':
                    quit();
                    return;
                default:
                    invalidInput();
                    break;
            }
        }
    }

    //Method implemented from homework documentation, the method that does most of the calling
    public static void play(Scanner input) {
        do {
            int computerChosenNumber = chooseRandomNumber();
            int attempts = playSession(input, computerChosenNumber, allowedAttempts);
            int pointsScored = pointsScored(attempts, allowedAttempts);
            System.out.println("Allowed attempts " + allowedAttempts + " attempts taken " + attempts +
                    " Old Score: " + userPoints + " Points earned " +
                    "= " + pointsScored + " New Score: " + (userPoints + pointsScored));
            userPoints += pointsScored;
        } while (playAgain(input));
        System.out.println("Thank you for playing");
    }

    //Gets pass phrase and returns max attempts
    public static int configure(Scanner input) {
        System.out.print("Enter pass phrase: ");
        String passPhrase;
        passPhrase = input.nextLine();
        passPhrase = input.nextLine(); //Have to do a duplicate to alleviate bug
        int passLength = passPhrase.length();
        int passValue = 0;
        for (int i = 0; i < passLength; i++) {
            passValue += (int) passPhrase.charAt(i);
        }
        passValue = passValue / passLength; //Divides passValue by the length of the phrase
        System.out.println(passValue);
        passValue *= 3;
        passValue = (passValue % 7); //To reduce to the last single digit of the value
        if (passValue < 3)
            passValue += 3;

        return passValue;
    }

    //Figures out score depending on attempts and maxAttempts
    public static int pointsScored(int attempts, int allowedAttempts) {
        int points = 15;
        points -= allowedAttempts;
        points -= attempts;
        if (attempts == allowedAttempts) {
            return points * -1;
        }
        return points;
    }

    //Figures out if user wants to play again if points are available
    public static boolean playAgain(Scanner input) {
        //If user points are negative, they will need to start new session
        if (userPoints <= 0) {
            System.out.println("Your points are too low! You need to start a new session.");
            System.out.println("Do you want to start a new session? (Y/N)");
            String answer = input.nextLine().toUpperCase();
            answer = input.nextLine().toUpperCase(); //Have to do a duplicate to alleviate bug
            char yesOrNo = answer.charAt(0);
            if (yesOrNo == 'Y') {
                userPoints = 25;
                allowedAttempts = 5;
                return true;
            } else if (yesOrNo == 'N') {
                return false;
            }
        } else {
            System.out.println("Do you want to play again? (Y/N)");
            String answer = input.nextLine().toUpperCase();
            answer = input.nextLine().toUpperCase(); //Have to do a duplicate to alleviate bug
            char yesOrNo = answer.charAt(0);
            if (yesOrNo == 'Y')
                return true;
            else
                return false;
        }
        return true;
    }

    public static int chooseRandomNumber() {
        Random r = new Random();
        int computerChosenNumber = r.nextInt(100);
        System.out.println("DEBUG LOG: Computer chose " + computerChosenNumber);
        return computerChosenNumber;
    }

    /* Method gets user input, computers random number and the phrasekey max attempts.
       It gives hints wether the users input (unit and tens digit) is lower or higher than
       the computers random number.
     */
    public static int playSession(Scanner input, int computerChosenNumber, int maxAttempts) {
        int tensCompDigit = computerChosenNumber / 10;
        int unitCompDigit = computerChosenNumber % 10;
        int attempts = 0;
        while (true) {
            if (attempts == maxAttempts) {
                pointsScored(attempts, maxAttempts);
                break;
            }

            System.out.println("Guess the computer's chosen number between 1 to 100 (inclusive): ");
            int playerGuess = input.nextInt(); //Asks for user's guess
            int tensDigit = playerGuess / 10; //Gets tens digit of users guess
            int unitDigit = playerGuess % 10; //Gets unit digit of users guess

            if (unitCompDigit == unitDigit && tensCompDigit == tensDigit) {
                System.out.println("You guessed it correctly!");
                pointsScored(attempts, maxAttempts);
                break;
            }
            //Both unit and tens digit are greater than user input
            if (tensCompDigit > tensDigit && unitCompDigit > unitDigit) {
                System.out.println("Both unit digit and tens digit are greater than user input");
                attempts++;

                //Both unit digit and tens digit are lesser than user input
            } else if (tensCompDigit < tensDigit && unitCompDigit < unitDigit) {
                System.out.println("Both unit digit and tens digit are lesser than user input");
                attempts++;

                //Unit digit is greater than user input, tens digit is smaller than user input
            } else if (unitCompDigit > unitDigit && tensCompDigit < tensDigit) {
                System.out.println("Unit digit is greater than user input, tens digit is smaller than user input");
                attempts++;

                //Unit digit is smaller than user input, tens digit is greater than user input
            } else if (unitCompDigit < unitDigit && tensCompDigit > tensDigit) {
                System.out.println("Unit digit is smaller than user input, tens digit is greater than user input");
                attempts++;

                //Unit digit is greater than user input, tens digit is the same user input
            } else if (unitCompDigit > unitDigit && tensCompDigit == tensDigit) {
                System.out.println("Unit digit is greater than user input, tens digit is the same as user input");
                attempts++;

                //unit digit is smaller than user input, tens digit is the same as user input
            } else if (unitCompDigit < unitDigit && tensCompDigit == tensDigit) {
                System.out.println("Unit digit is smaller than user input, tens digit is the same as user input");
                attempts++;

                //Unit digit is smaller than user input, tens digit is smaller than user input
            } else if (unitCompDigit < unitDigit && tensCompDigit < tensDigit) {
                System.out.println("Unit digit is smaller than user input, tens digit is smaller than user input");
                attempts++;

                //Unit digit is the same as user input, tens digit is greater than user input
            } else if (unitCompDigit == unitDigit && tensCompDigit > tensDigit) {
                System.out.println("Unit digit is same than user input, tens digit is greater than user input");
                attempts++;

                //Unit digit is the same as user input, tens digit is smaller than user input
            } else if (unitCompDigit == unitDigit && tensCompDigit < tensDigit) {
                System.out.println("Unit digit is same than user input, tens digit is smaller than user input");
                attempts++;
            }
        }
        return attempts;
    }

    //Help menu that explains the games rules
    public static void helpMenu() {
        System.out.println("******************************************************************************\n" +
                "Welcome to UW CSS 142 Point Machine.\n" +
                "This machine choses a number between 1-100 at random. You need to guess the number\n" +
                "You (i.e the user) will have fixed number of attempts to guess the number.\n" +
                "If you guess it correct, you will earn points based on the number of attempts taken.\n" +
                "If you cannot guess it, you will lose points\n" +
                "To proceed, please select one of the choice from\n" +
                " (h)elp , (p)lay, (c)onfigure, (r)eport, (q)uit)\n" +
                "******************************************************************************");
    }

    //Gets users current score
    public static void report() {
        System.out.println("Your current points are " + userPoints);
    }

    //If users inputs 'q', the game closes
    public static void quit() {
        System.out.println("Thank you for checking out our game. We hope you like it. Please do not forget to provide us\n" +
                "with your feedback");
    }

    //If user inputs an invalid char, it notifies them the correct inputs
    public static void invalidInput() {
        System.out.println("Invalid choice. Accepted values are (h)elp , (p)lay, (c)onfigure, (r)eport, (q)uit");
    }
}
