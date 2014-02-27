package package1;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineSweeperGameSetupPanel implements ActionListener{
	
	private JButton quitButton = new JButton("Quit");
	private JButton startButton = new JButton("Start Game");
	private JLabel sizeInputLabel = new JLabel("Size");
	private JLabel minesInputLabel = new JLabel("Mines");
	private JLabel gameLabel = new JLabel("Game Setup");
	private String[] size1 = new String[30];
	private String[] mine1 = new String[10];
    private JFrame frame = new JFrame("MineSweeper");
    private JComboBox minesComboBox;
    private JComboBox sizeComboBox;
    	
	public int wins = 0;
	public int loses = 0;
		
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
		
		for(int i=0; i<10;i++){
			mine1[i] = Integer.toString(i+1);
		}
		for(int i=0; i<30;i++){
			size1[i] = Integer.toString(i+1);
		}
		
		minesComboBox = new JComboBox(mine1);
		minesComboBox = new JComboBox(size1);
		minesComboBox.setSelectedIndex(9);
		minesComboBox.setSelectedIndex(9);
		
		optionPanel.add(startButton);
		optionPanel.add(quitButton);
		sizePanel.add(sizeInputLabel);
		sizePanel.add(minesComboBox);
		minesPanel.add(minesInputLabel);
		minesPanel.add(minesComboBox);
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
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == startButton){
			int mines = this.minesComboBox.getSelectedIndex() + 1;
			int size = this.minesComboBox.getSelectedIndex() + 1;
			MineSweeperPanel gamePanel = new MineSweeperPanel(mines, size);
			gamePanel.setLoses(loses);
			gamePanel.setWins(wins);
			gamePanel.displayBoard();
			frame.dispose();
		}
		if(e.getSource() == quitButton){
			System.exit(0);
		}
		
	}


	public int getWins() {
		return wins;
	}


	public void setWins(int wins) {
		this.wins = wins;
	}


	public int getLoses() {
		return loses;
	}


	public void setLoses(int loses) {
		this.loses = loses;
	}
	
	
}
	


	
