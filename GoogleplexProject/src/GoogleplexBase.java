import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Main part of the game where most of the logic is
 * it is used for both Math and Language mood
 * @author James Huang, Yimi Zheng, Sarah El-Tohamy 
 * @since Friday, May 1, 2009
 */
public abstract class GoogleplexBase extends JFrame implements ActionListener
{
	//Global variables
	int operationSign, num1 = -1, num2 = -1, num3 = -1, bonusPoints;
	static int score;
	JButton	resetButton, checkEquationButton, exitButton;
	JLabel backgroundLabel, num1Label, num2Label, num3Label, operationLabel, equalLabel, checkEquationLabel, timerLabel,
	bonus1Label, bonus2Label, bonus3Label, scoreLabel;
	ImageIcon checkEquationIcon, levelIcon;
	boolean isThereBonus = false; 
	boolean isPlayingMath, isTimerActive = false;
	JButton [] buttonList = new JButton[9];
	int [] randomNumList = new int [9];
	int levelChosen;
	Timer timer;
	ResourceBundle res;		//resource file
	String [] numWordList = new String [9];
	
	/**
	 * constructor method
	 * creates buttons, JLabels, and timer
	 * creates random numbers
	 * 
	 * @param level The Level that was chosen in GoogleplexMenu class
	 */
	public GoogleplexBase(int level, boolean playingMath)
	{
		super();
		
		isPlayingMath = playingMath;
		
		res = ResourceBundle.getBundle("Resource"); //first line
				
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(Color.WHITE);		
		setSize(1200, 900);
		
		//makes the level passed through from main menu into global variable
		levelChosen = level;
			
		//goes to method which randomly selects an operation sign depending on
		//difficulty selected
		operationSign = getOperation(levelChosen);
		
		//Creates random numbers based on level selected
		for(int i = 0; i < 9; i++)
		{
			if(level == 0 || level == 1 || level == 3)
			{
				randomNumList[i] = getRandomNum(1,9);
			}
			else if(level == 2)
			{
				randomNumList[i] = getRandomNum(1,12);
			}
		}
		
		//Randomly picks three numbers that are different from each other to use to make a viable equation
		int temp = getRandomNum(0,8);
		int temp2 = getRandomNum(0,8);
		int temp3 = getRandomNum(0,8);
		while(temp == temp2)
		{
			temp2 = getRandomNum(0,8);
		}
		while(temp2 == temp3 || temp == temp3)
		{
			temp3 = getRandomNum(0,8);
		}
		// Will make sure there will be at least one viable equation
		switch (operationSign)
		{		
			case 1:
			case 2:
			randomNumList[temp] = randomNumList[temp2] + randomNumList[temp3];
			break;
			case 3:
			case 4:
			randomNumList[temp] = randomNumList[temp2] * randomNumList[temp3];
		}
		
		//Creates the buttons on which the random numbers are on
		//will also set the text of the button to the word version of that number
		for(int i = 0; i < 9; i++)
		{
			buttonList[i] = new JButton();
						
			initButton(i);
			
			buttonList[i].setBackground(Color.black);
			buttonList[i].setOpaque(false);
			buttonList[i].setBorderPainted(false);
			buttonList[i].setForeground(Color.BLUE);
			buttonList[i].addActionListener(this);
			c.add(buttonList[i]);
		}
		
		score = 0;
	
		//Creates label for the first number user enters
		num1Label = new JLabel();
		num1Label.setBounds(815, 525, 90, 60);
		num1Label.setFont(new Font("Arial", Font.BOLD, 45));
		c.add(num1Label);
		
		//Creates label to display operation sign chosen
		operationLabel = new JLabel(operationSign (operationSign));
		operationLabel.setBounds(890, 520, 60, 60);
		operationLabel.setFont(new Font("Arial", Font.BOLD, 50));
		operationLabel.setHorizontalAlignment(JLabel.CENTER);
		//operationLabel.setForeground(new Color(18, 109, 18));
		c.add(operationLabel);
		
		//Creates label for the second number the user enters
		num2Label = new JLabel();
		num2Label.setBounds(935, 525, 90, 60);
		num2Label.setFont(new Font("Arial", Font.BOLD, 45));
		c.add(num2Label);
				
		//Creates label for the third number the user enters
		num3Label = new JLabel();
		num3Label.setBounds(1070, 525, 90, 60);
		num3Label.setFont(new Font("Arial", Font.BOLD, 45));
		c.add(num3Label);
		
		//Creates the reset button
		resetButton = new JButton();
		resetButton.setBounds(890, 720, 250, 60);
		ImageIcon resetIcon = new ImageIcon("Images/Clear Button.png");
		resetButton.setIcon(resetIcon);
		resetButton.setOpaque(false);
		resetButton.setBorderPainted(false);
		resetButton.setBackground(new Color(0,0,0));
		resetButton.addActionListener(this);
		c.add(resetButton);
		
		//Creates the check button
		checkEquationButton = new JButton();
		checkEquationButton.setBounds(890, 640, 250, 60);
		ImageIcon checkIcon = new ImageIcon("Images/Check Button.png");
		checkEquationButton.setIcon(checkIcon);
		checkEquationButton.setOpaque(false);
		checkEquationButton.setBorderPainted(false);
		checkEquationButton.setBackground(new Color(0,0,0));
		checkEquationButton.addActionListener(this);
		checkEquationButton.setEnabled(false);
		c.add(checkEquationButton);
		
		//Creates label to display image
		checkEquationLabel = new JLabel();
		checkEquationLabel.setBounds(720, 525, 95, 95);
		c.add(checkEquationLabel);
		
		//Creates exit button
		exitButton = new JButton();
		exitButton.setBounds(890, 90, 250, 60);
		ImageIcon exitIcon = new ImageIcon("Images/Exit Button.png");
		exitButton.setIcon(exitIcon);
		exitButton.setOpaque(false);
		exitButton.setBorderPainted(false);
		exitButton.setBackground(new Color(0,0,0));
		exitButton.addActionListener(this);
		c.add(exitButton);
		
		//Creates label to store bonus number
		bonus1Label = new JLabel();
		bonus1Label.setBounds(830, 365, 90, 60);
		bonus1Label.setHorizontalAlignment(bonus1Label.CENTER);
		bonus1Label.setFont(new Font ("Arial", Font.BOLD, 40));
		c.add(bonus1Label);
		
		//Creates label to store bonus number
		bonus2Label = new JLabel();
		bonus2Label.setBounds(945, 365, 90, 60);
		bonus2Label.setHorizontalAlignment(bonus2Label.CENTER);
		bonus2Label.setFont(new Font ("Arial", Font.BOLD, 40));
		c.add(bonus2Label);
		
		//Creates label to store bonus number
		bonus3Label = new JLabel();
		bonus3Label.setBounds(1060, 365, 90, 60);
		bonus3Label.setHorizontalAlignment(bonus3Label.CENTER);
		bonus3Label.setFont(new Font ("Arial", Font.BOLD, 40));
		c.add(bonus3Label);
		
		//Creates label to display timer
		timerLabel = new JLabel();
		timerLabel.setBounds(880, 220, 200, 50);
		timerLabel.setFont(new Font ("Arial", Font.BOLD, 40));
		c.add(timerLabel);
		
		//Creates label to display score
		scoreLabel = new JLabel("" + score);
		scoreLabel.setBounds(1060, 220, 200, 50);
		scoreLabel.setFont(new Font ("Arial", Font.BOLD, 40));
		c.add(scoreLabel);
		
		//Creates label that will display image based on difficulty
		JLabel levelImageLabel = new JLabel();
		levelImageLabel.setBounds(100, 790, 585, 70);
		
		//Determines the level ImageIcon that will be displayed 
		if(levelChosen == 0)
		{
			levelIcon = new ImageIcon("Images/easy.png");
		}
		else if(levelChosen == 1)
		{
			levelIcon = new ImageIcon("Images/medium.png");
		}
		else if(levelChosen == 2)
		{
			levelIcon = new ImageIcon("Images/hard.png");
		}
		else if(levelChosen == 3)
		{
			levelIcon = new ImageIcon("Images/Practise.png");
		}//end if
		
		//Adds the level ImageIcon
		levelImageLabel.setIcon(levelIcon);
		c.add(levelImageLabel);
		
		//Adds background label
		backgroundLabel = new JLabel();
		backgroundLabel.setBounds(0, 0, 1290, 900);
		
		//Sets Math ImageIcon
		ImageIcon backgroundImage = new ImageIcon("Images/Googleplex Math.jpg");
		
		//If they choose to play Language mood, sets Language ImageIcon
		if(false == isPlayingMath)
		{
			backgroundImage = new ImageIcon("Images/Googleplex Language.jpg");
		}
		backgroundLabel.setIcon(backgroundImage);
		backgroundLabel.setOpaque(true);
		c.add(backgroundLabel);
		
		//Starts timer if not on practise mode
		if (levelChosen < 3)
		{
		timer = new Timer (1000, new GameTimer(120, this, levelChosen));
		timer.start();
		isTimerActive = true;
		}
		else
		{
			timerLabel.setText("X");
		}//end if
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Rotate window
		for(int i = 0; i<4; i++)
		{
			Robot r = null;
			try
			{
			r = new Robot();
			}
			catch(AWTException e)
			{
				e.printStackTrace();
			}
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_SHIFT);
			r.keyPress(KeyEvent.VK_R);
			r.keyRelease(KeyEvent.VK_ALT);
			r.keyRelease(KeyEvent.VK_SHIFT);
			r.keyRelease(KeyEvent.VK_R);
		}//end Robot		
	}//end constructor 

	/**
	 * will allow user to exit game with the exit button
	 * will allow the numbers to enter the boxes when clicked
	 * will allow user to clear the boxes
	 * will allow the user to check if their equation is right
	 */
	public void actionPerformed(ActionEvent e) 
	{				
		//Will close the JFrame if user clicks the exit button
		//Displays the main menu
		if (e.getSource()==exitButton)
		{
			if(isTimerActive)
			{
				timer.stop();
			}
			dispose();
			new GoogleplexMenu();
		}
		
		//Will reset the number boxes
		if (e.getSource()==resetButton)
		{
			num1 = -1;
			num2 = -1;
			num3 = -1;
			num1Label.setText("");
			num2Label.setText("");
			num3Label.setText("");
			
			//Will re-enable all the number buttons
			for(int i = 0; i < 9; i++)
			{
				buttonList[i].setEnabled(true);
			}
			checkEquationButton.setEnabled(false);
		}//end if
		
		//Check if the equation is right when the check button is clicked
		//give points if equation is correct
		//clear bonus boxes if equation is incorrect
		if (e.getSource()==checkEquationButton)
		{
			int num1 = Integer.parseInt(num1Label.getText());
			int num2 = Integer.parseInt(num2Label.getText());
			int num3 = Integer.parseInt(num3Label.getText());
			
			//Goes to method to check is equation is correct
			boolean correctEquation = checkEquation(num1, num2, num3);
			
			if (correctEquation)
			{							
				//End bonus points if applicable
				if(isThereBonus)
				{
					score += getBonusPoints();
				}
				//Add points
				score++;
				//Clear the boxes
				bonus1Label.setText(""+num1);
				bonus2Label.setText(""+num2);
				bonus3Label.setText(""+num3);
				isThereBonus = true;
				//Changes image to a check mark
				checkEquationIcon = new ImageIcon("Images/checkmark.png");
				checkEquationLabel.setIcon(checkEquationIcon);
				//Get new random numbers and locations
				refresh();
			}//end if 
			
			else		//if equation is not right
			{
				bonus1Label.setText("");
				bonus2Label.setText("");
				bonus3Label.setText("");
				isThereBonus = false;
				//changes image to an x
				checkEquationIcon = new ImageIcon("Images/xmark.png");
				checkEquationLabel.setIcon(checkEquationIcon);
			}// end else
			scoreLabel.setText("" + score);
		}//end if 
		
		//Will check which number the user clicked
		//and places it in the first available box
		for(int i = 0; i <9; i++)
		{
			if(e.getSource()==buttonList[i])
			{
				if (num1 == -1)
				{
					num1 = randomNumList[i];
				}
				else if (num2 == -1)
				{
					num2 = randomNumList[i];
				}
				else if (num3 == -1)
				{
					num3 = randomNumList[i];
				}
				buttonList[i].setEnabled(false);
			}// end if
		}//end for loop
			
		//Once the user has selected their first number, display it in num1Label
		if (num1 != -1)
		{
			num1Label.setText(""+num1);
			num1Label.setHorizontalAlignment(JLabel.CENTER);
		}// end if
		
		//Once the user has selected their second number, display it in num2Label
		if(num2 != -1)
		{ 
			num2Label.setText(""+num2);
			num2Label.setHorizontalAlignment(JLabel.CENTER);
		}//end if
		
		//Once the user has selected their third number, display it in num3Label
		if(num3 != -1)
		{ 
			num3Label.setText(""+num3);
			num3Label.setHorizontalAlignment(JLabel.CENTER);
			checkEquationButton.setEnabled(true);
		}// end if
		
		repaint();		
	}//end actionPerformed
	
	/**
	 * Method that will choose a random x position
	 * for the random numbers
	 * @return 
	 */
	public abstract int ranXPos();
	
	public abstract int ranYPos();
	
	/**
	 * method that will choose a random integer
	 * between the two numbers entered
	 * @param first 
	 * @param last
	 * @return Random number between first and last variable
	 */
	public int getRandomNum (int first, int last)
	{
		int smallerNum, largerNum;
		smallerNum = Math.min(first, last);
		largerNum = Math.max(first, last);
		
		 return (int)(smallerNum + Math.random() * (largerNum-smallerNum+1));
	}// end getRandomNum
	
	/**
	 * method that will take the operation sign and return
	 * a string corresponding to that sign
	 * @param operationNum Random Number generated to determine what operation would be choosen
	 * @return Image of operation sign
	 */
	public String operationSign(int operationNum)
	{	
		switch (operationNum)
		{
		case 1:
			return "+";
		case 2:
			return "-";
		case 3:
			return "x";
		case 4:
			return "/";
		}// end Switch
		
		return "1";
	}//end operationSign
	
	/**
	 * Chooses a random operation sign
	 * with probabilities based on difficulty level
	 * @param levelChoosen The level difficulty the user chose
	 * @return Number used to determine the operation sign
	 */
	public int getOperation (int levelChoosen)
	{		
		if(levelChoosen == 0)	//if easy mode
		{
			int operationNum = getRandomNum(1,100);
						
			if (operationNum <50)
			{
				return 1;
			}
			else
			{
				return 2;
			}
		}// end if
		
		else if (levelChoosen == 1)	//if medium mode
		{
			int operationNum = getRandomNum(1,100);
			
			if (operationNum <40)
			{
				return 1;
			}
			else if (operationNum <80)
			{
				return 2;
			}
			else if (operationNum<90)
			{
				return 3;
			}
			else
			{
				return 4;
			}
		}// end else if
		else if (levelChoosen == 2 || levelChoosen == 3)	//if hard or practise mode
		{
			return getRandomNum(1,4);
		}// end else if
		return 1;
		
	} // end getOperation

	/**
	 * Refresh the JFrame after a successful equation.
	 */
	public void refresh()
	{
		num1 = -1;
		num2 = -1;
		num3 = -1;
		num1Label.setText("");
		num2Label.setText("");
		num3Label.setText("");
		
		checkEquationButton.setEnabled(false);
		
		operationSign = getOperation(levelChosen);
		
		//Choosing the number range for each level
		for(int i = 0; i < 9; i++)
		{
			if(levelChosen == 0 || levelChosen == 1 || levelChosen == 3)
			{
				randomNumList[i] = getRandomNum(1,9);
			}// end if
			else if(levelChosen == 2)
			{
				randomNumList[i] = getRandomNum(1,12);
			}// end else if
		}
		
		operationLabel.setText(""+operationSign (operationSign));
		
		//Randomly picks three numbers that are different from each other to use to make a viable equation
		int temp = getRandomNum(0,8);
		int temp2 = getRandomNum(0,8);
		int temp3 = getRandomNum(0,8);
		while(temp == temp2)
		{
			temp2 = getRandomNum(0,8);
		}// end while loop
		
		while(temp2 == temp3)
		{
			temp3 = getRandomNum(0,8);
		}// end while loop
		
		// will make sure there will be at least one viable equation
		switch (operationSign)
		{
			case 1:
			case 2:
			randomNumList[temp] = randomNumList[temp2] + randomNumList[temp3];
			break;
			case 3:
			case 4:
			randomNumList[temp] = randomNumList[temp2] * randomNumList[temp3];
		}// end case statements
		
		for(int i = 0; i < 9; i++)
		{
			initButton(i);
		}//end for 
	}// end refresh	
	
	/**
	 * Initialize the buttons for both Math and Language classes
	 * @param buttonIndex Button Counter
	 */
	public abstract void initButton(int buttonIndex);

	/**
	 * Check if the user's equation is correct or not
	 * @param num1 Number in num1Label
	 * @param num2 Number in num2Label
	 * @param num3 Number in num3Label
	 * @return boolean if true/false
	 */
	public boolean checkEquation(int num1, int num2, int num3)
	{
		switch(operationSign)
		{
		case 1:		//if addition
			if (num1 + num2 == num3)
			{
				return true;
			}
		case 2:		//if subtraction
			if (num1 - num2 == num3)
			{
				return true;
			}
		case 3:		//if multiplication
			if (num1*num2 == num3)
			{
				return true;
			}
		case 4:		//if division
			if (num1/num2 == num3)
			{
				return true;
			}
			default: 
				return false;
		}// end switch statements
	}// end checkEquation method
	
	/**
	 * Determines how much bonus points the user gets
	 * @return bonusPoints Amount of bonus points
	 */
	public int getBonusPoints()
	{
		bonusPoints = 0;
		
		//Gets the bonus numbers
		int bonusNum1 = Integer.parseInt(bonus1Label.getText());
		int bonusNum2 = Integer.parseInt(bonus2Label.getText());
		int bonusNum3 = Integer.parseInt(bonus3Label.getText());
		
		//Gets the user's numbers
		int num1 = Integer.parseInt(num1Label.getText());
		int num2 = Integer.parseInt(num2Label.getText());
		int num3 = Integer.parseInt(num3Label.getText());
		
		//Compares to see if bonus and user numbers match
		//give bonus points if they do
		if(bonusNum1 == num1 || bonusNum1 == num2 || bonusNum1 == num3)
		{
			bonusPoints += 1;
		}//end if
		
		if(bonusNum2 == num1 || bonusNum2 == num2 || bonusNum2 == num3)
		{
			bonusPoints += 1;
		}// end if
		
		if(bonusNum3 == num1 || bonusNum3 == num2 || bonusNum3 == num3)
		{
			bonusPoints += 1;
		}// end if
		
		return bonusPoints;
	}// end getBounusPoints method
		
	/**
	 * Passes the score to the highScore class 
	 * @return score
	 */
	public static int getScore()
	{
		return score;
	}// end getScore method
}// end class
