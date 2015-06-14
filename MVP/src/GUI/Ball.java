package GUI;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;


public class Ball {
	
	char dir;
	int x;
	int y;
	Timer timer;
	TimerTask myTask;
	Composite widget;
	Image image;
	
	public Ball(char dir,int X,int Y,Composite widget,TimerTask task) {
		
		this.x=X;
		this.y=Y;
		this.widget=widget;
		this.myTask = task;
		this.dir=dir;
		
		timer = new Timer();
		timer.scheduleAtFixedRate(myTask, 0, 20);
	}
	
	public void stop(){
		myTask.cancel();
		timer.cancel();
	}

	public void paint(GC  gc,int w,int h){
		if(image ==null)
			image = new Image(gc.getDevice(), "animations/ball.png");
		widget.redraw();
		gc.drawImage(image, 0, 0,image.getImageData().width,image.getImageData().height,x,y, (int) (w*0.4), (int) (h*0.4));
	}
	
}
