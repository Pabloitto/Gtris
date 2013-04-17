package com.gtris.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * Class for persist the high score game
 * @author pablo
 *
 */
public class Persistence {

	private FileOutputStream fileOut;
	private ObjectOutputStream objOut;
	
	private File file;
	
	private ObjectInputStream objIn;
	
	
	private String name;
	
	public Persistence(String name){
		this.name = name;
	}
	/**
	 * Generic method for save the object
	 * @param o
	 */
	public <T> void  save(T o){
		try {
			if(fileOut == null && objOut == null){
				file = new File(this.name+".data");
				if(file.exists()){
					fileOut = new FileOutputStream(file);
					objOut = new ObjectOutputStream (fileOut);
				}
			}
			objOut.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Generic method for get the generic object from the file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T load(){
		T readObject = null;
		try {
			if(objIn == null){
				//InputStream stream = getStoreFile();
				file = new File(this.name+".data");
				if(file != null && file.exists()){
					objIn = new ObjectInputStream(new FileInputStream(file));
				}
			}
			if(objIn != null){
				readObject= ((T) objIn.readObject());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return readObject;
	}
}
