package com.gtris.models;

import java.io.Serializable;

/**
 * Class represent the score in screen
 * @author Pablo
 *
 */
public class Score implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4978176518309191285L;
	private long currentScore;
	private final int REMOVE_PIECE = 1000; 
	private final int MINUTE_PLAYED = 1000;
	
	public Score(){
		
	}
	public long getCurrentScore() {
		return this.currentScore;
	}
	/**
	 * Up the score
	 * @param score score to sum
	 */
	private void scoreIncrease(long score){
		this.currentScore += score;
	}
	/**
	 * Call each minute of the game
	 */
	public void minutePlayed(){
		scoreIncrease(MINUTE_PLAYED);
	}
	/**
	 * Call when we remove a piece
	 */
	public void removePice(){
		scoreIncrease(REMOVE_PIECE);
	}

}
