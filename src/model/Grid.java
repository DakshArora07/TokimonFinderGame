package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Daksh Arora 301591964
 * @version 3.2
 * <br>
 * <p>
 * This class contains a list of lists of the {@link CellState} which acts like a
 * matrix of cells which collectively gives us a {@link Grid} and this class also holds
 * the locations of Tokimons anf Fokimons on the grid.<br>
 * It has getters for the attributes and a private function {@code populateGrid()} which
 * initially populates the grid with Tokimons, Fokimons and the Tokimon Trainer.<br>
 * It also has other different functions to perform actions required by {@link Game} class which
 * can be directly called from the {@link Game} class when the user performs an action.
 * </p>
 */

public class Grid {
    private final List<List<CellState>> grid = new ArrayList<>();
    private final List<Integer> tokimonLocations = new ArrayList<>();
    private final List<Integer> fokimonLocations = new ArrayList<>();

    private void populateGrid(int numToki, int numFoki, int initialPosition) {
        List<Integer> locations = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            if (i != initialPosition) {
                locations.add(i);
            }
        }

        Random rand = new Random();
        for (int i = 0; i < numToki; i++) {
            int index = rand.nextInt(locations.size());
            grid.get(locations.get(index)/10).get(locations.get(index)%10).putTokimon();
            tokimonLocations.add(locations.get(index));
            locations.remove(index);
        }
        for (int i = 0; i < numFoki; i++) {
            int location = rand.nextInt(locations.size());
            grid.get(locations.get(location)/10).get(locations.get(location)%10).putFokimon();
            fokimonLocations.add(locations.get(location));
            locations.remove(location);
        }
    }



    public Grid(int numToki, int numFoki, int initialPosition) {

        for (int i = 0; i < 10; i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                grid.get(i).add(new CellState());
            }
        }

        populateGrid(numToki, numFoki, initialPosition);
    }

    public List<List<CellState>> getGrid() {
        return grid;
    }

    public List<Integer> getTokimonLocations() {
        return tokimonLocations;
    }

    public List<Integer> getFokimonLocations() {
        return fokimonLocations;
    }

    public CellState getCellState(int location) {
        return grid.get(location/10).get(location%10);
    }

    public void leaveCell(int location) {
        grid.get(location/10).get(location%10).leave();
    }

    public void visitCell(int location) {
        grid.get(location/10).get(location%10).visit();
    }

    public void eliminateFokimon(int location) {
        grid.get(location/10).get(location%10).removeFokimon();
    }

    public void revealTokimon(int location) {
        grid.get(location/10).get(location%10).makeVisible();
    }
}