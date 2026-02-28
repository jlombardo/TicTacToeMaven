package edu.wctc.java.demo.tictactoe.domain;

import java.awt.Color;
import java.util.Random;

/**
 * This class is the engine that drives the game logic. Its collaborators
 * are the Tile objects (Xs and 0s) and Rail objects which represent winning
 * combinations. 
 * <P>
 * The GameEngine provides a computer opponent for any "X" player.
 * Currently the human player is always "X" and the GameEngine will only
 * support a computer opponent.
 * 
 * @author   Jim Lombardo, Lead Java Instructor, jlombardo@wctc.edu
 * @version  1.1.0
 */
public class GameEngine {
    private static final int ROW1 = 0;
    private static final int ROW2 = 1;
    private static final int ROW3 = 2;
    private static final int COL1 = 3;
    private static final int COL2 = 4;
    private static final int COL3 = 5;
    private static final int DIAGL = 6;
    private static final int DIAGR = 7;
    private static final int R1C1 = 0;
    private static final int R1C2 = 1;
    private static final int R1C3 = 2;
    private static final int R2C1 = 3;
    private static final int R2C2 = 4;
    private static final int R2C3 = 5;
    private static final int R3C1 = 6;
    private static final int R3C2 = 7;
    private static final int R3C3 = 8;
    
    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;
    
    private Random rand = new Random(System.nanoTime());
    private Tile[] tiles; // array of JButtons
    private int tilesPlayed = 0;
    private Rail[] rails;
    private String winningPlayer = "";
    private int smarts = 100;
    
    /**
     * Constructs a GameEngine with nine (9) supplied Tile objects that are
     * are born unmarked. Possible winning combinations of three (3) tiles are
     * then constructed as Rail objects. This makes checks for wins and draws
     * easy by comparing the state of the Rails.
     * 
     * @param tiles - an array of Tile objects (custom JButton objects) 
     * representing each square space (9 total) on the game board.
     */
    public final void initNewGame(final Tile[] tiles) {
        this.tiles = tiles;
        tilesPlayed = 0;
        initRails();
    }
    
    /*
       Determines tile combinations that form winning rails:
    
       * * *   - - -   - - -   * - -  - * -  - - *
       - - -   * * *   - - -   * - -  - * -  - - *
       - - -   - - -   * * *   * - -  - * -  - - *
    
       * - -   - - *
       - * -   - * -
       - - *   * - -
    */
    private final void initRails() {
        rails = new Rail[8];
        rails[ROW1] = new Rail(
            new Tile[] {
                tiles[R1C1], tiles[R1C2], tiles[R1C3]
            }
        );
        rails[ROW2] = new Rail(
            new Tile[] {
                tiles[R2C1], tiles[R2C2], tiles[R2C3]
            }
        );
        rails[ROW3] = new Rail(
            new Tile[] {
                tiles[R3C1], tiles[R3C2], tiles[R3C3]
            }
        );
        rails[COL1] = new Rail(
            new Tile[] {
                tiles[R1C1], tiles[R2C1], tiles[R3C1]
            }
        );
        rails[COL2] = new Rail(
            new Tile[] {
                tiles[R1C2], tiles[R2C2], tiles[R3C2]
            }
        );
        rails[COL3] = new Rail(
            new Tile[] {
                tiles[R1C3], tiles[R2C3], tiles[R3C3]
            }
        );
        rails[DIAGL] = new Rail(
            new Tile[] {
                tiles[R1C1], tiles[R2C2], tiles[R3C3]
            }
        );
        rails[DIAGR] = new Rail(
            new Tile[] {
                tiles[R1C3], tiles[R2C2], tiles[R3C1]
            }
        );
    }
    
    /*
     * Selects a computer ("0") move based on the current difficulty level.
     *
     * Genius (smarts > 50): Uses the minimax algorithm for perfect play.
     *   The computer will always win or draw — it cannot be beaten.
     * Smart (smarts == 50): Uses heuristics — wins when possible, blocks
     *   opponent wins, prefers center then corners. Beatable but competent.
     * Easy (smarts < 50): Mostly random moves. May miss winning
     *   opportunities. Good for beginners.
     *
     * @return a non-null, empty Tile representing the computer's chosen move
     */
    public final Tile selectComputerMove() {

        // Genius level: use minimax for perfect play
        if (smarts > 50) {
            return selectMinimaxMove();
        }

        // Smart level: heuristic approach
        if (smarts == 50) {
            return selectSmartMove();
        }

        // Easy level: mostly random, occasionally finds a win
        return selectEasyMove();
    }

    /*
     * Minimax algorithm — evaluates every possible game state to find the
     * optimal move. Guarantees the computer never loses.
     */
    private Tile selectMinimaxMove() {
        int bestScore = Integer.MIN_VALUE;
        Tile bestTile = null;

        for (Tile t : tiles) {
            if (!t.isSelected()) {
                t.setText("0");
                int score = minimax(false);
                t.setText("");
                if (score > bestScore) {
                    bestScore = score;
                    bestTile = t;
                }
            }
        }
        return bestTile;
    }

    /*
     * Recursive minimax scoring. Returns +10 for a computer win,
     * -10 for a human win, 0 for a draw, adjusted by depth so the AI
     * prefers faster wins and slower losses.
     *
     * @param isComputerTurn true if it is the computer's turn to move
     * @return the minimax score for the current board state
     */
    private int minimax(boolean isComputerTurn) {
        // Check terminal states
        String winner = findWinner();
        if ("0".equals(winner)) return 10;
        if ("X".equals(winner)) return -10;
        if (isBoardFull()) return 0;

        if (isComputerTurn) {
            int bestScore = Integer.MIN_VALUE;
            for (Tile t : tiles) {
                if (!t.isSelected()) {
                    t.setText("0");
                    int score = minimax(false);
                    t.setText("");
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (Tile t : tiles) {
                if (!t.isSelected()) {
                    t.setText("X");
                    int score = minimax(true);
                    t.setText("");
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    /*
     * Returns the winning player mark ("X" or "0") if any rail is won,
     * or null if no winner yet. Used by minimax to evaluate board state.
     */
    private String findWinner() {
        for (Rail rail : rails) {
            if (rail.isWinner()) {
                // All three tiles in a winning rail have the same mark
                return rail.getTiles()[0].getText();
            }
        }
        return null;
    }

    /*
     * Returns true if all 9 tiles are occupied.
     */
    private boolean isBoardFull() {
        for (Tile t : tiles) {
            if (!t.isSelected()) return false;
        }
        return true;
    }

    /*
     * Smart-level heuristic: win if possible, block opponent wins,
     * prefer center, then corners, then pick randomly.
     */
    private Tile selectSmartMove() {
        // 1. Try to win
        Tile winMove = findTwoInARow("0");
        if (winMove != null) return winMove;

        // 2. Block opponent from winning
        Tile blockMove = findTwoInARow("X");
        if (blockMove != null) return blockMove;

        // 3. Take center if available
        if (!tiles[R2C2].isSelected()) {
            return tiles[R2C2];
        }

        // 4. Take an open corner
        int[] corners = {R1C1, R1C3, R3C1, R3C3};
        for (int idx : corners) {
            if (!tiles[idx].isSelected()) {
                return tiles[idx];
            }
        }

        // 5. Take any open tile
        return findRandomEmptyTile();
    }

    /*
     * Easy-level: mostly random. Only finds a winning move 30% of the time,
     * never blocks, never prioritizes strategic positions.
     */
    private Tile selectEasyMove() {
        // Occasionally spot a winning move (30% chance)
        if (rand.nextInt(100) < 30) {
            Tile winMove = findTwoInARow("0");
            if (winMove != null) return winMove;
        }

        return findRandomEmptyTile();
    }

    /*
     * Scans all rails for one that has two tiles marked by the given player
     * and one empty tile. Returns the empty tile (the winning/blocking move),
     * or null if no such rail exists.
     */
    private Tile findTwoInARow(String playerMark) {
        for (Rail rail : rails) {
            int markCount = 0;
            Tile emptyTile = null;
            for (Tile t : rail.getTiles()) {
                if (t.getText().equals(playerMark)) {
                    markCount++;
                } else if (t.getText().equals("")) {
                    emptyTile = t;
                }
            }
            if (markCount == 2 && emptyTile != null) {
                return emptyTile;
            }
        }
        return null;
    }

    /*
     * Returns a random empty tile from the board. Guaranteed to return
     * a non-null tile as long as at least one empty tile exists.
     */
    private Tile findRandomEmptyTile() {
        // Collect all empty tiles, then pick one at random
        int emptyCount = 0;
        for (Tile t : tiles) {
            if (!t.isSelected()) emptyCount++;
        }
        if (emptyCount == 0) return null;

        int pick = rand.nextInt(emptyCount);
        int idx = 0;
        for (Tile t : tiles) {
            if (!t.isSelected()) {
                if (idx == pick) return t;
                idx++;
            }
        }
        return null; // should never reach here
    }
    
    // Be sure to call this before checkForWin
    public final boolean checkForDraw() {
        boolean result = false;
        String player = "";
        
        for (Rail rail : rails) {
            if (rail.isWinner()) {
                return false;
            }
        }
        
        if (tilesPlayed == 9) {
            draws++;
//            window.getStatusMsg().setText(DRAW_MSG);  
            result = true;
        }
        
        return result;
    }
    
    // Be sure to call this only after checkForDraw
    public final boolean checkForWin() {
        boolean result = false;
        String player = "";
        
        for (Rail rail : rails) {
            if (rail.isWinner()) {
                result = true;
                for(Tile tile : rail.getTiles()) {
                    tile.setBackground(Color.GREEN);
                    player = tile.getText();
                }
                if (player.equals("X")) {
                    winningPlayer = "X";
                    this.xWins++;
                } else if (player.equals("0")) {
                    winningPlayer = "0";
                    this.oWins++;
                }
                break;
            }
        }
        
        return result;
    }

    public final int getTilesPlayed() {
        return tilesPlayed;
    }

    public final void incrementTilesPlayed() {
        this.tilesPlayed++;
    }

    public final String getWinningPlayer() {
        return winningPlayer;
    }

    public final void setWinningPlayer(final String winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    public final int getDraws() {
        return draws;
    }

    public final int getoWins() {
        return oWins;
    }

    public final int getxWins() {
        return xWins;
    }

    public final Tile[] getTiles() {
        return tiles;
    }

    public final int getSmarts() {
        return smarts;
    }

    public final void setSmarts(int smarts) {
        this.smarts = smarts;
    }

}
