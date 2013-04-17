package com.gtris.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.gtris.enums.ModePersistent;

public class Persistence {

	private FileOutputStream fileOut;
	private ObjectOutputStream objOut;
	
	
	private FileInputStream fileIn;
	
	private ObjectInputStream objIn;
	
	
	private String name;
	
	public Persistence(String name){
		this.name = name;
		init(ModePersistent.WRITE);
		init(ModePersistent.READ);
	}
	
	public <T> void  save(T o){
		try {
			objOut.writeObject (o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public <T> T load(){
		T readObject = null;
		try {
			readObject= ((T) objIn.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return readObject;
	}
	private void init(ModePersistent mode){
		try {
			if(mode == ModePersistent.WRITE){
				
					fileOut = new FileOutputStream(this.name + ".data");
					objOut = new ObjectOutputStream (fileOut);
				
			}
			if(mode == ModePersistent.READ){
				fileIn = new FileInputStream(this.name+".data");
				objIn = new ObjectInputStream (fileIn);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
