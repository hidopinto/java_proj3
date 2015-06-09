package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;


public abstract class CommonGameBoard extends Composite implements GameBoard {

	MazeDisplayer md;
	int h;
	int w;
	GameCharacter gameCharecter;
	
	
	public CommonGameBoard(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
	}

	public void setMD(MazeDisplayer md){
		this.md = md;
	}
	
	public void setGameCharecter(GameCharacter gameCharecter) {
		this.gameCharecter = gameCharecter;
	}
	
	
	
}
