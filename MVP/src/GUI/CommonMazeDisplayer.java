package GUI;

public abstract class CommonMazeDisplayer implements MazeDisplayer {

	CommonGameBoard board;
	
	public void setBoard(CommonGameBoard board) {
		this.board = board;
	}
	
}
