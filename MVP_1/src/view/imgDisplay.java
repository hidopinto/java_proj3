package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class imgDisplay {

	Image image;
	
	public imgDisplay(Image img) {
		this.image = img;
	}
	public void paint(PaintEvent e,int start,int end,int w,int h){ 
		e.gc.drawImage(image, 0, 0,image.getImageData().width,image.getImageData().height,start,end,w,h);
	}
	public void paint(GC gc,int start,int end,int w,int h){ 
		gc.drawImage(image, 0, 0,image.getImageData().width,image.getImageData().height,start,end,w,h);
	}
	
	public void setI(Image i) {
		this.image = i;
	}
}
