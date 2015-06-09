package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface View {
	void start(); 
	void setCommands(HashMap<String, presenter.Presenter.Command> commands); 
	presenter.Presenter.Command getUserCommand(String command); 
	void displayMaze(Maze m); 
	void displaySolution(Solution s);
}
