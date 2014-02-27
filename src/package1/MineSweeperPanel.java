package package1;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineSweeperPanel implements ActionListener{
	
	private JButton[][] board;
	private Cell iCell;
	private MineSweeperGame game;
	private int size;
	private int mines;
	boolean showMinesToggle = false;
	boolean flagToggle = false;
	
	private int wins;
	private int loses;

	private JButton quitButton = new JButton("Quit");
	private JButton resetButton = new JButton("Reset");
	private JButton showMinesButton = new JButton("Reveal");
	private JButton newBoardButton = new JButton("New Board");

	private JLabel winsLabel = new JLabel();
	private JLabel losesLabel = new JLabel();
	
	private JFrame frame = new JFrame();
	
	Icon flag = new ImageIcon("flag_icon.png");
	
	
	public MineSweeperPanel(int mines, int size) {
		
		this.setSize(size);
		this.setMines(mines);
		
		game = new MineSweeperGame(size,mines);
		
		quitButton.addActionListener(this);
		resetButton.addActionListener(this);
		showMinesButton.addActionListener(this);
		newBoardButton.addActionListener(this);

		board = new JButton[size][size];
		
		JPanel optionPanel = new JPanel();
		JPanel scoreCountPanel = new JPanel();
		GridLayout boardlayout = new GridLayout(size,size);
		final JPanel boardPanel = new JPanel();
        boardPanel.setLayout(boardlayout);
        boardPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		optionPanel.setBorder(new EmptyBorder(0, 20, 20, 20) );
		scoreCountPanel.setBorder(new EmptyBorder(20, 0, 0, 0) );
		
		winsLabel.setText("Wins: " + this.getWins());
		losesLabel.setText("Loses: " + this.getLoses());
		
		optionPanel.add(showMinesButton);
		optionPanel.add(resetButton);
		optionPanel.add(quitButton);
		optionPanel.add(newBoardButton);
		scoreCountPanel.add(winsLabel);
		scoreCountPanel.add(losesLabel);
        
		for(int row = 0; row<size;row++)
			for(int col = 0; col<size;col++){
				board[row][col] = new JButton (""); 
				board[row][col].addActionListener(this); 
				board[row][col].addMouseListener( new placeFlag() );
				boardPanel.add(board[row][col]); 
			}

	    frame.setTitle("MineSweeper");
	    frame.add(scoreCountPanel, BorderLayout.NORTH);
	    frame.add(boardPanel, BorderLayout.CENTER);
	    frame.add(optionPanel, BorderLayout.SOUTH);
	    frame.setSize(300+size*20,300+size*20);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Display the window.
	    frame.setVisible(true);
	    frame.setLocationRelativeTo(null);

	}
	
	public void displayBoard(){
		
		for (int row = 0; row < size; row++) 
			 for (int col = 0; col < size; col++) { 
				iCell = game.getCell(row,col);
				if (iCell.isExposed()){ 
					board[row][col].setEnabled(false); 
					if(iCell.isMine())
						board[row][col].setText("*");
					if(iCell.getMineCount()>0 && !iCell.isMine())
						board[row][col].setText("" + iCell.getMineCount());
					if(iCell.getMineCount() == 0 && !iCell.isMine())
						board[row][col].setText("");
				}
				else{
					board[row][col].setEnabled(true);
					if(!iCell.isFlagged() && !showMinesToggle)
						board[row][col].setText("");
				}
				
			 }
		
		winsLabel.setText("Wins: " + this.getWins());
		losesLabel.setText("Loses: " + this.getLoses());
			 
		
	}
	
	private class placeFlag extends MouseAdapter{  
        public void mousePressed( MouseEvent e ){  
        	for (int row = 0; row < size; row++){
        		for (int col = 0; col < size; col++){ 
   				 	if (board[row][col] == e.getSource() && e.isMetaDown() && board[row][col].isEnabled()){
   				 		
   				 		iCell = game.getCell(row,col);
   				 		if(!flagToggle){
   				 			board[row][col].setText("!");
   				 			iCell.setFlagged(true);
   				 		}
   				 		else{
   				 			board[row][col].setText("");
   				 			iCell.setFlagged(false);
   				 		}
   				 		flagToggle = !flagToggle;
   				 	}
   				}
            }  
        }  
    }  

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for (int row = 0; row < size; row++) {
			 for (int col = 0; col < size; col++){ 
				 if (board[row][col] == e.getSource()){
					 game.select(row, col); 

					 if (game.getGameStatus() == GameStatus.LOST) { 
				    		JOptionPane.showMessageDialog(null,"You Lose" , "MINE STRUCK!", 
									JOptionPane.ERROR_MESSAGE);
				    		this.setLoses(loses+1);
					 } 
					 if (game.getGameStatus() == GameStatus.WON) { 
				    		JOptionPane.showMessageDialog(null,"You WON!");
				    		this.setWins(wins+1);
					 } 
					 
					 this.displayBoard();

				 }			 
			 }
		}
		
		if(e.getSource() == quitButton){
			MineSweeperGameSetupPanel game = new MineSweeperGameSetupPanel();
			frame.dispose();		
		}
		
		if(e.getSource() == resetButton){
			
				game = new MineSweeperGame(game.getSize(),game.getTotalMineCount());
				this.displayBoard();
			
		}
		
		if(e.getSource() == newBoardButton){
			MineSweeperGameSetupPanel setup = new MineSweeperGameSetupPanel();
			setup.setWins(this.getWins());
			setup.setLoses(this.getLoses());
			frame.dispose();
		}
		
		if(e.getSource() == showMinesButton){
			if(!showMinesToggle){
				for (int row = 0; row < size; row++) 
					 for (int col = 0; col < size; col++) { 
						iCell = game.getCell(row,col);
						if(iCell.isMine())
							board[row][col].setText("*");
						else if(iCell.getMineCount()>0)
							board[row][col].setText("" + iCell.getMineCount());
						else
							board[row][col].setText("");
					 }
				}
			else{
				for (int row = 0; row < size; row++) 
					 for (int col = 0; col < size; col++) { 
						iCell = game.getCell(row,col);
						if(!iCell.isExposed())
							board[row][col].setText("");
					 }
				}
			showMinesToggle = !showMinesToggle;
		}
	}
	
	
	
	public int getMines() {
		return mines;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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
	
