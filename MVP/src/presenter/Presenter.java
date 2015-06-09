package presenter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import algorithms.mazeGenerators.Maze;
import view.MyView;
import view.View;
import model.MazeNames;
import model.Model;
import model.MyModel;

/**
 * a presenter class, from the mvp framework. connects between the model and the view.
 * Presenter implements Observer.
 * @author Adir
 * @see Observer
 */
public class Presenter implements Observer{
	
	Model m;
	View v;
	HashMap<String, presenter.Presenter.Command> commands=new HashMap<String, presenter.Presenter.Command>();
	HashMap<String, Maze> mazes=new HashMap<String, Maze>(); 
	
	/**
	 * the update function implemented from observer, starts when observed classes are notifying.
	 * @param arg0 - an Observable(View/Model).
	 * @param arg1 - a command.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0==v)
		{
			MyView mv=(MyView) arg0;
			Command c=(Command) arg1;
			c.doCommand();
		}
		if( arg0==m)
		{
			MyModel mm=(MyModel) arg0;
			Maze maze=mm.getMaze();
			v.displayMaze(maze);
		}
	}

	/**
	 * a general constructor, Initializing the commands hashmap, and putting the data from DataBase to mazes HashMap.
	 * @param m - a Model.
	 * @param v - a View.
	 */
	public Presenter(Model m, View v) {
		super();
		this.m = m;
		this.v = v;
		commands.put("GenerateMaze", new GenerateMazeCommand());
		commands.put("DisplayMaze", new DisplayMazeCommand());
		commands.put("SolveMaze", new SolveMazeCommand());
		commands.put("DisplaySolution", new DisplaySolutionCommand());
		commands.put("Exit", new ExitCommand());
		
		v.setCommands(commands);
		
		//putting the data in mazes.
		MazeNames user2 = null;
		
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(MazeNames.class);
		config.configure("hibernate.cfg.xml");
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		Query query = session.createQuery("from MazeNames");
		
		List <MazeNames>li = query.list();
		Iterator<MazeNames> ite=li.iterator();

		while (ite.hasNext()){
		 user2=ite.next();
		 mazes.put(user2.getName(), new Maze(user2.getMaze()));
		}
		
		session.close();
	}
	
	public interface Command{
		public void doCommand();
	}
	/**
	 * a command type class, for the generate maze method
	 * @author Adir
	 * @see Command
	 */
	class GenerateMazeCommand implements Command
	{
		
		/**
		 * generating a maze, receives parameters from input
		 */
		@Override
		public void doCommand() {
			Scanner in = new Scanner(System.in);
			int cols=0,rows=0;
			String name=new String();
			System.out.println("enter num of cols and num of rows");
			cols=in.nextInt();
			rows=in.nextInt();
			System.out.println("enter name of maze");
			//do{
			name=in.next();
			//}while(!(name.equals("")));
			while(mazes.get(name)!=null && (!name.equals(""))){
				System.out.println("maze already exists, enter a new name of the maze");
				name=in.next();
			}
			m.generateMaze(rows,cols,name);
			Maze maze=m.getMaze();
			mazes.put(name, maze);
			System.out.println("maze "+name+" is ready");
		}		
	}
	
	/**
	 * a command type class, for the display maze method
	 * @author Adir
	 * @see Command
	 */
	class DisplayMazeCommand implements Command
	{

		/**
		 * displays the maze the user asked for
		 */
		@Override
		public void doCommand() {
			Scanner in = new Scanner(System.in);
			String name=new String();
			System.out.println("enter name of maze");
			name=in.next();
			if((mazes.get(name)!=null))
					v.displayMaze(mazes.get(name));
			else
				System.out.println("maze does not exist");
		}		
	}
	
	/**
	 * a command type class, for the solve maze method
	 * @author Adir
	 * @see Command
	 */
	class SolveMazeCommand implements Command
	{

		/**
		 * solves a maze that has already formed
		 */
		@Override
		public void doCommand() {
			Scanner in = new Scanner(System.in);
			String name=new String();
			System.out.println("enter name of maze");
			name=in.nextLine();
			if(!mazes.containsKey(name)){
				System.out.println("maze does not exist");
				return;
			}
			m.solveMaze(mazes.get(name));
			System.out.println("solution for "+name+" is ready");
		}		
	}
	
	/**
	 * a command type class, for the display solution method
	 * @author Adir
	 * @see Command
	 */
	class DisplaySolutionCommand implements Command
	{

		/**
		 * displays a solution of a maze
		 */
		@Override
		public void doCommand() {
			Scanner in = new Scanner(System.in);
			String name=new String();
			System.out.println("enter name of maze");
			name=in.nextLine();
			if(!mazes.containsKey(name)){
				System.out.println("maze does not exist");
				return;
			}
			//m.solveMaze(mazes.get(name));
			v.displaySolution(m.getSolution());
			
		}		
	}
	
	/**
	 * this command is used to exit the program elegantly, and save every data we want.
	 * @author Yehuda Hido Pinto
	 */
	class ExitCommand implements Command{

		@Override
		public void doCommand() {
			m.stop();
		}
		
	}

}
