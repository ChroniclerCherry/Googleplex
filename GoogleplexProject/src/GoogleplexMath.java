import java.awt.*;

/**
 * Runs Math game that will allow the user to create their own
 * equations and check if they are right
 * 
 * @author James Huang, Yiminxue Zheng, Sarah El-Tohamy 
 * @since Friday, May 22, 2009
 */
public class GoogleplexMath extends GoogleplexBase{
	
	/**
	 * creates buttons, JLabels, and timer
	 * creates random numbers
	 * @param level 
	 */
	public GoogleplexMath(int level)
	{
		super(level, true);
		
		setTitle("Welcome to Googleplex Math Mode");		
	}//end constructor 
	
	/**
	 * Generate a random x position
	 * from the random numbers
	 * @return
	 */
	public int ranXPos ()
	{
		return getRandomNum(5, 600);	
	}//end ranXPos
	
	/**
	 * Generate a random y position
	 * from the random numbers
	 * @return
	 */
	public int ranYPos ()
	{
		return getRandomNum(0, 40) - 20;
	}//end ranYPos
	
	/**
	 * Initilze the buttons
	 */
	public void initButton(int buttonIndex)
	{
		String number = ""+randomNumList[buttonIndex];
		buttonList[buttonIndex].setText(number);
		buttonList[buttonIndex].setBounds(ranXPos(), 120+70*buttonIndex+ranYPos(), number.length()*35+50, 75);
		buttonList[buttonIndex].setEnabled(true);
		buttonList[buttonIndex].setFont(new Font ("Arial", Font.BOLD, 45));
	}// end initButton method
	
}// end class