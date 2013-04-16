package com.gtris.components;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import com.gtris.events.KeyBoardEventListener;
import com.gtris.sound.SoundManager;

public final class TetrisFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3568407999805573130L;

	private GamePanel gamePanel;
	
	private JProgressBar progressBar;
	
	private SoundManager sound;
	
	
	public TetrisFrame(){
		this.progressBar = new JProgressBar(0,100);
		this.progressBar.setValue(0); 
		this.progressBar.setStringPainted(true);
		this.add(this.progressBar,BorderLayout.NORTH);
		sound = SoundManager.getInstance();
		sound.loadSongs(this.progressBar , this);
		initComponents();
	}
	/**
	 * Init the components in window
	 */
	private void initComponents(){
		this.gamePanel = new GamePanel();
		this.add(this.gamePanel);
		this.gamePanel.setVisible(false);
		this.builtFrame();
	}
	public void loadGame(){
		this.progressBar.setVisible(false);
		this.addKeyListener(new KeyBoardEventListener(this.gamePanel));
		this.gamePanel.setVisible(true);
	}
	/**
	 * Set elements to frame
	 */
	private void builtFrame(){
        this.setSize(380, 415);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("My Gtris!!!");
    }
	
}
