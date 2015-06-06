package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Cell;

public class CellDisplay extends Canvas{

	Image image = null;
	GameCharacter gc = null;
	
	public CellDisplay(Composite parent, int style) {
		super(parent, style);
        addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				if(image!=null){		
					System.out.println("PRINT CELL STUB");
					e.gc.drawImage(image, 0, 0,image.getImageData().width,image.getImageData().height,0,0,getSize().x,getSize().y);
				}
				if(gc!=null)
					gc.paint(e, (int) (getSize().x * 0.8), (int) (getSize().y * 0.8));
				}
		});

	}

	public void setI(Image i) {
		this.image = i;
	}

	public void setGc(GameCharacter gc) {
		this.gc = gc;
	}
}
