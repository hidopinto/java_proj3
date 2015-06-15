package GUI;

import jaco.mp3.player.MP3Player;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;

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

//	Maze myMaze;
	CommonGameBoard board;
	CommonDisplayer md;
	
	
	public MazeWindow(String title, int width, int height,CommonGameBoard board, CommonDisplayer md) {
		super(title, width, height);
		this.board=board;
		this.md=md;
	}
	
	public void setGameBoard(CommonGameBoard board){
		this.board = board;
	}
	
	public void setDisplayer(CommonDisplayer md){
		this.md = md;
	}
	
	/* (non-Javadoc)
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		
		//Text txt = new Text(shell, SWT.BORDER);
		//txt.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
//		md = new ImgMazeDisplayer(myMaze, null);
//		
//		board=new ImgGameBoard(shell, SWT.BORDER, md, myMaze);
//		md.setBoard(board);
		
		board.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,1));
		
		board.addKeyListener(new KeyListener() { //allows the user move the character with the arrows.
	
			@Override
			public void keyReleased(KeyEvent e) {
				int i1 = board.gameCharecters.get(0).x/board.w;
				int j1= board.gameCharecters.get(0).y/board.h;
				int i2 = -1;
				int j2 = -1;
				Cell cell2 =null;
				if(board.gameCharecters.size()==2){
					i2 = board.gameCharecters.get(1).x/board.w;
					j2= board.gameCharecters.get(1).y/board.h;
					cell2 = (Cell) md.getTile(j2,i2);//for some reason it flips the rows with the cols so this fixes it.
				}
				
				Cell cell1 = (Cell) md.getTile(j1,i1);//for some reason it flips the rows with the cols so this fixes it.
				switch( e.keyCode ) {
				case SWT.ARROW_LEFT:
		            // player1 handle left
					if((cell1.getHasLeftWall() == false) && !((i1 ==0)&&(j1==0))){
						
						board.gameCharecters.get(0).x -=board.w;
						board.redraw();
						board.gameCharecters.get(0).Last_direction='l';
						checkWinSituation(0);
					}
		            break;
		        case SWT.ARROW_UP:
		            // player1 handle up
		        	if((cell1.getHasTopWall() == false)){
		        		board.gameCharecters.get(0).y -=board.h;
		        		board.redraw();
		        		board.gameCharecters.get(0).Last_direction='u';
						checkWinSituation(0);
						}
		            break;
		        case SWT.ARROW_RIGHT:
		            // player1 handle right
					if((cell1.getHasRightWall() == false) && !(j1==(md.getCols()-1) && (i1==(md.getRows()-1)) )){
						board.gameCharecters.get(0).x +=board.w;
						board.redraw();	
						board.gameCharecters.get(0).Last_direction='r';
						checkWinSituation(0);
						}
		            break;
		        case SWT.ARROW_DOWN:
		            // player1 handle down
					if((cell1.getHasBottomWall() == false)){
						board.gameCharecters.get(0).y +=board.h;
						board.redraw();	
						board.gameCharecters.get(0).Last_direction='d';
						checkWinSituation(0);
						}
					break;
		        case 97:
		            // player2 handle left
		        	if(i2==-1)
		        		return;
					if((cell2.getHasLeftWall() == false) && !((i2 ==0)&&(j2==0))){
						
						board.gameCharecters.get(1).x -=board.w;
						board.redraw();
						board.gameCharecters.get(1).Last_direction='l';
						checkWinSituation(1);
					}
		            break;
		        case 119:
		            // player2 handle up
		        	if(i2==-1)
		        		return;
		        	if((cell2.getHasTopWall() == false)){
		        		board.gameCharecters.get(1).y -=board.h;
		        		board.redraw();
		        		board.gameCharecters.get(1).Last_direction='u';
						checkWinSituation(1);
						}
		            break;
		        case 100:
		            // player2 handle right
		        	if(i2==-1)
		        		return;
					if((cell2.getHasRightWall() == false) && !(j2==(md.getCols()-1) && (i2==(md.getRows()-1)) )){
						board.gameCharecters.get(1).x +=board.w;
						board.redraw();	
						board.gameCharecters.get(1).Last_direction='r';
						checkWinSituation(1);
						}
					break;
		        case 115:
		            // player2 handle down
		        	if(i2==-1)
		        		return;
					if((cell2.getHasBottomWall() == false)){
						board.gameCharecters.get(1).y +=board.h;
						board.redraw();	
						board.gameCharecters.get(1).Last_direction='d';
						checkWinSituation(1);
						}
					break;
		        case SWT.ESC:
		            // handle esc
		        	closeMe();
					break;
				
		        case SWT.ALT:
		        	if(board.gameCharecters.get(0).Last_direction == 's')
		        		break;
		        	if(board.gameCharecters.get(0).ball != null)
		        		break;
		        	
		        	characterShoot(0);
		        	
		        	break;
		        case SWT.SPACE:
		        	if(i2==-1)
		        		return;
		        	if(board.gameCharecters.get(1).Last_direction == 's')
		        		break;
		        	if(board.gameCharecters.get(1).ball != null)
		        		break;
		        	
		        	characterShoot(1);
		        	
		        	break;
		     }
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});

		board.addMouseListener(new MouseListener() {
			
			int mouseI=-1;
			int mouseJ=-1;
			GameCharacter gameC=null;
			int gameC_index=0;
			@Override
			public void mouseUp(MouseEvent e) {
				if(mouseI==-1)
					return;
				String dir="";
				if((e.x/board.w)>mouseJ)
					dir+='r';
				if((e.x/board.w)<mouseJ)
					dir+='l';
				if((e.y/board.h)>mouseI)
					dir+='d';
				if((e.y/board.h)<mouseI)
					dir+='u';
				
				if(dir=="")
					return; //mouse didn't move so do nothing.
				
				Cell curCell = (Cell) md.getTile( mouseI , mouseJ );
				Cell nextCell = null;
				
				switch(dir){
				case("ru"):
					if(!curCell.getHasRightWall() && !(mouseI==(md.getRows()-1) && mouseJ==(md.getCols()-1) )){
						nextCell=(Cell) md.getTile(mouseI, mouseJ+1);
						if(!nextCell.getHasTopWall()){
							//move right & up
							gameC.x +=board.w;
							gameC.y -=board.h;
							gameC.Last_direction='u';
						}
					}
					if(!curCell.getHasTopWall()){
						nextCell=(Cell) md.getTile(mouseI-1, mouseJ);
						if(!nextCell.getHasRightWall() && !((mouseI-1)==(md.getRows()-1) && mouseJ==(md.getCols()-1) )){
							//move up && right
							gameC.x +=board.w;
							gameC.y -=board.h;
							gameC.Last_direction='r';
						}
						else
							dir="u";
					}
					break;
				case("rd"):
					if(!curCell.getHasRightWall() && !(mouseI==(md.getRows()-1) && mouseJ==(md.getCols()-1) )){
						nextCell=(Cell) md.getTile(mouseI, mouseJ+1);
						if(!nextCell.getHasBottomWall()){
							//move right & up
							gameC.x +=board.w;
							gameC.y +=board.h;
							gameC.Last_direction='d';
						}
					}
					if(!curCell.getHasBottomWall()){
						nextCell=(Cell) md.getTile(mouseI+1, mouseJ);
						if(!nextCell.getHasRightWall() && !((mouseI+1)==(md.getRows()-1) && mouseJ==(md.getCols()-1) )){
							//move up && right
							gameC.x +=board.w;
							gameC.y +=board.h;
							gameC.Last_direction='r';
						}
						else
							dir="d";
					}
					break;
				case("lu"):
					if(!curCell.getHasLeftWall() && !(mouseI==0 && mouseJ==0)){
						nextCell=(Cell) md.getTile(mouseI, mouseJ-1);
						if(!nextCell.getHasTopWall()){
							//move left & up
							gameC.x -=board.w;
							gameC.y -=board.h;
							gameC.Last_direction='u';
						}
					}
					if(!curCell.getHasTopWall()){
						nextCell=(Cell) md.getTile(mouseI-1, mouseJ);
						if(!nextCell.getHasLeftWall() && !((mouseI-1)==0 && mouseJ==0)){
							//move up && left
							gameC.x -=board.w;
							gameC.y -=board.h;
							gameC.Last_direction='l';
						}
						else
							dir="u";
					}
					break;
				case("ld"):
					if(!curCell.getHasLeftWall() && !(mouseI==0 && mouseJ==0)){
						nextCell=(Cell) md.getTile(mouseI, mouseJ-1);
						if(!nextCell.getHasBottomWall()){
							//move left & down
							gameC.x -=board.w;
							gameC.y +=board.h;
							gameC.Last_direction='d';
						}
					}
					if(!curCell.getHasBottomWall()){
						nextCell=(Cell) md.getTile(mouseI+1, mouseJ);
						if(!nextCell.getHasLeftWall() && !((mouseI+1)==0 && mouseJ==0)){
							//move down && left
							gameC.x -=board.w;
							gameC.y +=board.h;
							gameC.Last_direction='l';
						}
						else
							dir="d";
					}
					break;
				case("r"):
					if(!curCell.getHasRightWall() && !(mouseI==(md.getRows()-1) && mouseJ==(md.getCols()-1) )){
						//move right
						gameC.x +=board.w;
						gameC.Last_direction='r';
					}
					break;
				case("l"):
					if(!curCell.getHasLeftWall() && !(mouseI==0 && mouseJ==0)){
						//move left
						gameC.x -=board.w;
						gameC.Last_direction='l';
					}
					break;
				case("u"):
					if(!curCell.getHasTopWall()){
						//move up
						gameC.y -=board.h;
						gameC.Last_direction='u';
					}
					break;
				case("d"):
					if(!curCell.getHasBottomWall()){
						//move down
						gameC.y +=board.h;
						gameC.Last_direction='d';
					}
					break;
				}
				
				//check if a player got to his end position.
				board.redraw();
				checkWinSituation(gameC_index);
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				gameC_index=0;
				int characterI=-1,characterJ=-1; //there is no point at checking if there are 2 characters in the Tile, because they need to shoot each other from distance.
				Iterator<GameCharacter> ite = board.gameCharecters.iterator();
				
				
			    while(ite.hasNext()){
				   gameC = ite.next();
				   
				   characterI = (int) (gameC.x / board.w);
				   characterJ = (int) (gameC.y / board.h);
				
				   if(characterI==(e.x/board.w) && characterJ==(e.y/board.h))
					   break;
				   else
					   gameC=null;
				   gameC_index++;
				}
			   	if(gameC!=null){
			   		mouseI = characterJ;
					mouseJ = characterI;
			   	}else
			   	{
			   		mouseI = -1;
					mouseJ = -1;
			   	}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				MessageBox msg = new MessageBox(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				msg.setText("Massage from the awesome programmer");
				msg.setMessage("stop double clicking " + System.lineSeparator() + "it's worthless -.-");
				msg.open();
			}
		});
		
		
		Button mini_game = new Button(shell, SWT.PUSH);
		mini_game.setText("play 1v1");
		mini_game.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		mini_game.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(board==null)
					return;
				
				if(board.gameCharecters.size()<2){
					GameCharacter gc2=null;
					
					Random r=new Random();
					switch(r.nextInt(3)){
					case 0:
						gc2 = new ImgGameCharacter( ((md.getRows()-1)*board.h) , ((md.getCols()-1))*board.w , 0, 0, new Image(board.getDisplay(), "characters/eli.png"));
						break;
					case 1:
						gc2 = new ImgGameCharacter( ((md.getRows()-1)*board.h) , ((md.getCols()-1))*board.w , 0, 0, new Image(board.getDisplay(), "characters/haim.png"));
						break;
					case 2:
						gc2 = new ImgGameCharacter( ((md.getRows()-1)*board.h) , ((md.getCols()-1))*board.w , 0, 0, new Image(board.getDisplay(), "characters/amit.png"));
						break;
					}
					board.gameCharecters.add(gc2);
					
					if(board.gameCharecters.get(0).targetI==0){ //if the other character doesn't start from 0,0 ,this character should.
						gc2.x=0;
				        gc2.y=0;
				        gc2.setTargetI((md.getRows()-1));
				        gc2.setTargetJ((md.getCols()-1));
				        
				        GameCharacter gm1 = board.gameCharecters.get(0);
				        board.removeGameCharecter(gm1);
				        board.gameCharecters.add(gm1);
					}
					
					board.redraw();
				}
				board.forceFocus();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
	}

	public void closeMe(){
		Iterator<GameCharacter> ite = board.gameCharecters.iterator();
		
		while(ite.hasNext()){
			GameCharacter gameChar = ite.next();
			if(gameChar.ball != null){
				gameChar.ball.stop();
				gameChar.ball = null;
			}
		}
		
		shell.dispose();
	}
	
	public void checkWinSituation(int index){
		GameCharacter myChar = board.gameCharecters.get(index);
		
		if(! ((myChar.x/board.w)==myChar.targetJ && (myChar.y/board.h)==myChar.targetI) )
			return;
		
		// play victory music
		MP3Player player = new MP3Player();
		player.addToPlayList(new File("audio/finish_music.mp3"));
		player.play();
		
		MessageBox msg = new MessageBox(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		msg.setText("Massage from the awesome programmer");
		msg.setMessage("player " + (index+1) + " is the winner." + System.lineSeparator() + "congrats and stuff..");
		msg.open();
		
		// move to win window
	}
	
	public void characterShoot(int index){
		// play shooting music
		MP3Player player = new MP3Player();
		player.addToPlayList(new File("audio/shoot_ball_sound.mp3"));
		player.play();
		
		GameCharacter myChar = board.gameCharecters.get(index);
		
		final TimerTask shoot_task = new TimerTask() {
			
			@Override
			public void run() {
				
				  //maze.getDisplay().syncExec(new Runnable() {
				board.getDisplay().syncExec(new Runnable() {

			    		@Override
			    		public void run() {
			    			int i = myChar.ball.x/board.w;
							int j= myChar.ball.y/board.h;
							Cell cell1 = (Cell) md.getTile(j, i);//for some reason it flips the rows with the cols so this fixes it.
			    			GC gc = new GC(board, SWT.NONE);
			    			
			    			Iterator<GameCharacter> ite = board.gameCharecters.iterator();
						   
						   while(ite.hasNext()){
							   GameCharacter gameC = ite.next();
							   if(gameC==myChar){
								   if(ite.hasNext())
									   gameC=ite.next();
								   else
									   break;
							   }
								
							   int x = (int) (gameC.x / board.w);
							   int y = (int) (gameC.y / board.h);
							
							   if(i==x && j==y){
								   
									// play kill music
									MP3Player player = new MP3Player();
									player.addToPlayList(new File("audio/death_sound.mp3"));
									player.play();
								   
								   if(gameC.ball !=null){
									   	gameC.ball.stop();
									   	gameC.ball=null;
								   }
								   
								   board.removeGameCharecter(gameC);
								   
								   //closes the thread
								   myChar.ball.stop();
								   myChar.ball=null;
								   board.redraw();
								   board.getDisplay().disposeExec(this);
								}
							   
							   if(board.gameCharecters.size()==1) //this statement is here because we can be sure that if we got in this loop, there are more game character than 1.
  							   	return;
							}
						   
			    			switch (myChar.ball.dir) {
							case 'l':
								// handle left
								if((cell1.getHasLeftWall() == false) && !((i ==0)&&(j==0))){
									myChar.ball.x -= (int) (board.w);//*0.1
									myChar.ball.paint(gc,board.w, board.h);
								}
								else{
									myChar.ball.stop();
									myChar.ball=null;
									board.redraw();
									board.getDisplay().disposeExec(this);
								}
								break;
							case 'u':
								// handle up
					        	if((cell1.getHasTopWall() == false)){
					        		myChar.ball.y -= (int) (board.h);//*0.1
					        		myChar.ball.paint(gc,board.w, board.h);
								}
								else{
									myChar.ball.stop();
									myChar.ball=null;
									board.redraw();
									board.getDisplay().disposeExec(this);
								}
								break;
							case 'r':
								// handle right
								if((cell1.getHasRightWall() == false) && !(j==(md.getCols()-1) && (i==(md.getRows()-1)) )){
									myChar.ball.x += (int) (board.w);//*0.1
									myChar.ball.paint(gc,board.w, board.h);
								}
								else{
									myChar.ball.stop();
									myChar.ball=null;
									board.redraw();
									board.getDisplay().disposeExec(this);
								}
								break;
							case 'd':
								// handle down
								if((cell1.getHasBottomWall() == false)){
									myChar.ball.y += (int) (board.h);//*0.1
									myChar.ball.paint(gc,board.w, board.h);
								}
								else{
									myChar.ball.stop();
									myChar.ball=null;
									board.redraw();
									board.getDisplay().disposeExec(this);
								}
								break;
							}
			    		}
			    	 });
				
			}
			
		};
		myChar.ball = new Ball(myChar.Last_direction,myChar.x, myChar.y, board,shoot_task);

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
		md.drawSol(new GC(board, SWT.NONE), s);
	}

	@Override
	public void start() {} // do nothing

}
