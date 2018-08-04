package main.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReportAnalysisFileConnection {

	private static String path;
	private static String filename;
	private static String prefix;
	private static FileWriter fileWriter;
	
	String propertiesPath = "/main/resources/application.properties";

//	private static ReportAnalysisConnection reportAnalysisConnection = new ReportAnalysisConnection();

	public ReportAnalysisFileConnection() {
		InputStream inputStream = getClass().getResourceAsStream(propertiesPath);
		Properties properties = new Properties();
		try {
			properties.load(inputStream);

			path = properties.getProperty("report.analysis.path");
			prefix = properties.getProperty("report.filename.prefix");
			filename = properties.getProperty("report.analysis.filename");
			
			//path는 empty체크하지 않음
			if(prefix.isEmpty() || filename.isEmpty()) {
				ResultReportService.writeAnalysisReport("application.properties에 report.filename.prefix, report.analysis.filename가 비어있어서 결과 파일 생성에 실패했습니다.");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			fileWriter = new FileWriter(path+prefix+filename, true); //set append option
		} catch (IOException e) {
			ResultReportService.writeAnalysisReport("Analysis 결과 파일 생성에 실패하였습니다.");
			e.printStackTrace();
		}
	}
	
	public static FileWriter getFileWriter() {
		return fileWriter;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public static void closeFileStream() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		fileWriter.close();
		super.finalize();
	}
}
