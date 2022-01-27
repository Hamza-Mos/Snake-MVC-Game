/*GameOverPanel.java
Panel that displays when snake collides
Created By: Ryan Li and Hamza
Date Modified: 01/22/2022
 */

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.Graphics;

public class GameOverPanel extends JPanel
{
    /**
     * Instance Variables
     */
    private final int width, height, scale;
    int applesEaten, highScore, roundsPlayed, roundsWon;
    Graphics2D g2d;
    private String difficulty;
    private SnakeModel model;

    /**
     * Default Constructor
     * @param width
     * @param height
     * @param scale
     * @param model
     */
    GameOverPanel(int width, int height, int scale, SnakeModel model)
    {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.model = model;
    }//end of constructor

    /**
     * Gets preferred size of GUI
     * @return
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }//end of method

    /**
     * Ensures GUI is painted when the window is moved or hidden.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Adding components
        paintGameOver();
        paintDifficulty();
        paintScore();
        paintHighScore();
        paintRoundsPlayed();
        paintPlayAgain();
    }//end of method

    /**
     * Paints round/game over title and results
     * of the game
     */
    public void paintGameOver()
    {
        //Setting colour and font
        g2d.setColor(Color.RED);
        Font font = new Font("Serif", Font.ITALIC, width / 10);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display either round or game over
        GlyphVector gv = font.createGlyphVector(frc, this.model.getGameOverStr() + " OVER");
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 4 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));
        //Displays game/round outcome
        font = new Font("Serif", Font.ITALIC, width / 15);
        gv = font.createGlyphVector(frc, "YOU " + this.model.getWinnerStr());
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * (float) 6.5 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));
    }//end of method

    /**
     * Paints the difficulty of game played
     */
    public void paintDifficulty()
    {
        //Setting colour and font
        g2d.setColor(Color.BLACK);
        Font font = new Font("Serif", Font.ITALIC, width / 25);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display Text
        GlyphVector gv = font.createGlyphVector(frc, "Difficulty: " + difficulty);
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 8 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));
    }//end of method

    /**
     * Paints score achieved on the round
     */
    public void paintScore()
    {
        //Setting font
        Font font = new Font("Serif", Font.ITALIC, width / 25);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display Text
        GlyphVector gv = font.createGlyphVector(frc, "Score: " + applesEaten);
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 10 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));
        gv = font.createGlyphVector(frc, "Target Score to Win: " + this.model.getDifficultyTarget());
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 12 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));
    }//end of method

    /**
     * Paints high score achieved on the game difficulty
     */
    public void paintHighScore()
    {
        //Setting Font
        Font font = new Font("Serif", Font.ITALIC, width / 25);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display Text
        GlyphVector gv = font.createGlyphVector(frc, "High Score: " + highScore);
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 14 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));
    }//end of method

    /**
     * Displays statistics of amount of rounds played and won
     */
    public void paintRoundsPlayed()
    {
        //Setting font
        Font font = new Font("Serif", Font.ITALIC, width / 25);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display Text
        GlyphVector gv = font.createGlyphVector(frc, "Rounds Played: " + roundsPlayed);
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 16 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));
        gv = font.createGlyphVector(frc, "Rounds Won: " + roundsWon);
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 18 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));
    }//end of method

    /**
     * Paints Play Again text with key input instructions
     */
    public void paintPlayAgain()
    {
        //Setting colour and font
        g2d.setColor(Color.RED);
        Font font = new Font("Serif", Font.ITALIC, width / 15);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display Text
        GlyphVector gv = font.createGlyphVector(frc, "Play Again? Y/N");
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 21 / 20 - ((int) gv.getVisualBounds().getHeight() / 2));
    }//end of method

    /**
     * Update display
     * @param difficulty
     * @param applesEaten
     * @param highScore
     * @param roundsPlayed
     * @param roundsWon
     */
    public void update(String difficulty, int applesEaten, int highScore, int roundsPlayed, int roundsWon)
    {
        this.difficulty = difficulty;
        this.applesEaten = applesEaten;
        this.highScore = highScore;
        this.roundsPlayed  = roundsPlayed;
        this.roundsWon = roundsWon;
    }//end of method
}//end of method
