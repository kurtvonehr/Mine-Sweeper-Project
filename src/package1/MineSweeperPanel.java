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
	private JButton showMines = new JButton("Reveal");
	private JButton newBoard = new JButton("New Board");

	private JLabel winsLabel = new JLabel();
	private JLabel losesLabel = new JLabel();
	
	private JFrame frame = new JFrame();
	
	Icon flag = new ImageIcon("flag_icon.png");
	
	//creates the frame for the game play
	public MineSweeperPanel(int mines, int size) {
		
		this.setSize(size);
		this.setMines(mines);
		
		game = new MineSweeperGame(size,mines);
		
		quitButton.addActionListener(this);
		resetButton.addActionListener(this);
		showMines.addActionListener(this);
		newBoard.addActionListener(this);

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
		
		optionPanel.add(showMines);
		optionPanel.add(resetButton);
		optionPanel.add(quitButton);
		optionPanel.add(newBoard);
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
	    
	    //Scales the frame for different size boards
	    frame.setSize(300+size*20,300+size*20);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Display the window.
	    frame.setVisible(true);
	    frame.setLocationRelativeTo(null);

	}
	
	//Displays the board by checking each individual cell for it's contents
	//then sets the button to be revealed or hidden. If revealed the contents
	// are shown to the user.
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
		//displays the number of wins and loses to the user at the top of the board
		winsLabel.setText("Wins: " + this.getWins());
		losesLabel.setText("Loses: " + this.getLoses());
			 
		
	}
	//Places a flag onto a cell that is unexposed, when double right clicked
	private class placeFlag extends MouseAdapter{  
        public void mousePressed( MouseEvent e ){  
        	for (int row = 0; row < size; row++){
        		for (int col = 0; col < size; col++){ 
   				 	if (board[row][col] == e.getSource() && e.isMetaDown() && board[row][col].isEnabled()){
   				 		//toggles flag on and off when double right clicked
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
					 //checks if the game is over due to a mine being exposed
					 if (game.getGameStatus() == GameStatus.LOST) { 
				    		JOptionPane.showMessageDialog(null,"You Lose" , "MINE STRUCK!", 
									JOptionPane.ERROR_MESSAGE);
						//increments loses
				    		this.setLoses(loses+1);
					 } 
					 //checks if the game has been won
					 if (game.getGameStatus() == GameStatus.WON) { 
				    		JOptionPane.showMessageDialog(null,"You WON!");
				    		//increments wins
				    		this.setWins(wins+1);
					 } 
					 //displays board if win or loss does not occur
					 this.displayBoard();

				 }			 
			 }
		}
		//if the current game is exited, the setup panel is shown for a new player
		//or new game with no history of wins or losses
		if(e.getSource() == quitButton){
			MineSweeperGameSetupPanel game = new MineSweeperGameSetupPanel();
			frame.dispose();		
		}
		//resets the board, keeping score count
		if(e.getSource() == resetButton){
			
				game = new MineSweeperGame(game.getSize(),game.getTotalMineCount());
				this.displayBoard();
			
		}
		//allows user to choose a new board size and new number of mines,while
		//retaining score history
		if(e.getSource() == newBoard){
			MineSweeperGameSetupPanel setup = new MineSweeperGameSetupPanel();
			setup.setWins(this.getWins());
			setup.setLoses(this.getLoses());
			frame.dispose();
		}
		//toggles mines and minecount on and off
		if(e.getSource() == showMines){
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
	
	
	//gets the number of total mines
	public int getMines() {
		return mines;
	}
	//sets the number of total mines
	public void setMines(int mines) {
		this.mines = mines;
	}
	//gets the size of the board
	public int getSize() {
		return size;
	}
	//sets the size of the board
	public void setSize(int size) {
		this.size = size;
	}
	//gets the nubmer of wins
	 public int getWins() {
		return wins;
	}
	//sets the number of wins
	public void setWins(int wins) {
		this.wins = wins;
	}
	//gets the number of losses
	public int getLoses() {
		return loses;
	}
	//sets the number of losses
	public void setLoses(int loses) {
		this.loses = loses;
	}
}
	
