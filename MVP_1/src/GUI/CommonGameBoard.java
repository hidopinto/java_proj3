package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze;

public abstract class CommonGameBoard extends Composite implements GameBoard {

	MazeDisplayer md;
	int h;
	int w;
	GameCharacter gameCharecter;
	Maze mazeData;
	
	public CommonGameBoard(Composite parent, int style,Maze mazeData) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		this.mazeData = mazeData;
	}

	public void setMD(MazeDisplayer md){
		this.md = md;
	}
	
	public void setGameCharecter(GameCharacter gameCharecter) {
		this.gameCharecter = gameCharecter;
	}
	
	
	
}
