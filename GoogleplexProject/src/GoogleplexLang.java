import java.awt.*;
import java.util.ResourceBundle;
import javax.swing.*;

import java.awt.event.*;
/**
 * Runs Math game that will allow the user to create their own
 * equations and check if they are right.
 * The numbers are shown in word format.	//TODO
 * 
 * @author James Huang, Yiminxue Zheng, Sarah E-Tohamy 
 * @since Friday, May 22, 2009
 */
public class GoogleplexLang extends GoogleplexBase
{	
	//TODO comments for this variable.
	String text = "";
	
	public GoogleplexLang(int gameLevel)
	{
		super(gameLevel, false);
		
		setTitle("Welcome to Googleplex Language Mode");
	}
	
	/**
	 * This method chooses a random x position
	 * for displaying the random number.
	 * 
	 * @return	the x position
	 */
	public int ranXPos ()
	{
		if(0 == levelChosen)
		{
			return getRandomNum(5, 600);	//TODO
		}// end if
		
		else if (1 == levelChosen)
		{
			return getRandomNum(5, 450);	//TODO
		}// end else if
		
		else
		{
			return getRandomNum(5, 325);	//TODO
		}// end else
	}//end ranXPos
	
	/**
	 * This method chooses a random y position
	 * for displaying the random number.
	 * 
	 * @return	the y position
	 */
	public int ranYPos ()
	{
		return getRandomNum(0, 40) - 20;	//TODO
	}//end ranYPos
	
	/**
	 * Each number is actually a button with the word to be the label.
	 * This method initializes one button.
	 * 
	 * @param	the index of the button to be initialized
	 */
	public void initButton(int buttonIndex)
	{
		numWordList[buttonIndex] = res.getString(String.valueOf(randomNumList[buttonIndex]));
		String text = numWordList[buttonIndex];
		buttonList[buttonIndex].setText(""+text);
		//buttonList[buttonIndex].setBounds(ranXPos(), ranYPos(), text.length()*35, 25);
		buttonList[buttonIndex].setBounds(ranXPos(), 120+70*buttonIndex+ranYPos(), text.length()*20+50, 30);
		buttonList[buttonIndex].setEnabled(true);
		buttonList[buttonIndex].setFont(new Font ("Arial", Font.BOLD, 30));
	}// end initButton method
}// end class