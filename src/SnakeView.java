/*
 * Class Name: SnakeView
 * Created By: Ryan Li and Hamza Mostafa
 * Last Modified: 1/23/2021
 * Purpose: View class of the Snake Game (displays the appropriate JPanels for game)
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class SnakeView
{
    /**
     * Instance Variables
     */
    //Calling all panels
    private final GamePanel gamePanel;								//Game Panel with Snake
    private final GameHeaderPanel gameHeaderPanel;					//Game Header Panel that displays stats
    private final ViewListener viewListener = new ViewListener();   //Keyboard interactions
    private final GameOverPanel gameOverPanel;						//Game Over Panel
    private final NewGamePanel newGamePanel;						//New Game Panel
    private final DifficultyPanel difficultyPanel;					//Panel to choose difficulty
    private RoundsPanel roundsPanel;								//Panel to choose number of rounds
    private JFrame frame;											//JFrame for View
    private JPanel content;											//JPanel to display content
    private final int scale;										//Scale of grid
    private SnakeModel model;   									//Model class

    /**
     * Default Constructor
     * @param width
     * @param height
     * @param scale
     * @param snakeBody
     * @param apple
     * @param model
     */
    public SnakeView(int width, int height, int scale, Deque<Point> snakeBody, Point apple, SnakeModel model)
    {
        this.model = model;
        gamePanel = new GamePanel(width, height, scale, snakeBody, apple);
        newGamePanel = new NewGamePanel(width, height, scale);
        gameOverPanel = new GameOverPanel(width, height, scale, model);
        roundsPanel = new RoundsPanel(width, height, scale);
        difficultyPanel = new DifficultyPanel(width, height, scale);
        gameHeaderPanel = new GameHeaderPanel(width, height, scale);
        this.scale = scale;
        initGridView();
    }//end of constructor

    /**
     * Initializes GUI.
     */
    private void initGridView()
    {
        frame = new JFrame("Snake");
        frame.addKeyListener(viewListener);
        //Adding and initializing the panels
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(scale, scale, scale, scale));
        content.setBackground(Color.black);
        content.add(gameHeaderPanel);
        content.add(gamePanel);
        //Setting up JFrame
        frame.add(content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        newGame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }//end of method

    /**
     * Updates snake game view
     * @param snakeBody
     * @param apple
     * @param difficulty
     * @param highScore
     * @param applesEaten
     */
    public void updateView(Deque<Point> snakeBody, Point apple, String difficulty, int highScore, int applesEaten)
    {
        gamePanel.setSnakeBody(snakeBody);
        gameHeaderPanel.update(difficulty, highScore, applesEaten);
        gameHeaderPanel.repaint();
        gamePanel.repaint();
    }//end of method

    /**
     * Displays Game Over Panel
     */
    public void gameOver()
    {
        viewListener.setGameOver(true);
        content.removeAll();
        content.add(gameOverPanel);
        content.validate();
        content.repaint();
    }//end of method

    /**
     * Displays New Game Panel (Home screen)
     */
    public void newGame()
    {
        viewListener.setNewGame(true);
        content.removeAll();
        content.add(newGamePanel);
        content.validate();
        content.repaint();
    }//end of method

    /**
     * Displays rounds panel
     */
    public void chooseRoundNumber()
    {
        viewListener.setChoosingRoundNumber(true);
        content.removeAll();
        content.add(roundsPanel);
        content.validate();
        content.repaint();
    }//end of method

    /**
     * Displays difficulty panel
     */
    public void chooseDifficulty()
    {
        if(this.model.getRounds() <= 0)
        {
            viewListener.setChoosingDifficulty(true);
            content.removeAll();
            content.add(difficultyPanel);
            content.validate();
            content.repaint();
        }//end of if
    }//end of method

    /**
     * Displays snake game panel (Main game)
     */
    public void continueGame()
    {
        viewListener.setGameOver(false);
        viewListener.setNewGame(false);
        viewListener.setChoosingDifficulty(false);
        content.removeAll();
        content.add(gameHeaderPanel);
        content.add(gamePanel);
        content.validate();
        content.repaint();
    }//end of method

    /**
     * Updates statistics of the game
     * @param difficulty
     * @param applesEaten
     * @param highScore
     * @param roundsPlayed
     * @param roundsWon
     */
    public void update(String difficulty, int applesEaten, int highScore, int roundsPlayed, int roundsWon)
    {
        gameOverPanel.update(difficulty, applesEaten, highScore, roundsPlayed, roundsWon);
    }//end of method
}//end of method