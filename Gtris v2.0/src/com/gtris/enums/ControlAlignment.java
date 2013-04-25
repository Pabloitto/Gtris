package com.gtris.enums;

/**
 * This enum represent the direction
 * @author Pablo
 *
 */
public enum ControlAlignment {
	LEFT(1),RIGHT(3),TOP(4),BOTTOM(2);	
	
	int d = 0;
	
	ControlAlignment(int d){
		this.d = d;
	}
	public int getValue(){
		return this.d;
	}
	public static ControlAlignment getDirection(int state) {
		for(ControlAlignment d : ControlAlignment.values()){
            if(state == d.getValue()){
                return d;
            }
        }
		return null;
	}
}
