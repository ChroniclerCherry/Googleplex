import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Allows user to choose a difficulty level and play either the math or language mode
 * Also allows the user to view the instructions
 * 
 * @author James Huang, Yiminxue Zheng, Sarah E-Tohamy 
 * @since Thursday, April 30, 2009
 */
public class GoogleplexMenu extends JFrame implements ActionListener
{
	//declares global variables
	JButton runGoogleplexMathButton, runGoogleplexLangButton, runGoogleplexInstructionsButton;
	JComboBox difficultyMenu;
	
	/**
	 * Constructor method
	 * Creates buttons and drop-down menu
	 * sets background image
	 */
	public GoogleplexMenu()
	{
		super("Googleplex Main Menu!");
		
		setSize(1200, 900);		//sets size of JFrame
		
		Container c = getContentPane();
		c.setLayout(null);
		
		//creates button to run Math mode
		runGoogleplexMathButton = new JButton ();
		runGoogleplexMathButton.setBounds(200, 400, 700, 100);
		ImageIcon mathIcon = new ImageIcon("Images/PlayMathButton.png");
		runGoogleplexMathButton.setIcon(mathIcon);
		runGoogleplexMathButton.addActionListener(this);
		c.add(runGoogleplexMathButton);
		
		//creates button to run Language mode
		runGoogleplexLangButton = new JButton ();
		runGoogleplexLangButton.setBounds(200, 550, 700, 100);
		ImageIcon langIcon = new ImageIcon("Images/PlayLanguageButton.png");
		runGoogleplexLangButton.setIcon(langIcon);
		runGoogleplexLangButton.addActionListener(this);
		c.add(runGoogleplexLangButton);
		
		//creates button to view instructions
		runGoogleplexInstructionsButton = new JButton ();
		runGoogleplexInstructionsButton.setBounds(200, 700, 700, 100);
		ImageIcon instructionsIcon = new ImageIcon("Images/InstructionsButton.png");
		runGoogleplexInstructionsButton.setIcon(instructionsIcon);
		runGoogleplexInstructionsButton.addActionListener(this);
		c.add(runGoogleplexInstructionsButton);
		
		//creates array for choosing difficulty
		String [] difficultyList = new String [4];
		difficultyList [0] = "Easy";
		difficultyList [1] = "Medium";
		difficultyList [2] = "Hard";
		difficultyList [3] = "Practise";
		
		//creates drop down menu for choosing difficulty
		difficultyMenu = new JComboBox(difficultyList);
		difficultyMenu.setBounds(400, 250, 350, 100);
		difficultyMenu.setFont(new Font("Arial", Font.BOLD, 50));
		difficultyMenu.setBackground(Color.white);
		c.add(difficultyMenu);
		
		//sets background colour
		JLabel titleLabel = new JLabel ();
		titleLabel.setBounds(0, 0, getWidth(), 900);
		ImageIcon titleIcon = new ImageIcon("Images/Welcome background.jpg");
		titleLabel.setIcon(titleIcon);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		c.add(titleLabel);
				
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//stops timer when the JFrame closes
		
		//rotate window
		for(int i = 0; i<4; i++)
		{
			Robot r = null;
			try
			{
			r = new Robot();
			}// end try
			catch(AWTException e)
			{
				e.printStackTrace();
			}// end catch
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_SHIFT);
			r.keyPress(KeyEvent.VK_R);
			r.keyRelease(KeyEvent.VK_ALT);
			r.keyRelease(KeyEvent.VK_SHIFT);
			r.keyRelease(KeyEvent.VK_R);
		}// end for loop
	}//end constructor
	
	/**
	 * Checks which button the user clicked
	 * checks the difficulty level selected
	 * opens game frame or Instructions
	 */
	public void actionPerformed(ActionEvent e)
	{
		//if Math mode is selected, check the level selected and pass it
		//on to GoogleplexBase
		if(e.getSource()==runGoogleplexMathButton)
		{
			int difficultyLevel = difficultyMenu.getSelectedIndex();
			switch (difficultyLevel)
			{
			case 0:
				new GoogleplexMath(0);
				break;
			case 1:
				new GoogleplexMath(1);
				break;
			case 2:
				new GoogleplexMath(2);
				break;
			case 3:
				new GoogleplexMath(3);
			}// end switch statemnents
		}// end IF
		
		//if Language mode is selected, check the level selected and pass it
		//on to GoogleplexBase
		if(e.getSource()==runGoogleplexLangButton)
		{
			int difficultyLevel = difficultyMenu.getSelectedIndex();
			switch (difficultyLevel)
			{
			case 0:
				new GoogleplexLang(0);
				break;
			case 1:
				new GoogleplexLang(1);
				break;
			case 2:
				new GoogleplexLang(2);
				break;
			case 3:
				new GoogleplexLang(3);
			}// end switch statments
		}// end if
		
		//if instructions button clicked, open the instruction JFrame
		if (e.getSource()==runGoogleplexInstructionsButton)
		{
			new InstructionFrame();
		}// end if
		dispose();
	}//end actionPerformed	
}//end class
