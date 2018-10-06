package main.query;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResultQueryFileConnection {

	private static String path;
	private static String filename;
	private static String prefix;
//	private static FileWriter fileWriter;
	private static FileOutputStream output;
	
	String propertiesPath = "/main/resources/application.properties";
	
//	private static ReportAnalysisConnection reportAnalysisConnection = new ReportAnalysisConnection();

	public ResultQueryFileConnection() {
		InputStream inputStream = getClass().getResourceAsStream(propertiesPath);
		Properties properties = new Properties();
		try {
			properties.load(inputStream);

			path = properties.getProperty("result.query.path");
			prefix = properties.getProperty("result.filename.prefix");
			filename = properties.getProperty("result.query.filename");
			
			//path는 empty체크하지 않음
			if(prefix.isEmpty() || filename.isEmpty()) {
				ResultQueryService.writeResultQuery("application.properties에 report.filename.prefix, report.analysis.filename가 비어있어서 결과 파일 생성에 실패했습니다.");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
//			fileWriter = new FileWriter(path+prefix+filename, true); //set append option
			output = new FileOutputStream(path+prefix+filename, true);
		} catch (IOException e) {
			ResultQueryService.writeResultQuery("Analysis 결과 파일 생성에 실패하였습니다.");
			e.printStackTrace();
		}
	}
	
//	synchronized public static FileWriter getFileWriter() {
////		return fileWriter;
//		return output;
//	}

	synchronized public static FileOutputStream getFileOutputStream() {
//		return fileWriter;
		return output;
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
	
//	public static void closeFileStream() {
//		try {
//			if(fileWriter != null) {
//				fileWriter.close();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Override
//	protected void finalize() throws Throwable {
//		fileWriter.close();
//		super.finalize();
//	}
}
