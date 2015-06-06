package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;

import presenter.Presenter.Command;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**
 * @author HFL
 *
 */
public class MazeWindow extends BasicWindow implements View{//implements View

	Maze maze;
	
	public MazeWindow(String title, int width, int height, Maze m) {
		super(title, width, height);
		this.maze = m;
	}

	/* (non-Javadoc)
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		
		final MazeDisplay maze=new MazeDisplay(shell, SWT.FILL, this.maze);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		
		maze.addListener (SWT.Resize,  new Listener () {
		    @Override
			public void handleEvent(Event arg0) {
		    	GC gc = new GC(maze, SWT.FILL);
		        for(int i=0;i<maze.mazeData.getRows();i++){
					for(int j=0;j<maze.mazeData.getCols();j++){
						/*if(gcI == i && gcJ==j){
							maze[i][j].setGc(gameCharecter);
							maze[i][j].redraw();
						}else{
							maze[i][j].redraw();
							maze[i][j].setGc(null);	
						}*/
						maze.maze[j][i].paint(gc, i*maze.w, j*maze.h, maze.w, maze.h); //for some reason it flips the rows with the cols so this fixes it.
					}
			   	}//draws the maze
		        maze.gameCharecter.paint(gc, maze.w,maze.h);
			}
		    });
		
		maze.addKeyListener(new KeyListener() { //allows the user move the character with the arrows.
			
			@Override
			public void keyReleased(KeyEvent e) {
				int i = maze.gameCharecter.x/maze.w;
				int j= maze.gameCharecter.y/maze.h;
				Cell cell = maze.mazeData.getCell(j,i);//for some reason it flips the rows with the cols so this fixes it.
				GC gc = new GC(maze, SWT.FILL);
				switch( e.keyCode ) {
				case SWT.ARROW_LEFT:
		            // handle left
					if((cell.getHasLeftWall() == false) && !((i ==0)&&(j==0))){
						
						maze.gameCharecter.x -=maze.w;
						maze.maze[j][i].paint(gc, i*maze.w, j*maze.h, maze.w, maze.h);
						maze.gameCharecter.paint(gc, maze.w, maze.h);
					}
		            break;
		        case SWT.ARROW_UP:
		            // handle up
		        	if((cell.getHasTopWall() == false)){
						maze.gameCharecter.y -=maze.h;
						maze.maze[j][i].paint(gc, i*maze.w, j*maze.h, maze.w, maze.h);
						maze.gameCharecter.paint(gc, maze.w, maze.h);					}
		            break;
		        case SWT.ARROW_RIGHT:
		            // handle right
					if((cell.getHasRightWall() == false) && !(j==(maze.mazeData.getCols()-1) && (i==(maze.mazeData.getRows()-1)) )){
						maze.gameCharecter.x +=maze.w;
						maze.maze[j][i].paint(gc, i*maze.w, j*maze.h, maze.w, maze.h);
						maze.gameCharecter.paint(gc, maze.w, maze.h);					}
		            break;
		        case SWT.ARROW_DOWN:
		            // handle down
					if((cell.getHasBottomWall() == false)){
						maze.gameCharecter.y +=maze.h;
						maze.maze[j][i].paint(gc, i*maze.w, j*maze.h, maze.w, maze.h);
						maze.gameCharecter.paint(gc, maze.w, maze.h);					}
					break;
		        case SWT.ESC:
		            // handle esc
					shell.dispose();
					break;
		     }
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});


	}

	@Override
	public void start() {
		// TODO start a mini game
		
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Command getUserCommand(String command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayMaze(Maze m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(Solution s) {
		// TODO Auto-generated method stub
		
	}

}
