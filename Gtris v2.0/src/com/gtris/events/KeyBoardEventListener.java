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
	/**
	 * This override method execute when the client press arrows for move the cursor, press the space bar
	 * for change a color block or press enter for pause the game
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER){
			this.gamePanel.start();
		}
		if(!this.gamePanel.isPaused()){
			/**
			 * When we move the cursor check if is active,
			 * if is active then we need to move the blocks depend the
			 * direction on the method moveSquare, also we need check if the block can be moved =)
			 */
			if(key == KeyEvent.VK_LEFT){
				if(gamePanel.cursor.getX() > 0){
					if(gamePanel.cursor.isActive()){
						if(!gamePanel.canActiveOnNextNode(ControlAlignment.LEFT)){
							return;
						}
						gamePanel.moveSquare(ControlAlignment.LEFT , gamePanel.cursor);
						//gamePanel.cursor.setActive(false);
					}
					gamePanel.cursor.move(ControlAlignment.LEFT);
				}
			}else if(key == KeyEvent.VK_RIGHT){
				if(gamePanel.cursor.getX() < gamePanel.getFactory().getWidth()){
					if(gamePanel.cursor.isActive()){
						if(!gamePanel.canActiveOnNextNode(ControlAlignment.RIGHT)){
							return;
						}
						gamePanel.moveSquare(ControlAlignment.RIGHT , gamePanel.cursor);
						//gamePanel.cursor.setActive(false);
					}
					gamePanel.cursor.move(ControlAlignment.RIGHT);
				}
			}else if(key == KeyEvent.VK_DOWN){
				if(gamePanel.cursor.getY() < gamePanel.getFactory().getHeight()){
					if(gamePanel.cursor.isActive()){
						if(!gamePanel.canActiveOnNextNode(ControlAlignment.BOTTOM)){
							return;
						}
						gamePanel.moveSquare(ControlAlignment.BOTTOM , gamePanel.cursor);
						//gamePanel.cursor.setActive(false);
					}
					gamePanel.cursor.move(ControlAlignment.BOTTOM);
				}
			}else if(key == KeyEvent.VK_UP){
				if(gamePanel.cursor.getY() > 0){
					if(gamePanel.cursor.isActive()){
						if(!gamePanel.canActiveOnNextNode(ControlAlignment.TOP)){
							return;
						}
						gamePanel.moveSquare(ControlAlignment.TOP , gamePanel.cursor);
						//gamePanel.cursor.setActive(false);
					}
					gamePanel.cursor.move(ControlAlignment.TOP);
				}
			}else if(key == KeyEvent.VK_SPACE){
				if(gamePanel.canActiveCursor(gamePanel.cursor)){//Check if the cursor is hover the block valid to move
					gamePanel.cursor.setActive(!gamePanel.cursor.isActive());
					if(!gamePanel.cursor.isActive()){
						gamePanel.getFactory().runAnalizer();
					}
				}
			}
		}
	}
}
