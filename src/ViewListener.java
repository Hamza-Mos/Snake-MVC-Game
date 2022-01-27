
/*
 * Class Name: ViewListener
 * Created By: Ryan Li and Hamza Mostafa
 * Last Modified: 1/23/2021
 * Purpose: Determines keys pressed on the keyboard and updates Controller
 */

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class ViewListener implements KeyListener
{
	//Snake Controller
	private SnakeController controller = new SnakeController();

    public ViewListener()
    {
        super();
    }//end of method

    //Checks if key was pressed and updates Controller's direction
    public void keyPressed(KeyEvent key)
    {
        controller.directionInput(key);
        
    }//end of method

    /*
     * Required method for KeyListener Implementation
     */
    public void keyTyped(KeyEvent e)
    {
    }//end of method

    /*
     * Required method for KeyListener Implementation
     */
    public void keyReleased(KeyEvent e)
    {
    }//end of method

    //Sets Game Over boolean value for controller
    public void setGameOver(boolean isGameOver)
    {
        controller.setGameOver(isGameOver);
        
    }//end of method

    //Sets New Game boolean value for controller
    public void setNewGame(boolean isNewGame)
    {
        controller.setNewGame(isNewGame);
        
    }//end of method

    //Sets Choosing Difficulty boolean value for controller
    public void setChoosingDifficulty(boolean isChoosingDifficulty)
    {
    	controller.setChoosingDifficulty(isChoosingDifficulty);
    	
    }//end of method

    //Sets Choosing Round Number boolean value for controller
    public void setChoosingRoundNumber(boolean isChoosingRoundNumber)
    {
        controller.setChoosingRoundNumber(isChoosingRoundNumber);
        
    }//end of method

}//end of class
