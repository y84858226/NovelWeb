package com.novel.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.jca.cci.object.EisOperation;

public class JsonReaderUtils {
	public static String JsonReader(String path) {
		String readerStr = "";
		File file = new File(path);
		BufferedReader reader = null;
		try {
			FileInputStream input = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			String tempStr = "";
			if((tempStr = reader.readLine())!= null) {
				readerStr += tempStr;
			}
		}catch(IOException e){
			 e.printStackTrace();
		}finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return readerStr;
	}
}
