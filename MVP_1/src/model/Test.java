package model;

import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class Test {
	public static void main(String[] args) {
		
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(MazeData.class);
		config.addAnnotatedClass(MazeNames.class);
		
		config.configure("hibernate.cfg.xml");
		
		new SchemaExport(config).create(true, true);
		
		Model Model = new MyModel();
		Model.generateMaze(5, 5,"momo");
		Maze maze = Model.getMaze();
		Model.generateMaze(6, 6,"mimo");
		Maze maze2 = Model.getMaze();
		System.out.println();
		System.out.println();
		Model.solveMaze(maze);
		Solution sol = Model.getSolution();
		Model.solveMaze(maze2);
		Solution sol2 = Model.getSolution();
		System.out.println(maze.toString() + System.lineSeparator() + sol.toString());
		System.out.println(maze2.toString() + System.lineSeparator() + sol2.toString());
		Model.stop();
		
		return;
	}
}