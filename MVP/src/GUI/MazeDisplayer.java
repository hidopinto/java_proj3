package GUI;

import org.eclipse.swt.events.PaintEvent;

import algorithms.search.Solution;

public interface MazeDisplayer {
	void draw(PaintEvent e);
	void drawSol(PaintEvent e, Solution sol);
	void undrawSol(PaintEvent e, Solution sol);
}
