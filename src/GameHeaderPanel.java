/*GameHeaderPanel.java
Game header panel when playing snake
Created By: Ryan Li and Hamza
Date Modified: 01/22/2022
 */

import java.awt.*;
import javax.swing.*;
import java.awt.font.GlyphVector;
import java.awt.font.FontRenderContext;
public class GameHeaderPanel extends JPanel
{
    /**
     * Instance Variables
     */
    private final int width, height, scale;
    private String difficulty, applesEaten, highScore;
    private Graphics2D g2d;

    /**
     * Default Constructor
     * @param width
     * @param height
     * @param scale
     */
    public GameHeaderPanel(int width, int height, int scale)
    {
        this.width = width;
        this.height = scale * 2;
        this.scale = scale;
    }//end of constructor

    /**
     * @return
     * Sets preferred size of panel
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }//end of method

    /**
     * @return
     * Sets background colour to black
     */
    @Override
    public Color getBackground()
    {
        return Color.black;
    }//end of method

    /**
     * Display components onto GUI
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        paintHeader();
        repaint();
    }//end of method

    /**
     * Creates a header on top of GamePanel that
     * displays statistics of the game
     */
    public void paintHeader()
    {
        //Setting colour and font
        g2d.setColor(Color.white);
        Font font = new Font("Serif", Font.ITALIC, scale);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display text
        GlyphVector text = font.createGlyphVector(frc,
                "Difficulty: " + difficulty + "    High Score on this difficulty: " + highScore
                        + "    Apples Eaten: " + applesEaten + "    Press \'P\' to " + SnakeController.getPauseStr()
                        + "    Target Apples to Win: " + SnakeController.getTarget()
                        + "    Round Number: " + (SnakeController.getRoundsPlayed() + 1));
        g2d.drawGlyphVector(text, width / 2 - ((int) text.getVisualBounds().getWidth() / 2), height - ((int) text.getVisualBounds().getHeight() / 2));
    }//end of method

    /**
     * @param difficulty
     * @param highScore
     * @param applesEaten
     * Updates statistics being displayed
     */
    public void update(String difficulty, int highScore, int applesEaten)
    {
        this.difficulty = difficulty;
        this.highScore = String.valueOf(highScore);
        this.applesEaten = String.valueOf(applesEaten);
    }//end of method
}//end of class
