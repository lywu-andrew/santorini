package main.java.edu.cmu.cs214.hw3.player;

import java.util.Hashtable;

public class Player {
    
    public int id;
    public Dictionary<Worker.id, Worker> workers;
    public Location[] workerPositions;

    public Player(int id) {
        this.id = id;
        Worker w1 = new Worker(1);
        Worker w2 = new Worker(2);
        this.workers = new Hashtable<>();
        this.workers.add(1, w1);
        this.workers.add(2, w2);
        this.workerPositions = new Location[this.workers.size()];
    }

    private Worker getWorker(Worker.id wid) {
        return this.workers.get(wid);
    }

    public Location getWorkerPosition(Worker.id wid) {
        Worker worker = getWorker();
        return worker.getPosition();
    }

    public Location[] getWorkerPositions() {
        return workerPositions;
    }

    // is this an adj location to the worker
    public boolean isAdjLocation(Worker.id wid, Location loc) {
        Worker worker = getWorker();
        Location pos = worker.getPosition();
        if (loc.adjacent(pos)) return true;
        else return false;
    }

    public void place(Worker.id wid, Location loc) {
        Worker worker = getWorker();
        worker.move(loc);
        this.workerPositions[wid - 1] = loc;
    }

}
