package main.java.edu.cmu.cs214.hw3.state;

import java.util.List;

public class Grid {
    
    public Tower[][] gridState;
    public List<Location> occupiedFields;

    public Grid() {
        this.gridState = new Tower[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.gridState[i][j] = new Tower(i, j);
            }
        }
        this.occupiedFields = new ArrayList<Location>();
    }
    
    public Tower getTower(Location loc) {
        int row = loc.row;
        int col = loc.col;
        return gridState[row][col];
    }

    public boolean highest(Location loc) {
        Tower tower = getTower(loc);
        if (tower.getLevel() == 3) return true;
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
            if (tower.isDomed()) this.occupiedFields.add(tower.loc);
            return true;
        }
    }
}
