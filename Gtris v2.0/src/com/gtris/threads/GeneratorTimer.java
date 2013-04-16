package com.gtris.threads;

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
			 
			 //Fix the problem with the concurrency
			 if(first.getY() != second.getY()){
				 second.setY(first.getY());
				 System.out.println("Changing value!!!");
			 }
			 
		}
	}	 
	public int getSeconds(){
		return seconds;
	}
	public Figure getNewFigure(int rdmPosition){
		Figure f = this.gamePanel.getFactory().getMatrix()[rdmPosition][0];
		Figure result = new Figure();
		result.setId(f.getId());
		result.setX(f.getX());
		result.setY(f.getY());
		result.setHeight(f.getHeight());
		result.setWidth(f.getWidth());
		result.setColor(ColorFigure.getRandom());
		result.setImage(this.gamePanel.getFactory().getSpecificImage(result.getColor().toString()));
		return result;
	}
}
