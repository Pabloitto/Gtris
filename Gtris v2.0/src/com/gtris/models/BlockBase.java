package com.gtris.models;
import java.awt.Image;
import com.gtris.enums.ColorFigure;
import com.gtris.enums.ControlAlignment;
import com.gtris.factory.FactoryGtris;

public abstract class BlockBase {

	protected Image image;
	protected int height;
	protected int width;
	protected boolean ground;
	protected ColorFigure color;
	protected int x;
	protected int y;
	
	
	public Image getImage() {
		return image;
	}


	public void setImage(Image image) {
		this.image = image;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
	public boolean isGround() {
		return ground;
	}

	public void setGround(boolean ground) {
		this.ground = ground;
	}
	public ColorFigure getColor() {
		return color;
	}

	public void setColor(ColorFigure color) {
		this.color = color;
	}
	public void move(ControlAlignment direction){
		this.move();
	}
	public void move(){
		this.y += FactoryGtris.SIZE_FIGURE;
	}
}
