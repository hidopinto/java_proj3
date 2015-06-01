package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import presenter.Properties;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MazeGenerator;
import algorithms.mazeGenerators.MazeGeneratorDFS;
import algorithms.search.Astar;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import demo.SearchableMaze;

/**
 * MyModel is a class that do all the maze stuff. It creates a maze, and solves it in threads.
 * MyModel contains a HashMap used to Map between Map(toString) and Solution.
 * MyModel implements model.
 * @author Yehuda Hido Pinto
 * @see Model
 * @see Maze
 * @see Solution
 */
public class MyModel extends Observable implements Model {
	
	Maze m;
	Solution sol;
	ExecutorService executor = Executors.newCachedThreadPool();
	HashMap<String, Solution> hm = new  HashMap<String, Solution>(); // String represents the Maze using toString function. using String instead of Maze, it will be easier to use HashMap
	HashMap<String,String> mazesNames = new HashMap<String, String>(); //the other HashMap for saving maze's names. The first String describes a maze;
	Searcher searcher = new Astar(new MazeAirDistance());
	MazeGenerator mazeGenerator = new MazeGeneratorDFS();
	
	
	/**
	 * MyModel constructor. Reads the data from the DataBase and puts it in the HashMap.
	 */
	public MyModel() {
		super();
		
		//loads HashMap with HyberNate.
		loadFromDataBase();
	}
	
	/**
	 * MyModel constructor. Reads the data from the DataBase and puts it in the HashMap.
	 */
	public MyModel(Properties p) {
		super();
		
		if(p.getNumOfThreads()<1)
			executor = Executors.newCachedThreadPool();
		else
			executor = Executors.newFixedThreadPool(p.getNumOfThreads());
		
		mazeGenerator = p.getGenerateAlgorithm();
		searcher = p.getSearchAlgorithm();
		
		//loads HashMap with HyberNate.
		loadFromDataBase();
	}

	protected void loadFromDataBase(){
		MazeData user = null;
		
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(MazeData.class);
		config.addAnnotatedClass(MazeNames.class);
		config.configure("hibernate.cfg.xml");
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();

		Query query = session.createQuery("from MazeData");
		
		List <MazeData>list = query.list();
		Iterator<MazeData> it=list.iterator();

		while (it.hasNext()){
		 user=it.next();
		 hm.put(user.maze, new Solution(user.sol));
		}
		
		session.close();
		
		MazeNames user2 = null;
		session = factory.openSession();
		
		query = session.createQuery("from MazeNames");
		
		List <MazeNames>li = query.list();
		Iterator<MazeNames> ite=li.iterator();

		while (ite.hasNext()){
		 user2=ite.next();
		 mazesNames.put(user2.maze, user2.name);
		}
		
		session.close();
	}
	
	/**
	 * generates Maze generates a Maze using 2 parameters to describe the rows and columns.
	 * @param i - for the rows
	 * @param j - for the columns.
	 */
	@Override
	public void generateMaze(int i, int j,String name) {
		Future<Maze> f = executor.submit(new Callable<Maze>() {
			@Override
			public Maze call() throws Exception {
				m= new Maze(i,j);
				mazeGenerator.generateMaze(m, 0, 0);
				mazesNames.put(m.toString(), name);
				return m;
			}
			
		});
		try {
			m = f.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * returns the last Maze generated.
	 * @return m - the Maze.
	 */
	@Override
	public Maze getMaze() {
		return m;
	}
	
	/**
	 * solves a Maze.
	 * @param m - the Maze we want to solve.
	 */
	@Override
	public void solveMaze(Maze m) {
		
		if(!hm.containsKey(m.toString())){
			Future<Solution> f = executor.submit(new Callable<Solution>() {
				@Override
				public Solution call() throws Exception {
					sol = searcher.search(new SearchableMaze(m, false));
					hm.put(m.toString(), sol);
					return sol;
				}
				
			});
			try {
				sol = f.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			sol = hm.get(m);

		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * returns the Solution for the last Maze generated.
	 * @return sol - the Solution.
	 */
	@Override
	public Solution getSolution() {
		return sol;
	}

	/**
	 * shuts down the ThreadPool - executor.
	 * after shutting down the ThreadPool it writes the data in the HashMap into a DataBase.
	 */
	@Override
	public void stop() {
		executor.shutdown();
		// Writes HashMap to DataBase with HiberNate.
		
		//creates a set which contains all the HashMap data.
		Set<String> mazes = hm.keySet();
		Set<String> mazeSet = mazesNames.keySet();
		LinkedList<MazeNames> names = new LinkedList<MazeNames>();
		LinkedList<MazeData> data = new LinkedList<MazeData>();
		Iterator<String> ite = mazes.iterator();
		
		while(ite.hasNext()){
			String maze = ite.next();
			Solution solution = hm.get(maze);
			MazeData md = new MazeData(maze,solution.toString());
			data.add(md);
		}
		
		ite = mazeSet.iterator();
		while(ite.hasNext()){
			String maze = ite.next();
			String name = mazesNames.get(maze);
			MazeNames mn = new MazeNames(name,maze);
			names.add(mn);
		}
		
		//puts the data in the DataBase.
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(MazeData.class);
		
		config.configure("hibernate.cfg.xml");
		
		new SchemaExport(config).create(true, true);
		
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		Iterator<MazeData> dataIt = data.iterator();
		while(dataIt.hasNext()){
			MazeData md = dataIt.next();
			session.save(md);
		}
		
		session.getTransaction().commit();
		session.close();
		
		
		config = new AnnotationConfiguration();
		config.addAnnotatedClass(MazeNames.class);
		config.configure("hibernate.cfg.xml");
		
		new SchemaExport(config).create(true, true);
		
		factory = config.buildSessionFactory();
		session = factory.openSession();
		
		session.beginTransaction();
		
		Iterator<MazeNames> namesIte = names.iterator();
		while(namesIte.hasNext()){
			MazeNames mn = namesIte.next();
			session.save(mn);
		}
		
		session.getTransaction().commit();
		session.close();
	}

}