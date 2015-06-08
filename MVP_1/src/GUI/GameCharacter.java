package GUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class GameCharacter {
	int x;
	int y;
		   
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
}
