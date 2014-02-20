package package1;

import java.util.Random;

import javax.swing.JOptionPane;

public class MineSweeperGame {
	
	 private Cell[][] board; 
	 private GameStatus status; 
	 private int size; 
	 private int totalMineCount; 
	 
	 public MineSweeperGame(int size, int totalMineCount){
		 
		 //Takes arguments passed from game setup and sets values
		 this.setTotalMineCount(totalMineCount);
		 this.setSize(size);
		 //Sets game status to "not over yet"
		 this.setGameStatus(GameStatus.NOTOVERYET);
		 board = new Cell[size][size]; 
		 //Initializes board for set dimensions with no mines and no other info
		 for (int row = 0; row < size; row++) 
			 for (int col = 0; col < size; col++){  
				 board[row][col] = new Cell(0, false, false, false); 
			 }
		 
		//Sets specified number of mines randomly around
		//board of specified size
		Random random = new Random(); 
		int mineCount = 0; 
		
		while (mineCount < totalMineCount) { 
			 int col = random.nextInt(size); 
			 int row = random.nextInt(size); 
			 
			 if (!board[row][col].isMine()) { 
				 board[row][col].setMine(true); 
				 mineCount++; 
			 } 
		 }
		
		mineCount = 0; 
		
		
		//Check the surround mines and updates each cell to contain that value
		//does this by locating each cell that is a mine and adding one to the 
		//cells that surround this mine, until each mine has been accounted for.
		for(int row = 0; row < size ; row++){
			for(int col = 0; col < size; col++){
				if(board[row][col].isMine()){	
					for(int i = -1; i < 2 ; i++)
						for(int j = -1; j < 2 ; j++){
							if((row+i)>=0 && (col+j)>=0 && (row+i)<size && (col+j)<size && !(i==0 && j==0)){
								mineCount = board[row+i][col+j].getMineCount();
								board[row+i][col+j].setMineCount(mineCount+1);
							}	
					}
				}
			}
		}
	 }
	 
	 public void select (int row, int col){
		//sets selected cell to be exposed 
		board[row][col].setExposed(true);
		//recursively reveals each empty cell location in the area
		//with the edge of the area being cells that border a mine
		if(board[row][col].getMineCount() == 0 && !board[row][col].isMine()){	
				for(int i = -1; i < 2 ; i++)
					for(int j = -1; j < 2 ; j++){
						if((row+i)>=0 && (col+j)>=0 && (row+i)<size && (col+j)<size){
							if(board[row+i][col+j].getMineCount() == 0 && !board[row+i][col+j].isExposed()){
								board[row+i][col+j].setExposed(true);
								this.select(row+i, col+j);
							}
							//board[row+i][col+j].setExposed(true); Shows Cells on edge of blank cells
						}	
				}
		}
		//if the user clicks a cell that is a mine, the game is over and game status set to LOST 		 
		if(board[row][col].isMine()){
	    		this.setGameStatus(GameStatus.LOST);
		}
		
		int exposed = 0;
		//counts the number of cells that remain unexposed 
		for(int row1 = 0; row1 < size ; row1++)
			for(int col1 = 0; col1 < size; col1++){
				if(!board[row1][col1].isExposed())
					exposed = exposed + 1;
			}
		//if the number of cells unexposed is equal to the number of mines,
		//the user has won, and game status is updated to WON.
		if(exposed == this.totalMineCount){
			this.setGameStatus(GameStatus.WON);
		}
	 }
	 //resets the game board, without removing the score
	 public void reset() {
		 new MineSweeperGame(size,totalMineCount);
	 }
	 //returns the current status of the game
	 public GameStatus getGameStatus(){
		 return status;
	 }
	 //sets the current status of the game
	 public void setGameStatus(GameStatus status){
		 this.status = status;
	 }
	 //returns the object Cell
	 public Cell getCell(int row, int col){
		 return board[row][col];
	 }
	//returns to the total mine count for the board
	 public int getTotalMineCount() {
		return totalMineCount;
	}
	//sets the mine count for the board
	public void setTotalMineCount(int totalMineCount) {
		this.totalMineCount = totalMineCount;
	}
	//gets the size of the board
	public int getSize() {
		return size;
	}
	//sets the size of the board
	public void setSize(int size) {
		this.size = size;
	}
	 
	 
}
