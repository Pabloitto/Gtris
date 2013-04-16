package com.gtris.events;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.gtris.components.GamePanel;
import com.gtris.enums.ControlAlignment;
/**
 * This class is the listener for a events on a keyboard
 * @author Pablo
 *
 */
public class KeyBoardEventListener extends KeyAdapter{
	
	private GamePanel gamePanel;
	
	public KeyBoardEventListener(GamePanel gamePanel){
		this.gamePanel=gamePanel;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER){
			this.gamePanel.start();
		}
		if(!this.gamePanel.isPaused()){
			if(key == KeyEvent.VK_LEFT){
				if(gamePanel.cursor.getX() > 0){
					if(gamePanel.cursor.isActive()){
						gamePanel.moveSquare(ControlAlignment.LEFT);
						gamePanel.cursor.setActive(false);
					}
					gamePanel.cursor.move(ControlAlignment.LEFT);
				}
			}else if(key == KeyEvent.VK_RIGHT){
				if(gamePanel.cursor.getX() < gamePanel.getFactory().getWidth()){
					if(gamePanel.cursor.isActive()){
						gamePanel.moveSquare(ControlAlignment.RIGHT);
						gamePanel.cursor.setActive(false);
					}
					gamePanel.cursor.move(ControlAlignment.RIGHT);
				}
			}else if(key == KeyEvent.VK_DOWN){
				if(gamePanel.cursor.getY() < gamePanel.getFactory().getHeight()){
					if(gamePanel.cursor.isActive()){
						gamePanel.moveSquare(ControlAlignment.BOTTOM);
						gamePanel.cursor.setActive(false);
					}
					gamePanel.cursor.move(ControlAlignment.BOTTOM);
				}
			}else if(key == KeyEvent.VK_UP){
				if(gamePanel.cursor.getY() > 0){
					if(gamePanel.cursor.isActive()){
						gamePanel.moveSquare(ControlAlignment.TOP);
						gamePanel.cursor.setActive(false);
					}
					gamePanel.cursor.move(ControlAlignment.TOP);
				}
			}else if(key == KeyEvent.VK_SPACE){
				if(gamePanel.canActiveCursor()){
					gamePanel.cursor.setActive(!gamePanel.cursor.isActive());
				}
			}
		}
	}
}