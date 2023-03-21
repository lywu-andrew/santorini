package edu.cmu.cs214.hw3.player;

/**
 * Class for default player
 */
public class DefaultPlayer extends Player {

    /**
     * Creates a new {@link DefaultPlayer} instance.
     * Also stores attribute of its {@link Worker} objects' {@link Location}
     *
     * @param id Player id
     * @param w1 The player's first {@link Worker}
     * @param w2 The player's second {@link Worker}
     */
    public DefaultPlayer(int id, Worker w1, Worker w2) {
        super(id, w1, w2);
    }
}
