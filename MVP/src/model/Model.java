package model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface Model {
	void generateMaze(int i,int j,String name);
	Maze getMaze();
	void solveMaze(String name);
	Solution getSolution();
	void stop();
}