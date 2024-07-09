package ui;

import model.CellState;
import model.Game;

import java.util.List;

/**
 * @author Daksh Arora 301591964
 * @version 3.2
 * <br>
 * <p>
 * This class displays the game grid to the user and other statistics along with intro
 * and outro to make the gaming experience possible for the user.<br>
 * It has only one field which is a {@link Game} object passed on by the
 * {@link UserConsole} when instantiating an object of this class.<br>
 * It has static functions to display general information to the user and has other
 * functions related to the object which use the information stored in {@link Game} object
 * and are called by the {@link UserConsole} during the gameplay and interaction with
 * the user.
 * </p>
 */

public class UserDisplay {
    private final Game game;

    public UserDisplay(Game game) {
        this.game = game;
    }

    public static void displayHeader() {
        System.out.print("""

                ============================================= TOKIMON FINDER =============================================
                
                ==================================== A Game by Daksh Arora(301591964) ====================================
                
                We welcome you, our newest Tokimon Trainer!
                
                This game uses a JFrame object to take user input so there are 3 important things to keep in mind:
                1. The JFrame object(on the top left of your screen) should stay in focus so please do not use your mouse.
                2. The keys you press which will surely lead to unsuccessful input will automatically be ignored.
                3. You do not have to press 'Enter' or any other key after giving an input unless asked to do so.
                
                
                Press [i] to read game information or press any other key to start directly.\s
                
                """);
    }

    public static void displayGameInfo() {
        System.out.println("""
                ============================================ Game Information ============================================
                
                The program will randomly select positions on a grid to place the Tokimons and Fokimons.
                The game grid has columns numbered 1, 2, and so on and rows labelled A, B, and so forth.
                The program will begin by asking the user for an initial position, for example, "B5," and then prompt the user to press "Enter."
                
                The player will start with 3 spells, each of which can be used to either:
                * Jump the player to another grid location.
                * Randomly reveal the location of one of the Tokimons.
                * Randomly eliminate one of the Fokimons.
                
                At each turn, the player is prompted for the next move. The player can choose to:
                * Move up, down, left, or right from their current position (using keys W, A, S, or D).
                * Use a spell (using F key).

                If a move results in the player landing on a cell occupied by a Fokimon, the game ends, and the player loses.
                If a move results in the player landing on a cell occupied by a Tokimon, the player will be notified and congratulated.

                After each turn, the player is shown the number of Tokimons they have collected, the number of Tokimons remaining, and the number of spells remaining.
                Additionally, at each turn, the player is shown a map indicating what is known about the game grid so far:
                "~" indicates an unknown (unvisited) location.
                "$" indicates a found Tokimon.
                " " (space) indicates a visited but empty location.
                "@" denotes the player's current position.
                
                A player wins when all Tokimons on the board have been collected.
                """);
    }

    public void displayMap() {
        for (int i=1; i<=10; i++) {
            System.out.print("     " + i);
        }
        System.out.println();
        for (List<CellState> Row : game.getGameGrid().getGrid()) {
            System.out.print((char) (game.getGameGrid().getGrid().indexOf(Row) + 65) + "  ");
            for (CellState cellState : Row) {
                System.out.print("[");
                if (!cellState.isVisible() && !game.isInCheatMode()) {
                    System.out.print(" ~ ");
                }
                else {
                    if (cellState.hasTokimon()) { System.out.print("$");}
                    else { System.out.print(" ");}
                    if (cellState.hasTrainer()) { System.out.print("@");}
                    else { System.out.print(" ");}
                    if (cellState.hasFokimon()) { System.out.print("X");}
                    else { System.out.print(" ");}
                }
                System.out.print("] ");
            }
            System.out.println();
        }
    }

    public void displayStats() {
        if (game.isGameLost()) {
            System.out.println("\nYou lost!\n");
            game.setCheatMode(true);
            System.out.println("=========================== MAP REVEAL ===========================");
            displayMap();
            System.out.println("===================== Better Luck Next Time ======================");
            System.exit(0);
        }
        if (game.getGameGrid().getCellState(game.getCurrentLocation()).hasTokimon()) {
            System.out.println("You just found a new Tokimon! (or landed again on an already found Tokimon)");
        }
        System.out.println("Number of Tokimons Collected = " + game.getTokimonsCollected());
        System.out.println("Number of Tokimons Remaining = " + game.getTokimonsRemaining());
        System.out.println("Number of Spells Remaining = " + game.getRemainingSpells());
        if (game.getTokimonsRemaining() == 0) {
            System.out.println("\nYou won!\n");
            game.setCheatMode(true);
            System.out.println("=========================== MAP REVEAL ===========================");
            displayMap();
            System.out.println("======================= Exiting the Game! ========================");
            System.exit(0);
        }
    }

    public void displayControls() {
        System.out.print("""
                
                          [W]
                Press [A] [S] [D] to move the Tokimon Trainer or Press [F] to use a spell
                
                """);
    }

    public void displaySpells() {
        System.out.print("""
                There are 3 different spells:
                1 - Jump the player to another grid location.
                2 - Randomly reveal the location of one of the Tokimons.
                3 - Randomly eliminate one of the Fokimons.
                Which spell would you like to choose? Enter spell number:\s""");
    }

    public void out() {
        displayMap();
        displayStats();
        displayControls();
    }
}