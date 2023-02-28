package edu.cmu.cs214.hw3.state;

public class Location {
    
    private int row;
    private int col;

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

    // checking valid locations for a 5x5 grid
    public boolean checkValidLocation() {
        if (this.row < 0 || this.row >= Grid.GRID_SIZE 
         || this.col < 0 || this.col >= Grid.GRID_SIZE) return false;
        else return true;
    }

    public boolean equals(Location loc) {
        if (loc.row == this.row && loc.col == this.col) return true;
        else return false;
    }

    public boolean adjacent(Location loc) {
        int lrow = loc.row;
        int lcol = loc.col;
        if (Math.abs(lrow - this.row) < 2 && Math.abs(lcol - this.col) < 2) return true;
        else return false;
    }

}
