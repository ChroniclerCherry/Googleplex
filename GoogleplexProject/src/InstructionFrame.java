import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*; 

/**
 * allows user to move between pages to check the instructions of the game
 * @author James Huang, Yiminxue Zheng, Sarah E-Tohamy 
 * @since Friday, may 22, 2009
 */
public class InstructionFrame extends JFrame implements ActionListener 
{
	//Declaring Global Variables
	JLabel instructionLabel, instruction2Label, backgroundLabel;
	JTextArea instructionWords, instructionWords2;
	int pageNum = 0; 
	JButton back, forward, menu;
	ImageIcon image1, image2;
	
	/** 
	 * Create image icons
	 * Create buttons
	 * Create labels
	 * set the default page as 0
	 */
	public InstructionFrame()
	{ 		
		super ("Instructions");
		
		setSize (1200, 900);
		
		Container c = getContentPane(); 
		c.setLayout(null);
		
		// creates the image icons
		image1 = new ImageIcon ("Images/ClickExample1.jpg");
		image2 = new ImageIcon ("Images/ClickExample2.jpg");
		
		//add a label to place the first screen shot
		instructionLabel = new JLabel ();
		instructionLabel.setIcon(image1);
		instructionLabel.setBounds(63, 100, 513, 385);
		c.add(instructionLabel);		
		
		//add a label to place the second screen shot
		instruction2Label = new JLabel ();
		instruction2Label.setIcon(image2);
		instruction2Label.setBounds(635, 100, 513, 385);
		c.add(instruction2Label);
		
		// add a label to place the instructions
		instructionWords = new JTextArea();
		instructionWords.setBounds(63, 520, 513, 100);
		instructionWords.setFont(new Font ("ARIAL", Font.BOLD, 30));
		instructionWords.setEditable(false);
		instructionWords.setLineWrap(true);
		instructionWords.setWrapStyleWord(true);
		instructionWords.setOpaque(false);
		c.add(instructionWords);
		
		//add another label to place the instructions
		instructionWords2 = new JTextArea ("");
		instructionWords2.setBounds(635, 520, 513, 100);
		instructionWords2.setFont(new Font ("ARIAL", Font.BOLD, 30));
		instructionWords2.setEditable(false);
		instructionWords2.setLineWrap(true);
		instructionWords2.setWrapStyleWord(true);
		instructionWords2.setOpaque(false);
		c.add(instructionWords2);
		
		//first page and for clicking 
		clickingExample();
		
		//create a back button and add the icon
		back = new JButton(); 
		ImageIcon backIcon = new ImageIcon("Images/BackButton.png");
		back.setOpaque(false);
		back.setBorderPainted(false);
		back.setBackground(new Color(0,0,0));
		back.setIcon(backIcon);
		back.setBounds(50, 700, 200, 100);
		back.addActionListener(this);
		c.add(back);
		back.setEnabled(false);
		
		//create the forward button and add the icon 
		forward = new JButton("Forward");
		ImageIcon nextIcon = new ImageIcon ("Images/nextButton.png");
		forward.setOpaque(false);
		forward.setBorderPainted(false);
		forward.setBackground(new Color(0,0,0));
		forward.setIcon(nextIcon);
		forward.setBounds(913, 700, 190, 100);
		forward.addActionListener(this);
		c.add(forward); 
		
		//create a menu button and add the icon
		menu = new JButton ("Back to Main Menu");
		ImageIcon menuIcon = new ImageIcon ("Images/TitlePageButton.png");
		menu.setOpaque(false);
		menu.setBorderPainted(false);
		menu.setBackground(new Color(0,0,0));
		menu.setIcon(menuIcon);
		menu.setBounds(513, 650, 170, 142); 
		menu.addActionListener(this);
		c.add(menu);
		
		//create a label for the background and add a background
		backgroundLabel = new JLabel();
		backgroundLabel.setBounds(0, 0, 1200, 900);
		ImageIcon backgroundImage = new ImageIcon("Images/InstructionsBackground.jpg");
		backgroundLabel.setIcon(backgroundImage);
		backgroundLabel.setOpaque(true);
		c.add(backgroundLabel);
		
		setVisible(true);
		
		//rotate window
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
		}
	}// end constructor
	
	/** 
	 * Allow the user to go back and forth between the 
	 * pages of the "Instructions" option from the main menu 
	 * "Main Menu" button to return 
	 * back to the main menu to resume playing the game
	 */
	public void actionPerformed(ActionEvent e)
	{ 		
		//if forward is clicked increase the page number
		if (e.getSource()==forward)
		{
			pageNum++;
		}
		
		//if back button is clicked decrese the page number
		if (e.getSource () == back)
		{
			pageNum --;
		}
		
		//if the user is on the first page, disable the back button 
		if (pageNum == 0)
		{
			back.setEnabled(false);
		}
		else		//otherwise keep it enabled
		{
			back.setEnabled(true);
		}
		
		//if the page number is 5, disable the forward button 
		if (pageNum == 5)
		{
			forward.setEnabled(false);
		}
		else		//otherwise keep it enabled
		{
			forward.setEnabled(true);
		}
		
		//if the menu button is clicked, return to the main menu
		if (e.getSource()==menu)
		{
			dispose();
			new GoogleplexMenu();
		}
		
		//enter different page methods depending on the page number
		switch(pageNum)
		{ 
		case 0: 
			clickingExample();
			break; 	
		case 1 : 
			checkEquationButton(); 
			break;
		case 2:
			scoring();
			break;
		case 3:
			bonusNums();
			break;
		case 4:
			clearExample();
			break;
		case 5:
			extra();
		}// end switch statements 
		
		//change the image on the icons
		instructionLabel.setIcon(image1);
		instruction2Label.setIcon(image2);
		
		repaint(); 
					
	}//end actionPerformed method
	
	/**
	 * Display images and words for the clicking example instructions
	 */
	public void clickingExample()
	{ 
		//add the first 2 screen shots and the instructions on the first page
		image1 = new ImageIcon ("Images/ClickExample1.jpg");
		image2 = new ImageIcon ("Images/ClickExample2.jpg");
		instructionWords.setText("When you click a number...");
		instructionWords2.setText("It goes in the box.");
		
	}//end clickingExample
	
	/**
	 * Display images and words for the checking equation instructions
	 */
	public void checkEquationButton()
	{ 
		//add the second 2 screen shots and the instructions on the second page
		image1 = new ImageIcon ("Images/Check1.jpg");
		image2 = new ImageIcon ("Images/check2.jpg");
		instructionWords.setText("If have three numbers, click check button to see if equation is right!");
		instructionWords2.setText("If right, you get a point. The numbers will appear in the bonus boxes.");
		
	}//end checkEquationButton
	
	/**
	 * Display images and words for the scoring instructions
	 */
	public void scoring()
	{ 
		//add the third 2 screen shots and the instructions on the third page
		image1 = new ImageIcon ("Images/scoring1.jpg");
		image2 = new ImageIcon ("Images/scoring2.jpg");
		instructionWords.setText("If you get an answer wrong though...");
		instructionWords2.setText("Your bonus boxes get cleared!");
		
	}//end scoring
	
	/**
	 * Display images and words for the clearing instructions
	 */
	public void clearExample()
	{ 
		//add the fourth 2 screen shots and the instructions on the fourth page
		image1 = new ImageIcon ("Images/clear1.jpg");
		image2 = new ImageIcon ("Images/clear2.jpg"); 
		instructionWords.setText("If you click the clear button...");
		instructionWords2.setText("The numbers in the boxes will disappear!");	
		
	}//end clearExample
	
	/**
	 * Display images and words for the Bonus Numbers instructions
	 */
	public void bonusNums()
	{
		//add the fifth 2 screen shots and the instructions on the fifth page
		image1 = new ImageIcon ("Images/Bonus1.jpg");
		image2 = new ImageIcon ("Images/Bonus2.jpg");
		instructionWords.setText("If you use some numbers that are the same as those in the bonus boxes...");
		instructionWords2.setText("You earn more points than normal!");	
		
	}//end bonusNums
	
	/**
	 * Display images and words for the extra instructions
	 */
	public void extra ()
	{
		//add the sixth 2 screen shots and the instructions on the sixth page
		image1 = new ImageIcon ("Images/extra2.jpg");
		image2 = new ImageIcon ("Images/extra1.jpg");
		instructionWords.setText("In Language mode, numbers are words!");
		instructionWords2.setText("In practise mode, no time limit.");	
		
	}// end extra
}//end class
