package view;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import java.util.HashMap;
import java.util.Observable;

/**
 * a class that represents a view, from the mvp framework.
 * MyView extends Observable.
 * MyView implements View .
 * @author Adir
 * @see Observable
 * @see View
 */
public class MyView extends CommonView {
	
	HashMap<String, presenter.Presenter.Command> commands;

	/**
	 * starts the view
	 */
	@Override
	public void start() {
		//System.out.println("starting");
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * setting the commands hashmap
	 * @param commands - a HashMap<String, presenter.Presenter.Command>.
	 */
	@Override
	public void setCommands(HashMap<String, presenter.Presenter.Command> commands) {
		this.commands=commands;
	}

	/**
	 * getting the commands hashmap
	 * @param command - a String.
	 * @return commands.get(command) - a presenter.Presenter.Command.
	 */
	public presenter.Presenter.Command getUserCommand(String command) {
		System.out.println("getting user command");
		return (presenter.Presenter.Command) commands.get(command);
	}

	/**
	 * displays the maze
	 * @param m - a Maze.
	 * @see Maze
	 */
	@Override
	public void displayMaze(Maze m) {
		System.out.println("displaying maze");
		System.out.println(m.toString());
		//this.setChanged();
		//this.notifyObservers();
	}

	/**
	 * displays the solution
	 * @param s - a Solution.
	 */
	@Override
	public void displaySolution(Solution s) {
		System.out.println("displaying solution");
		System.out.println(s.toString());
		//this.setChanged();
		//this.notifyObservers();
	}

	
	/**
	 * just like Observable.setChanged, but public.
	 */
	public void setChanged1 ()
	{
		this.setChanged();
	}


}
