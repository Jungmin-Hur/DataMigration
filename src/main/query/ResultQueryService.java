package main.query;

import java.io.FileWriter;
import java.io.IOException;

import main.analysis.model.Constants;

public class ResultQueryService {

	public static void writeResultQuery (String message) {
		FileWriter output = null;
		output = ResultQueryFileConnection.getFileWriter();
		try {
			System.out.println(message);
			output.write(message + "\r\n");
			
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
