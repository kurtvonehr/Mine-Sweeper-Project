package package1;

import java.util.Random;

import javax.swing.JOptionPane;

public class MineSweeperGame {
	
	 private Cell[][] board; 
	 private GameStatus status; 
	 private int size; 
	 private int totalMineCount; 
	 
	 public MineSweeperGame(int size, int totalMineCount){
		 
		 
		 this.setTotalMineCount(totalMineCount);
		 this.setSize(size);
		 
		 this.setGameStatus(GameStatus.NOTOVERYET);
		 board = new Cell[size][size]; 

		 for (int row = 0; row < size; row++) 
			 for (int col = 0; col < size; col++){  
				 board[row][col] = new Cell(0, false, false, false); 
			 }
		 
		 
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
		 
		board[row][col].setExposed(true);
		
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
		 		 
		if(board[row][col].isMine()){
	    	this.setGameStatus(GameStatus.LOST);
		}
		
		int exposed = 0;
		 
		for(int row1 = 0; row1 < size ; row1++)
			for(int col1 = 0; col1 < size; col1++){
				if(!board[row1][col1].isExposed())
					exposed = exposed + 1;
			}
		if(exposed == this.totalMineCount){
			this.setGameStatus(GameStatus.WON);
		}
	 }
	 
	 public void reset() {
		 new MineSweeperGame(size,totalMineCount);
	 }
	 
	 public GameStatus getGameStatus(){
		 return status;
	 }
	 
	 public void setGameStatus(GameStatus status){
		 this.status = status;
	 }
	 
	 public Cell getCell(int row, int col){
		 return board[row][col];
	 }
	
	 public int getTotalMineCount() {
		return totalMineCount;
	}

	public void setTotalMineCount(int totalMineCount) {
		this.totalMineCount = totalMineCount;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	 
	 
}
