package GUI;

public abstract class CommonDisplayer implements Displayer {

	CommonGameBoard board;
	
	public void setBoard(CommonGameBoard board) {
		this.board = board;
	}
		
}
