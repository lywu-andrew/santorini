package edu.cmu.cs214.hw3.player.godcards;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

/**
 * Class for the Demeter god card. 
 */
public class Demeter extends Player {

    private boolean firstBuild;  // indicates whether first build has been performed
    private Location firstLoc;   // first build location
    private String instruction;

    /**
     * Creates a new {@link Demeter} instance.
     * 
     * @param id Player id
     * @param w1 The player's first {@link Worker}
     * @param w2 The player's second {@link Worker}
     */
    public Demeter(int id, Worker w1, Worker w2) {
        super(id, w1, w2);
        this.firstBuild = true;
        this.firstLoc = new Location(-1, -1);
        this.instruction = "";
    }

    public boolean getFirstBuild() {
        return this.firstBuild;
    }

    public Location getFirstLoc() {
        return this.firstLoc;
    }

    public String getInstruction() {
        return this.instruction;
    }

    /**
     * Builds at the location, and gives option to build again at a different location.
     * If user picks a worker's position, the optional build is cancelled (no extra action).
     * Then, switches over to other player's turn.
     * Sets instruction to next action if successful or user error
     *
     * @param loc The target {@link Location}
     * @param grid The game board
     * @return {@link String} indicating action success, optional build action, or error
     * @error If the location is the same as the first build location, there will be no action.
     * @error If the locations are not valid, there will be no action.
     * @error If the location is not adjacent to any of the player's workers, there will be no action.
     * @error If the location is previously occupied, there will be no action.
     */
    @Override
    public String build(Location loc, Grid grid) {
        // cancel optional 2nd build if click on worker's current location
        if (!firstBuild && super.getWorkerFromLocation(loc) != -1) {
            this.firstBuild = true;
            return "success";
        } else if (!firstBuild && loc.equals(this.firstLoc)) {
            return String.format("Player %d: Pick a different location from the first.", super.getID());
        } else {
            this.instruction = super.build(loc, grid);
            // first build
            if (this.instruction == "success" && firstBuild) {
                this.firstBuild = false;
                this.firstLoc = loc;
                return String.format("Player %d's turn to (optionally) build! Click a worker to skip.", super.getID());
            } else if (this.instruction == "success") this.firstBuild = true;
            return this.instruction;
        }
    }
    
}
