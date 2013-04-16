package com.gtris.components;

import javax.swing.JFrame;

import com.gtris.events.KeyBoardEventListener;

public final class TetrisFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3568407999805573130L;

	private GamePanel gamePanel;
	
	
	
	public TetrisFrame(){
		initComponents();
	}
	/**
	 * Init the components in window
	 */
	private void initComponents(){
		this.gamePanel = new GamePanel();
		this.addKeyListener(new KeyBoardEventListener(this.gamePanel));
		this.builtFrame();
	}
	/**
	 * Set elements to frame
	 */
	private void builtFrame(){
        this.add(this.gamePanel);
        this.setSize(380, 415);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("My Gtris!!!");
    }
	
}
