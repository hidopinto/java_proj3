package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import view.imgDisplay;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

public class ImgMazeDisplayer extends CommonMazeDisplayer {
	
	Image[][] images = null;
	imgDisplay[][] maze =null;
	Maze mazeData;
	
	public ImgMazeDisplayer(Maze mazeData,  CommonGameBoard board) {
		super();
		this.mazeData = mazeData;
		this.board = board;
	}
	
	@Override
	public void draw(PaintEvent e) {
		
		GridLayout layout = new GridLayout(mazeData.getCols(), true);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		board.setLayout(layout);
		board.layout();
		board.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,mazeData.getRows(),mazeData.getCols() ));
		
		if(maze==null){
			   if(images==null){
					images = new Image[mazeData.getRows()][mazeData.getCols()];
					maze = new imgDisplay[mazeData.getRows()][mazeData.getCols()];
			   }
			   
				   for(int i=0;i<mazeData.getRows();i++){
					   for(int j=0;j<mazeData.getCols();j++){
						   if(maze[i][j] == null){
			        			Image im = chooseImage(mazeData.getCell(i, j),i,j);
			        			maze[i][j] = new imgDisplay(im);
			        		}
					   }
				   }
		   }//setting up the maze
		board.getShell().layout();
		
		for(int i=0;i<mazeData.getRows();i++){
			for(int j=0;j<mazeData.getCols();j++){
				maze[j][i].paint(e, i*board.w, j*board.h, board.w, board.h); //for some reason it flips the rows with the cols so this fixes it.
			}
	   	}//paints the window
	}

	public Image chooseImage(Cell cell,int i,int j){
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
		
		im = new Image(board.getDisplay(), new ImageData(image_path));
		
		return im;
	}
}
