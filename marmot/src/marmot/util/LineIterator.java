// Copyright 2013 Thomas Müller
// This file is part of MarMoT, which is licensed under GPLv3.

package marmot.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LineIterator implements Iterator<List<String>> {
	
	private BufferedReader reader_;
	
	public LineIterator(String filename){
		try {
			reader_ = FileUtils.openFile(filename);
		} catch(FileNotFoundException e){
			throw new RuntimeException(e);			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public LineIterator(InputStream in) {
		try {
			reader_ = FileUtils.openStream(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean hasNext(){
		try {
			return reader_.ready();
		}
		catch (IOException e){
			throw new RuntimeException("IOException: " + e);
		}
	}
	
	public List<String> next(){
		
		if (!hasNext()){
			throw new NoSuchElementException();
		}
		
		try {
			String line = reader_.readLine();
						
			String[] tokens = line.split("\\s+");
			ArrayList<String> list = new ArrayList<String>(tokens.length);
			for (int i=0;i<tokens.length;i++){
				if (!tokens[i].isEmpty()){
					list.add(tokens[i]);
				}
			}
			return list;
		}
		catch (IOException e){
			throw new RuntimeException("IOException: " + e);
		}
	}
	
    public void remove() {
        throw new UnsupportedOperationException();
    }

}