package GUI;

import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Maze;
import view.imgDisplay;

public abstract class CommonMazeDisplayer implements MazeDisplayer {

	CommonGameBoard board;
	
	public void setBoard(CommonGameBoard board) {
		this.board = board;
	}
	
}