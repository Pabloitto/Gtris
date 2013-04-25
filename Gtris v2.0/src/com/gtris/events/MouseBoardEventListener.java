package com.gtris.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.gtris.components.GamePanel;
import com.gtris.factory.FactoryGtris;
import com.gtris.models.Cursor;

public class MouseBoardEventListener implements MouseListener{

	
	private GamePanel gamePanel;
	
	public MouseBoardEventListener(GamePanel gamePanel){
		this.gamePanel=gamePanel;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		onClick(arg0.getX() , arg0.getY());
	}
	
	
	public void onClick(final int xS , final int yS){
		
		final int limitX = ((FactoryGtris.COLS) * FactoryGtris.SIZE_FIGURE);
		final int limitY = ((FactoryGtris.ROWS + 1) * FactoryGtris.SIZE_FIGURE);
		
		
		if(xS  > limitX || xS < 0){
			return;
		}
		if(yS > limitY || yS < 0){
			return;
		}
		
		int x = getRealPosition(xS);
		int y = getRealPosition(yS) - 1;
		
		Cursor mouseCursor = gamePanel.cursorMouse;
		
		boolean active = mouseCursor.isActive();
		

		
		Cursor newCursorPosition = new Cursor();
		
		newCursorPosition.setX(x * FactoryGtris.SIZE_FIGURE);
		newCursorPosition.setY(y * FactoryGtris.SIZE_FIGURE);
		
		if(gamePanel.canActiveCursor(newCursorPosition)){
			if(active){
				gamePanel.moveSquare(x, y, mouseCursor);
				mouseCursor.setActive(false);
			}else{
				mouseCursor.setActive(true);
			}
			if(!mouseCursor.isActive()){
				gamePanel.getFactory().runAnalizer();
			}
		}else{
			mouseCursor.setActive(false);
		}
		mouseCursor.setX(newCursorPosition.getX());
		mouseCursor.setY(newCursorPosition.getY());
	}
	
	public int getRealPosition(int n){
		if(n <= FactoryGtris.SIZE_FIGURE){
			return 0;
		}
		return n / FactoryGtris.SIZE_FIGURE;
	}

	
	
}
