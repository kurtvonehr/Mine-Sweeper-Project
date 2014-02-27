package package1;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineSweeperGameSetupPanel implements ActionListener{
	
	//TESTETSESTSETSETSET
	
	private JButton quitButton = new JButton("Quit");
	private JButton startButton = new JButton("Start Game");
	private JLabel sizeInput = new JLabel("Size");
	private JLabel minesInput = new JLabel("Mines");
	private JLabel gameLabel = new JLabel("Game Setup");
	private String[] size1 = new String[30];
	private String[] mine1 = new String[10];
	private JFrame frame = new JFrame("MineSweeper");
    	private JComboBox mines;
    	private JComboBox size;
    	
	public int wins = 0;
	public int loses = 0;
	
	
	//creates a frame for the setup of the game	
	MineSweeperGameSetupPanel(){
	
		
		quitButton.addActionListener(this);
		startButton.addActionListener(this);
			
		JPanel optionPanel = new JPanel();
		JPanel sizePanel = new JPanel();
		JPanel minesPanel = new JPanel();
		JPanel setupPanel = new JPanel();
		JPanel gamePanel = new JPanel();
		sizePanel.setBorder(new EmptyBorder(0, 20, 20, 20) );
		minesPanel.setBorder(new EmptyBorder(0, 20, 20, 20) );
		gamePanel.setBorder(new EmptyBorder(20, 10, 0, 0) );
		optionPanel.setBorder(new EmptyBorder(0, 20, 20, 20) );
		
		//populates the array for the mine combo box options
		for(int i=0; i<10;i++){
			mine1[i] = Integer.toString(i+1);
		}
		//populates the arry for the size combo box options
		for(int i=0; i<30;i++){
			size1[i] = Integer.toString(i+1);
		}
		
		mines = new JComboBox(mine1);
		size = new JComboBox(size1);
		mines.setSelectedIndex(9);
		size.setSelectedIndex(9);
		
		optionPanel.add(startButton);
		optionPanel.add(quitButton);
		sizePanel.add(sizeInput);
		sizePanel.add(size);
		minesPanel.add(minesInput);
		minesPanel.add(mines);
		setupPanel.add(sizePanel);
		setupPanel.add(minesPanel);
		gamePanel.add(gameLabel);
		

	    frame.add(gamePanel, BorderLayout.NORTH);	    
	    frame.add(setupPanel, BorderLayout.CENTER);
	    frame.add(optionPanel, BorderLayout.SOUTH);
	    frame.setSize(300,200);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Display the window.
	    frame.setVisible(true);
	    frame.setLocationRelativeTo(null);
	    
		}
	

	@Override
	public void actionPerformed(AasdfasdfasdfasdfctionEvent e) {
		
		//when the start button is pressed, begin a new game,
		//set the loses and wins and refresh the board to reflect
		//the loses and wins. Default is 0, 0. When the user requests for
		//a new board from the minesweeperpanel, the wins and loses in this 
		//class are set to the current score and then sent back to the new 
		//board created by the new minesweeper panel. 
		if(e.getSource() == startButton){
			int mines = this.mines.getSelectedIndex() + 1;
			int size = this.size.getSelectedIndex() + 1;
			MineSweeperPanel gamePanel = new MineSweeperPanel(mines, size);
			gamePanel.setLoses(loses);
			gamePanel.setWins(wins);
			gamePanel.displayBoard();
			frame.dispose();
		}
		//exits the frame and game
		if(e.getSource() == quitButton){
			System.exit(0);
		}
		
	}

	
}
	


	
