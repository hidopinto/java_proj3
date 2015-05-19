package presenter;

import java.io.Serializable;

import algorithms.mazeGenerators.MazeGenerator;
import algorithms.mazeGenerators.MazeGeneratorDFS;
import algorithms.search.Astar;
import algorithms.search.CommonSearcher;
import algorithms.search.MazeManhattanDistance;

/**
 * Properties is a class for saving user properties used at the model.
 * The user can choose how much Threads he want at the ThreadPool,and what search and maze generator algorithm he want to use.
 * Don't worry though, for the ordinary man that doesn't know what any of that mean(and also for the lazy ones), we defined default properties. 
 * @author Adir
 * @see Serializable
 * @see generateAlgorithm
 * @see searchAlgorithm
 */
public class Properties implements Serializable{

	int numOfThreads;
	CommonSearcher searchAlgorithm;
	MazeGenerator generateAlgorithm;
	
	/**
	 * general constructor for Properties. sets default values:
	 * numOfThreads = 1.
	 * searchAlgorithm = Astar algorithm with Maze ManhattanDistance.
	 * generateAlgorithm = DFS maze generating algorithm.
	 */
	public Properties()
	{
		this.numOfThreads = 1;
		this.searchAlgorithm = new Astar(new MazeManhattanDistance());
		this.generateAlgorithm = new MazeGeneratorDFS();
	}
	
	/**
	 * constructor with parameters. sets every dataMember to the value it gets by parameter. 
	 * @param numOfThreads - an int. describes the number of Threads in the ThreadPool.
	 * @param searchAlgorithm - a searchAlgorithm.
	 * @param generateAlgorithm - a generateAlgorithm.
	 */
	public Properties(int numOfThreads, CommonSearcher searchAlgorithm,MazeGenerator generateAlgorithm) {
		super();
		this.numOfThreads = numOfThreads;
		this.searchAlgorithm = searchAlgorithm;
		this.generateAlgorithm = generateAlgorithm;
	}
	
	/**
	 * returns an int that describes the number of Threads in the ThreadPool.
	 * @return numOfThreads - int that describes the number of Threads in the ThreadPool.
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}
	
	/**
	 * sets the numOfThreads .
	 * @param numOfThreads - int that describes the number of Threads in the ThreadPool.
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}
	
	/**
	 * returns searchAlgorithm.
	 * @return a searchAlgorithm - CommonSearcher.
	 * @see CommonSearcher
	 */
	public CommonSearcher getSearchAlgorithm() {
		return searchAlgorithm;
	}
	
	/**
	 * sets the searchAlgorithm.
	 * @param searchAlgorithm - CommonSearcher.
	 * @see CommonSearcher 
	 */
	public void setSearchAlgorithm(CommonSearcher searchAlgorithm) {
		this.searchAlgorithm = searchAlgorithm;
	}
	
	/**
	 * returns generateAlgorithm.
	 * @return generateAlgorithm - a MazeGenerator.
	 * @see MazeGenerator
	 */
	public MazeGenerator getGenerateAlgorithm() {
		return generateAlgorithm;
	}
	
	/**
	 * sets generateAlgorithm.
	 * @param generateAlgorithm - a MazeGenerator.
	 * @see MazeGenerator
	 */
	public void setGenerateAlgorithm(MazeGenerator generateAlgorithm) {
		this.generateAlgorithm = generateAlgorithm;
	}
	
}