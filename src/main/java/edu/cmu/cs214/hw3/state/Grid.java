package main.java.edu.cmu.cs214.hw3.state;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    
    static final int gridSize = 5;

    private Tower[][] gridState;
    private List<Location> occupiedFields;

    public Grid() {
        this.gridState = new Tower[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                this.gridState[i][j] = new Tower(i, j);
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

    public Tower getTower(Location loc) {
        int row = loc.getRow();
        int col = loc.getCol();
        return gridState[row][col];
    }

    public boolean highest(Location loc) {
        Tower tower = getTower(loc);
        if (tower.getLevel() == Tower.highestLevel) return true;
        else return false;
    }

    public boolean isOccupied(Location loc) {
        for (Location occ: occupiedFields) {
            if (loc.equals(occ)) return true;
        }
        return false;
    }

    public boolean tryOccupy(Location loc) {
        if (isOccupied(loc)) return false;
        else {
            occupiedFields.add(loc);
            return true;
        }
    }

    public boolean tryMove(Location prev, Location next) {
        if (!tryOccupy(next)) return false;
        else {
            occupiedFields.remove(prev);
            return true;
        }
    }

    public boolean tryBuild(Location loc) {
        if (isOccupied(loc)) return false;
        else {
            Tower tower = getTower(loc);
            tower.build();
            if (tower.isDomed()) this.occupiedFields.add(tower.getLocation());
            return true;
        }
    }
}
