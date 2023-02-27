package main.java.edu.cmu.cs214.hw3.player;

import main.java.edu.cmu.cs214.hw3.state.Location;

public class Worker {
    
    public Integer id;
    private Location position;

    public Worker(Integer id) {
        this.id = id;
        this.position = new Location(-1, -1); // default: hasn't been placed
    }

    public Location getPosition() {
        return this.position;
    }

    public void move(Location newpos) {
        this.position = newpos;
    }
}