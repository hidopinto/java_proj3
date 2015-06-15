package boot;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import demo.SearchableMaze;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MazeGenerator;
import algorithms.mazeGenerators.RandomMazeGenerator;
import algorithms.search.Astar;
import algorithms.search.Heuristics;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.State;

public class AstarTest {

	@Test
	public void testNullHuristics() {
		MazeGenerator mg = new RandomMazeGenerator();
		Random rnd = new Random();
		Maze m= new Maze(rnd.nextInt(30),rnd.nextInt(30));
		mg.generateMaze(m, 0, 0);
		
		Searcher searcher = new Astar(null);
		
		System.out.println(searcher.search(new SearchableMaze(m, false)));
	}
	
	@Test
	public void testForeignHuristics() {
		MazeGenerator mg = new RandomMazeGenerator();
		Random rnd = new Random();
		int i=rnd.nextInt(30);
		int j=rnd.nextInt(30);
		System.out.println("maze size : " + i + "," + j);
		Maze m= new Maze(i,j);
		mg.generateMaze(m, 0, 0);
		
		Searcher searcher = new Astar(new Heuristics() {
			
			@Override
			public int h(State s, State GoalState) {
				return s.hashCode() * GoalState.hashCode();
			}
		});
		
		System.out.println(searcher.search(new SearchableMaze(m, false)));
	}
	
	@Test
	public void testTooSmallMaze() {
		MazeGenerator mg = new RandomMazeGenerator();
		Maze m= new Maze(1,1);
		mg.generateMaze(m, 0, 0);
		
		Searcher searcher = new Astar(new MazeManhattanDistance());
		
		System.out.println(searcher.search(new SearchableMaze(m, false)));
	}

}
