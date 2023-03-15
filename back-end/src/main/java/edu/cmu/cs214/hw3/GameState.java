package edu.cmu.cs214.hw3;

import java.util.Arrays;
import java.util.Collection;

import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.state.Tower;

public class GameState {

    private final Cell[] cells;
    private final Integer winner;
    private final Integer playerTurn;
    private final Integer nextAction;

    private GameState(Cell[] cells, Integer winnerID, int turn, int nextAction) {
        this.cells = cells;
        this.winner = winnerID;
        this.playerTurn = turn;
        this.nextAction = nextAction;
    }

    public static GameState forGame(Game game) {
        Cell[] gridState = getGridState(game);
        Integer winner = null;
        if (game.getHasGameEnded()) winner = game.getPlayerTurn();
        return new GameState(gridState, winner, game.getPlayerTurn(), game.getNextAction());
    }

    public Cell[] getCells() {
        return this.cells;
    }

    public Integer getNextAction() {
        return this.nextAction;
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
                        "winner": null
                    }
                    """.formatted(Arrays.toString(this.cells), this.playerTurn);
        } else {
            return """
                    {
                        "cells": %s,
                        "playerTurn": %d,
                        "winner": %d
                    }
                   """.formatted(Arrays.toString(this.cells), this.playerTurn, 
                                this.winner);
        }
    }

    private static Cell[] getGridState(Game game) {
        Cell[] cells = new Cell[25];
        Grid grid = game.getGrid();
        Collection<Location> p1locs = game.getPlayer(1).getAllPositions();
        Collection<Location> p2locs = game.getPlayer(2).getAllPositions();
        for (int x = 0; x <= 5; x++) {
            for (int y = 0; y <= 5; y++) {
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
                } else if (p2locs.contains(loc)) {
                    text += "Y";
                } else if (dome) {
                    text += "O";
                } else {
                    text += " ";
                    playable = true;
                }
                for (int i = 0; i < level; i++) {
                    text += "]";
                }
                cells[5 * y + x] = new Cell(x, y, text, playable, level, dome);
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
    private final int level;
    private final boolean dome;

    Cell(int x, int y, String text, boolean playable, int level, boolean dome) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.playable = playable;
        this.level = level;
        this.dome = dome;
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

    public int getLevel() {
        return this.level;
    }

    public boolean hasDome() {
        return this.dome;
    }

    @Override
    public String toString() {
        return """
            {
                "text": "%s",
                "playable": %b,
                "x": %d,
                "y": %d,
                "level": %d,
                "dome": %b 
            }
            """.formatted(this.text, this.playable, this.x, this.y, this.level, this.dome);
    }
}