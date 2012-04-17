package edu.wctc.java.demo.tictactoe.domain;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author jlombardo
 */
public class RailTest {
    
    public RailTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * A rail containing 3 tiles that are each set
     * "X" should be a winner.
     */
    @Test
    public void railShouldBeWinnerIf3TilesMarkedX() {
        Tile[] tiles = new Tile[3];
        Tile tile = new Tile();
        tile.setText("X");
        tile.setSelected(true);
        tiles[0] = tile;
        tile = new Tile();
        tile.setText("X");
        tile.setSelected(true);
        tiles[1] = tile;
        tile = new Tile();
        tile.setText("X");
        tile.setSelected(true);
        tiles[2] = tile;
        Rail rail = new Rail(tiles);
        
        assertTrue(rail.isWinner());
    }

    /**
     * A rail containing 3 tiles that are each set
     * "0" should be a winner.
     */
    @Test
    public void railShouldBeWinnerIf3TilesMarked0() {
        Tile[] tiles = new Tile[3];
        Tile tile = new Tile();
        tile.setText("0");
        tile.setSelected(true);
        tiles[0] = tile;
        tile = new Tile();
        tile.setText("0");
        tile.setSelected(true);
        tiles[1] = tile;
        tile = new Tile();
        tile.setText("0");
        tile.setSelected(true);
        tiles[2] = tile;
        Rail rail = new Rail(tiles);
        
        assertTrue(rail.isWinner());
    }

    /**
     * A rail containing less than 3 tiles marked "0"
     * should not be a winner.
     */
    @Test
    public void railShouldNotBeWinnerIfLessThan3TilesMarked0() {
        Tile[] tiles = new Tile[3];
        Tile tile = new Tile();
        tile.setText("0");
        tile.setSelected(true);
        tiles[0] = tile;
        tile = new Tile();
        tile.setText("");
        tile.setSelected(false);
        tiles[1] = tile;
        tile = new Tile();
        tile.setText("0");
        tile.setSelected(true);
        tiles[2] = tile;
        Rail rail = new Rail(tiles);
        
        assertFalse(rail.isWinner());
    }

    /**
     * A rail containing less than 3 tiles marked "X"
     * should not be a winner.     */
    @Test
    public void railShouldNotBeWinnerIfLessThan3TilesMarkedX() {
        Tile[] tiles = new Tile[3];
        Tile tile = new Tile();
        tile.setText("");
        tile.setSelected(true);
        tiles[0] = tile;
        tile = new Tile();
        tile.setText("X");
        tile.setSelected(false);
        tiles[1] = tile;
        tile = new Tile();
        tile.setText("X");
        tile.setSelected(true);
        tiles[2] = tile;
        Rail rail = new Rail(tiles);
        
        assertFalse(rail.isWinner());
    }
}
