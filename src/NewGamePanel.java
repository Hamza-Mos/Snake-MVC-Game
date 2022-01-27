/*NewGamePanel.java
Home screen panel of snake
Created By: Ryan Li and Hamza
Date Modified: 01/22/2022
 */

import java.awt.*;
import javax.swing.*;
import java.awt.font.GlyphVector;
import java.awt.font.FontRenderContext;

public class NewGamePanel extends JPanel
{
    //Instance Variables
    private final int width, height, scale;	//Width, height, and scale of the game grid
    private Graphics2D g2d;					//Graphics 2D Object

    /**
     * Default Constructor
     * @param width
     * @param height
     * @param scale
     */
    public NewGamePanel(int width, int height, int scale)
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
        paintSubtitle();
    }//end of method

    /**
     * Create Snake game title
     */
    public void paintTitle()
    {
        //Setting colour and font
        g2d.setColor(Color.BLACK);
        Font font = new Font("Serif", Font.ITALIC, width / 10);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display Text
        GlyphVector text = font.createGlyphVector(frc, "Snake");
        g2d.drawGlyphVector(text, width / 2 - ((int) text.getVisualBounds().getWidth() / 2), height / 2 - ((int) text.getVisualBounds().getHeight() / 2));
    }//end of method

    /**
     * Create Snake game Subtitle
     */
    public void paintSubtitle()
    {
        //Setting colour and font
        g2d.setColor(Color.RED);
        Font font = new Font("Serif", Font.ITALIC, width / 25);
        FontRenderContext frc = g2d.getFontRenderContext();
        //Display Text
        GlyphVector text = font.createGlyphVector(frc, "Press any key to continue");
        g2d.drawGlyphVector(text, width / 2 - ((int) text.getVisualBounds().getWidth() / 2), height * 6 / 10 - ((int) text.getVisualBounds().getHeight() / 2));
    }//end of method
}//end of class
