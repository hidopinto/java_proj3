/**
 * 
 */
package GUI;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.win32.MCHITTESTINFO;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import view.imgDisplay;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

/**
 * @author HFL
 *
 */
public class ImgGameBoard extends CommonGameBoard {
	Maze mazeData;
	
	public ImgGameBoard(Composite parent,int style, MazeDisplayer md,Maze mazeData){
        super(parent, style | SWT.DOUBLE_BUFFERED);
        this.md = md;
        this.mazeData = mazeData;
        gameCharecter = new GameCharacter(0,0);
        h = 1;
        w = 1;
        
        addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				   int width=getSize().x;
				   int height=getSize().y;

				   int x = (int)(gameCharecter.x / w);
				   int y = (int)(gameCharecter.y / h);
				   
				   if(ImgGameBoard.this.md == null)
					   return;
				   
				   w=width/mazeData.getCols();
				   h=height/mazeData.getRows();
				   
				   x *= w;
				   y *= h;
				   
				   gameCharecter.x = x + (int)(w*0.2);
				   gameCharecter.y = y + (int)(h*0.2);
				   if(md==null)
					  return;
				   md.draw(e);//draws the maze
				   
				gameCharecter.paint(e, w, h);
			}
		});
	}
	
	public void generateBoard(){
		redraw();
	}
	
	public void stop(){
		//TODO
	}

}
