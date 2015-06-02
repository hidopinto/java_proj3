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

	Image i;
	
	public CellDisplay(Composite parent, int style, Image i) {
		super(parent, style);
        this.i=i;
        addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				ImageData data = i.getImageData();
				e.gc.drawImage(i, 0, 0,data.width,data.height,0,0,getSize().x,getSize().y);
			}
		});

	}

	public void setI(Image i) {
		this.i = i;
		System.out.println("setting I");
		//redraw();
	}
	
}
