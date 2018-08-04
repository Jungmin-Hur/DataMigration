package main.report;

import java.io.FileWriter;
import java.io.IOException;

import main.common.model.MigrationProgressType;

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
	
	public void writeReport(MigrationProgressType migrationProgressType, String message) {
		FileWriter output = ReportAnalysisConnection.getFileWriter();
		try {
			System.out.println(message);
			output.write(message + "\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
