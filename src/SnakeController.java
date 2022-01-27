/*
 * Class Name: SnakeController
 * Created By: Ryan Li and Hamza Mostafa
 * Last Modified: 1/21/2021
 * Purpose: Updates model based on user interactions with View (Controller Class)
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*;

public class SnakeController
{

	//Instance Variables
    private static int tps; 						//Ticks or Frames per second
    private static boolean buttonPressed = false;	//If user pressed an arrow key to change direction
    private static boolean isGameOver = false;		//If game is over
    private static boolean isNewGame = true;		//If it is a new game
    private static boolean isPaused = false;		//If the game is paused
    private boolean isChoosingDifficulty;			//If the user is currently choosing difficulty
    private boolean isChoosingRoundNumber;			//If the user is currently choosing number of rounds

    private static final SnakeModel model = new SnakeModel();	//Model
    private static ActionListener taskPerformer;				//Updates view based on timer
    private static Direction direction;							//Direction of snake
    private static Timer timer;									//Timer to refresh view

    //Starts refreshing the Snake's movement in the view
    public static void start()
    {
    	//Initialize task performer
        taskPerformer = (ActionEvent e) -> 
        {
            model.moveSnake();
            buttonPressed = false;
        };
        
        //Initialize Timer
        timer = new Timer(tps, taskPerformer);
        timer.setInitialDelay(0);
    }

    /**
     * Maps key presses.
     */
    public void directionInput(KeyEvent key) 
    {
    	//The code value of the key pressed
        int keyCode = key.getKeyCode();

        //First game user plays (displays start screen)
        if (isNewGame) 
        {
        	//Reset variable value
            isNewGame = false;
            
            //Choose difficulty
            model.chooseDifficulty();
            
            return;
        }
        
        //If difficulty menu is shown
        if (isChoosingDifficulty) 
        {
            switch (keyCode) 
            {
                case KeyEvent.VK_1: // 1 - Easy
                    setDifficulty(0);
                    break;
                    
                case KeyEvent.VK_2: // 2 - Medium
                    setDifficulty(1);
                    break;
                    
                case KeyEvent.VK_3: // 3 - Hard
                    setDifficulty(2);
                    break;
                    
                default: // No valid key is pressed
                    return;
            }
            
            //Reset choosing difficulty value
            isChoosingDifficulty = false;
            
            //Choose rounds
            model.chooseRounds();
            
            return;
        }
        
        //If round number choosing screen is shown
        if(isChoosingRoundNumber)
        {
        	switch(keyCode)
        	{
        		case KeyEvent.VK_1: // 1 Round
        			setRoundNumber(1);
        			break;
        			
        		case KeyEvent.VK_2: // 2 Rounds
        			setRoundNumber(2);
        			break;
        			
        		case KeyEvent.VK_3: // 3 Rounds
        			setRoundNumber(3);
        			break;
        			
        		case KeyEvent.VK_4: // 4 Rounds
        			setRoundNumber(4);
        			break;
        			
        		case KeyEvent.VK_5: // 5 Rounds
        			setRoundNumber(5);
        			break;
        			
        		default: // No valid key is entered
        			return;
        	}
        	
        	//Reset variable value
        	isChoosingRoundNumber = false;
        	
        	//Start gameplay
        	model.continueGame();
        	timer.start();
        	
        	return;
        }

        //If Game is over
        if (isGameOver) 
        {
        	//User chooses to play another game
            if (keyCode == KeyEvent.VK_Y) 
            {
            	//If all rounds are played then set up next game
                if(this.getRounds() <= 0)
                {
                	model.setRoundsPlayed(0);
                	model.chooseDifficulty();
                }
                
                //Play remaining rounds
                else
                {
                	model.continueGame();
                	timer.start();
                }
            } 
            
            //User chooses to quit game
            else if (keyCode == KeyEvent.VK_N) 
            {
                model.quit();
            } 
            
            //No valid key is entered
            else 
            {
                return;
            }
            
            //Reset variable value
            isGameOver = false;
            
            return;

        }

        //Game controls
        switch (key.getKeyCode()) 
        {
            case KeyEvent.VK_UP: //Up arrow
            	
            	//Change direction if user is not going down and no other arrow key is pressed
                if (direction != Direction.DOWN && !buttonPressed) 
                {
                    direction = Direction.UP;
                }
                
                buttonPressed = true;
                
                break;
                
            case KeyEvent.VK_DOWN: //Down Arrow
            	
            	//Change direction if user is not going up and no other arrow key is pressed
                if (direction != Direction.UP && !buttonPressed) 
                {
                    direction = Direction.DOWN;
                }
                
                buttonPressed = true;
                
                break;
                
            case KeyEvent.VK_LEFT: //Left Arrow
            	
            	//Change direction if user is not going right and no other arrow key is pressed
                if (direction != Direction.RIGHT && !buttonPressed) 
                {
                    direction = Direction.LEFT;
                }
                
                buttonPressed = true;
                
                break;
                
            case KeyEvent.VK_RIGHT: //Right Arrow
            	
            	//Change direction if user is not going left and no other arrow key is pressed
                if (direction != Direction.LEFT && !buttonPressed) 
                {
                    direction = Direction.RIGHT;
                }
                
                buttonPressed = true;
                
                break;
                
            case KeyEvent.VK_P: //P key to pause game
            	
            	//Pauses game
                if (!isPaused) 
                {
                    isPaused = true;
                    timer.stop();
                } 
                
                //Plays game if already paused
                else 
                {
                    isPaused = false;
                    timer.start();
                }
                
                break;
                
            default: //No valid key is entered
                break;
        	}

    	//If direction is already initialized then Model handles direction changes
        if (direction != null) 
        {
            model.setDirection(direction);
        }
    }
    
    //Set value for game over boolean variable
    public void setGameOver(boolean isGameOver) 
    {
        timer.stop();
        SnakeController.isGameOver = isGameOver;
    }

    //Set value for new game boolean variable
    public void setNewGame(boolean isNewGame) 
    {	
    	//Initialize direction with a random direction
    	Random random = new Random();
        direction = Direction.values()[random.nextInt(4)];
        
        //Set new Game value
        SnakeController.isNewGame = isNewGame;
    }

    //Set value for choosing difficulty boolean variable
    public void setChoosingDifficulty(boolean isChoosingDifficulty) 
    {
        timer.stop();
        this.isChoosingDifficulty = isChoosingDifficulty;
    }

    //User sets difficulty
    public void setDifficulty(int difficulty) 
    {
        switch (difficulty) 
        {
            case 0:	// 1 - Easy
                tps = 1000 / 8;
                timer = new Timer(tps, taskPerformer);
                break;
            
            case 1: // 2 - Medium
                tps = 1000 / 15;
                timer = new Timer(tps, taskPerformer);
                break;
                
            case 2: // 3 - Hard
                tps = 1000 / 20;
                timer = new Timer(tps, taskPerformer);
                break;
                
            default: // No valid key is entered
                break;
        }
        
        //Set difficulty for model
        model.setDifficulty(difficulty);
    }

    //Set value for choosing round number boolean variable
    public void setChoosingRoundNumber(boolean isChoosingRoundNumber)
    {
        timer.stop();
        this.isChoosingRoundNumber = isChoosingRoundNumber;
        
    }//end of method

    //Set number of rounds for model
    public void setRoundNumber(int round)
    {
    	model.setRounds(round);
    }
    
    //Returns game state in GameHeaderPanel when player presses 'P' changes game state from pause to play
    public static String getPauseStr()
    {
    	//Game is paused
        if(isPaused == true)
        {
            return "Play";
        }
        
        //Game is playing
        else
        {
            return "Pause";
        }
    }
    
    //Returns number of rounds remaining
    public int getRounds()
    {
    	return model.getRounds();
    }

    //Returns rounds played
	public static int getRoundsPlayed() 
	{
		return model.getRoundsPlayed();
	}
	
	//Returns target score required to win round
	public static int getTarget()
	{
		return model.getDifficultyTarget();
	}
}
