package GUI;

import java.util.HashMap;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import presenter.Presenter.Command;
import view.View;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**
 * @author HFL
 *
 */
public class MazeWindow extends BasicWindow implements View{//implements View

	Maze myMaze;
	CommonGameBoard maze;
	CommonMazeDisplayer md;
	char LastPressed = 's';
	
	public MazeWindow(String title, int width, int height, Maze m) {
		super(title, width, height);
		this.myMaze = m;
	}

	/* (non-Javadoc)
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(1, false));
		md = new ImgMazeDisplayer(myMaze, null);
		
		maze=new ImgGameBoard(shell, SWT.FILL, md, myMaze);
		md.setBoard(maze);
		
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,1));
		
		maze.addListener (SWT.Resize,  new Listener () {
		    @Override
			public void handleEvent(Event arg0) {
		    	maze.redraw();
		    }
		    });
		
		maze.addKeyListener(new KeyListener() { //allows the user move the character with the arrows.
			
			@Override
			public void keyReleased(KeyEvent e) {
				int i1 = maze.gameCharecters.get(0).x/maze.w;
				int j1= maze.gameCharecters.get(0).y/maze.h;
				//int i2 = maze.gameCharecters.get(1).x/maze.w;
				//int j2= maze.gameCharecters.get(1).y/maze.h;
				Cell cell = myMaze.getCell(j1,i1);//for some reason it flips the rows with the cols so this fixes it.
				switch( e.keyCode ) {
				case SWT.ARROW_LEFT:
		            // handle left
					if((cell.getHasLeftWall() == false) && !((i1 ==0)&&(j1==0))){
						
						maze.gameCharecters.get(0).x -=maze.w;
						maze.redraw();
						LastPressed='l';
					}
		            break;
		        case SWT.ARROW_UP:
		            // handle up
		        	if((cell.getHasTopWall() == false)){
						maze.gameCharecters.get(0).y -=maze.h;
						maze.redraw();
						LastPressed='u';
						}
		            break;
		        case SWT.ARROW_RIGHT:
		            // handle right
					if((cell.getHasRightWall() == false) && !(j1==(myMaze.getCols()-1) && (i1==(myMaze.getRows()-1)) )){
						maze.gameCharecters.get(0).x +=maze.w;
						maze.redraw();	
						LastPressed='r';
						}
					if(j1==(myMaze.getCols()-1) && (i1==(myMaze.getRows()-1)) ){
						//finish line - play music, show a win window, etc.
					}
		            break;
		        case SWT.ARROW_DOWN:
		            // handle down
					if((cell.getHasBottomWall() == false)){
						maze.gameCharecters.get(0).y +=maze.h;
						maze.redraw();	
						LastPressed='d';
						}
					break;
		        case SWT.ESC:
		            // handle esc
		        	if(maze.gameCharecters.get(0).ball !=null){
		        		maze.gameCharecters.get(0).ball.stop();
						maze.gameCharecters.get(0).ball=null;
		        	}
					shell.dispose();
					break;
		        case SWT.CTRL:
		        	if(LastPressed == 's')
		        		break;
		        	if(maze.gameCharecters.get(0).ball != null)
		        		break;
		        	
		        	TimerTask task = new TimerTask() {
		    			
		    			@Override
		    			public void run() {
		    				
		    				  //maze.getDisplay().syncExec(new Runnable() {
		    			      maze.getDisplay().asyncExec(new Runnable() {

		    			    		@Override
		    			    		public void run() {
		    			    			int i = maze.gameCharecters.get(0).ball.x/maze.w;
		    							int j= maze.gameCharecters.get(0).ball.y/maze.h;
		    							Cell cell = myMaze.getCell(j, i);//for some reason it flips the rows with the cols so this fixes it.
		    			    			GC gc = new GC(maze, SWT.NONE);
		    			    			
		    			    			switch (maze.gameCharecters.get(0).ball.dir) {
		    							case 'l':
		    								// handle left
		    								if((cell.getHasLeftWall() == false) && !((i ==0)&&(j==0))){
		    									maze.gameCharecters.get(0).ball.x -= (int) (maze.w*0.1);
			    								maze.gameCharecters.get(0).ball.paint(gc);
			    							}
		    								else if( ((maze.gameCharecters.get(0).x - (int) (maze.w*0.1))/maze.w) < j){
		    									maze.gameCharecters.get(0).ball.stop();
		    									maze.gameCharecters.get(0).ball=null;
		    									maze.redraw();
		    									maze.getDisplay().disposeExec(this);
		    								}
		    								break;
		    							case 'u':
		    								// handle up
		    					        	if((cell.getHasTopWall() == false)){
		    					        		maze.gameCharecters.get(0).ball.y -= (int) (maze.h*0.1);
			    								maze.gameCharecters.get(0).ball.paint(gc);
		    								}
		    					        	else if( ((maze.gameCharecters.get(0).y - (int) (maze.h*0.1))/maze.h) < i){
		    									maze.gameCharecters.get(0).ball.stop();
		    									maze.gameCharecters.get(0).ball=null;
		    									maze.redraw();
		    									maze.getDisplay().disposeExec(this);
		    								}
		    								break;
		    							case 'r':
		    								// handle right
		    								if((cell.getHasRightWall() == false) && !(j==(myMaze.getCols()-1) && (i==(myMaze.getRows()-1)) )){
		    									maze.gameCharecters.get(0).ball.x += (int) (maze.w*0.1);
			    								maze.gameCharecters.get(0).ball.paint(gc);
		    								}
		    								else if( ((maze.gameCharecters.get(0).x + (int) (maze.w*0.1))/maze.w) < j){
		    									maze.gameCharecters.get(0).ball.stop();
		    									maze.gameCharecters.get(0).ball=null;
		    									maze.redraw();
		    									maze.getDisplay().disposeExec(this);
		    								}
		    								break;
		    							case 'd':
		    								// handle down
		    								if((cell.getHasBottomWall() == false)){
		    									maze.gameCharecters.get(0).ball.y += (int) (maze.h*0.1);
			    								maze.gameCharecters.get(0).ball.paint(gc);
		    								}
		    								else if( ((maze.gameCharecters.get(0).y - (int) (maze.h*0.1))/maze.h) < i){
		    									maze.gameCharecters.get(0).ball.stop();
		    									maze.gameCharecters.get(0).ball=null;
		    									maze.redraw();
		    									maze.getDisplay().disposeExec(this);
		    								}
		    								break;
		    							}
		    			    		}
		    			    	 });
		    				
		    			}
		    		};
		        	maze.gameCharecters.get(0).ball = new Ball(LastPressed,maze.gameCharecters.get(0).x, maze.gameCharecters.get(0).y, maze.w, maze.h, maze,task);
		        	
		        	break;
		     }
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});


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
		//Solution sol= myMaze
		
	}

	@Override
	public void start() {} // do nothing

}
