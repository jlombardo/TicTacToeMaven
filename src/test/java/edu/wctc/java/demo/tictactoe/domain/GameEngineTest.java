package edu.wctc.java.demo.tictactoe.domain;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests for GameEngine covering game initialisation, win/draw detection,
 * tile-play tracking, score accumulation, and all three AI difficulty levels.
 */
public class GameEngineTest {

    private GameEngine engine;
    private Tile[] tiles;

    @Before
    public void setUp() {
        engine = new GameEngine();
        tiles = new Tile[9];
        for (int i = 0; i < 9; i++) {
            tiles[i] = new Tile();
        }
        engine.initNewGame(tiles);
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /** Mark a tile by index — mirrors how GameWindow drives the domain layer. */
    private void mark(int idx, String mark) {
        tiles[idx].setText(mark);
    }

    /** Simulate the UI incrementing the played-tile counter n times. */
    private void playTiles(int n) {
        for (int i = 0; i < n; i++) {
            engine.incrementTilesPlayed();
        }
    }

    // -----------------------------------------------------------------------
    // initNewGame / basic state
    // -----------------------------------------------------------------------

    @Test
    public void newGameShouldHaveZeroTilesPlayed() {
        assertEquals(0, engine.getTilesPlayed());
    }

    @Test
    public void newGameShouldReturnTilesPassedIn() {
        assertSame(tiles, engine.getTiles());
    }

    @Test
    public void defaultSmartsShouldBeGeniusLevel() {
        assertEquals(100, engine.getSmarts());
    }

    @Test
    public void setSmartsShouldUpdateSmarts() {
        engine.setSmarts(50);
        assertEquals(50, engine.getSmarts());
    }

    // -----------------------------------------------------------------------
    // incrementTilesPlayed
    // -----------------------------------------------------------------------

    @Test
    public void incrementTilesPlayedShouldIncrementByOne() {
        engine.incrementTilesPlayed();
        assertEquals(1, engine.getTilesPlayed());
    }

    @Test
    public void incrementTilesPlayedShouldAccumulate() {
        playTiles(5);
        assertEquals(5, engine.getTilesPlayed());
    }

    // -----------------------------------------------------------------------
    // checkForWin
    // -----------------------------------------------------------------------

    @Test
    public void checkForWinReturnsFalseWhenNoWinner() {
        assertFalse(engine.checkForWin());
    }

    @Test
    public void checkForWinReturnsTrueWhenXWinsRow1() {
        mark(0, "X"); mark(1, "X"); mark(2, "X");
        assertTrue(engine.checkForWin());
        assertEquals("X", engine.getWinningPlayer());
        assertEquals(1, engine.getxWins());
    }

    @Test
    public void checkForWinReturnsTrueWhenXWinsRow2() {
        mark(3, "X"); mark(4, "X"); mark(5, "X");
        assertTrue(engine.checkForWin());
        assertEquals("X", engine.getWinningPlayer());
    }

    @Test
    public void checkForWinReturnsTrueWhenXWinsRow3() {
        mark(6, "X"); mark(7, "X"); mark(8, "X");
        assertTrue(engine.checkForWin());
        assertEquals("X", engine.getWinningPlayer());
    }

    @Test
    public void checkForWinReturnsTrueWhenXWinsColumn1() {
        mark(0, "X"); mark(3, "X"); mark(6, "X");
        assertTrue(engine.checkForWin());
        assertEquals("X", engine.getWinningPlayer());
    }

    @Test
    public void checkForWinReturnsTrueWhenXWinsLeftDiagonal() {
        mark(0, "X"); mark(4, "X"); mark(8, "X");
        assertTrue(engine.checkForWin());
        assertEquals("X", engine.getWinningPlayer());
    }

    @Test
    public void checkForWinReturnsTrueWhenXWinsRightDiagonal() {
        mark(2, "X"); mark(4, "X"); mark(6, "X");
        assertTrue(engine.checkForWin());
        assertEquals("X", engine.getWinningPlayer());
    }

    @Test
    public void checkForWinReturnsTrueWhen0WinsColumn1() {
        mark(0, "0"); mark(3, "0"); mark(6, "0");
        assertTrue(engine.checkForWin());
        assertEquals("0", engine.getWinningPlayer());
        assertEquals(1, engine.getoWins());
    }

    @Test
    public void checkForWinReturnsTrueWhen0WinsRow1() {
        mark(0, "0"); mark(1, "0"); mark(2, "0");
        assertTrue(engine.checkForWin());
        assertEquals("0", engine.getWinningPlayer());
        assertEquals(1, engine.getoWins());
    }

    @Test
    public void xWinsCountAccumulatesAcrossGames() {
        mark(0, "X"); mark(1, "X"); mark(2, "X");
        engine.checkForWin();
        assertEquals(1, engine.getxWins());

        // reset board tiles then start a new game (engine keeps running totals)
        for (Tile t : tiles) t.setText("");
        engine.initNewGame(tiles);
        mark(0, "X"); mark(1, "X"); mark(2, "X");
        engine.checkForWin();
        assertEquals(2, engine.getxWins());
    }

    @Test
    public void oWinsCountAccumulatesAcrossGames() {
        mark(0, "0"); mark(1, "0"); mark(2, "0");
        engine.checkForWin();
        assertEquals(1, engine.getoWins());

        for (Tile t : tiles) t.setText("");
        engine.initNewGame(tiles);
        mark(0, "0"); mark(1, "0"); mark(2, "0");
        engine.checkForWin();
        assertEquals(2, engine.getoWins());
    }

    // -----------------------------------------------------------------------
    // checkForDraw
    // -----------------------------------------------------------------------

    @Test
    public void checkForDrawReturnsFalseWhenBoardNotFull() {
        playTiles(8); // one short
        assertFalse(engine.checkForDraw());
    }

    @Test
    public void checkForDrawReturnsTrueWhenBoardFullAndNoWinner() {
        // X O X / O X O / O X O — no winning rails
        mark(0, "X"); mark(1, "0"); mark(2, "X");
        mark(3, "0"); mark(4, "X"); mark(5, "0");
        mark(6, "0"); mark(7, "X"); mark(8, "0");
        playTiles(9);
        assertTrue(engine.checkForDraw());
        assertEquals(1, engine.getDraws());
    }

    @Test
    public void checkForDrawReturnsFalseWhenThereIsAWinner() {
        mark(0, "X"); mark(1, "X"); mark(2, "X");
        playTiles(9);
        assertFalse(engine.checkForDraw());
    }

    @Test
    public void drawCountAccumulatesAcrossGames() {
        mark(0, "X"); mark(1, "0"); mark(2, "X");
        mark(3, "0"); mark(4, "X"); mark(5, "0");
        mark(6, "0"); mark(7, "X"); mark(8, "0");
        playTiles(9);
        engine.checkForDraw();
        assertEquals(1, engine.getDraws());

        for (Tile t : tiles) t.setText("");
        engine.initNewGame(tiles);
        mark(0, "X"); mark(1, "0"); mark(2, "X");
        mark(3, "0"); mark(4, "X"); mark(5, "0");
        mark(6, "0"); mark(7, "X"); mark(8, "0");
        playTiles(9);
        engine.checkForDraw();
        assertEquals(2, engine.getDraws());
    }

    // -----------------------------------------------------------------------
    // selectComputerMove — general contract
    // -----------------------------------------------------------------------

    @Test
    public void computerMoveShouldNotReturnNullOnEmptyBoard() {
        assertNotNull(engine.selectComputerMove());
    }

    @Test
    public void computerMoveReturnedTileShouldStillBeEmpty() {
        // selectComputerMove only recommends; it does not mark the tile
        Tile move = engine.selectComputerMove();
        assertFalse(move.isSelected());
    }

    // -----------------------------------------------------------------------
    // selectComputerMove — Genius level (smarts > 50)
    // -----------------------------------------------------------------------

    @Test
    public void geniusLevelShouldTakeWinningMove() {
        engine.setSmarts(100);
        mark(0, "0"); mark(1, "0"); // tiles[2] completes row 1 for "0"
        assertSame(tiles[2], engine.selectComputerMove());
    }

    @Test
    public void geniusLevelShouldBlockOpponentWin() {
        engine.setSmarts(100);
        mark(0, "X"); mark(1, "X"); // tiles[2] must be blocked
        assertSame(tiles[2], engine.selectComputerMove());
    }

    @Test
    public void geniusLevelReturnsEmptyTile() {
        engine.setSmarts(100);
        Tile move = engine.selectComputerMove();
        assertNotNull(move);
        assertFalse(move.isSelected());
    }

    // -----------------------------------------------------------------------
    // selectComputerMove — Smart level (smarts == 50)
    // -----------------------------------------------------------------------

    @Test
    public void smartLevelShouldTakeWinningMove() {
        engine.setSmarts(50);
        mark(0, "0"); mark(1, "0"); // tiles[2] completes row 1
        assertSame(tiles[2], engine.selectComputerMove());
    }

    @Test
    public void smartLevelShouldBlockOpponentWin() {
        engine.setSmarts(50);
        mark(0, "X"); mark(1, "X"); // tiles[2] must be blocked
        assertSame(tiles[2], engine.selectComputerMove());
    }

    @Test
    public void smartLevelPrefersCenterOnEmptyBoard() {
        engine.setSmarts(50);
        // tiles[4] is the center (R2C2); no threats exist yet
        assertSame(tiles[4], engine.selectComputerMove());
    }

    @Test
    public void smartLevelPreferencesCornerWhenCenterTaken() {
        engine.setSmarts(50);
        mark(4, "X"); // center occupied, no two-in-a-row threats
        Tile move = engine.selectComputerMove();
        // Should be one of the four corners: 0, 2, 6, 8
        boolean isCorner = move == tiles[0] || move == tiles[2]
                        || move == tiles[6] || move == tiles[8];
        assertTrue(isCorner);
    }

    @Test
    public void smartLevelReturnsEmptyTile() {
        engine.setSmarts(50);
        Tile move = engine.selectComputerMove();
        assertNotNull(move);
        assertFalse(move.isSelected());
    }

    // -----------------------------------------------------------------------
    // selectComputerMove — Easy level (smarts < 50)
    // -----------------------------------------------------------------------

    @Test
    public void easyLevelReturnsNonNullEmptyTile() {
        engine.setSmarts(0);
        Tile move = engine.selectComputerMove();
        assertNotNull(move);
        assertFalse(move.isSelected());
    }

    @Test
    public void easyLevelReturnsEmptyTileWhenBoardAlmostFull() {
        engine.setSmarts(0);
        // Fill 8 tiles, leaving only tiles[8] empty
        mark(0, "X"); mark(1, "0"); mark(2, "X");
        mark(3, "0"); mark(4, "X"); mark(5, "0");
        mark(6, "X"); mark(7, "0");
        Tile move = engine.selectComputerMove();
        assertNotNull(move);
        assertSame(tiles[8], move);
    }
}
