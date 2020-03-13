import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.event.*;

/**
 * Set up the game timer for the game
 * @author James Huang, Yimi Zheng, Sarah El-Tohamy, Usman Khan
 * @since Friday, May 15, 2009
 *
 */
public class GameTimer implements ActionListener
{
	//Global variables
	int seconds;	//seconds
	GoogleplexBase googleplexBase;	//a label which will draw the seconds
	int scoreFinal, userLevel;
	
	/**
	 * Construct a class with an amount of seconds
	 * and access to a label.
	 * 
	 * @param s The number of seconds.
	 * @param l The label which will draw the seconds.
	 */
	public GameTimer(int s, GoogleplexBase gp, int level)
	{
		seconds = s;
		googleplexBase = gp;
		userLevel = level;
	}//end game timer
	
	/**
	 * Check if the timer goes to 0. If it does then the timer will stop
	 * which will prevent it from going into the negatives.
	 * 
	 * @param e The action performed by the user.
	 */
	public void actionPerformed(ActionEvent e)
	{
		String userName = "";
		//make the timer go down by 1 every second
		seconds-=1;
		
		googleplexBase.timerLabel.setText(""+seconds);
		
		//if the time gets to 0
		if(seconds == 0)
		{
			((Timer)(e.getSource())).stop();	//stop the timer
			
			for(int i = 0; i < 9; i++)
			{
				googleplexBase.buttonList[i].setEnabled(false);
			}// end for loop
	
			googleplexBase.checkEquationButton.setEnabled(false);
			googleplexBase.resetButton.setEnabled(false);
						
			JOptionPane.showMessageDialog(googleplexBase, "Time is up!");
			//Get the User name for the high scores
			try
			{
				 userName = JOptionPane.showInputDialog("Enter Your Name: ");
				
				 //If User clicked "Cancel"
				 if(null == userName)
				 {
					 googleplexBase.dispose();
					 new GoogleplexMenu();
					 return;
				 }
				 
				 //If no name was entered, but "Ok" button was clicked
				 else if (0 == userName.length())
				 {
					 userName = "Unnamed";
				 }

				 //If a name was entered
				 while(userName.length()>=16)
				{
					JOptionPane.showMessageDialog(googleplexBase, "Enter a (shorter) Name");
					userName = JOptionPane.showInputDialog("Enter Your Name: ");
					
					if(null == userName)
					 {
						 // User clicked "Cancel".
						 googleplexBase.dispose();
						 new GoogleplexMenu();
						 return;
					 }
					 else if (0 == userName.length())
					 {
						 userName = "Unnamed";
					 }
				}	
			}// end try
			catch(Exception ec)
			{
				System.out.println(ec);
			}// end catch
			
			new HighScore((new Integer(googleplexBase.getScore())).toString(), userName, userLevel);
			googleplexBase.dispose();
		}
	}//end actionPerformed
}//end class