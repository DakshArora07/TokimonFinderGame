import ui.UserConsole;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Daksh Arora 301591964
 * @version 3.2
 * <br>
 * <p>
 * This class holds the {@code main()} function of the project which takes optional
 * arguments from the user in the command line and does error checking.
 * Then, if everything is good, a new {@link UserConsole} object will be created
 * which will start the game.
 * </p>
 */

public class TokimonFinder {
    public static void main(String[] args) {
        try {
            if (args.length > 3) {
                throw new IllegalArgumentException("""
                        You can enter a maximum of 3 optional arguments:
                        --numToki=x (where x is a positive integer >= 5): this determines the number of Tokimons in the 10x10 grid
                        --numFoki=x (where x is a positive integer >= 5): this determines the number of Fokimons in the 10x10 grid
                        --cheat : puts the program into cheat mode\s""");
            }

            int numToki = 10;
            int numFoki = 5;
            boolean cheat = false;

            for (String s : args) {
                Scanner input = new Scanner(s);
                input.useDelimiter("=");
                String arg = input.next();
                if (arg.equals("--numToki")) {
                    numToki = input.nextInt();
                    if (numToki < 5) {
                        throw new IllegalArgumentException("You can only enter a positive integer >= 5 for number of Tokimons!");
                    }
                }
                if (arg.equals("--numFoki")) {
                    numFoki = input.nextInt();
                    if (numFoki < 5) {
                        throw new IllegalArgumentException("You can only enter a positive integer >= 5 for number of Fokimons!");
                    }
                }
                if (arg.equals("--cheat")) {
                    cheat = true;
                }
                input.close();
            }
            if (numFoki + numToki > 99) {
                throw new IllegalArgumentException("The combined number of Tokimons and Fokiomons should not exceed 99!\n" +
                        "1 space is reserved for the initial position of the Tokimon Trainer.");
            }
            new UserConsole(numToki, numFoki, cheat);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Error:\n" + e.getMessage());
            System.exit(-1);
        }
        catch (InputMismatchException e) {
            System.out.println("Error:" + e.getMessage());
            System.exit(-1);
        }
    }
}