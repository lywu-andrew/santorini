package edu.cmu.cs214.hw3.player.godcards;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

/**
 * Class for the Demeter god card. 
 */
public class Demeter extends Player {

    private boolean firstBuild;
    private Location firstLoc;
    private String instruction;

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
            if (this.instruction == "success" && firstBuild) {
                this.firstBuild = false;
                this.firstLoc = loc;
                return String.format("Player %d's turn to (optionally) build! Click a worker to skip.", super.getID());
            } else if (this.instruction == "success") this.firstBuild = true;
            return this.instruction;
        }
    }
    
}
