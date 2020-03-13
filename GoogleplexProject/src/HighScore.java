import java.io.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument.Iterator;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.*;

/**
 * This class is used to get the player's high score, and save it
 * @author James Huang, Dipesh Mistry and Mohammed Chamma
 * @since Wednesday, May 13, 2009
 */
public class HighScore extends JFrame implements ActionListener
{
	//Declare global variables
	JLabel backgroundImage, levelBadgeImage;
	JButton menu;

	ArrayList<String> userScores = new ArrayList<String>();
	
	// The key is the user name. The value is the score for that user.
	Map<String, String> namesAndScores = new HashMap<String, String>();
	
	StringBuffer summaryInfo = new StringBuffer();
	
	/**
	 * Constructor method
	 * @param score Integer storing the player's score
	 */
	public HighScore(String newScore, String name, int level)
	{
		super ("High Scores");
		
		Container c = getContentPane();
		c.setLayout(null);
		
		// set the size
		setSize(1200,900);

		// run methods to read scores, check them, and write them back out
		String scoreFileName = getScoreFileName(level);
		String image = null;
		
		readScore(scoreFileName);		
		
		writeScore(newScore, name, scoreFileName);

		JTextArea text = new JTextArea();
		text.setText(summaryInfo.toString());
		text.setBounds(200, 200, 800, 600);
		text.setFont(new Font("Arial", Font.BOLD, 40));
		text.setEditable(false);
		text.setOpaque(false);
		c.add(text);
		
		menu = new JButton ("Back to Main Menu");
		ImageIcon menuIcon = new ImageIcon ("Images/TitlePageButton.png");
		menu.setOpaque(false);
		menu.setBorderPainted(false);
		menu.setBackground(new Color(0,0,0));
		menu.setIcon(menuIcon);
		menu.setBounds(25, 25, 170, 142); 
		menu.addActionListener(this);
		c.add(menu);
		
		//Initialize level badge ImageIcon
		levelBadgeImage = new JLabel();
		levelBadgeImage.setBounds(500, 50, 198, 198);
		switch (level)
		{
			case 0:
				image = "EasyBadge.png";
				break;
			case 1:
				image = "MediumBadge.png";
				break;
			case 2:
				image = "HardBadge.png";
		}
		ImageIcon badgeIcon = new ImageIcon("Images/"+image);
		levelBadgeImage.setIcon(badgeIcon);
		c.add(levelBadgeImage);
		
		backgroundImage = new JLabel();
		backgroundImage.setBounds(0, 0, 1200, 900);
		ImageIcon backgroundIcon = new ImageIcon("Images/HighScores background.jpg");
		backgroundImage.setIcon(backgroundIcon);
		c.add(backgroundImage);
		
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
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}// end constructor

	/**
	 * Closes the JFrame and displays the main menu
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==menu)
		{
			dispose();
			new GoogleplexMenu();
		}
		repaint();
	}//end actionPerformed
	
	/**
	 * This method reads the score file if it exists already and saves data to the class temporarily
	 */
	public void readScore(String scoreFileName)
	{
		File file = new File(scoreFileName);
		
		if(!file.exists())
		{
			return;
		}
		
		FileInputStream fileInputStream = null;
		BufferedInputStream bufferInputStream= null;
		DataInputStream dataInputStream = null;

		try 
		{
			fileInputStream = new FileInputStream(file);

			// Here BufferedInputStream is added for fast reading.
			bufferInputStream = new BufferedInputStream(fileInputStream);
			dataInputStream = new DataInputStream(bufferInputStream);			
						
			// dis.available() returns 0 if the file does not have more lines.
			while (dataInputStream.available() != 0) 
			{	
				String line = dataInputStream.readLine();
				
				userScores.add(line);
				
				String [] array = line.split("=");
				
				String name = array[0];
				String score = array[1];
				
				namesAndScores.put(name, score);
				
			}// end while loop

			// dispose all the resources after using them.
			fileInputStream.close();
			bufferInputStream.close();
			dataInputStream.close();
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}// end readScore method

	/**
	 * Chooses what textField to display and write data
	 * @param level
	 * @return
	 */
	public String getScoreFileName(int level)
	{
		switch(level)
		{
			case 0:
				return "highScoreEasy.txt";
				
			case 1:
				return "highScoreMedium.txt";
				
			case 2:
				return "highScoreHard.txt";
				
			default:
				return "highScoreEasy.txt";
		}
	}//end getScoreFileName
	
	/**
	 * This method writes back a file containing the top 10 high scores of all time along with the player's names
	 */
	public void writeScore(String userNewScore, String userName, String scoreFileName)
	{
		try
		{
			summaryInfo.append("You got " + userNewScore + ".\n");
			summaryInfo.append("Below is the highest 10 scores so far. \n\n");
			//summaryInfo.append("Name\tScore\n");
			
			// Create file 
			File file = new File(scoreFileName);
			FileWriter fstream = new FileWriter(scoreFileName);
			BufferedWriter out = new BufferedWriter(fstream);

			int newScore = Integer.parseInt(userNewScore);
			
			if(!file.exists() || 0 == userScores.size())
			{
				summaryInfo.append(userName + "\t\t" + newScore + "\n");				
				out.write(userName + "=" + newScore);
				out.newLine();
				out.close();
				return;
			}
			
			java.util.Iterator<String> iter = userScores.iterator();
			
			int counter = 1;
			boolean done = false;
			
			// Case 1: this is new user. Insert the score if it is good enough.
			if(!namesAndScores.containsKey(userName))
			{
				while(iter.hasNext())
				{				
					String line = iter.next();
	
					String [] array = line.split("=");
					
					String name = array[0];
					String scoreString = array[1];
					
					int score = Integer.parseInt(scoreString);						
						
					if(newScore >= score && !done)
					{
						done = true;						
						
						summaryInfo.append(userName + "\t\t" + newScore + "\n");
						
						out.write(userName + "=" + newScore);
						out.newLine();
											
						counter++;
					}
					
					summaryInfo.append(name + "\t\t" + score + "\n");
	
					out.write(name + "=" + score);
					out.newLine();
					
					if(counter == 10)
					{
						break;
					}
					
					counter++;
				}
				
				if(!done)
				{
					done = true;
					summaryInfo.append(userName + "\t\t" + newScore + "\n");
					
					out.write(userName + "=" + newScore);
					out.newLine();
				}
			}
			else
			{
				// Find this user's recorded score.
				String recordedScoreString = namesAndScores.get(userName);
			
				int recordedScore = Integer.parseInt(recordedScoreString);
				
				if(newScore > recordedScore)
				{
					// Case 2: the user exists. But he/she got a better score.
					// Update this.
					
					while(iter.hasNext())
					{				
						String line = iter.next();

						String [] array = line.split("=");
						
						String name = array[0];
						String scoreString = array[1];
						counter = 0;
						
						int score = Integer.parseInt(scoreString);
												
						if(!done && !name.equals(userName) && newScore > score)
						{							
							done = true;
							
							summaryInfo.append(userName + "\t\t" + newScore + "\n");
							
							out.write(userName + "=" + newScore);
							out.newLine();											
							counter++;	
							
							if(counter == 10)
							{
								break;
							}
							
							summaryInfo.append(name + "\t\t" + score + "\n");
							
							out.write(name + "=" + score);
							out.newLine();
							counter++;
						}
						else if(name.equals(userName))
						{
							if(!done)
							{
								summaryInfo.append(userName + "\t\t" + newScore + "\n");
								
								out.write(userName + "=" + newScore);
								out.newLine();											
								counter++;
							}	
						}						
						else
						{
							summaryInfo.append(name + "\t\t" + score + "\n");
							
							out.write(name + "=" + score);
							out.newLine();
							counter++;
						}
						
						if(counter == 10)
						{
							break;
						}					
					}
				}
				// Case 3: the user exists. But he/she did not get a better score.
				// Ignore this. 
				else
				{
					while(iter.hasNext())
					{							
						String line = iter.next();
						
						String [] array = line.split("=");
						
						String name = array[0];
						String scoreString = array[1];
						
						summaryInfo.append(name + "\t\t" + scoreString + "\n");
						out.write(line);
						out.newLine();				
					}
					
				}
			}
						
			//Close the output stream
			out.close();
		}
		catch (Exception e)
		{	
			//Catch exception if any
			System.err.println("Error: not writing properly" + e.getMessage());
		}
	}// end writeScore method
}// end class