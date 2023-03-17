package edu.cmu.cs214.hw3;

import java.io.IOException;
import java.util.Map;

import edu.cmu.cs214.hw3.state.Location;
import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD
{
    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(8080);
        this.game = new Game();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/newgame")) {
            this.game = new Game();
        } else if (uri.equals("/play")) {
            // e.g., /play?x=x&y=y
            Location loc = new Location(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
            this.game.selectLocation(loc);
        }
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game, this.game.getInstruction());
        return newFixedLengthResponse(gameplay.toString());
    }
}
