package ui;

import model.Game;

import javax.swing.*;
import java.awt.event.*;

/**
 * @author Daksh Arora 301591964
 * @version 3.2
 * <br>
 * <p>
 * This class controls all the input from the user using a {@link JFrame} object which
 * is created on the front and stays in focus (unless the user interferes with it) and
 * does the work of calling functions of the {@link Game} object in the background
 * according to the actions taken by the user (key pressed). <br>
 * It has a {@link Game} object over which the whole user interface will be arranged and
 * also has a {@link UserDisplay} object and passes on the {@link Game} object to the
 * {@link UserDisplay} object to control the outputs to be given.
 * It displays accurate output to the user using {@link UserDisplay} Object according
 * to the actions received from the user.<br>
 * It has only one method {@code initiateControls()} which is private (as no object of
 * this class is being used anywhere) and it overrides a method of the {@link KeyAdapter} class
 * which is used to track keys pressed for taking user input. Appropriate output is
 * also shown accordingly.
 * </p>
 */

public class UserConsole {
    private Game game;
    private UserDisplay display;
    private boolean usingSpells;
    private boolean takingInput;
    private boolean initializingGame;
    private String inputString;

    private void initiateControls (int numToki, int numFoki, boolean cheat) {
        JFrame myJFrame = new JFrame();
        myJFrame.setVisible(true);
        myJFrame.requestFocus();

        myJFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                boolean isInGridRange = (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_J) || (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9);

                if (initializingGame) {
                    if (!takingInput) {
                        if (keyCode == KeyEvent.VK_I) {
                            UserDisplay.displayGameInfo();
                            System.out.println("\nNow that you have read the information, ");
                        }
                        System.out.print("LET'S START! Please enter the initial position of the Tokimon Trainer and then press Enter: ");
                        takingInput = true;
                    }
                    else {
                        if (isInGridRange) {
                            String s = KeyEvent.getKeyText(keyCode);
                            System.out.print(s);
                            inputString += s;
                        }
                        else if (keyCode == KeyEvent.VK_ENTER) {
                            try {
                                if (inputString.length() < 2 || inputString.length() > 3 || inputString.charAt(0) < 'A' || inputString.charAt(0) > 'Z' || inputString.charAt(1) < '1' || inputString.charAt(1) > '9') {
                                    inputString = "";
                                    throw new IllegalArgumentException();
                                } else {
                                    int columnNumber = Character.getNumericValue(inputString.charAt(1));
                                    if (inputString.length() == 3) {
                                        if (inputString.charAt(1) == '1' && inputString.charAt(2) == '0') {
                                            columnNumber = 10;
                                        } else {
                                            inputString = "";
                                            throw new IllegalArgumentException();
                                        }
                                    }
                                    System.out.println("\n");
                                    game = new Game(numToki, numFoki, cheat, ((int) (inputString.charAt(0)) - 65) * 10 + columnNumber - 1);
                                    display = new UserDisplay(game);
                                    initializingGame = false;
                                    takingInput = false;
                                    display.out();
                                    game.setCheatMode(false);
                                    inputString = "";
                                }
                            }
                            catch (IllegalArgumentException ex) {
                                System.out.print("\nTokimon Trainer could not be placed as the location you entered is invalid! Please enter it again: ");
                            }
                        }
                    }
                }
                else {
                    if (!usingSpells) {
                        if (keyCode == KeyEvent.VK_F) {
                            if (game.getRemainingSpells() > 0) {
                                display.displaySpells();
                                usingSpells = true;
                            } else {
                                System.out.println("You have exhausted all your Spells!\n");
                                display.out();
                            }
                        }
                        else {
                            if (keyCode == KeyEvent.VK_W) {
                                if (game.getCurrentLocation() < 10) {
                                    System.out.println("Cannot move further up!\n");
                                }
                                else {
                                    game.moveUp();
                                    display.out();
                                }
                            } else if (keyCode == KeyEvent.VK_S) {
                                if (game.getCurrentLocation() >= 90 && game.getCurrentLocation() < 100) {
                                    System.out.println("Cannot move further down!\n");
                                }
                                else {
                                    game.moveDown();
                                    display.out();
                                }
                            } else if (keyCode == KeyEvent.VK_A) {
                                if (game.getCurrentLocation()%10 == 0) {
                                    System.out.println("Cannot move further left!\n");
                                }
                                else {
                                    game.moveLeft();
                                    display.out();
                                }
                            } else if (keyCode == KeyEvent.VK_D) {
                                if (game.getCurrentLocation()%10 == 9) {
                                    System.out.println("Cannot move further right!\n");
                                }
                                else {
                                    game.moveRight();
                                    display.out();
                                }
                            }
                        }
                    }
                    else {
                        if (!takingInput) {
                            if (keyCode == KeyEvent.VK_1) {
                                System.out.println("1");
                                System.out.print("Where in the grid would you like to jump to? Type a location and press Enter: ");
                                takingInput = true;
                            }
                            else if (keyCode == KeyEvent.VK_2) {
                                game.useSpell2();
                                System.out.println("2\n");
                                System.out.println("A Tokimon is visible now!\n");
                                usingSpells = false;
                                display.out();
                            }
                            else if (keyCode == KeyEvent.VK_3) {
                                game.useSpell3();
                                System.out.println("3\n");
                                System.out.println("A Fokimon has been eliminated!\n");
                                usingSpells = false;
                                display.out();
                            }
                        } else {
                            if (isInGridRange) {
                                String s = KeyEvent.getKeyText(keyCode);
                                System.out.print(s);
                                inputString += s;
                            } else if (keyCode == KeyEvent.VK_ENTER) {
                                try {
                                    if (inputString.length() < 2 || inputString.length() > 3 || inputString.charAt(0) < 'A' || inputString.charAt(0) > 'Z' || inputString.charAt(1) < '1' || inputString.charAt(1) > '9') {
                                        inputString = "";
                                        throw new IllegalArgumentException();
                                    } else {
                                        int columnNumber = Character.getNumericValue(inputString.charAt(1));
                                        if (inputString.length() == 3) {
                                            if (inputString.charAt(1) == '1' && inputString.charAt(2) == '0') {
                                                columnNumber = 10;
                                            } else {
                                                inputString = "";
                                                throw new IllegalArgumentException();
                                            }
                                        }
                                        System.out.println("\n");
                                        game.useSpell1(((int) (inputString.charAt(0)) - 65) * 10 + columnNumber - 1);
                                        System.out.print("\nThe Tokimon Trainer has jumped to " + inputString + "!\n");
                                        inputString = "";
                                        usingSpells = false;
                                        takingInput = false;
                                        display.out();
                                    }
                                }
                                catch (IllegalArgumentException ex) {
                                    System.out.print("\nSpell could not be used as the location you entered is invalid! Please enter it again: ");
                                }
                            }
                        }
                    }
                }
            }
        });
    }



    public UserConsole(int numToki, int numFoki, boolean cheat) {
        usingSpells = false;
        takingInput = false;
        initializingGame = true;
        inputString = "";

        UserDisplay.displayHeader();
        initiateControls(numToki, numFoki, cheat);
    }
}