package GUI;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TimerTask;

import model.MazeData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import presenter.Presenter.Command;
import view.View;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**
 * @author HFL
 *
 */
public class MazeWindow extends BasicWindow implements View{

	Maze myMaze;
	CommonGameBoard maze;
	CommonMazeDisplayer md;
	char LastPressed = 's';
	char player2_LastPressed = 's';
	
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
	}

	public void setMaze(String m){
		this.myMaze = new Maze(m);
	}
	
	/* (non-Javadoc)
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		
		//Text txt = new Text(shell, SWT.BORDER);
		//txt.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		md = new ImgMazeDisplayer(myMaze, null);
		
		maze=new ImgGameBoard(shell, SWT.BORDER, md, myMaze);
		md.setBoard(maze);
		
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		
		maze.addListener (SWT.Resize,  new Listener () {
		    @Override
			public void handleEvent(Event arg0) {
		    	maze.redraw();
		    }
		    });
		
		maze.addKeyListener(new KeyListener() { //allows the user move the character with the arrows.
			
			@Override
			public void keyReleased(KeyEvent e) {
				int i1 = maze.gameCharecters.get(0).x/maze.w;
				int j1= maze.gameCharecters.get(0).y/maze.h;
				int i2 = -1;
				int j2 = -1;
				Cell cell2 =null;
				if(maze.gameCharecters.size()==2){
					i2 = maze.gameCharecters.get(1).x/maze.w;
					j2= maze.gameCharecters.get(1).y/maze.h;
					cell2 = myMaze.getCell(j2,i2);//for some reason it flips the rows with the cols so this fixes it.
				}
				
				Cell cell1 = myMaze.getCell(j1,i1);//for some reason it flips the rows with the cols so this fixes it.
				switch( e.keyCode ) {
				case SWT.ARROW_LEFT:
		            // player1 handle left
					if((cell1.getHasLeftWall() == false) && !((i1 ==0)&&(j1==0))){
						
						maze.gameCharecters.get(0).x -=maze.w;
						maze.redraw();
						LastPressed='l';
					}
		            break;
		        case SWT.ARROW_UP:
		            // player1 handle up
		        	if((cell1.getHasTopWall() == false)){
						maze.gameCharecters.get(0).y -=maze.h;
						maze.redraw();
						LastPressed='u';
						}
		            break;
		        case SWT.ARROW_RIGHT:
		            // player1 handle right
					if((cell1.getHasRightWall() == false) && !(j1==(myMaze.getCols()-1) && (i1==(myMaze.getRows()-1)) )){
						maze.gameCharecters.get(0).x +=maze.w;
						maze.redraw();	
						LastPressed='r';
						}
					if(j1==(myMaze.getCols()-1) && (i1==(myMaze.getRows()-1)) ){
						//finish line - play music, show a win window, etc.
					}
		            break;
		        case SWT.ARROW_DOWN:
		            // player1 handle down
					if((cell1.getHasBottomWall() == false)){
						maze.gameCharecters.get(0).y +=maze.h;
						maze.redraw();	
						LastPressed='d';
						}
					break;
		        case 97:
		            // player2 handle left
		        	if(i2==-1)
		        		return;
					if((cell2.getHasLeftWall() == false) && !((i2 ==0)&&(j2==0))){
						
						maze.gameCharecters.get(1).x -=maze.w;
						maze.redraw();
						player2_LastPressed='l';
					}
		            break;
		        case 119:
		            // player2 handle up
		        	if(i2==-1)
		        		return;
		        	if((cell2.getHasTopWall() == false)){
						maze.gameCharecters.get(1).y -=maze.h;
						maze.redraw();
						player2_LastPressed='u';
						}
		            break;
		        case 100:
		            // player2 handle right
		        	if(i2==-1)
		        		return;
					if((cell2.getHasRightWall() == false) && !(j2==(myMaze.getCols()-1) && (i2==(myMaze.getRows()-1)) )){
						maze.gameCharecters.get(1).x +=maze.w;
						maze.redraw();	
						player2_LastPressed='r';
						}
					if(j2==(myMaze.getCols()-1) && (i2==(myMaze.getRows()-1)) ){
						//finish line - play music, show a win window, etc.
					}
		            break;
		        case 115:
		            // player2 handle down
		        	if(i2==-1)
		        		return;
					if((cell2.getHasBottomWall() == false)){
						maze.gameCharecters.get(1).y +=maze.h;
						maze.redraw();	
						player2_LastPressed='d';
						}
					break;
		        case SWT.ESC:
		            // handle esc
		        	if(maze.gameCharecters.get(0).ball !=null){
		        		maze.gameCharecters.get(0).ball.stop();
						maze.gameCharecters.get(0).ball=null;
		        	}
		        	if((maze.gameCharecters.size()>1) && maze.gameCharecters.get(1).ball !=null){
		        		maze.gameCharecters.get(1).ball.stop();
						maze.gameCharecters.get(1).ball=null;
		        	}
					shell.dispose();
					break;
				
		        case SWT.CTRL:
		        	if(LastPressed == 's')
		        		break;
		        	if(maze.gameCharecters.get(0).ball != null)
		        		break;
		        	
		        	
		        			        	
		        	TimerTask player1_task = new TimerTask() {
		    			
		    			@Override
		    			public void run() {
		    				
		    				  //maze.getDisplay().syncExec(new Runnable() {
		    			      maze.getDisplay().syncExec(new Runnable() {

		  			    		@Override
		  			    		public void run() {
		  			    			int i = maze.gameCharecters.get(0).ball.x/maze.w;
		  							int j= maze.gameCharecters.get(0).ball.y/maze.h;
		  							Cell cell1 = myMaze.getCell(j, i);//for some reason it flips the rows with the cols so this fixes it.
		  			    			GC gc = new GC(maze, SWT.NONE);
		  			    			
		  			    			Iterator<GameCharacter> ite = maze.gameCharecters.iterator();
		  						   
		  						   while(ite.hasNext()){
		  							   GameCharacter gameC = ite.next();
		  							   if(gameC==maze.gameCharecters.get(0)){
		  								   if(ite.hasNext())
		  									   gameC=ite.next();
		  								   else
		  									   break;
		  							   }
		  								
		  							   int x = (int) (gameC.x / maze.w);
		  							   int y = (int) (gameC.y / maze.h);
		  							
		  							   if(i==x && j==y){
		  								   
		  								   if(gameC.ball !=null){
		  									   	gameC.ball.stop();
		  									   	gameC.ball=null;
			  								}
		  								   
		  								   maze.removeGameCharecter(gameC);
		  								   
		  								   //closes the thread
		  								   	maze.gameCharecters.get(0).ball.stop();
		  									maze.gameCharecters.get(0).ball=null;
		  									maze.redraw();
		  									maze.getDisplay().disposeExec(this);
		  								}
		  							   
		  							   if(maze.gameCharecters.size()==1) //this statement is here because we can be sure that if we got in this loop, there are more game character than 1.
			  							   	return;
		  							}
		  						   
		  			    			switch (maze.gameCharecters.get(0).ball.dir) {
		  							case 'l':
		  								// handle left
		  								if((cell1.getHasLeftWall() == false) && !((i ==0)&&(j==0))){
		  									maze.gameCharecters.get(0).ball.x -= (int) (maze.w);//*0.1
		      								maze.gameCharecters.get(0).ball.paint(gc,maze.w, maze.h);
		  								}
		  								else{
		  									maze.gameCharecters.get(0).ball.stop();
		  									maze.gameCharecters.get(0).ball=null;
		  									maze.redraw();
		  									maze.getDisplay().disposeExec(this);
		  								}
	  									break;
		  							case 'u':
		  								// handle up
		  					        	if((cell1.getHasTopWall() == false)){
		  					        		maze.gameCharecters.get(0).ball.y -= (int) (maze.h);//*0.1
		      								maze.gameCharecters.get(0).ball.paint(gc,maze.w, maze.h);
		  								}
		  								else{
		  									maze.gameCharecters.get(0).ball.stop();
		  									maze.gameCharecters.get(0).ball=null;
		  									maze.redraw();
		  									maze.getDisplay().disposeExec(this);
		  								}
		  								break;
		  							case 'r':
		  								// handle right
		  								if((cell1.getHasRightWall() == false) && !(j==(myMaze.getCols()-1) && (i==(myMaze.getRows()-1)) )){
		  									maze.gameCharecters.get(0).ball.x += (int) (maze.w);//*0.1
		      								maze.gameCharecters.get(0).ball.paint(gc,maze.w, maze.h);
		  								}
		  								else{
		  									maze.gameCharecters.get(0).ball.stop();
		  									maze.gameCharecters.get(0).ball=null;
		  									maze.redraw();
		  									maze.getDisplay().disposeExec(this);
		  								}
		  								break;
		  							case 'd':
		  								// handle down
		  								if((cell1.getHasBottomWall() == false)){
		  									maze.gameCharecters.get(0).ball.y += (int) (maze.h);//*0.1
		      								maze.gameCharecters.get(0).ball.paint(gc,maze.w, maze.h);
		  								}
		  								else{
		  									maze.gameCharecters.get(0).ball.stop();
		  									maze.gameCharecters.get(0).ball=null;
		  									maze.redraw();
		  									maze.getDisplay().disposeExec(this);
		  								}
		  								break;
		  							}
		  			    		}
		  			    	 });
		    				
		    			}
		    			
		    		};
		        	maze.gameCharecters.get(0).ball = new Ball(LastPressed,maze.gameCharecters.get(0).x, maze.gameCharecters.get(0).y, maze,player1_task);
		        	
		        	break;
		        case SWT.SPACE:
		        	if(i2==-1)
		        		return;
		        	if(player2_LastPressed == 's')
		        		break;
		        	if(maze.gameCharecters.get(1).ball != null)
		        		break;
		        	
		        	
		        			        	
		        	TimerTask player2_task = new TimerTask() {
		    			
		    			@Override
		    			public void run() {
		    				
		    				  //maze.getDisplay().syncExec(new Runnable() {
		    			      maze.getDisplay().syncExec(new Runnable() {

		  			    		@Override
		  			    		public void run() {
		  			    			int i = maze.gameCharecters.get(1).ball.x/maze.w;
		  							int j= maze.gameCharecters.get(1).ball.y/maze.h;
		  							Cell cell1 = myMaze.getCell(j, i);//for some reason it flips the rows with the cols so this fixes it.
		  			    			GC gc = new GC(maze, SWT.NONE);
		  			    			
		  			    			Iterator<GameCharacter> ite = maze.gameCharecters.iterator();
			  						   
			  						   while(ite.hasNext()){
			  							   GameCharacter gameC = ite.next();
			  							   if(gameC==maze.gameCharecters.get(1)){
			  								   if(ite.hasNext())
			  									   gameC=ite.next();
			  								   else
			  									   break;
			  							   }
			  								
			  							   int x = (int) (gameC.x / maze.w);
			  							   int y = (int) (gameC.y / maze.h);
			  							
			  							   if(i==x && j==y){
			  								   
			  								   if(gameC.ball !=null){
			  									   	gameC.ball.stop();
			  									   	gameC.ball=null;
				  								}
			  								   
			  								   maze.removeGameCharecter(gameC);
			  								   
			  								   //closes the thread
			  								   	maze.gameCharecters.get(0).ball.stop();
			  									maze.gameCharecters.get(0).ball=null;
			  									maze.redraw();
			  									maze.getDisplay().disposeExec(this);
			  								} //checks collision between this character's ball and other characters
			  							   
			  							   if(maze.gameCharecters.size()==1) //this statement is here because we can be sure that if we got in this loop, there are more game character than 1.
				  							   	return;
			  							}
		  			    			
		  			    			switch (maze.gameCharecters.get(1).ball.dir) {
		  							case 'l':
		  								// handle left
		  								if((cell1.getHasLeftWall() == false) && !((i ==0)&&(j==0))){
		  									maze.gameCharecters.get(1).ball.x -= (int) (maze.w);//*0.1
		      								maze.gameCharecters.get(1).ball.paint(gc,maze.w, maze.h);
		  								}
		  								else{
		  									maze.gameCharecters.get(1).ball.stop();
		  									maze.gameCharecters.get(1).ball=null;
		  									maze.redraw();
		  									maze.getDisplay().disposeExec(this);
		  								}
	  									break;
		  							case 'u':
		  								// handle up
		  					        	if((cell1.getHasTopWall() == false)){
		  					        		maze.gameCharecters.get(1).ball.y -= (int) (maze.h);//*0.1
		      								maze.gameCharecters.get(1).ball.paint(gc,maze.w, maze.h);
		  								}
		  								else{
		  									maze.gameCharecters.get(1).ball.stop();
		  									maze.gameCharecters.get(1).ball=null;
		  									maze.redraw();
		  									maze.getDisplay().disposeExec(this);
		  								}
		  								break;
		  							case 'r':
		  								// handle right
		  								if((cell1.getHasRightWall() == false) && !(j==(myMaze.getCols()-1) && (i==(myMaze.getRows()-1)) )){
		  									maze.gameCharecters.get(1).ball.x += (int) (maze.w);//*0.1
		      								maze.gameCharecters.get(1).ball.paint(gc,maze.w, maze.h);
		  								}
		  								else{
		  									maze.gameCharecters.get(1).ball.stop();
		  									maze.gameCharecters.get(1).ball=null;
		  									maze.redraw();
		  									maze.getDisplay().disposeExec(this);
		  								}
		  								break;
		  							case 'd':
		  								// handle down
		  								if((cell1.getHasBottomWall() == false)){
		  									maze.gameCharecters.get(1).ball.y += (int) (maze.h);//*0.1
		      								maze.gameCharecters.get(1).ball.paint(gc,maze.w, maze.h);
		  								}
		  								else{
		  									maze.gameCharecters.get(1).ball.stop();
		  									maze.gameCharecters.get(1).ball=null;
		  									maze.redraw();
		  									maze.getDisplay().disposeExec(this);
		  								}
		  								break;
		  							}
		  			    		}
		  			    	 });
		    				
		    			}
		    			
		    		};
		        	maze.gameCharecters.get(1).ball = new Ball(player2_LastPressed,maze.gameCharecters.get(1).x, maze.gameCharecters.get(1).y, maze,player2_task);
		        	
		        	break;
		     }
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});

		
		Button mini_game = new Button(shell, SWT.PUSH);
		mini_game.setText("play 1v1");
		mini_game.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		mini_game.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(maze==null)
					return;
				
				if(myMaze ==null)
					return;
				
				if(maze.gameCharecters.size()<2){
					GameCharacter gc2 = new ImgGameCharacter( ((myMaze.getCols()-1)*maze.w) , ((myMaze.getRows()-1))*maze.h , new Image(maze.getDisplay(), "characters/haim.png"));
					maze.gameCharecters.add(gc2);
					maze.redraw();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Command getUserCommand(String command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayMaze(Maze m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displaySolution(Solution s) {
		//Solution sol= myMaze
		
	}

	@Override
	public void start() {} // do nothing

}
