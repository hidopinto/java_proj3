package model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class is used only for data. It's only purpose is to be written in a DataBase later.
 * @author Yehuda Hido Pinto
 * @see Maze
 * @see Solution
 */
@Entity
public class MazeData {
	String maze;
	String sol;
	
	/**
	 * simple constructor. sets the variable to null.
	 */
	public MazeData() {
		super();
		this.maze = null;
		this.sol = null;
	}
	
	/**
	 * a constructor that sets the variables to the ones it receives.
	 * @param maze - a String that describes a Maze.
	 * @param sol - a String that describes a Solution
	 */
	public MazeData(String maze,String sol) {
		super();
		this.maze = maze;
		this.sol = sol;
	}
	
	/**
	 * returns maze.
	 * @return maze - a String that describes a Maze;
	 */
	@Id
	public String getMaze() {
		return maze;
	}
	
	/**
	 * set maze to the variable maze.
	 * @param maze - a String that describes a Maze.
	 */
	public void setMaze(String maze) {
		this.maze = maze;
	}
	
	/**
	 * returns sol.
	 * @return sol - a String that describes a Solution;
	 */
	public String getSol() {
		return sol;
	}
	
	/**
	 * set sol to the variable sol.
	 * @param sol - a String that describes a Solution.
	 */
	public void setSol(String sol) {
		this.sol = sol;
	}
	
}
