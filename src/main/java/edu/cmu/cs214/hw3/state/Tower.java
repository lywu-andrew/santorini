package main.java.edu.cmu.cs214.hw3.state;

public class Tower {

    static final int highestLevel = 3;
    
    private int level;
    private boolean hasDome;
    private Location loc;

    public Tower(int row, int col) {
        this.loc = new Location(row, col);
        this.level = 0;
        this.hasDome = false;
    }

    public int getLevel() {
        return level;
    }

    public boolean isDomed() {
        return hasDome;
    }

    public Location getLocation() {
        return this.loc;
    }

    public void build() {
        // no check for dome; already checked in Grid isOccupied
        if (this.level == highestLevel) this.hasDome = true;
        else this.level += 1;
    }
}