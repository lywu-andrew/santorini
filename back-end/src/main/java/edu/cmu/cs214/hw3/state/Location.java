package edu.cmu.cs214.hw3.state;

import java.util.Objects;

/**
 * Class for location in the grid.
 */
public class Location {
    
    private int row;
    private int col;

    /**
     * Creates a new {@link Location} instance.
     *
     * @param row Row value in grid
     * @param col Col value in grid
     */
    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    /**
     * Checks if location is valid for grid size.
     *
     * @return {@code true} if location is valid.
     */
    public boolean checkValidLocation() {
        if (this.row < 0 || this.row >= Grid.GRID_SIZE 
         || this.col < 0 || this.col >= Grid.GRID_SIZE) return false;
        else return true;
    }

    /**
     * Checks if locations are equal.
     *
     * @param obj The {@link Object} to check
     * @return {@code true} if locations are equal
     */
    public boolean equals(Object obj) {
        if (! (obj instanceof Location)) return false;
        Location loc = (Location) obj;
        if (loc.row == this.row && loc.col == this.col) return true;
        else return false;
    }

    /**
     * from https://stackoverflow.com/questions/22826326/good-hashcode-function-for-2d-coordinates
     * @return hashcode for Location object.
     */
    public int hashCode() {
        return Objects.hash(this.row, this.col);
    }

    /**
     * Checks if location is adjacent.
     *
     * @param loc The {@link Location} to check
     * @return {@code true} if location is adjacent.
     */
    public boolean adjacent(Location loc) {
        int lrow = loc.row;
        int lcol = loc.col;
        if (Math.abs(lrow - this.row) < 2 && Math.abs(lcol - this.col) < 2) return true;
        else return false;
    }

}
