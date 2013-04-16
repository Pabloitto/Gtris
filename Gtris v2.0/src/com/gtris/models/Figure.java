package com.gtris.models;

import java.awt.Image;
import com.gtris.enums.ColorFigure;
import com.gtris.enums.ControlAlignment;
import com.gtris.factory.FactoryGtris;

/**
 * Class represent a block in the screen
 * @author Pablo
 *
 */
public class Figure implements Comparable<Figure>{

	private Integer id;
	private Image image;
	private int height;
	private int width;
	private int x;
	private int y;
	private boolean ground;
	private ColorFigure color;
	private ControlAlignment origin;

	public Figure(){
		
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
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
	public ControlAlignment getOrigin() {
		return origin;
	}

	public void setOrigin(ControlAlignment origin) {
		this.origin = origin;
	}
	/**
	 * Move image down (falling the block)
	 */
	public void move(){
		this.y += FactoryGtris.SIZE_FIGURE;
	}


	@Override
	public int compareTo(Figure arg0) {
		return this.getId().compareTo(arg0.getId());
	}

	
	
}
