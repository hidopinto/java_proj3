package GUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

public class ImgGameCharacter extends GameCharacter{

	Image image; 
	
	public ImgGameCharacter(int y,int x,int targetI,int targetJ, Image image) {
		super(x,y,targetI,targetJ);
		this.image = image;
	}
	
	public void paint(PaintEvent e,int w,int h){
		e.gc.setForeground(new Color(null,255,0,0));
		e.gc.setBackground(new Color(null,255,0,0));
		e.gc.drawOval(x, y, (int) (w*0.6), (int) (h*0.6));
		if(image==null)
			image = new Image(e.gc.getDevice(), "characters/eli.png");
		e.gc.drawImage(image, 0, 0,image.getImageData().width,image.getImageData().height,x,y, (int) (w*0.6), (int) (h*0.6));
	}
	
	public void paint(GC gc,int w,int h){
		gc.setForeground(new Color(null,255,0,0));
		gc.setBackground(new Color(null,255,0,0));
		gc.drawOval(x, y, (int) (w*0.6), (int) (h*0.6));
		if(image==null)
			image = new Image(gc.getDevice(), "characters/eli.png");
		gc.drawImage(image, 0, 0,image.getImageData().width,image.getImageData().height,x,y, (int) (w*0.6), (int) (h*0.6));
	}
	
	@Override
	public void setData(Object image){
		if((Image) image ==null)
			return;
		this.image = (Image) image;
	}
	
}
