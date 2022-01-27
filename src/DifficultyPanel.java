/*DifficultyPanel.java
Difficulty Panel selection for Snake
Created By: Ryan Li and Hamza
Date Modified: 01/22/2022
 */


import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class DifficultyPanel extends JPanel
{
    /**
     * Instance Variables
     */
    private final int width, height, scale;
    private Graphics2D g2d;

    /**
     * Default Constructor
     * @param width
     * @param height
     * @param scale
     */
    public DifficultyPanel(int width, int height, int scale)
    {
        this.width = width;
        this.height = height;
        this.scale = scale;
    }//end of constructor

    /**
     * Sets preferred size of panel
     * @return
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
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
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Adding components
        paintTitle();
        paintDifficulties();
    }//end of method

    /**
     * Creates the title for the difficulty panel
     */
    public void paintTitle()
    {
        //Setting colour and font
        g2d.setColor(Color.BLACK);
        Font font = new Font("Serif", Font.ITALIC, width / 10);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display Text
        GlyphVector gv = font.createGlyphVector(frc, "Difficulty");
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 2 / 5 - ((int) gv.getVisualBounds().getHeight() / 2));
    }//end of method

    /**
     * Creates the subtitles for the difficulties
     */
    public void paintDifficulties()
    {
        //Setting colour and font
        g2d.setColor(Color.RED);
        Font font = new Font("Serif", Font.ITALIC, width / 25);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display Text with key binds
        GlyphVector gv = font.createGlyphVector(frc, "1 - Easy");
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 5 / 10 - ((int) gv.getVisualBounds().getHeight() / 2));
        gv = font.createGlyphVector(frc, "2 - Medium");
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 6 / 10 - ((int) gv.getVisualBounds().getHeight() / 2));
        gv = font.createGlyphVector(frc, "3 - Hard");
        g2d.drawGlyphVector(gv, width / 2 - ((int) gv.getVisualBounds().getWidth() / 2), height * 7 / 10 - ((int) gv.getVisualBounds().getHeight() / 2));
    }//end of method
}//end of class
