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
        } else if (uri.equals("/placeworkers")) {
            // e.g., /placeworkers?x1=x1&y1=y1&x2=x2&y2=y2
            Location loc1 = new Location(Integer.parseInt(params.get("x1")), Integer.parseInt(params.get("y1")));
            Location loc2 = new Location(Integer.parseInt(params.get("x2")), Integer.parseInt(params.get("y2")));
            this.game = this.game.placeWorkers(loc1, loc2);
        } else if (uri.equals("/move")) {
            // e.g., /move?x1=xcurr&y1=ycurr&x2=xnext&y2=ynext
            Location curr = new Location(Integer.parseInt(params.get("x1")), Integer.parseInt(params.get("y1")));
            Location next = new Location(Integer.parseInt(params.get("x2")), Integer.parseInt(params.get("y2")));
            this.game = this.game.move(curr, next);
        } else if (uri.equals("/build")) {
            // e.g., /build?x=x&y=y
            Location loc = new Location(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
            this.game = this.game.build(loc);
        }
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game);
        return newFixedLengthResponse(gameplay.toString());
    }
}
