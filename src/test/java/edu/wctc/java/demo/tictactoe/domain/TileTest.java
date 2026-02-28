/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.java.demo.tictactoe.domain;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author jlombardo
 */
public class TileTest {
    private Tile tile;
    
    public TileTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        tile = new Tile();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void newTileShouldNotBeSelected() {
        assertFalse(tile.isSelected());
    }

    @Test
    public void newTileShouldHaveEmptyText() {
        // JButton default text is null; Tile doesn't set it, so getText() returns null or ""
        String text = tile.getText();
        assertTrue(text == null || text.isEmpty());
    }

    /**
     * Test of isSelected method, of class Tile.
     */
    @Test
    public void testIsSelected() {
        tile.setText("X");
        assertTrue(tile.isSelected());
        tile.setText("0");
        assertTrue(tile.isSelected());
        tile.setText("");
        assertFalse(tile.isSelected());
        tile.setText(null);
        assertFalse(tile.isSelected());
    }

    @Test
    public void setSelectedTrueShouldMarkTileSelected() {
        tile.setSelected(true);
        assertTrue(tile.isSelected());
    }

    @Test
    public void setSelectedFalseShouldMarkTileNotSelected() {
        tile.setText("X");
        tile.setSelected(false);
        assertFalse(tile.isSelected());
    }
}
