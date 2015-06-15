package boot;


import org.eclipse.swt.SWT;





import GUI.CommonDisplayer;
import GUI.CommonGameBoard;
import GUI.Displayer;
import GUI.GameBoard;
/*import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;*/
import GUI.ImgGameBoard;
import GUI.ImgMazeDisplayer;
import GUI.MazeWindow;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MazeGeneratorDFS;


public class Run {

	public static void main(String[] args) {
		
		/*
		 * commiters:
		 * yehuda hido pinto id-318865805
		 * adir ben avi id-318509627
		 * 
		*/
		/*XMLEncoder encoder;
				try {
					encoder = new XMLEncoder(new FileOutputStream("properties.xml"));
					Properties properties = new Properties(3,new BFS(),new MazeGeneratorDFS());
					encoder.writeObject(properties);
					encoder.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				XMLDecoder decoder;
				Properties pro = new Properties();
				try {
					decoder = new XMLDecoder(new FileInputStream("properties.xml"));
					pro = (Properties) decoder.readObject();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		MyModel m=new MyModel(pro);
		MyView v=new MyView();
		Presenter p=new Presenter(m,v);
		m.addObserver(p);
		v.addObserver(p);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(System.out);
		RunnabelCLI cli=new RunnabelCLI(reader, writer,v);
		cli.start();*/
		
		/*int w='w';
		int a='a';
		int s='s';
		int d='d';
		System.out.println("w = " + w);
		System.out.println("a = " + a);
		System.out.println("s = " + s);
		System.out.println("d = " + d);
		*/
		MazeGeneratorDFS mg = new MazeGeneratorDFS();
		Maze m = new Maze(8,8);
		mg.generateMaze(m, 0, 0);
		
		CommonDisplayer md = new ImgMazeDisplayer(m, null);
		
		MazeWindow mw = new MazeWindow("maze", 600, 600,null,md);

		CommonGameBoard board=new ImgGameBoard(mw.getShell(), SWT.BORDER, md, m);
		md.setBoard(board);
		
		mw.setGameBoard(board);
		mw.run();
	}

}
