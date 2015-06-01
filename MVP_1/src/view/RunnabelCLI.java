package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze;

/**
 * a class made by object adapter, forming a runnable CLI class, for using threads.
 * RunnabelCLI implements Runnable.
 * @author Adir
 * @see Runnable
 */
public class RunnabelCLI implements Runnable{
	
	
	BufferedReader in;
	PrintWriter out;
	//UserCommands userCommands;
	//String lastCommand;
	CommonView v;
	/**
	 * general getters and setters
	 * @return v - a CommonView.
	 */
	public CommonView getV() {
		return v;
	}
	/**
	 * general getters and setters
	 * @param v - MyView.
	 */
	public void setV(MyView v) {
		this.v = v;
	}

	/**
	 * general constructor
	 * @param in - a BufferedReader.
	 * @param out - a PrintWriter.
	 * @param v - a MyView.
	 */
	public RunnabelCLI(BufferedReader in, PrintWriter out,MyView v) {
		//super(in, out,null);
		this.in = in;
		this.out = out;
		this.v=v;
		// TODO Auto-generated constructor stub
	}

	/**
	 * run method from Runnable
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * the starting function of the command line interface
	 */
	public void start()
	{
		System.out.print("Enter command: ");
		try {
			String line = in.readLine();
			
			while (!line.equals("exit"))
			{
				String[] sp = line.split(" ");
								
				String commandName = sp[0];
				String arg = null;
				if (sp.length > 1)
					arg = sp[1];
				
				presenter.Presenter.Command command =  (presenter.Presenter.Command) v.getUserCommand(commandName);
				if(command!=null){
					v.setChanged1();
					v.notifyObservers(command);
				}
				System.out.print("Enter command: ");
				line = in.readLine();
			}
			out.write("Goodbye");
			presenter.Presenter.Command command = (presenter.Presenter.Command) v.getUserCommand("Exit");
			command.doCommand();
						
		} catch (IOException e) {			
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}	
	}

}
