package com.gtris.sound;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JProgressBar;

import com.gtris.components.TetrisFrame;
import com.gtris.utilities.Utilities;
/**
 * Class to handle the music and sounds
 * @author Pablo
 *
 */
public class SoundManager {

	private static SoundManager soundManager;
	private URL url;
	private HashMap<String,String> songs;
	private HashMap<String,Clip> songsPlayed;
	
	private double progress;
	
	private SoundManager(){
		songs = Utilities.readConfig("sound");
		songsPlayed = new HashMap<>();
	}
	public void loadSongs(final JProgressBar progressBar, final TetrisFrame tetrisFrame){
		Thread loaderSongs = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					progress = 0;
					Iterator<Entry<String, String>> it = songs.entrySet().iterator();
					double percent = (100d / songs.size());
					while(progress < 100d){
						while(it.hasNext()){
							Map.Entry<String,String> pairs = it.next();
							progress+=percent;
							progressBar.setValue((int)progress);
							progressBar.setString("Load sound file " + pairs.getValue() + " please wait...");
							songsPlayed.put(pairs.getKey(),init(pairs.getValue()));
						}
					}
					tetrisFrame.loadGame();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		loaderSongs.start();
	}
	private Clip init(String path) throws Exception{
		 Clip clip;
		 url = getClass().getClassLoader().getResource(path);
		 AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		 clip = AudioSystem.getClip();
		 clip.open(audioIn);
		return clip;
	}
	public void playSound(final String song , boolean repeat){
		Clip currentSong  = songsPlayed.get(song);
		currentSong.setMicrosecondPosition(0);
		currentSong.start();
		if(repeat){
			songsPlayed.get(song).loop(Clip.LOOP_CONTINUOUSLY);
		}else{
			songsPlayed.get(song).loop(0);
		}
	}
	public void stopSound(String song , boolean close){
		Clip currentSong  = songsPlayed.get(song);
		currentSong.stop();
		if(close){
			currentSong.close();
		}
	}
	public static SoundManager getInstance(){
		if(soundManager == null){
			soundManager = new SoundManager();
		}
		return soundManager;
	}
	
	
}
