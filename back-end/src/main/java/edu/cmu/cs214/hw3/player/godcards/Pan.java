package edu.cmu.cs214.hw3.player.godcards;

import java.util.HashMap;
import java.util.Map;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

/**
 * Class for the Pan god card. 
 */
public class Pan extends Player {

    private Map<Integer, Integer> prevLevel;

    public Pan(int id, Worker w1, Worker w2) {
        super(id, w1, w2);
        this.prevLevel = new HashMap<Integer, Integer>();
    }

    public Map<Integer, Integer> getPrevLevel() {
        return this.prevLevel;
    }

    @Override
    public boolean checkWin(Grid grid) {
        if (super.checkWin(grid)) return true;
        Map<Integer, Location> positions = super.getWorkerPositions();
        for (Integer wid : positions.keySet()) {
            Integer currLvl = grid.getTower(positions.get(wid)).getLevel();
            Integer prevLvl = this.prevLevel.get(wid);
            if (prevLvl == null) continue;
            if (prevLvl - currLvl > 1) return true;
        }
        return false;

    }

    private boolean modifiedTryMove(Location prev, Location next, Grid grid) {
        Integer prevLvl = grid.getTower(prev).getLevel();
        Integer nextLvl = grid.getTower(next).getLevel();
        if (nextLvl - prevLvl > 1 || !grid.tryOccupy(next)) return false;
        else grid.remOccupy(prev);
        return true;
    }

    @Override
    public String move(Location curr, Location next, Grid grid) {
        if (!curr.checkValidLocation() || !next.checkValidLocation()) {
            return String.format("Player %d: Please input valid location (rows 0-4, cols 0-4)", super.getID());
        }
        Integer wid = super.getWorkerFromLocation(curr);
        if (wid == -1) {
            return String.format("Player %d: Please select a worker to move", super.getID());
        }
        if (!super.getWorker(wid).isAdjLocation(next)) {
            return String.format("Player %d: Please input adjacent location", super.getID());
        }
        Location prevPos = super.getWorkerPosition(wid);
        if (!this.modifiedTryMove(prevPos, next, grid)) {
            return String.format("Player %d: Please input unoccupied climbable location", super.getID());
        }
        super.place(wid, next);
        Integer level = grid.getTower(curr).getLevel();
        this.prevLevel.put(wid, level);
        return "success";
    }
    
}
