package main.java.edu.cmu.cs214.hw3.player;

import java.util.Dictionary;
import java.util.Hashtable;

import main.java.edu.cmu.cs214.hw3.state.Location;

public class Player {
    
    private Integer id;
    private Dictionary<Integer, Worker> workers;
    private Location[] workerPositions;

    public Player(int id, Worker w1, Worker w2) {
        this.id = id;
        this.workers = new Hashtable<>();
        this.workers.put(w1.getID(), w1);
        this.workers.put(w2.getID(), w2);
        this.workerPositions = new Location[this.workers.size()];
    }

    public Integer getID() {
        return this.id;
    }

    public Dictionary<Integer, Worker> getWorkers() {
        return this.workers;
    }

    public Location[] getWorkerPositions() {
        return workerPositions;
    }

    private Worker getWorker(Integer wid) {
        return this.workers.get(wid);
    }

    public Location getWorkerPosition(Integer wid) {
        Worker worker = getWorker(wid);
        return worker.getPosition();
    }

    // is this an adj location to the worker
    public boolean isAdjLocation(Integer wid, Location loc) {
        Worker worker = getWorker(wid);
        Location pos = worker.getPosition();
        if (loc.adjacent(pos)) return true;
        else return false;
    }

    public void place(Integer wid, Location loc) {
        Worker worker = getWorker(wid);
        worker.move(loc);
        this.workerPositions[wid - 1] = loc; // player id's start at 1, but arrays are 0 indexed
    }

}
