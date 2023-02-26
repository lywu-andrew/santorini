package main.java.edu.cmu.cs214.hw3.state;

public class Location {
    
    public int row;
    public int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean equals(Location l) {
        if (l.row == this.row && l.col == this.col) return true;
        else return false;
    }

}
