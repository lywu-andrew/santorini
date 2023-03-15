package edu.cmu.cs214.hw3;

import java.util.Arrays;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.state.Tower;

public class GameState {

    private final Tower[][] cells;
    private final Integer winner;
    private final Integer playerTurn;

    private GameState(Tower[][] cells, Integer winnerID, int turn) {
        this.cells = cells;
        this.winner = winnerID;
        this.playerTurn = turn;
    }

    public static GameState forGame(Game game) {
        Tower[][] gridState = getGridState(game);
        Integer winner = null;
        if (game.getHasGameEnded()) winner = game.getPlayerTurn();
        return new GameState(gridState, winner, game.getPlayerTurn());
    }

    public Tower[][] getGridState() {
        return this.cells;
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
                                this.winner.value);
        }
    }

    private static Tower[][] getGridState(Game game) {
        Tower[][] gridState = new Tower[5][5];
        Grid grid = game.getGrid();
        for (int x = 0; x <= 5; x++) {
            for (int y = 0; y <= 5; y++) {
                String text = "";
                boolean playable = false;
                Location loc = new Location(x, y);
                Tower tower = grid.getTower(loc);
                int level = tower.getLevel();
                Tower newTower = new Tower(loc);
                for (int i = 0; i < level; i++) {
                    newTower.build();
                }
                if (tower.isDomed()) newTower.build();
                gridState[x][y] = newTower;
            }
        }
        Player p1 = game.getPlayer(1);
        Player p2 = game.getPlayer(2);
        for (Location loc: p1.getAllPositions()) {
            String text = "X";
            int row = loc.getRow();
            int col = loc.getCol();
        }
        for (Location loc: p2.getAllPositions()) {
            String text = "Y";
            int row = loc.getRow();
            int col = loc.getCol();
        }
        return gridState;

        /**
        if (player == Player.PLAYER0)
            text = "X";
        else if (player == Player.PLAYER1)
            text = "O";
        else if (player == null) {
            playable = true;
        }
        return cells;
        */
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