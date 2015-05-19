package model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class is used only for data. It's only purpose is to be written in a DataBase later.
 * @author Yehuda Hido Pinto
 * @see Maze
 */
@Entity
public class MazeNames {

	String name;
	String maze;
	
	
	/**
	 * a simple constructor that sets the name and maze to null. 
	 */
	public MazeNames() {
		this.name = null;
		this.maze = null;
	}
	
	/**
	 * a constructor that sets the variables to the ones it receives.
	 * @param name - the maze's name.
	 * @param maze - the maze.
	 */
	public MazeNames(String name, String maze) {
		super();
		this.name = name;
		this.maze = maze;
	}
	
	/**
	 * gets the maze's name.
	 * @return name - the maze's name.
	 */
	@Id
	public String getName() {
		return name;
	}

	/**
	 * sets the maze's name.
	 * @param name - the maze's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * returns a String that describes the maze.
	 * @return maze - a String that describes the maze.
	 */
	public String getMaze() {
		return maze;
	}

	
	/**
	 * sets a String that describes the maze.
	 * @param maze - a String that describes the maze.
	 */
	public void setMaze(String maze) {
		this.maze = maze;
	}
}
