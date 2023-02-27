package main.java.edu.cmu.cs214.hw3.player;

import java.util.Dictionary;
import java.util.Hashtable;

import main.java.edu.cmu.cs214.hw3.state.Location;

public class Player {
    
    public int id;
    public Dictionary<Integer, Worker> workers;
    public Location[] workerPositions;

    public Player(int id) {
        this.id = id;
        Worker w1 = new Worker(1);
        Worker w2 = new Worker(2);
        this.workers = new Hashtable<>();
        this.workers.put(1, w1);
        this.workers.put(2, w2);
        this.workerPositions = new Location[this.workers.size()];
    }

    private Worker getWorker(Integer wid) {
        return this.workers.get(wid);
    }

    public Location getWorkerPosition(Integer wid) {
        Worker worker = getWorker(wid);
        return worker.getPosition();
    }

    public Location[] getWorkerPositions() {
        return workerPositions;
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
