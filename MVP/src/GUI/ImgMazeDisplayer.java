package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class ImgMazeDisplayer extends CommonMazeDisplayer {
	
	Image[][] images = null;
	imgCellDisplay[][] maze =null;
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
		
		if(maze==null){//or maze have been changed
			   if(images==null){
					images = new Image[mazeData.getRows()][mazeData.getCols()];
					maze = new imgCellDisplay[mazeData.getRows()][mazeData.getCols()];
			   }
			   
				   for(int i=0;i<mazeData.getRows();i++){
					   for(int j=0;j<mazeData.getCols();j++){
						   if(maze[i][j] == null){
			        			Image im = chooseImage(mazeData.getCell(i, j),i,j,false);
			        			maze[i][j] = new imgCellDisplay(im);
			        		}
					   }
				   }
		   }//setting up the maze
		board.getShell().layout();
		
		for(int i=0;i<mazeData.getCols();i++){
			for(int j=0;j<mazeData.getRows();j++){
				maze[j][i].paint(e, i*board.w, j*board.h, board.w, board.h); //for some reason it flips the rows with the cols so this fixes it.
			}
	   	}//paints the window
	}

	public Image chooseImage(Cell cell,int i,int j, boolean sol){
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
		
		if(sol==true)
			image_path+="_sol";
		
		image_path+=".jpg";
		
		im = new Image(board.getDisplay(), new ImageData(image_path));
		
		return im;
	}

	@Override
	public void drawSol(PaintEvent e, Solution sol) {
		String[] solMaze = sol.toString().split(System.lineSeparator());
		String[] CellPoint = null;
		
		for(String line : solMaze){
			CellPoint = line.split(" ");
			int x = new Integer(CellPoint[0].split(",")[0]);
			int y = new Integer(CellPoint[0].split(",")[1]);
			Image im = chooseImage(mazeData.getCell(x, y), x, y, true);
			maze[x][y].setI(im);
			maze[x][y].paint(e, x*board.w, y*board.h, board.w, board.h); //for some reason it flips the rows with the cols so this fixes it.
		}
							
	}
	
	@Override
	public void undrawSol(PaintEvent e, Solution sol) {
		String[] solMaze = sol.toString().split(System.lineSeparator());
		String[] CellPoint = null;
		
		for(String line : solMaze){
			CellPoint = line.split(" ");
			int x = new Integer(CellPoint[0].split(",")[0]);
			int y = new Integer(CellPoint[0].split(",")[1]);
			Image im = chooseImage(mazeData.getCell(x, y), x, y, false);
			maze[x][y].setI(im);
			maze[x][y].paint(e, x*board.w, y*board.h, board.w, board.h); //for some reason it flips the rows with the cols so this fixes it.
		}
							
	}
	
}
