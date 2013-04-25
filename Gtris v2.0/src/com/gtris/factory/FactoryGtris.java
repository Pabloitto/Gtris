package com.gtris.factory;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import com.gtris.enums.ColorFigure;
import com.gtris.enums.ControlAlignment;
import com.gtris.models.Figure;
import com.gtris.models.Score;
import com.gtris.sound.SoundManager;
import com.gtris.utilities.Utilities;
/**
 * Blocks Factory is the class where the logic game are
 * @author Pablo
 *
 */
public class FactoryGtris {

	private static FactoryGtris myFactory;
	private HashMap<String,String> figures;
	private Score score;
	private Score highScore;
	private Figure[][] matrix;
	public static final int SIZE_FIGURE = 32;
	public static final int ROWS = 24;
	public static final int COLS = 12;
	private int clicks;
	
	private FactoryGtris(){
		figures = Utilities.readConfig("figure");
		matrix = new Figure[COLS][ROWS];
		this.score = new Score();
		init(matrix);
	}
	/**
	 * Get image random
	 * @return
	 */
	public Image getRandomImage(){
		return new ImageIcon(getClass()
					.getClassLoader()
					.getResource(figures.get(ColorFigure.getRandom().toString())))
					.getImage();
	}
	/**
	 * Get image by string color attribute in a xml
	 * @param color
	 * @return
	 */
	public Image getSpecificImage(String color){
		return new ImageIcon(getClass()
					.getClassLoader()
					.getResource(figures.get(color)))
					.getImage();
	}
	/**
	 * Get the image of cursor
	 * @return
	 */
	public Image getCursorImage(){
		return new ImageIcon(getClass()
					.getClassLoader()
					.getResource(figures.get(ColorFigure.CURSOR.toString())))
					.getImage();
	}
	/**
	 * This method initialize the matrix, when the matrix is 
	 * created with a specific size 12 x 6 which each block size is 32px 
	 * created a instance for each block, but the attribute "image" is null
	 * when we change the position or the new block falling on ground 
	 * only the attribute is changed
	 */
	public void init(Figure[][] matrix){
		Figure f = null;
		int id = 0;
		int x = (SIZE_FIGURE * -1) , y = (SIZE_FIGURE * -1);
		for(int i=0;i<matrix.length;i++){
			x = x + SIZE_FIGURE;
			y = (SIZE_FIGURE * -1);
			for(int j=0;j<matrix[i].length;j++){
				y = y + SIZE_FIGURE;
				f = new Figure(++id, SIZE_FIGURE, SIZE_FIGURE, x , y);
				matrix[i][j] = f;
			}
		}
	}
	public void init(){
		this.init(this.matrix);
	}
	/**
	 * This method return an instance of class FactoryGtris
	 * @return unique instance
	 */
	public static FactoryGtris getInstance(){
		if(myFactory == null){
			myFactory = new FactoryGtris();
		}
		return myFactory;
	}
	/**
	 * This method execute the algorithm
	 */
	public void runAnalizer(){
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[i].length;j++){
				if(matrix[i][j].getImage() != null){
					tetrisAnalizer(i , j);
				}
			}
		}
	}
	/**
	 * This method search the valid block for destroy a tetris block
	 * start in a node and check is the neighborhoods has the same color
	 * until the sum is 4 
	 * @param x 
	 * @param y
	 */
	private void tetrisAnalizer(int x , int y){
		
		final int NUMBERS_FIGURE = 4;//This is the sum for a complete figure
		
		Figure first = this.matrix[x][y];
		
		Figure currentNode = null;
		
		ArrayList<Figure> toDelete = new ArrayList<>();
		
		ArrayList<Figure> toCheck = new ArrayList<>();
		
		List<ControlAlignment> directions = null;
		
		toCheck.add(first);//Add on list to check
		
		toDelete.add(first);// Add on list to delete too
		
		
		while(!toCheck.isEmpty()){
			
			currentNode = toCheck.remove(0);
			
			/**
			 * When the block to delete is 4 then we need to destroy the figure
			 */
			if(toDelete.size() == NUMBERS_FIGURE){
				for(Figure del : toDelete){
					del.setColor(null);
					del.setImage(null);
					this.score.removePice();
				}
				toCheck.clear();
				SoundManager.getInstance().playSound("explode", false);
				break;
			}
			
			//Each iteration we need to change the coordinates
			x = getRealNodePosition(currentNode.getX());
			y = getRealNodePosition(currentNode.getY());
			
			directions = new LinkedList<ControlAlignment>(Arrays.asList(ControlAlignment.values()));//This is a list of directions
			
			while(!directions.isEmpty()){
				
				ControlAlignment d = directions.remove(0);//Get current direction
				
				/**
				 * Check if is the same color of a nodeIni then we need to check if 
				 * not came the same direction currentNode.getOrigin() != ControlAlignment.RIGHT
				 */
				switch(d){
					case RIGHT:{
						if( x < (COLS - 1) && equalColor(this.matrix[x  + 1][y].getColor(), currentNode.getColor())){//Go to RIGHT
							if(currentNode.getOrigin() != ControlAlignment.RIGHT){
								this.matrix[x  + 1][y].setOrigin(ControlAlignment.LEFT);
								toCheck.add(this.matrix[x  + 1][y]);
								toDelete.add(this.matrix[x  + 1][y]);
								directions.clear();
							}
						}
						break;
					}
					case LEFT:{
						if( x > 0 && equalColor(this.matrix[x  - 1][y].getColor() , currentNode.getColor())){// Go left
							if(currentNode.getOrigin() != ControlAlignment.LEFT){
								this.matrix[x  - 1][y].setOrigin(ControlAlignment.RIGHT);
								toCheck.add(this.matrix[x  - 1][y]);
								toDelete.add(this.matrix[x  - 1][y]);
								directions.clear();
							}
						}
						break;
					}
					case BOTTOM:{
						if( y < (ROWS - 1) && equalColor(this.matrix[x][y + 1].getColor() , currentNode.getColor())){ // Go down			
							if(currentNode.getOrigin() != ControlAlignment.BOTTOM){
								this.matrix[x][y + 1].setOrigin(ControlAlignment.TOP);
								toCheck.add(this.matrix[x][y + 1]);
								toDelete.add(this.matrix[x][y + 1]);
								directions.clear();
							}
						}
						break;
					}
					case TOP:{
						if(y > 0 && equalColor(this.matrix[x][y - 1].getColor() , currentNode.getColor())){ // Go up
							
							if(currentNode.getOrigin() != ControlAlignment.TOP){
								this.matrix[x][y - 1].setOrigin(ControlAlignment.BOTTOM);
								toCheck.add(this.matrix[x][y - 1]);
								toDelete.add(this.matrix[x][y - 1]);
								directions.clear();
							}
						}
						break;
					}
				}	
			}
		}
		//Clear origin directions
		cleanOriginNode(toDelete);
	}
	/**
	 * This method remove the origin of nodes deleted and not deleted
	 * @param toDelete
	 */
	private void cleanOriginNode(ArrayList<Figure> trash){
		for(Figure deleted : trash){
			matrix[getRealNodePosition(deleted.getX())]
				  [getRealNodePosition(deleted.getY())]
				  .setOrigin(null);//Update the origin
		}
		trash.clear();//Clean trash
	}
	/**
	 * This method load the initial blocks in screen
	 */
	public void loadBlocksRandom(){
		Figure f;
		for(int i=0; i<matrix.length ; i++){
			for(int j=(matrix[i].length - Utilities.getRandomNumber(1, 4));j<matrix[i].length;j++){
				f = matrix[i][j];
				ColorFigure c = ColorFigure.getRandom();
				f.setImage(getSpecificImage(c.toString()));
				f.setColor(c);
				f.setGround(true);
			}
		}
		this.runAnalizer();
	}
	public void rotateMatrix(){
		if(clicks == 4){
			clicks = 0;
		}
		clicks++;
	}
	/**
	 * This method compare two colors of blocks
	 * @param colorA color in matrix
	 * @param colorB color to compare
	 * @return true if the color is the same
	 */
	public boolean equalColor(ColorFigure colorA , ColorFigure colorB){
		return  (colorA != null && colorB != null) && (colorA == colorB);
	}
	/**
	 * This method return the real postion in the matrix
	 * the block size is 32 when the x value is
	 * 32, 64 , 96 ... 
	 * 32 / 32 return the first position
	 * 64 / 32 return the second position
	 * @param x
	 * @return
	 */
	public int getRealNodePosition(int x){
		return x / SIZE_FIGURE;
	}
	/**
	 * Get real height panel
	 * @return
	 */
	public int getHeight(){
		return (ROWS - 1) * SIZE_FIGURE;
	}
	/**
	 * Get real width panel
	 * @return
	 */
	public int getWidth(){
		return (FactoryGtris.COLS - 1) * SIZE_FIGURE;
	}

	//Getter setters
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}
	public Score getHighScore() {
		return highScore;
	}
	public void setHighScore(Score highScore) {
		this.highScore = highScore;
	}
	public Figure[][] getMatrix(){
		return this.matrix;
	}
	public int getState(){
		return clicks;
	}
	
}
