package com.gtris.threads;

import java.awt.Image;
import java.util.TimerTask;

import com.gtris.components.GamePanel;
import com.gtris.enums.ColorFigure;
import com.gtris.models.Figure;
import com.gtris.utilities.Utilities;
/**
 * This class generate the pieces to fall every 2 minutes 
 * when start the game
 * @author Pablo
 *
 */
public class GeneratorTimer extends TimerTask{
	

	private GamePanel gamePanel;
	
	private int seconds;
	
	public GeneratorTimer(GamePanel gamePanel){
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void run() {
		if(!this.gamePanel.isPaused()){
			 seconds+=2;
			 int p = Utilities.getRandomNumber(0, 5);
			 Figure first = getNewFigure(p);
			 this.gamePanel.getOnAir().add(first);
			 if(p == 5){
				 p = p - 1;
			 }else{
				 p = p + 1;
			 }
			 Figure second = getNewFigure(p);
			 this.gamePanel.getOnAir().add(second);
			 this.gamePanel.block();//Need to sync the process
		}
	}	
	/**
	 * Getter of seconds
	 * @return int represent the seconds played
	 */
	public int getSeconds(){
		return seconds;
	}
	/**
	 * Get new image for put in a list of block falling
	 * @param rdmPosition position in x - screen
	 * @return new figure falling
	 */
	public Figure getNewFigure(int rdmPosition){
		Figure f = this.gamePanel.getFactory().getMatrix()[rdmPosition][0];
		ColorFigure colorF = ColorFigure.getRandom();
		Image im = this.gamePanel.getFactory().getSpecificImage(colorF.toString());
		Figure result = new Figure(f.getId() , im ,f.getHeight() ,f.getWidth() , f.getX() , f.getY() , false , colorF, null);
		return result;
	}
}
