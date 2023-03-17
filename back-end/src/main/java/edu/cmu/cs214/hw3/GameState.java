package edu.cmu.cs214.hw3;

import java.util.Arrays;
import java.util.Collection;

import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.state.Tower;

public final class GameState {

    private final Cell[] cells;
    private final Integer winner;
    private final Integer playerTurn;
    private final String instruction;
    static final int GRID_SIZE = 5;

    private GameState(Cell[] cells, Integer winnerID, int turn, String instruction) {
        this.cells = cells;
        this.winner = winnerID;
        this.playerTurn = turn;
        this.instruction = instruction;
    }

    public static GameState forGame(Game game, String instruction) {
        Cell[] gridState = getGridState(game);
        Integer winner = null;
        if (game.getHasGameEnded()) winner = game.getPlayerTurn();
        return new GameState(gridState, winner, game.getPlayerTurn(), instruction);
    }

    public Cell[] getCells() {
        return this.cells;
    }

    public String getInstruction() {
        return this.instruction;
    }

    /**
     * toString() of GameState will return the string representing
     * the GameState in JSON format.
     */
    @Override
    public String toString() {
        if (this.winner == null) {
            return """
                    {
                        "cells": %s,
                        "playerTurn": %d,
                        "winner": null,
                        "instruction": "%s"
                    }
                    """.formatted(Arrays.toString(this.cells), this.playerTurn, this.instruction);
        } else {
            return """
                    {
                        "cells": %s,
                        "playerTurn": %d,
                        "winner": %d,
                        "instruction": "%s"
                    }
                   """.formatted(Arrays.toString(this.cells), this.playerTurn, 
                                this.winner, this.instruction);
        }
    }

    private static Cell[] getGridState(Game game) {
        Cell[] cells = new Cell[GRID_SIZE * GRID_SIZE];
        Grid grid = game.getGrid();
        Collection<Location> p1locs = game.getPlayer(1).getAllPositions();
        Collection<Location> p2locs = game.getPlayer(2).getAllPositions();
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                String text = "";
                boolean playable = false;
                Location loc = new Location(x, y);
                Tower tower = grid.getTower(loc);
                int level = tower.getLevel();
                boolean dome = tower.isDomed();
                for (int i = 0; i < level; i++) {
                    text += "[";
                }
                if (p1locs.contains(loc)) {
                    text += "X";
                    playable = true;
                } else if (p2locs.contains(loc)) {
                    text += "Y";
                    playable = true;
                } else if (dome) {
                    text += "O";
                } else {
                    text += " ";
                    playable = true;
                }
                for (int i = 0; i < level; i++) {
                    text += "]";
                }
                cells[GRID_SIZE * x + y] = new Cell(x, y, text, playable);
            }
        }
        return cells;
    }
}

class Cell {
    private final int x;
    private final int y;
    private final String text;
    private final boolean playable;

    Cell(int x, int y, String text, boolean playable) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.playable = playable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return this.text;
    }

    public boolean isPlayable() {
        return this.playable;
    }

    @Override
    public String toString() {
        return """
            {
                "text": "%s",
                "playable": %b,
                "x": %d,
                "y": %d
            }
            """.formatted(this.text, this.playable, this.x, this.y);
    }
}