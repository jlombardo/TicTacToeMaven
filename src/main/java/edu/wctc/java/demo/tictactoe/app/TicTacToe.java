package edu.wctc.java.demo.tictactoe.app;

import edu.wctc.java.demo.tictactoe.ui.GameWindow;
import javax.swing.UIManager;

/**
 * The startup class for a classic Tic-Tac-Toe game.
 *
 * @author   Jim Lombardo, Lead Java Instructor, jlombardo@wctc.edu
 * @version  1.08
 */
public class TicTacToe {
    
    private TicTacToe() {}

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        
        try {
            // Normally we would set L&F to system, but on Mac the L&F
            // doesn't work right ... so just use Nimbus
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
         } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GameWindow().setVisible(true);
            }
        });
    }

}
