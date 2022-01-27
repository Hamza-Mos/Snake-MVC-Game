/*GamePanel.java
The panel of the snake game
Created By: Ryan Li and Hamza
Date Modified: 01/22/2022
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GamePanel extends JPanel
{
    /**
     * Instance Variables
     */
    private Deque<Point> snakeBody; //Deque Array of the snake parts
    private final Point apple;
    private final int width, height, scale;
    private Color snakeColor = Color.GREEN;
    private Graphics2D g2d;

    /**
     * Default Constructor
     * @param width
     * @param height
     * @param scale
     * @param snakeBody2
     * @param apple
     */
    public GamePanel(int width, int height, int scale, Deque<Point> snakeBody, Point apple)
    {
        this.snakeBody = snakeBody;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.apple = apple;
    }//end of constructor

    /**
     * Sets the size of the panel
     * @return
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }//end of method

    /**
     * Sets the background for the snake game
     * @return
     */
    @Override
    public Color getBackground()
    {
        //Setting up colour of background
        return Color.DARK_GRAY;
    }//end of method

    /**
     * Displays componenets onto GUI
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Adding components
        paintDots();
        paintApple();
        paintSnake();
    }//end of method

    /**
     * Placements of the snake body using Deque
     * Deque: A queue where you may add and remove elements to and from both ends
     * @param snakeBody
     */
    public void setSnakeBody(Deque<Point> snakeBody)
    {
        this.snakeBody = snakeBody;
    }//end of method

    /**
     * Draws an apple on the grid of snake
     */
    public void paintApple()
    {
        int xPos = (int) apple.getX();
        int yPos = (int) apple.getY();
        g2d.setColor(Color.red);
        g2d.fillOval(xPos + 2, yPos + 2, scale - 4, scale - 3);
        g2d.fillOval(xPos + 4, yPos + 2, scale - 4, scale - 3);
    }//end of method

    /**
     * Draws the body of the snake
     */
    public void paintSnake()
    {
        int xPos, yPos;
        for (Point position : snakeBody) 
        {
            g2d.setColor(snakeColor);
            xPos = (int) position.getX();
            yPos = (int) position.getY();
            g2d.fillRect(xPos + 2, yPos + 2, scale - 4, scale - 4);
        }//end of loop
    }//end of method

    /**
     * Paints small circles around each grid intersection
     * for the user to have better visual of the snake
     */
    public void paintDots()
    {
        g2d.setColor(Color.ORANGE);
        for (int i = 0; i <= width / scale; i++)
        {
            for (int j = 0; j <= height / scale; j++)
            {
                if (i * scale == width)
                {
                    g2d.fillRect(i * scale - 1, j * scale, 1, 1);
                }//end of if
                
                else if (j * scale == height)
                {
                    g2d.fillRect(i * scale, j * scale - 1, 1, 1);
                }//end of else if
                
                else
                {
                    g2d.fillRect(i * scale, j * scale, 1, 1);
                }//end of else
                
            }//end of 2nd loop
        }//end of 1st loop
        g2d.fillRect(width - 1, height - 1, 1, 1);
    }//end of method
}//end of class
