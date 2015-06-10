package GUI;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Cell;

public class Ball {
	
	char dir;
	int x;
	int y;
	int w;
	int h;
	Timer timer;
	TimerTask myTask;
	Composite widget;
	Image image;
	
	public Ball(char dir,int X,int Y,int W,int H,Composite widget,TimerTask task) {
		
		this.x=X;
		this.y=Y;
		this.w=W;
		this.h=H;
		this.widget=widget;
		this.myTask = task;
		this.dir=dir;
		
		timer = new Timer();
		timer.scheduleAtFixedRate(myTask, 0, 1);
	}
	
	public void stop(){
		myTask.cancel();
		timer.cancel();
	}

	public void paint(GC  gc){
		if(image ==null)
			image = new Image(gc.getDevice(), "animations/ball.png");
		widget.redraw();
		gc.drawImage(image, 0, 0,image.getImageData().width,image.getImageData().height,x,y, (int) (w*0.4), (int) (h*0.4));
	}
	
}
