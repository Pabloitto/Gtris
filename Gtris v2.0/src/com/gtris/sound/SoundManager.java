package com.gtris.sound;

import java.net.URL;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
	
	private SoundManager(){
		songs = Utilities.readConfig("sound");
		
		songsPlayed = new HashMap<>();
	}
	private Clip init(String path) throws Exception{
		 Clip clip;
		 url = getClass().getClassLoader().getResource(path);
		 AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		 clip = AudioSystem.getClip();
		 clip.open(audioIn);
         clip.start();
         clip.loop(Clip.LOOP_CONTINUOUSLY);
		return clip;
	}
	public void playSound(String song){
		Clip currentSong  = null;
		try {
			currentSong = init(songs.get(song));
			songsPlayed.put(song, currentSong);
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentSong.start();
	}
	public void playSound(String song , boolean repeatForever){
		playSound(song);
		if(repeatForever){
			songsPlayed.get(song).loop(Clip.LOOP_CONTINUOUSLY);
		}else{
			songsPlayed.get(song).loop(0);
		}
	}
	public void stopSound(String song){
		Clip currentSong  = songsPlayed.get(song);
		currentSong.stop();
	}
	public static SoundManager getInstance(){
		if(soundManager == null){
			soundManager = new SoundManager();
		}
		return soundManager;
	}
	
	
}
