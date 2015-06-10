package GUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;


public class GameCharacter {
	int x;
	int y;
	Ball ball = null;
		   
	public GameCharacter(int x,int y) {
		this.x=x;this.y=y;
	}
	public void paint(PaintEvent e,int w,int h){
		e.gc.setForeground(new Color(null,255,0,0));
		e.gc.setBackground(new Color(null,255,0,0));
		e.gc.fillOval(x,y, (int) (w*0.6), (int) (h*0.6));
	}
	public void paint(GC gc,int w,int h){
		gc.setForeground(new Color(null,255,0,0));
		gc.setBackground(new Color(null,255,0,0));
		gc.fillOval(x,y, (int) (w*0.6), (int) (h*0.6));
	}
	
	public void setData(Object o){} // this function is here for the classes that extends it, so they could change the sprite, image, etc in the class. Putting this function here makes the programming much more flexable and generic.
	
	public void setBall(Ball b){
		this.ball = b;
	}
}
