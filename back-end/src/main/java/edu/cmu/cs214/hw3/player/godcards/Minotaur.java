package edu.cmu.cs214.hw3.player.godcards;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

/**
 * Class for the Minotaur god card. 
 */
public class Minotaur extends Player {

    /**
     * Creates a new {@link Minotaur} instance.
     * 
     * @param id Player id
     * @param w1 The player's first {@link Worker}
     * @param w2 The player's second {@link Worker}
     */
    public Minotaur(int id, Worker w1, Worker w2) {
        super(id, w1, w2);
    }

    private Player getOpponent() {
        for (Integer pid: Player.getPlayers().keySet()) {
            if (pid != super.getID()) return Player.getPlayers().get(pid); // gets opposing player
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

    /**
     * Moves player's worker to location.
     * If target location has opposing player's worker, can take their space if
     * can force them 1 space backwards to an unoccupied space at any level.
     * 
     * Then checks if the current player has won, and if not, goes to build action.
     * Sets instruction to next action if successful or user error
     *
     * @param curr The current {@link Location} of the worker to move
     * @param next The destination {@link Location}
     * @param grid The game board
     * @return {@link String} indicating action success or error
     * @error If the locations are not valid, there will be no action.
     * @error If the first location selected is not occupied by worker, there will be no action.
     * @error If the location is not adjacent to the worker, there will be no action.
     * @error If the location is previously occupied or unclimbable, there will be no action.
     */
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
