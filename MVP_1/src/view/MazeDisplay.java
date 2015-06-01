/**
 * 
 */
package view;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

/**
 * @author HFL
 *
 */
public class MazeDisplay extends Composite {
	
	Maze mazeData;
	
	Timer timer;
	TimerTask myTask;
	GameCharacter gameCharecter;
	int h;
	int w;

	
	public MazeDisplay(Composite parent,int style, Maze m){
        super(parent, style);
        
        mazeData = m;
        
        //setBackground(new Color(null, 255, 255, 255));
        gameCharecter = new GameCharacter(0, 0);//change to (0,0)
        
        displayMaze();
        
	}
	
	public void displayMaze(){
		
		final CellDisplay[][] maze = new CellDisplay[mazeData.getRows()][mazeData.getCols()];
        for(int i = 0;i<mazeData.getRows();i++)
        	for(int j=0;j<mazeData.getCols();j++)
        	{
        		Image im = chooseImage(mazeData.getCell(i, j));
        		maze[i][j] = new CellDisplay(this, SWT.FILL, im);
        		maze[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,1));
        	}

        addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				
				//e.gc.setForeground(new Color(null,0,0,0));
				   //e.gc.setBackground(new Color(null,0,0,0));
				   int width=getSize().x;
				   int height=getSize().y;

				   w=width/mazeData.getCols();
				   h=height/mazeData.getRows();

				   for(int i=0;i<mazeData.getRows();i++)
				      for(int j=0;j<mazeData.getCols();j++){
				          //int x=j*w;
				          //int y=i*h;
				          if(mazeData.getCell(i, j)!=null){
				        		Image im = chooseImage(mazeData.getCell(i, j));
				        		maze[i][j].setI(im);
				          }				              
				        	  
				        	  //e.gc.fillRectangle(x,y,w,h);
				          	  //e.gc.drawOval(x, y, w, h);
				        	  //e.gc.drawRectangle(x, y, w, h);
				          	
				      }
				   //gameCharecter.x = 3*w;
				   //gameCharecter.y = 5*h;
				   gameCharecter.paint(e, w, h);

					
				
				
			}
		});
		
	}
	
	public void start(){ // change it so the arrow keys would move the character.
						 // use KeyListener
		myTask = new TimerTask() {
			
			@Override
			public void run() {
			
			      getDisplay().syncExec(new Runnable() {

			    		@Override
			    		public void run() {
			    		   Random r=new Random();
			    		   gameCharecter.x = r.nextInt(100);
							gameCharecter.y = r.nextInt(100);
			    		   redraw();
			    		}
			    	 });

				
				/*Random r = new Random();
				gameCharecter.x = r.nextInt(100);
				gameCharecter.y = r.nextInt(100);
				redraw();*/
				
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(myTask, 0, 500);
	}
	
	public void stop(){
		myTask.cancel();
		timer.cancel();
	}
	
	final public Image chooseImage(Cell cell){
		Image i = null;
		
		if(cell.getHasBottomWall())
			i = new Image(getDisplay(), new ImageData("images/cell.jpg"));
		
		if(cell.getHasLeftWall())
			i = new Image(getDisplay(), new ImageData("images/cell.jpg"));
		
		if(cell.getHasRightWall())
			i = new Image(getDisplay(), new ImageData("images/cell.jpg"));
		
		if(cell.getHasTopWall())
			i = new Image(getDisplay(), new ImageData("images/cell.jpg"));
		//needs to check if cell is in the borders 
		//sets the right image for the cell
		
		return i;
	}

}
