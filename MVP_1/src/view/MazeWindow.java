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
		Button startButton = new Button(shell, SWT.PUSH);
		startButton.setText("start");
		startButton.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
		
		
		final MazeDisplay maze=new MazeDisplay(shell, SWT.BORDER, this.maze);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		maze.addKeyListener(new KeyListener() { //allows the user move the character with the arrows.
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch( e.keyCode ) {
				case SWT.ARROW_LEFT:
		            // handle left
					maze.gameCharecter.x -=maze.w;
					maze.redraw();
		            break;
		        case SWT.ARROW_UP:
		            // handle up
		        	maze.gameCharecter.y -=maze.h;
		        	maze.redraw();
		            break;
		        case SWT.ARROW_RIGHT:
		            // handle right
		        	maze.gameCharecter.x +=maze.w;
		        	maze.redraw();
		            break;
		        case SWT.ARROW_DOWN:
		            // handle down
		        	maze.gameCharecter.y +=maze.h;
		        	maze.redraw();
		            break;
		     }
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		
		
		Button stopButton = new Button(shell, SWT.PUSH);
		stopButton.setText("stop");
		stopButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		startButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				maze.start();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		stopButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				maze.stop();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});


	}

}
