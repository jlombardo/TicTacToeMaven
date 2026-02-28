package edu.wctc.java.demo.tictactoe;

import edu.wctc.java.demo.tictactoe.domain.GameEngine;
import edu.wctc.java.demo.tictactoe.domain.Tile;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Smoke tests at the application level confirming that the domain objects
 * wire together without error.
 */
public class AppTest {

    @Test
    public void gameEngineCanBeInitialisedWithNineTiles() {
        Tile[] tiles = new Tile[9];
        for (int i = 0; i < 9; i++) {
            tiles[i] = new Tile();
        }
        GameEngine engine = new GameEngine();
        engine.initNewGame(tiles);
        assertSame(tiles, engine.getTiles());
        assertEquals(0, engine.getTilesPlayed());
    }
}
