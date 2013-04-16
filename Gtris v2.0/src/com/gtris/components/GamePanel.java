package com.gtris.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.gtris.enums.ColorFigure;
import com.gtris.enums.ControlAlignment;
import com.gtris.exceptions.GameOverException;
import com.gtris.factory.FactoryGtris;
import com.gtris.models.Figure;
import com.gtris.models.Score;
import com.gtris.sound.SoundManager;
import com.gtris.threads.GameCoreImpl;
import com.gtris.threads.GeneratorTimer;
import com.gtris.utilities.Utilities;
/**
 * Class represent the board game 
 * @author Pablo
 *
 */
public final class GamePanel extends JPanel{

	private static final long serialVersionUID = -8207110468263963436L;

	private HashMap<String,String> levels;
	
	private CopyOnWriteArrayList<Figure> onAir;
	
	private FactoryGtris factory;
	
	private Image background;
	
	private Thread gameThread;
	
	private Timer timer;
	
	private GeneratorTimer generatorTimer;
	
	private int delay = 2000;
	
	private final String KEY_STAGE = "stage";
	
	private final int UP_LEVEL_TIME = 2;
	
	private int currentLevel;
	
	private int finalStage;
	
	private boolean started;
	
	private boolean paused;
	
	private String textStatus;
	
	private boolean firstLoad;
	
	private Date startDate;
	
	private Date pauseDate;
	
	private String time;
	
	public com.gtris.models.Cursor cursor;//Can't create a getter and setter
	
	
	public GamePanel(){
		levels = Utilities.readConfig("background");
		currentLevel = 1;
		finalStage = 4;
		background = new ImageIcon(getClass().getClassLoader().getResource(levels.get(getLevelStage()))).getImage();
		factory = FactoryGtris.getInstance();
		cursor = new com.gtris.models.Cursor();
        onAir = new CopyOnWriteArrayList<>();
        textStatus = "start";
        firstLoad = true;
        time = "00:00";
	}
	/**
	 * This method init the thread game
	 */
	public void start(){
			if(!isStarted()){
				gameThread= new Thread(new GameCoreImpl(this));
				gameThread.start();
				timer = new Timer("Generator");
				onAir.clear();
				factory.setScore(new Score());
				factory.init();
				SoundManager.getInstance().playSound(getLevelStage() , true);
				started = true;
				textStatus = "pause";
				startDate = new Date();
			}else{
				if(!isPaused()){
					generatorTimer.cancel();
					generatorTimer = null;
					paused = true;
					textStatus = "resume";
					SoundManager.getInstance().stopSound(getLevelStage());
					pauseDate = new Date();
					
				}else{
					paused = false;
					textStatus = "pause";
					SoundManager.getInstance().playSound(getLevelStage() , true);
					startDate = new Date(startDate.getTime() + (new Date().getTime() - pauseDate.getTime()));
				}
				SoundManager.getInstance().playSound("pause" , false);
			}
	}

	/**
	 * Override the method for paint
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                RenderingHints.VALUE_ANTIALIAS_ON); 
		g2d.drawImage(background, 0, 0, factory.getWidth() + FactoryGtris.SIZE_FIGURE, factory.getHeight() + FactoryGtris.SIZE_FIGURE, this);
		if(isStarted() && !isPaused()){
			drawMatrix(g2d);
			drawFigure(g2d);
			if(firstLoad){
				factory.loadBlocksRandom();
				firstLoad = false;
			}
			try {
				repaintMatrix(g2d);
			} catch (GameOverException e) {
				gameOver();
			}
			if(generatorTimer == null){
				generatorTimer = new GeneratorTimer(this);
				timer.schedule(generatorTimer, 0 , delay);
			}else{
				growLevel();
			}
			if(cursor.isActive()){
				g2d.setColor(Color.RED);
				g2d.setStroke(new BasicStroke(5));
				g2d.drawRect(cursor.getX(), cursor.getY(), cursor.getWidth(), cursor.getHeight());
			}
			g2d.drawImage(cursor.getImage(), cursor.getX() ,cursor.getY() , cursor.getWidth(), cursor.getHeight(),this);
			time = getGameTime();
		}else if(isPaused()){
			g2d.setColor(Color.BLACK);
			g2d.drawString("Paused!!!", factory.getWidth() / 2, factory.getHeight() /2 );
		}
		g2d.drawString("Score : " + factory.getScore().getCurrentScore(), factory.getWidth() + (FactoryGtris.SIZE_FIGURE * 2) , FactoryGtris.SIZE_FIGURE / 2);
		g2d.drawString("Press Enter to  "+ textStatus, factory.getWidth() + (FactoryGtris.SIZE_FIGURE * 2) , FactoryGtris.SIZE_FIGURE);	
		g2d.drawString("Time played : " + time, factory.getWidth() + (FactoryGtris.SIZE_FIGURE * 2) ,  FactoryGtris.SIZE_FIGURE * 2);
		g2d.drawString("Level : " + currentLevel, factory.getWidth() + (FactoryGtris.SIZE_FIGURE * 2) ,  FactoryGtris.SIZE_FIGURE * 3);
			
	}
	private String getGameTime(){
		if(startDate == null)
			return "";
		
		long difference = new Date().getTime() - startDate.getTime();
		
		Calendar referenceDate = Calendar.getInstance();
		
		referenceDate.setTime(new Date(difference));
		
		return new SimpleDateFormat("mm:ss").format(referenceDate.getTime());
	}
	/**
	 * Paint the block falling
	 * @param g2d
	 */
	public void drawFigure(Graphics2D g2d){
		try{
			if(!onAir.isEmpty()){
				for(Figure f : onAir){
					g2d.drawImage(f.getImage(),f.getX(), f.getY(), f.getWidth(), f.getHeight(),this);
					drawMoved(f);
				}
				clear();
			}
		}catch(GameOverException e){
			gameOver();
		}
	}
	/**
	 * Paint the block falling
	 * @param f
	 * @throws GameOverException
	 */
	public void drawMoved(Figure f) throws GameOverException{
		if(f.getY() < ((FactoryGtris.ROWS - 1) * FactoryGtris.SIZE_FIGURE)){
			if(!scratch(f)){
				f.move();
			}else{
				int x = factory.getRealNodePosition(f.getX());
				int y = factory.getRealNodePosition(f.getY());
				onDown(f, factory.getMatrix()[x][y]);
			}
		}else{
			int x = factory.getRealNodePosition(f.getX());
			int y = factory.getRealNodePosition(f.getY());
			onDown(f, factory.getMatrix()[x][y]);
		}
	}
	/**
	 * Move block selected from the cursor
	 * @param direction direction to move
	 */
	public void moveSquare(ControlAlignment direction){
		int x = factory.getRealNodePosition(cursor.getX());
		int y = factory.getRealNodePosition(cursor.getY());
		Figure cursorPosition = factory.getMatrix()[x][y];
		Figure newPosition = null;
		switch(direction){
			case LEFT:
				newPosition = factory.getMatrix()[x - 1][y];
				break;
			case RIGHT:
				newPosition = factory.getMatrix()[x + 1][y];
				break;
			case BOTTOM:
				newPosition = factory.getMatrix()[x][y + 1];
				break;
			case TOP:
				newPosition = factory.getMatrix()[x][y - 1];
				break;
		}
		if(newPosition != null && newPosition.getImage() != null){
			Image oldImage = cursorPosition.getImage(),
					  newImage = newPosition.getImage();
			
			ColorFigure colorOld = cursorPosition.getColor(),
					    colorNew = newPosition.getColor();
			
				newPosition.setImage(oldImage);
				newPosition.setColor(colorOld);
				cursorPosition.setImage(newImage);
				cursorPosition.setColor(colorNew);
		}
		factory.searchFigureValid();
		
	}
	/**
	 * Verify if  we can active the cursor for move blocks
	 * @return
	 */
	public boolean canActiveCursor(){
		int x = factory.getRealNodePosition(cursor.getX());
		int y = factory.getRealNodePosition(cursor.getY());
		Figure cursorPosition = factory.getMatrix()[x][y];
		return cursorPosition.getImage() != null && cursorPosition.isGround();
	}
	/**
	 * Reset the game and execute when the GameOverException is thrown
	 */
	private void gameOver(){
		gameThread.interrupt();
		SoundManager.getInstance().stopSound(getLevelStage());
		factory.init();
		onAir.clear();
		currentLevel = 1;
		delay = 2000;
		background = new ImageIcon(getClass().getClassLoader().getResource(levels.get(getLevelStage()))).getImage();
		generatorTimer.cancel();
		generatorTimer = null;
		started = false;
		textStatus = "start";
		firstLoad = true;
	}
	/**
	 * Check the value in minutes and grow level if is necessary
	 */
	private void growLevel(){
		if((generatorTimer.getSeconds()/60) == UP_LEVEL_TIME){
			generatorTimer.cancel();
			generatorTimer = new GeneratorTimer(this);
			delay-=250;
			//System.out.println(delay);
			timer.schedule(generatorTimer, 0 , delay);
			factory.getScore().minutePlayed();
			if(finalStage == currentLevel)
				return;
			SoundManager.getInstance().stopSound(getLevelStage());
			currentLevel++;
			SoundManager.getInstance().playSound(getLevelStage(),true);
			background = new ImageIcon(getClass().getClassLoader().getResource(levels.get(getLevelStage()))).getImage();
		}
	}
	/**
	 * Execute when the top element in the list fall in the ground
	 * @param aux element in the air list
	 * @param real instance in a matrix
	 */
	private void onDown(Figure aux , Figure real){
		aux.setGround(true);
		real.setImage(aux.getImage());
		real.setColor(aux.getColor());
		real.setGround(true);
		factory.searchFigureValid();
	}
	/**
	 * CHeck if the block fall in ground or another block
	 * @param f1 the instance of a block
	 * @return true if the block scratch
	 * @throws GameOverException if scratch with the top
	 */
	private boolean scratch(Figure f1) throws GameOverException{
		Figure [][] matrix = factory.getMatrix();
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[i].length;j++){
				Figure storeFigure = matrix[i][j];
				if(f1.getX() == storeFigure.getX()){
					if(storeFigure.getImage() != null && storeFigure.isGround()){
						if(storeFigure.getY() <= 0){
							throw new GameOverException();
						}
						if(f1.getY() == (storeFigure.getY() - storeFigure.getHeight())){
							return true;
						}
					}
				}
			}
		}	
		return false;
	}
	/**
	 * Remove the blocks in the ground from the list
	 */
	private void clear(){
		ArrayList<Figure> toClear = new ArrayList<>();
		for(Figure f : this.onAir){
			if(f.isGround()){
				toClear.add(f);
			}
		}
		for(Figure f : toClear){
			onAir.remove(f);
		}
	}
	/**
	 * Draw empty and filled matrix
	 * @param g2d
	 */
	private void drawMatrix(Graphics2D g2d){
		Figure [][] matrix = factory.getMatrix();
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[i].length;j++){
				Figure storeFigure = matrix[i][j];
				g2d.setColor(Color.black);
				if(storeFigure.getImage() != null){
					g2d.drawImage(storeFigure.getImage(),storeFigure.getX(), storeFigure.getY(), storeFigure.getWidth(), storeFigure.getHeight(),this);
				}else{
					g2d.drawRect(storeFigure.getX(), storeFigure.getY(), storeFigure.getWidth(), storeFigure.getHeight());
				}
				
			}
		}
	}
	/**
	 * This method repain all matrix colors
	 * @param g2d
	 * @throws GameOverException if no more blocks for add
	 */
	private void repaintMatrix(Graphics2D g2d) throws GameOverException{
		Figure [][] matrix = factory.getMatrix();
		for(int i=0;i<matrix.length;i++){
			for(int j=1;j<matrix[i].length;j++){
				Figure storeFigure = matrix[i][j],
					   overFigure = matrix[i][j - 1];
				if(storeFigure.getImage() == null && overFigure.getImage() != null){
					
					storeFigure.setColor(overFigure.getColor());
					storeFigure.setImage(overFigure.getImage());
					storeFigure.setGround(overFigure.isGround());
					
					overFigure.setColor(null);
					overFigure.setImage(null);
					overFigure.setGround(false);
					
				}
			}
		}
	}
	//Getters and setters
	private String getLevelStage(){
		return KEY_STAGE + currentLevel;
	}
	public FactoryGtris getFactory(){
		return this.factory;
	}
	public CopyOnWriteArrayList<Figure> getOnAir() {
		return onAir;
	}
	public void setOnAir(CopyOnWriteArrayList<Figure> onAir) {
		this.onAir = onAir;
	}
	public boolean isStarted(){
		return started;
	}
	public boolean isPaused(){
		return paused;
	}
	
}