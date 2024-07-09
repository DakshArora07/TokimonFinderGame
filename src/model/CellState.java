package model;

/**
 * @author Daksh Arora 301591964
 * @version 3.2
 * <br>
 * <p>
 * This class provides a basic structure of the state of a cell in the game's {@link Grid}.
 * It stores information like is there a Tokimon or Fokimon in that space or not, is
 * it visited or not, etc.<br>
 * This class also provides the basic functions to get different attributes of the cell
 * state and several others to make changes in the current state of a cell.
 * </p>
 */

public class CellState {
    private boolean hasTokimon;
    private boolean hasFokimon;
    private boolean hasTrainer;
    private boolean isVisible;
    private boolean isVisited;

    public CellState() {
        hasTokimon = false;
        hasFokimon = false;
        hasTrainer = false;
        isVisible = false;
        isVisited = false;
    }

    public boolean hasTokimon() {
        return hasTokimon;
    }

    public void putTokimon() {
        this.hasTokimon = true;
    }

    public boolean hasFokimon() {
        return hasFokimon;
    }

    public void putFokimon() {
        this.hasFokimon = true;
    }

    public void removeFokimon() {
        this.hasFokimon = false;
    }

    public boolean hasTrainer() {
        return hasTrainer;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void makeVisible() {
        this.isVisible = true;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void visit() {
        hasTrainer = true;
        isVisible = true;
        isVisited = true;
    }

    public void leave() {
        hasTrainer = false;
    }
}