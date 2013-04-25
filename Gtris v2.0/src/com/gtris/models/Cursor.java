package com.gtris.models;

import com.gtris.enums.ControlAlignment;
import com.gtris.factory.FactoryGtris;
/**
 * Represent the cursor in the screen
 * @author Pablo
 *
 */
public class Cursor extends BlockBase{

	private boolean active;

	public Cursor(){
		FactoryGtris f = FactoryGtris.getInstance();
		this.image = f.getCursorImage();
		this.height = FactoryGtris.SIZE_FIGURE;
		this.width = FactoryGtris.SIZE_FIGURE;
		this.x = 0;
		this.y = FactoryGtris.SIZE_FIGURE * (FactoryGtris.ROWS -1);
	}
	/**
	 * We need to move the cursor
	 */
	@Override
	public void move(ControlAlignment direction) {
		switch(direction){
			case LEFT : moveLeft();break;
			case RIGHT : moveRight();break;
			case BOTTOM : moveDown();break; 
			case TOP : moveUp();break; 
		}
	}
	/**
	 * the x - the size of image when we move the cursor to left
	 */
	private void moveLeft(){
		this.x-=FactoryGtris.SIZE_FIGURE;
	}
	/**
	 * x + the size of image when we move the cursor to right
	 */
	private void moveRight(){
		this.x+=FactoryGtris.SIZE_FIGURE;
	}
	/**
	 * y - size of image when we move the cursor up
	 */
	private void moveUp(){
		this.y-=FactoryGtris.SIZE_FIGURE;
	}
	/**
	 * y + size of image when we move the cursor down
	 */
	private void moveDown(){
		this.y+=FactoryGtris.SIZE_FIGURE;
	}
	
	//Getters and setters
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
