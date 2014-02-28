package package1;

public class Cell {
    	private int mineCount = 0; 
    	private boolean isFlagged; 
    	private boolean isExposed; 
	private boolean isMine; 
	 
	//Constructor to set surrounding minecount, flagged, exposed and mine 
	public Cell(int i, boolean b, boolean c, boolean d) {
		this.setMineCount(i);
		this.setFlagged(b);
		this.setExposed(c);
		this.setMine(d);
	}
	public int getMineCount() {
		return mineCount;
	}
	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}
	public boolean isFlagged() {
		return isFlagged;
	}
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}
	public boolean isExposed() {
		return isExposed;
	}
	public void setExposed(boolean isExposed) {
		this.isExposed = isExposed;
	}
	public boolean isMine() {
		return isMine;
	}
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
	
	CELELELELELLELELE
	
}
