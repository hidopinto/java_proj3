/**
 * 
 */
package view;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.hibernate.annotations.Parent;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

/**
 * @author HFL
 *
 */
public class MazeDisplay extends Composite {
	
	Maze mazeData;
	MazeDisplay Me = this;
	
	//CellDisplay[][] maze = null;
	
	imgDisplay[][] maze =null;
	Image[][] images = null;
	GameCharacter gameCharecter;
	int h;
	int w;

	
	public MazeDisplay(Composite parent,int style, Maze m){
        super(parent, style);
        mazeData = m;        
        gameCharecter = new GameCharacter(0,0);
        displayMaze();
        
        addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				   int width=getSize().x;
				   int height=getSize().y;

				   w=width/mazeData.getCols();
				   h=height/mazeData.getRows();
				   
				   if(gameCharecter.x == 0 && gameCharecter.y == 0){
					   gameCharecter.x = (int)(w*0.2);
					   gameCharecter.y = (int)(h*0.2);
				   }
				   
				   int gcI = gameCharecter.x/w;
				   int gcJ = gameCharecter.y/h;
					for(int i=0;i<mazeData.getRows();i++){
						for(int j=0;j<mazeData.getCols();j++){
							/*if(gcI == i && gcJ==j){
								maze[i][j].setGc(gameCharecter);
								maze[i][j].redraw();
							}else{
								maze[i][j].redraw();
								maze[i][j].setGc(null);	
							}*/
							maze[j][i].paint(e, i*w, j*h, w, h); //for some reason it flips the rows with the cols so this fixes it.
						}
				   	}//draws the maze
				gameCharecter.paint(e, w, h);
			}
		});
	}
	
	public void displayMaze(){
		
		GridLayout layout = new GridLayout(mazeData.getCols(), true);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		setLayout(layout);
		layout();
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,mazeData.getRows(),mazeData.getCols() ));
		
		if(maze==null){
			   if(images==null){
					images = new Image[mazeData.getRows()][mazeData.getCols()];
					//maze = new CellDisplay[mazeData.getRows()][mazeData.getCols()];
					maze = new imgDisplay[mazeData.getRows()][mazeData.getCols()];
			   }
			   
				   for(int i=0;i<mazeData.getRows();i++){
					   for(int j=0;j<mazeData.getCols();j++){
							   /*if(images[i][j]==null){
			        	  			images[i][j] = chooseImage(mazeData.getCell(i, j),i,j);
			        	  			maze[i][j].setI(images[i][j]);
			        	  		}*/
						   		//maze[j][i].paint(e, i*w, j*h, w, h); //for some reason it flips the rows with the cols so this fixes it.				              
						   /*if(maze[i][j] == null){
						   		Image im = chooseImage(mazeData.getCell(i, j),i,j);
						   		maze[i][j] = new CellDisplay(this, SWT.FILL);
						   		maze[i][j].setI(im);
						   }*/
						   if(maze[i][j] == null){
			        			Image im = chooseImage(mazeData.getCell(i, j),i,j);
			        			maze[i][j] = new imgDisplay(im);
			        		}
					   }
				   }
		   }//setting up the maze
		this.getShell().layout();
		
		/*if(maze==null){
			images = new Image[mazeData.getCols()][mazeData.getRows()];
			//maze = new imgDisplay[mazeData.getCols()][mazeData.getRows()];
        	maze = new CellDisplay[mazeData.getCols()][mazeData.getRows()];
			for(int i = 0;i<mazeData.getCols();i++)
        		for(int j=0;j<mazeData.getRows();j++)
        		{
        			Image im = chooseImage(mazeData.getCell(i, j),i,j);
        			//maze[i][j] = new imgDisplay(im);
        			maze[i][j] = new CellDisplay(this, SWT.NONE, im);
        		}
		}
		
		this.update();*/
	}
	
	
	
	public void stop(){
		//TODO
	}
	
	final public Image chooseImage(Cell cell,int i,int j){
		Image im = null;
		
		String image_path = "squares/square";
		if(cell.getHasRightWall())
			image_path+="_right";
		
		if(cell.getHasBottomWall())
			image_path+="_bottom";
		
		if(cell.getHasLeftWall() && (j == 0))//for some reason it flips the rows with the cols so this fixes it.
			image_path+="_left";
		
		if(cell.getHasTopWall() && (i == 0))//for some reason it flips the rows with the cols so this fixes it.
			image_path+="_top";
		
		image_path+=".jpg";
		
		im = new Image(getDisplay(), new ImageData(image_path));
		
		return im;
	}

	public Maze getMazeData() {
		return mazeData;
	}

	public void setMazeData(Maze mazeData) {
		this.mazeData = mazeData;
		this.images = null;
		//displayMaze();
	}

}
