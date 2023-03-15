package edu.cmu.cs214.hw3.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for game grid. Default is a 5x5 square.
 */
public class Grid {
    
    static final int GRID_SIZE = 5; // Default size of grid

    private Tower[][] gridState;
    private List<Location> occupiedFields;

    /**
     * Creates a new {@link Grid} instance, a matrix of {@link Tower} objects.
     * Also stores attribute of currently occupied {@link Location} objects.
     */
    public Grid() {
        this.gridState = new Tower[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Location newLoc = new Location(i, j);
                this.gridState[i][j] = new Tower(newLoc);
            }
        }
        this.occupiedFields = new ArrayList<Location>();
    }

    public Tower[][] getGridState() {
        return this.gridState;
    }

    public List<Location> getOccupiedFields() {
        return this.occupiedFields;
    }

    /**
     * Gets a {@link Tower} object from the grid.
     *
     * @param loc The {@link Location} in the grid
     * @return {@link Tower} at that location
     */
    public Tower getTower(Location loc) {
        int row = loc.getRow();
        int col = loc.getCol();
        return gridState[row][col];
    }

    /**
     * Checks if the {@link Tower} at {@link Location} in the grid is at the highest level.
     *
     * @param loc The {@link Location} in the grid
     * @return {@code true} if {@link Tower} at that location is at the highest level
     */
    public boolean highest(Location loc) {
        Tower tower = getTower(loc);
        if (tower.getLevel() == Tower.HIGHEST_LEVEL) return true;
        else return false;
    }

    /**
     * Checks if location is occupied in the grid.
     * Occupied means there is a worker or a domed tower occupying the location.
     *
     * @param loc The {@link Location} in the grid
     * @return {@code true} if location is occupied
     */
    public boolean isOccupied(Location loc) {
        for (Location occ: occupiedFields) {
            if (loc.equals(occ)) return true;
        }
        return false;
    }

    private boolean tryOccupy(Location loc) {
        if (isOccupied(loc)) return false;
        else {
            occupiedFields.add(loc);
            return true;
        }
    }

    /**
     * Tries to occupy at 2 locations in the grid.
     *
     * @param loc1 The first {@link Location} in the grid
     * @param loc2 The second {@link Location} in the grid
     * @return {@code true} if success:
     *                         both locations were not previously occupied
     */
    public boolean tryPlace(Location loc1, Location loc2) {
        if (!tryOccupy(loc1)) {
            return false;
        } else if (!tryOccupy(loc2)) {
            occupiedFields.remove(loc1);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Tries to move from previous location to next location in the grid.
     *
     * @param prev The previous {@link Location} in the grid
     * @param next The next {@link Location} in the grid
     * @return {@code true} if success:
     *                         next was not previously occupied,
     *                         prev to next is climbable
     */
    public boolean tryMove(Location prev, Location next) {
        Tower prevTower = getTower(prev);
        Tower nextTower = getTower(next);
        if (!tryOccupy(next)) return false;
        else if (!prevTower.isClimbable(nextTower)) { // checks if levels between towers is not more than 1
            occupiedFields.remove(next);
            return false;
        } else {
            occupiedFields.remove(prev);
            return true;
        }
    }

    /**
     * Tries to build at location in the grid.
     *
     * @param loc The {@link Location} in the grid
     * @return {@code true} if success: location was not previously occupied
     *                         (note, domed towers are in occupiedFields)
     */
    public boolean tryBuild(Location loc) {
        if (isOccupied(loc)) return false;
        else {
            Tower tower = getTower(loc);
            tower.build();
            // domed towers are checked for in isOccupied
            if (tower.isDomed()) this.occupiedFields.add(tower.getLocation());
            return true;
        }
    }
}
