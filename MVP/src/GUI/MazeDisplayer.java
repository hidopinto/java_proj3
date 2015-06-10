package GUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.GC;

import algorithms.search.Solution;

public interface MazeDisplayer {
	void draw(PaintEvent e);
	void drawSol(PaintEvent e, Solution sol);
}
