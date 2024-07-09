package model;

import java.util.Random;

/**
 * @author Daksh Arora 301591964
 * @version 3.2
 * <br>
 * <p>
 * This class has the whole model of the game inside it and acts as the primary in the
 * {@code model} package. It has various fields which one of which is a {@link Grid} object
 * and others which keep track of the progress of the game.<br>
 * It has getters and setters and has methods corresponding to every action the user
 * does in the UI which can be called directly from the classes in the {@code ui} package
 * and this class also has a private method {@code updateStatus()} which is used in most
 * of the other methods to update the status of the {@link Grid} object and other fields
 * after every move.
 * </p>
 */

public class Game {
    private final Grid gameGrid;
    private boolean cheat;
    private int currentLocation;
    private int remainingSpells;
    private int tokimonsCollected;
    private int tokimonsRemaining;
    private boolean isGameLost;

    private void updateStatus() {
        if (gameGrid.getCellState(currentLocation).hasTokimon() && !gameGrid.getCellState(currentLocation).isVisited()) {
            tokimonsCollected++;
            tokimonsRemaining--;
        } else if (gameGrid.getCellState(currentLocation).hasFokimon()) {
            isGameLost = true;
        }
    }



    public Game(int numToki, int numFoki, boolean cheat, int initialPosition) {
        gameGrid = new Grid(numToki, numFoki, initialPosition);
        this.cheat = cheat;
        this.currentLocation = initialPosition;
        remainingSpells = 3;
        tokimonsCollected = 0;
        tokimonsRemaining = numToki;
        isGameLost = false;

        gameGrid.visitCell(currentLocation);
    }

    public Grid getGameGrid() {
        return gameGrid;
    }

    public boolean isInCheatMode() {
        return cheat;
    }

    public void setCheatMode(boolean cheat) {
        this.cheat = cheat;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public int getRemainingSpells() {
        return remainingSpells;
    }

    public int getTokimonsCollected() {
        return tokimonsCollected;
    }

    public int getTokimonsRemaining() {
        return tokimonsRemaining;
    }

    public boolean isGameLost() {
        return isGameLost;
    }

    public void moveUp() {
        gameGrid.leaveCell(currentLocation);
        currentLocation = currentLocation - 10;
        updateStatus();
        gameGrid.visitCell(currentLocation);
    }

    public void moveDown() {
        gameGrid.leaveCell(currentLocation);
        currentLocation = currentLocation + 10;
        updateStatus();
        gameGrid.visitCell(currentLocation);
    }

    public void moveLeft() {
        gameGrid.leaveCell(currentLocation);
        currentLocation = currentLocation - 1;
        updateStatus();
        gameGrid.visitCell(currentLocation);
    }

    public void moveRight() {
        gameGrid.leaveCell(currentLocation);
        currentLocation = currentLocation + 1;
        updateStatus();
        gameGrid.visitCell(currentLocation);
    }

    public void useSpell1(int newLocation) {
        gameGrid.leaveCell(currentLocation);
        currentLocation = newLocation;
        updateStatus();
        gameGrid.visitCell(currentLocation);
        remainingSpells--;
    }

    public void useSpell2() {
        Random rand = new Random();
        int index = rand.nextInt(gameGrid.getTokimonLocations().size());
        gameGrid.revealTokimon(gameGrid.getTokimonLocations().get(index));
        remainingSpells--;
    }
    public void useSpell3() {
        Random rand = new Random();
        int index = rand.nextInt(gameGrid.getFokimonLocations().size());
        gameGrid.eliminateFokimon(gameGrid.getFokimonLocations().get(index));
        gameGrid.getFokimonLocations().remove(index);
        remainingSpells--;
    }
}