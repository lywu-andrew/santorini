package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Worker w1;
    private Worker w2;
    private Worker w3;
    private Worker w4;
    private Player p1;
    private Player p2;

    @Before
    public void setUp() {
        w1 = new Worker(1);
        w2 = new Worker(2);
        p1 = new Player(1, w1, w2);
        w3 = new Worker(1);
        w4 = new Worker(2);
        p2 = new Player(2, w3, w4);
        game = new Game(p1, p2);
    }

    @Test
    public void testPlaceWorkers() {
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc2));
        Location[] p1wpos = p1.getWorkerPositions();
        assertTrue(p1wpos[0].equals(loc1));
        assertTrue(p1wpos[1].equals(loc2));
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        assertTrue(grid.isOccupied(loc3));
        assertTrue(grid.isOccupied(loc4));
        Location[] p2wpos = p2.getWorkerPositions();
        assertTrue(p2wpos[0].equals(loc3));
        assertTrue(p2wpos[1].equals(loc4));
    }
}

/*
 * import java.util.List;

public class AchievementTest {

    private AchievementChecker ac;
    private CardDeck cardDeck;

    public CardDeck reset(CardDeck cd) {
        for (CardStatus card : cardDeck.getCards()) {
            card.clearResults();
        }
        return cardDeck;
    }

    @Before
    public void setUp() throws IOException {
        ac = new AchievementChecker();
        File f = new File("cards/designpatterns.csv");
        CardStore cards = new CardLoader().loadCardsFromFile(f);
        cardDeck = new CardDeck(cards.getAllCards(), new RepeatingCardOrganizer(10));
    }

    @Test
    public void testGetStartTime() {
        assertTrue(ac.getStartTime() == 0);
    }

    @Test
    public void testNewStartTime() {
        ac.beginRound();
        assertTrue(ac.getStartTime() != 0);
    }

    @Test
    public void testCorrectAchievementTypeCorrect() {
        cardDeck = reset(cardDeck);
        for (CardStatus card : cardDeck.getCards()) {
            card.recordResult(true);
        }
        assertTrue(AchievementType.CORRECT.isAchieved(ac.getStartTime(), cardDeck));
    }

    @Test
    public void testCorrectAchievementTypeIncorrect() {
        cardDeck = reset(cardDeck);
        for (CardStatus card : cardDeck.getCards()) {
            card.recordResult(false);
        }
        assertTrue(!AchievementType.CORRECT.isAchieved(ac.getStartTime(), cardDeck));
    }

    @Test
    public void testSpeedAchievementTypeCorrect() {
        cardDeck = reset(cardDeck);
        ac.beginRound();
        assertTrue(AchievementType.SPEED.isAchieved(ac.getStartTime(), cardDeck));
    }

    @Test
    public void testSpeedAchievementTypeIncorrect() {
        AchievementChecker tempac = new AchievementChecker();
        cardDeck = reset(cardDeck);
        assertTrue(!AchievementType.SPEED.isAchieved(tempac.getStartTime(), cardDeck));
    }

    @Test
    public void testRepeatAchievementTypeCorrect() {
        cardDeck = reset(cardDeck);
        for (CardStatus card : cardDeck.getCards()) {
            for (int i = 0; i < 6; i++) card.recordResult(true);
        }
        assertTrue(AchievementType.REPEAT.isAchieved(ac.getStartTime(), cardDeck));
    }


    @Test
    public void testRepeatAchievementTypeIncorrect() {
        cardDeck = reset(cardDeck);
        for (CardStatus card : cardDeck.getCards()) {
            card.recordResult(true);
        }
        assertTrue(!AchievementType.REPEAT.isAchieved(ac.getStartTime(), cardDeck));
    }

    @Test
    public void testCheckNewAchievementsAll() {
        cardDeck = reset(cardDeck);
        for (CardStatus card : cardDeck.getCards()) {
            for (int i = 0; i < 6; i++) card.recordResult(true);
        }
        ac.beginRound();
        List<String> achievements = ac.checkNewAchievements(cardDeck);
        assertTrue(achievements.size() == 3);
        assertTrue(achievements.contains(AchievementType.CORRECT.toString()));
        assertTrue(achievements.contains(AchievementType.SPEED.toString()));
        assertTrue(achievements.contains(AchievementType.REPEAT.toString()));
        List<String> results = ac.checkNewAchievements(cardDeck);
        assertTrue(results.size() == 0);
    }

    @Test
    public void testCheckNewAchievementsNone() {
        cardDeck = reset(cardDeck);
        for (CardStatus card : cardDeck.getCards()) {
            card.recordResult(false);
        }
        AchievementChecker tempac = new AchievementChecker();
        List<String> achievements = tempac.checkNewAchievements(cardDeck);
        assertTrue(achievements.size() == 0);
    }
}
 */