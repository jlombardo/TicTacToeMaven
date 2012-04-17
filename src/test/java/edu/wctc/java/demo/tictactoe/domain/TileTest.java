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
}
