package main.java.edu.cmu.cs214.hw3.state;

public class Location {
    
    public int row;
    public int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // checking valid locations for a 5x5 grid
    public boolean checkValidLocation() {
        if (this.row < 0 || this.row > 4 || this.col < 0 || this.col > 4) return false;
        else return true;
    }

    public boolean equals(Location loc) {
        if (loc.row == this.row && loc.col == this.col) return true;
        else return false;
    }

    public boolean adjacent(Location loc) {
        int Lrow = loc.row;
        int Lcol = loc.col;
        if (Math.abs(Lrow - this.row) < 2 && Math.abs(Lcol - this.col) < 2) return true;
        else return false;
    }

}
