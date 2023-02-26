package main.java.edu.cmu.cs214.hw3.state;

public class Tower{
    
    private int level;
    private boolean hasDome;
    public Location loc;

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

    public void build() {
        // no check for dome; already checked in Grid isOccupied
        if (this.level == 3) this.hasDome = true;
        else this.level += 1;
    }
}