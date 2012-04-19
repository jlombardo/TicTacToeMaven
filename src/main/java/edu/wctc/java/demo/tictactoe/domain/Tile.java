package edu.wctc.java.demo.tictactoe.domain;

import javax.swing.JButton;

/**
 * This class represents a space where an "X" or an "0" is selected. It is 
 * a custom JButton that knows if it's been selected or not.
 * 
 * @author   Jim Lombardo, Lead Java Instructor, jlombardo@wctc.edu
 * @version  1.01
 */
public class Tile extends JButton {
    private boolean selected;

    public Tile() {
    }
    
    /**
     * Sets the "X" or "0" mark on a Tile and the selected state of the Tile.
     * 
     * @param text - an "X" or an "0". Not validated because those are the 
     * only choices possible.
     */
    @Override
    public void setText(final String text) {
        super.setText(text);
        if(text == null) {
            selected = false;
            return;
        }
        selected = text.equals("X") || text.equals("0") ? true : false;
    }

    public final boolean isSelected() {
        return selected;
    }
}
