package GUI;

import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;


public abstract class CommonGameBoard extends Composite implements GameBoard {

	MazeDisplayer md;
	int h;
	int w;
	//GameCharacter gameCharecter;
	LinkedList<GameCharacter> gameCharecters;
	
	
	public CommonGameBoard(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		gameCharecters = new LinkedList<GameCharacter>();
	}

	public void setMD(MazeDisplayer md){
		this.md = md;
	}
	
	public void setGameCharecter(GameCharacter gameCharecter) {
		//this.gameCharecter = gameCharecter;
	}
	
	public void addGameCharecter(GameCharacter gameCharecter) {
		this.gameCharecters.add(gameCharecter);
	}
	
	public void removeGameCharecter(GameCharacter gameCharecter) {
		this.gameCharecters.remove(gameCharecter);
	}
	
	public void SetMD(MazeDisplayer MD){
		this.md = MD;
	}
}
