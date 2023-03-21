package edu.cmu.cs214.hw3.player.godcards;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

/**
 * Class for the Minotaur god card. 
 */
public class Minotaur extends Player {

    public Minotaur(int id, Worker w1, Worker w2) {
        super(id, w1, w2);
    }

    private Player getOpponent() {
        for (Integer pid: Player.getPlayers().keySet()) {
            if (pid != super.getID()) return Player.getPlayers().get(pid);
        }
        return this;
    }

    private boolean modifiedTryMove(Location prev, Location next, Grid grid) {
        if (grid.tryMove(prev, next)) return true;
        // check that target loc isn't domed + is occupied by the opponent's worker
        if (grid.isOccupied(next) && !grid.getTower(next).isDomed() && super.getWorkerFromLocation(next) == -1) {
            Integer drow = next.getRow() - prev.getRow();
            Integer dcol = next.getCol() - prev.getCol();
            Location newLoc = new Location(next.getRow() + drow, next.getCol() + dcol);
            // check if opponent worker's resulting loc is valid (not off grid or occupied + any level)
            if (newLoc.checkValidLocation()) {
                if (!grid.tryOccupy(newLoc)) return false;
                Player p2 = this.getOpponent();
                Integer wid = p2.getWorkerFromLocation(next);
                p2.place(wid, newLoc);
                grid.remOccupy(prev);
                return true;
            }
        }
        return false;
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
            return String.format("Player %d: Please input valid climbable location", super.getID());
        }
        super.place(wid, next);
        return "success";
    }
    
}
