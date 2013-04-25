package com.gtris.models;

import java.awt.Image;
import com.gtris.enums.ColorFigure;
import com.gtris.enums.ControlAlignment;

/**
 * Class represent a block in the screen
 * @author Pablo
 *
 */
public class Figure extends BlockBase implements Comparable<Figure>{

	private Integer id;
	private ControlAlignment origin;

	public Figure(){
		
	}

	public Figure(Integer id, Image image, int height, int width, int x, int y,
			boolean ground, ColorFigure color, ControlAlignment origin) {
		super();
		this.id = id;
		super.image = image;
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		this.ground = ground;
		this.color = color;
		this.origin = origin;
	}

	public Figure(Integer id, int height, int width, int x, int y) {
		super();
		this.id = id;
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setOrigin(ControlAlignment origin) {
		this.origin = origin;
	}
	public ControlAlignment getOrigin() {
		return origin;
	}
	@Override
	public int compareTo(Figure arg0) {
		return this.getId().compareTo(arg0.getId());
	}
}
