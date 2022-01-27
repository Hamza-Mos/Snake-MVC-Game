/*
 * Class Name: SnakeModel
 * Created By: Ryan Li and Hamza Mostafa
 * Last Modified: 1/23/2021
 * Purpose: Model class of the Snake Game (handles data and logic)
 */

import java.awt.*;
import java.util.*;

public class SnakeModel
{
	//Instance Variables
    private final int WIDTH = 900;							//Screen width
    private final int HEIGHT = 600;							//Screen height
    private final int SCALE = 15;							//Scale for the grid (how big is each unit on the grid)
    private final int GROWTH_SPURT = 8;						//Number of rectangles the snake starts with

    private final int MAX_INDEX_X = WIDTH / SCALE;			//Last column in the grid
    private final int MAX_INDEX_Y = HEIGHT / SCALE;			//Last row in the grid

    private int squaresToGrow;								//Squares that snake obtains after eating apple
    private int applesEaten = 0;							//Score
    private Direction direction = Direction.UP;				//Direction
    private int rounds = 0;									//Number of rounds user has chosen
    private int difficulty;									//Difficulty set by user
    private int roundsPlayed;								//Number of rounds played so far
    private final int [] difficultyTargets = {5, 10, 15};	//Target score for each difficulty
    private int currentGameTarget;							//Current target score
    private int roundsWon;									//Number of rounds won by user

    private final SnakeView view;							//View class
    private final Point apple = new Point();				//Apple (x,y)
    private final Random random = new Random();				//Random object
    private final Deque<Point> snakeBody = new ArrayDeque<>();					//Snake body
    private final Set<Point> occupiedPositions = new LinkedHashSet<Point>();	//Positions occupied by any Snake or Apple

    private final String[] difficulties = {"Easy", "Medium", "Hard"};			//Difficulties

    private final String HIGH_SCORE_EASY = "High Score " + difficulties[0] + ": ";		//Easy High Score
    private final String HIGH_SCORE_MEDIUM = "High Score " + difficulties[1] + ": ";	//Medium High Score
    private final String HIGH_SCORE_HARD = "High Score " + difficulties[2] + ": ";		//Hard High Score
    private final String ROUNDS_PLAYED = "Rounds Played: ";								//Number of rounds played
    private final String APPLES_EATEN = "Apples Eaten: ";								//Score

    private final String[] dataID = {HIGH_SCORE_EASY, HIGH_SCORE_MEDIUM,				//String data
            HIGH_SCORE_HARD, ROUNDS_PLAYED, APPLES_EATEN};								//Used for Panels

    private final int[] data = new int[dataID.length];									//Data of User (tracks score, games played, etc.)

    private final int TOTAL_GAMES_PLAYED_LOC = 3;										//Index of games played data
    private final int TOTAL_APPLES_EATEN_LOC = 4;										//Index of apples eaten data
    
    private String gameState = "ROUND";													//Game state

    //Constructor
    public SnakeModel()
    {
    	//Initialize occupied positions and create View
        occupiedPositions.add(apple);
        view = new SnakeView(WIDTH, HEIGHT, SCALE, snakeBody, apple, this);
    }

    /**
     * Generates snake at center of grid moving in upward direction.
     */
    private void generateSnakeAtCenter() 
    {
        int x = WIDTH / 2;
        int y = HEIGHT / 2;
        snakeBody.add(new Point(x, y));
        
        //Add Snake Head to HashSet
        occupiedPositions.add(snakeBody.getFirst());
        
        //Grow Snake with default squares
        squaresToGrow += GROWTH_SPURT;
    }

    /**
     * Generates an apple position randomly and avoids collisions.
     * 
     * Ensures that the Apple is created in an open space on the grid
     */
    private void generateApple() 
    {
    	// Coordinates of Apple
        int x = 0;
        int y = 0;
        
        //Checks if space is occupied by another object
        boolean spaceIsOccupied;
        
        //Generates random location
        do 
        {
            spaceIsOccupied = false;
            x = random.nextInt((int) MAX_INDEX_X) * SCALE;
            y = random.nextInt((int) MAX_INDEX_Y) * SCALE;
            
            //Checks if place is occupied
            for (Point point : occupiedPositions) 
            {
                if (point.getX() == x && point.getY() == y) 
                {
                    System.out.println("");
                    spaceIsOccupied = true; //if space is occupied
                }
                
            }
        } while (spaceIsOccupied);
        
        //Set new location
        apple.setLocation(x, y);
    }

    /**
     * Moves snake by moving tail position one grid square in front of the head
     * in the current direction. The translated tail is then dequeued before
     * being queued as the new head. If an apple is eaten, the snake is extended
     * by not dequeuing the tail.
     */
    public void moveSnake() 
    {

    	//Next set of coordinates of Snake in the next frame of the game
        int nextHeadX = (int) snakeBody.getFirst().getX();
        int nextHeadY = (int) snakeBody.getFirst().getY();

        //Move snake based on direction
        switch (direction) 
        {
            case UP:   	//Direction is Up
                nextHeadY -= SCALE;
                break;
                
            case DOWN: 	//Direction is Down
                nextHeadY += SCALE;
                break;
                
            case LEFT:	//Direction is Left
                nextHeadX -= SCALE;
                break;
                
            case RIGHT:	//Direction is Right
                nextHeadX += SCALE;
                break;
                
            default: //If Direction is null
                break;
        }

        //Checks if snake has collided if next square has an obstacle or wall
        if (collided(nextHeadX, nextHeadY)) 
        {
        	//Update high score and rounds played
            roundsPlayed++;
            data[difficulty] = Math.max(applesEaten, data[difficulty]);
            
            //Increment rounds won if the round is won
            if(this.hasWonRound())
            {
            	this.roundsWon++;
            }
            
            //Generate random direction for next round or game
            Random random = new Random();
            
            direction = Direction.values()[random.nextInt(4)]; 
            
            //Update view and set up Game Over Panel
            view.update(difficulties[difficulty], applesEaten, data[difficulty], roundsPlayed, roundsWon);
            view.gameOver();
        }

        //Moves the Tail of snake
        snakeBody.getLast().setLocation(nextHeadX, nextHeadY);
        
        //If Snake ate Apple
        if (ateApple()) 
        {
        	//Add new body rectangle and add new point to HashSet
            snakeBody.addFirst(new Point(nextHeadX, nextHeadY));
            occupiedPositions.add(snakeBody.getFirst());
            
            //Generate new Apple location
            generateApple();
            
            //Increment Score
            applesEaten++;
            data[TOTAL_APPLES_EATEN_LOC]++;
            
            //Add number of squares left to grow for Snake
            squaresToGrow += GROWTH_SPURT - 1;
        } 
        
        //If there are still more squares to grow for snake (should repeat until all squares are added_
        else if (squaresToGrow > 0) 
        {
        	//Add another tail square and Add coordinates to HashSet
            snakeBody.addFirst(new Point(nextHeadX, nextHeadY));
            occupiedPositions.add(snakeBody.getFirst());
            
            //Decrement SquaresToGrow
            squaresToGrow--;
        } 
        
        //Moves squares by replacing last set of squares with the elements before it in the Deque (repeats until all elements have moved)
        else 
        {
            snakeBody.addFirst(snakeBody.removeLast());
        }

        //Update View
        view.updateView(snakeBody, apple, difficulties[difficulty], data[difficulty], applesEaten);

    }
    
    //Returns Game Over String to GameOverPanel
    public String getGameOverStr()
    {
    	//Decrement number of rounds left
    	this.rounds--;
    	
    	//No Rounds are left - Game is over
    	if(this.rounds == 0)
    	{
    		this.gameState = "GAME";
    	}
    	
    	//There are still more rounds remaining - Round is over
    	else
    	{
    		this.gameState = "ROUND";
    	}
    	
    	//Return Game State
    	return this.gameState;
    }

    //Set direction for Snake
    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    //Check if Apple has been eaten
    private boolean ateApple()
    {
        return snakeBody.getFirst().equals(apple);
    }

    /**
     * Checks for snake collision with self and edges
     */
    private boolean collided(int nextHeadX, int nextHeadY) 
    {
    	//Snake Collision
        boolean snakeCollision = snakeBody.contains(new Point(nextHeadX, nextHeadY));
        
        //Wall Collisions
        boolean leftEdgeCollision = nextHeadX < 0;
        boolean rightEdgeCollision = nextHeadX >= WIDTH;
        boolean topEdgeCollision = nextHeadY < 0;
        boolean bottomEdgeCollision = nextHeadY >= HEIGHT;
        
        return snakeCollision || leftEdgeCollision || rightEdgeCollision || topEdgeCollision || bottomEdgeCollision;
    }

    //Continues to play game
    public void continueGame() 
    {
    	//Resets Model and Snake data
        clearModel();
        
        //Generates Snake and Apple
        generateSnakeAtCenter();
        generateApple();
        
        //Updates View and displays game
        view.updateView(snakeBody, apple, difficulties[difficulty], data[difficulty], applesEaten);
        view.continueGame();
        
        //Increments number of Games played
        data[TOTAL_GAMES_PLAYED_LOC]++;
    }

    //Displays RoundsPanel
    public void chooseRounds()
    {
        view.chooseRoundNumber();
    }

    //Sets number of rounds to play
    public void setRounds(int rounds)
    {
        this.rounds = rounds;
    }
    
    //Returns number of rounds left
    public int getRounds()
    {
    	return this.rounds;
    }

    //Displays DifficultyPanel
    public void chooseDifficulty() 
    {
        view.chooseDifficulty();
    }

    //Sets difficulty for game
    public void setDifficulty(int difficulty) 
    {
        this.difficulty = difficulty;
        
        this.currentGameTarget = difficultyTargets[difficulty];
        
        this.roundsWon = 0; //resets rounds won since game has restarted
    }
    
    //Checks if game has been won
    public boolean hasWonGame()
    {
    	//If User has won majority of their games (half or more of their games and has won at least 1 round)
    	if(this.roundsWon >= roundsPlayed/2 && this.roundsWon >= 1)
    	{
    		return true;
    	}
    	
    	//User has lost game
    	else
    	{
    		return false;
    	}
    }
    
    //Checks if round has been won
    public boolean hasWonRound()
    {
    	//If User was able to reach target score
    	if(this.applesEaten >= this.currentGameTarget)
    	{
    		return true;
    	}
    	
    	//Round Lost
    	else
    	{
    		return false;
    	}
    }

    //Clears Model and Snake data
    public void clearModel() 
    {
        occupiedPositions.clear();
        snakeBody.clear();
        apple.setLocation(0, 0);
        applesEaten = 0;
    }

    /**
     * Method to quit program (User chooses to stop playing)
     */
    public void quit() 
    {
        System.exit(0);
    }

    //Returns number of Rounds played
	public int getRoundsPlayed() 
	{
		return this.roundsPlayed;
	}
	
	//Sets number of rounds played
	public void setRoundsPlayed(int roundsPlayed)
	{
		this.roundsPlayed = roundsPlayed;
	}
	
	//Returns Winner String to GameOverPanel
	public String getWinnerStr()
	{
		//If more rounds are left to play
		if(this.gameState == "ROUND")
		{
			//Won Round
			if(this.hasWonRound())
			{
				return "WON THE ROUND :) !!";
			}
			
			//Lost Round
			else
			{
				return "LOST THE ROUND :( !!";
			}
		}
		
		//If Game is Over
		else
		{
			//Won Game
			if(this.hasWonGame())
			{
				return "WON THE GAME :) !!";
			}
			
			//Lost Game
			else
			{
				return "LOST THE GAME :( !!";
			}
		}
	}

	//Returns Score target for the set Difficulty
	public int getDifficultyTarget() 
	{
		return this.currentGameTarget;
	}
}
