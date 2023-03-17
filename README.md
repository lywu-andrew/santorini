## Starting a Game ##

1. Set Up Backend Server

Either run the Java backend by using your IDE or by typing 

```
mvn exec:exec
```
in the back-end folder. This will start the Java server at http://localhost:8080.

2. Set Up Frontend Server

In the front-end folder, run

```
npm install
npm start
```

This will start the front-end server at http://localhost:3000. You can update the front-end code as the server is running in the development mode (i.e., npm start). It will automatically recompile and reload.

3. Play the Game!

The instructions will be on the top bar, and you can start a new game anytime with the bottom button.

Place workers by clicking 2 distinct unoccupied grid locations.
Move by clicking a location with your worker on it and then clicking it to a valid move location.
Build by clicking a valid build location by one of your workers.

Note: a player only wins after their build turn
