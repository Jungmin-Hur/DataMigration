package main.query;

import java.io.FileOutputStream;
import java.io.IOException;

import main.analysis.model.Constants;

public class ResultQueryService {

	synchronized public static void writeResultQuery (String message) {
//		FileWriter output = null;
		FileOutputStream output = null;
		output = ResultQueryFileConnection.getFileOutputStream();
//		output = ResultQueryFileConnection.getFileWriter();
		try {
			System.out.println(message);
			output.write((message + "\r\n").getBytes());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeResultQuery(String message, boolean enter) {
		writeResultQuery(message);
		if(enter == true) {
			writeResultQuery(Constants.EMPTY);
		}
	}
}
