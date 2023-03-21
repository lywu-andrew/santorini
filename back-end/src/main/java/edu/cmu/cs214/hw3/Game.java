package edu.cmu.cs214.hw3;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.player.godcards.Demeter;
import edu.cmu.cs214.hw3.player.godcards.Minotaur;
import edu.cmu.cs214.hw3.player.godcards.Pan;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

/**
 * Controller class for Santorini game. 
 * This implementation is assuming the players are switching off when interacting with the system.
 */
public class Game {

    private boolean hasGameEnded;
    private Integer playerTurn;
    private Player p1;
    private Player p2;
    private Grid grid;
    private Integer nextAction; // -1 = none, 0 = place workers, 1 = move, 2 = build, 3 = pick god card
    private Location selectedLoc;
    private boolean selected;
    private String instruction;

    private static final Integer PLACE_WRKS = 0;
    private static final Integer MOVE = 1;
    private static final Integer BUILD = 2;
    private static final Integer PICK_CARD = 3;

    /**
     * Creates a new {@link Game} instance, which contains the players.
     * Also stores attributes for if game has ended, the current player's turn,
     * next action, selected location, and current instruction.
     */
    public Game() {
        this.p1 = new Player(1, new Worker(1), new Worker(2));
        this.p2 = new Player(2, new Worker(1), new Worker(2));
        this.grid = new Grid();
        this.hasGameEnded = false;
        this.playerTurn = p1.getID();
        this.nextAction = PICK_CARD;
        this.selectedLoc = new Location(-1, -1);
        this.selected = false;
        this.instruction = "Player 1's turn to pick a card!";
        Player.getPlayers().put(p1.getID(), this.p1);
        Player.getPlayers().put(p2.getID(), this.p2);
    }

    public Integer getPlayerTurn() {
        return this.playerTurn;
    }

    public Player getPlayer(Integer id) {
        if (id == p1.getID()) return p1;
        return p2;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Integer getNextAction() {
        return this.nextAction;
    }

    private String getNextActionString() {
        if (this.nextAction == PICK_CARD) return "pick a card";
        else if (this.nextAction == PLACE_WRKS) return "place workers";
        else if (this.nextAction == MOVE) return "move";
        else if (this.nextAction == BUILD) return "build";
        else return "do nothing";
    }

    public Location getSelectedLoc() {
        return this.selectedLoc;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public String getInstruction() {
        return this.instruction;
    }

    public boolean getHasGameEnded() {
        return this.hasGameEnded;
    }

    private void nextPlayer() {
        if (this.playerTurn == p1.getID()) this.playerTurn = p2.getID();
        else this.playerTurn = p1.getID();
    }

    private void nextTurn() {
        if (this.nextAction == PICK_CARD && this.playerTurn == 2) this.nextAction = PLACE_WRKS;
        else if (this.nextAction == PLACE_WRKS && this.playerTurn == 2) this.nextAction = MOVE;
        else if (this.nextAction == MOVE) {
            this.nextAction = BUILD;
            return;
        } else if (this.nextAction == BUILD) this.nextAction = MOVE;
        nextPlayer();
    }

    private Player getCurrentPlayer() {
        if (this.playerTurn == p1.getID()) return p1;
        return p2;
    }

    /**
     * User can pick a god card.
     * 0 = Demeter, 1 = Minotaur, 2 = Pan, 3 = default Player
     *
     * @param num The {@link Integer} which represents the user's selection
     */
    public void pickCard(Integer num) {
        Player p = null;
        switch(num) {
            case 0: p = new Demeter(this.playerTurn, new Worker(1), new Worker(2)); break;
            case 1: p = new Minotaur(this.playerTurn, new Worker(1), new Worker(2)); break;
            case 2: p = new Pan(this.playerTurn, new Worker(1), new Worker(2)); break;
            default: p = new Player(this.playerTurn, new Worker(1), new Worker(2)); break;
        }
        if (this.playerTurn == this.p1.getID()) {
            this.p1 = p;
            Player.getPlayers().put(p1.getID(), this.p1);
        } else {
            this.p2 = p;
            Player.getPlayers().put(p2.getID(), this.p2);
        }
        nextTurn();
        this.instruction = String.format("Player %d's turn to %s!", playerTurn, this.getNextActionString());
    }

    /**
     * If first selection, stores location as selected. If second selection, performs corresponding action.
     * Sets instruction to next action if won, successful action, or user error
     *
     * @param loc The {@link Location} the user selects
     * @error If game has ended, there will be no action.
     */
    public void selectLocation(Location loc) {
        if (this.hasGameEnded) return;
        Player currPlayer = this.getCurrentPlayer();
        String res = this.instruction;
        if (this.nextAction == BUILD) {
            res = currPlayer.build(loc, this.grid);
        } else if (!this.selected) {
            this.selectedLoc = loc;
            this.selected = true;
        } else {
            if (this.nextAction == PLACE_WRKS) {
                res = currPlayer.placeWorkers(this.selectedLoc, loc, this.grid);
            } else if (this.nextAction == MOVE) {
                res = currPlayer.move(this.selectedLoc, loc, this.grid);
                if (currPlayer.checkWin(this.grid)) {    // check if current player has won
                    this.hasGameEnded = true;
                    this.nextAction = -1;
                    this.instruction = String.format("Player %d has won!", playerTurn);
                    return;
                }
            }
            this.selected = false;
        }
        if (res == "success") {
            nextTurn();                 // if player has not won on this turn, go to next action
            this.instruction = String.format("Player %d's turn to %s!", playerTurn, this.getNextActionString());
        } else {
            this.instruction = res;     // error message
        }
    }
}