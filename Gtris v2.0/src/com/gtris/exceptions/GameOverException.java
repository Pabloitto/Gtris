package com.gtris.exceptions;
/**
 * Custom exception represent when the game is finished
 * @author Pablo
 *
 */
public class GameOverException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6108479553832742959L;

	public GameOverException(){super("Game Over!!!");}
	
}
