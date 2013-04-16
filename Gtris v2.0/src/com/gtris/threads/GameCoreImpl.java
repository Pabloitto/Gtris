package com.gtris.threads;

import com.gtris.components.GamePanel;
/**
 * Thread game
 * @author Pablo
 *
 */
public class GameCoreImpl implements Runnable{

    private final long DELAY = 200;
    
    private GamePanel aThis;
    
    public GameCoreImpl(GamePanel aThis) {
        this.aThis = aThis;
    }

    @Override
    public void run() {
        try{
            while(true){
                this.aThis.repaint();
                Thread.sleep(DELAY);
            }
        }catch(InterruptedException e){
        	this.aThis.repaint();
            //System.out.println("Thread fail!!!");
        }
    }
    
}
