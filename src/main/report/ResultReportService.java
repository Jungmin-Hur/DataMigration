package main.report;

import java.io.FileWriter;
import java.io.IOException;

import main.analysis.model.Constants;

/**
 * analysis
 * pre-migration
 * migration은 본 클래스를 사용해서 file write함
 * 
 * file path는 application.properties에 존재함
 * 
 * @author duck
 *
 */
public class ResultReportService {

	String propertiesPath = "/main/resources/application.properties";
	
	public static void writeAnalysisReport(String message) {
		FileWriter output = null;
		output = ReportAnalysisFileConnection.getFileWriter();
		try {
			System.out.println(message);
			output.write(message + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeAnalysisReport(String message, boolean enter) {
		writeAnalysisReport(message);
		if(enter == true) {
			writeAnalysisReport(Constants.EMPTY);
		}
	}
}
