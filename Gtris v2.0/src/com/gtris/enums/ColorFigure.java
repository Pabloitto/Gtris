package com.gtris.enums;

import com.gtris.utilities.Utilities;
/**
 * Enum represent the color for a figure
 * @author Pablo
 *
 */
public enum ColorFigure {

	CURSOR(0),BLUE(1),GREEN(2),PINK(3),RED(4),YELLOW(5),BLANK(-1);
	
	int color;
	
	ColorFigure(int color){
		this.color = color;
	}
	/**
	 * Get a real value of a enum
	 * @return
	 */
	public int getValue(){
		return this.color;
	}
	/**
	 * Get random color
	 * @return
	 */
	public static ColorFigure getRandom(){
		int rdm = Utilities.getRandomNumber(1, 5);
		return getByColor(rdm);
	}
	/**
	 * Get specific color by the int
	 * @param color
	 * @return
	 */
	public static ColorFigure getByColor(int color){
	        for(ColorFigure c : ColorFigure.values()){
	            if(color == c.getValue()){
	                return c;
	            }
	        }
	        return null;
	}
	@Override
	public String toString() {
		switch(color){
			case 1:return "blue";
			case 2:return "green";
			case 3:return "pink";
			case 4:return "red";
			case 5:return "yellow";
			case 0 : return "cursor";
		}
		return super.toString();
	}
}
