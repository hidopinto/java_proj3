/**
 * 
 */
package GUI;


import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze;

/**
 * @author HFL
 *
 */
public class ImgGameBoard extends CommonGameBoard {
	Maze mazeData;
	
	public ImgGameBoard(Composite parent,int style, Displayer md,Maze mazeData){
        super(parent, style | SWT.DOUBLE_BUFFERED);
        this.md = md;
        this.mazeData = mazeData;
        
        gameCharecters.add(new ImgGameCharacter(0,0,((mazeData.getRows()-1)) , ((mazeData.getCols()-1)),null));
        
        h = 1;
        w = 1;
        
        addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				   int width=getSize().x;
				   int height=getSize().y;
				   
				   int Last_w = w;
				   int Last_h = h;
				   
				   w=width/mazeData.getCols();
				   h=height/mazeData.getRows();
				   
				   Iterator<GameCharacter> ite = gameCharecters.iterator();
				   
				   while(ite.hasNext()){
					   GameCharacter gc = ite.next();
					   int x = (int) (gc.x / Last_w);
					   int y = (int) (gc.y / Last_h);
					   
					   x *= w;
					   y *= h;
					   
					   gc.x = x + (int)(w*0.2);
					   gc.y = y + (int)(h*0.2);
					   
					   //no need to check on the ball because the w&h settings are only for reSizeing events, which are not likely to happen when a player shoots.
					   
				   }//sets the characters to be in the center of the cell, even if the cell's sizes have been changed.
				   
				   if(md==null)
					  return;
				   
				   md.draw(e);//draws the maze
				   
				   ite=gameCharecters.iterator();
				   
				   while(ite.hasNext()){
					   GameCharacter gc = ite.next();
					   gc.paint(e, w, h);
				   }//draws all of the characters
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
