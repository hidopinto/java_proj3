package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

/**
 * @author HFL
 *
 */
public class MazeWindow extends BasicWindow {

	Maze maze;
	
	public MazeWindow(String title, int width, int height, Maze m) {
		super(title, width, height);
		this.maze = m;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		
		final MazeDisplay maze=new MazeDisplay(shell, SWT.BORDER, this.maze);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		maze.addKeyListener(new KeyListener() { //allows the user move the character with the arrows.
			
			@Override
			public void keyReleased(KeyEvent e) {
				int i = maze.gameCharecter.x/maze.w;
				int j= maze.gameCharecter.y/maze.h;
				Cell cell = maze.mazeData.getCell(j,i);//for some reason it flips the rows with the cols so this fixes it.
				switch( e.keyCode ) {
				case SWT.ARROW_LEFT:
		            // handle left
					if((cell.getHasLeftWall() == false)){
						
						maze.gameCharecter.x -=maze.w;
						maze.update();
						maze.redraw();
					}
		            break;
		        case SWT.ARROW_UP:
		            // handle up
		        	if((cell.getHasTopWall() == false)){
						maze.gameCharecter.y -=maze.h;
						maze.update();
						maze.redraw();
					}
		            break;
		        case SWT.ARROW_RIGHT:
		            // handle right
					if((cell.getHasRightWall() == false)){
						maze.gameCharecter.x +=maze.w;
						maze.update();
						maze.redraw();
					}
		            break;
		        case SWT.ARROW_DOWN:
		            // handle down
					if((cell.getHasBottomWall() == false)){
						maze.gameCharecter.y +=maze.h;
						maze.update();
						maze.redraw();
					}
					break;
		     }
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		


	}

}
