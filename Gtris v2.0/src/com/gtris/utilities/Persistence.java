package com.gtris.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Persistence {

	private FileOutputStream fileOut;
	private ObjectOutputStream objOut;
	
	private File file;
	
	private ObjectInputStream objIn;
	
	
	private String name;
	
	public Persistence(String name){
		this.name = name;
	}
	
	public <T> void  save(T o){
		try {
			if(fileOut == null && objOut == null){
				file = new File(this.name+".data");
				if(!file.exists()){
					fileOut = new FileOutputStream(file);
					objOut = new ObjectOutputStream (fileOut);
				}
			}
			objOut.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
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
	public InputStream getStoreFile(){
		InputStream stream =  getClass()
							 .getClassLoader()
							 .getResourceAsStream("com/gtris/utilities/"+this.name+".data");
		
		return stream;
	}
}
