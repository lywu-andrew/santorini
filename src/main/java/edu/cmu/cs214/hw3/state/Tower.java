package edu.cmu.cs214.hw3.state;

/**
 * Class for towers in the grid.
 */
public class Tower {

    static final int HIGHEST_LEVEL = 3; // Default highest level of tower
    
    private int level;
    private boolean hasDome;
    private Location loc;

    /**
     * Creates a new {@link Tower} instance at level 0.
     *
     * @param locin The {@link Location} in the grid
     */
    public Tower(Location locin) {
        this.loc = locin;
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

    /**
     * Checks if the {@link Tower} is climbable from current Tower.
     *
     * @param tower The {@link Tower} to check
     * @return {@code true} if {@link Tower} is climbable (level difference of at most 1)
     */
    public boolean isClimbable (Tower tower) {
        int level2 = tower.getLevel();
        return Math.abs(level2 - this.level) < 2;
    }

    /**
     * Builds on the tower. If the tower is at the highest level, it will build a dome.
     */
    public void build() {
        // no check for dome; already checked in Grid isOccupied
        if (this.level == HIGHEST_LEVEL) this.hasDome = true;
        else this.level += 1;
    }
}