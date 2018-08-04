package main.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReportAnalysisConnection {

	private static String path;
	private static String filename;
	private static String prefix;
	private static FileWriter fileWriter;
	
	String propertiesPath = "/main/resources/application.properties";

//	private static ReportAnalysisConnection reportAnalysisConnection = new ReportAnalysisConnection();

	public ReportAnalysisConnection() {
		InputStream inputStream = getClass().getResourceAsStream(propertiesPath);
		Properties properties = new Properties();
		try {
			properties.load(inputStream);

			path = properties.getProperty("report.analysis.path");
			prefix = properties.getProperty("report.filename.prefix");
			filename = properties.getProperty("report.analysis.filename");
			
			//path는 empty체크하지 않음
			if(prefix.isEmpty() || filename.isEmpty()) {
				System.out.println("application.properties에 Analysis 결과 파일 경로 또는 파일명이 존재하지 않습니다.");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			fileWriter = new FileWriter(path+prefix+filename, true); //set append option
		
//			fileWriter.close(); //삭제해야 함..

		} catch (IOException e) {
			System.out.println("Analysis 결과 파일 생성에 실패하였습니다.");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("finalllllllllllllllllllllllllllize");
		fileWriter.close();

		super.finalize();
	}
}
