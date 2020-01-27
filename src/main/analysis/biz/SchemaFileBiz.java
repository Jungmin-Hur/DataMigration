package main.analysis.biz;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import main.analysis.model.Constants;
import main.report.ResultReportService;

public class SchemaFileBiz {
	/**
	 * 정상 파일 여부
	 * @param filename
	 * @return
	 */
	public boolean isValidFileExtensions(String filename) {
		ResultReportService.writeAnalysisReport("정상 파일 체크 시작. 파일명 : " + filename);
		
		boolean isValidFile = isValidFileExtension(filename.toUpperCase());
		
		if(!isValidFile) {
			ResultReportService.writeAnalysisReport("지원하지 않는 확장자입니다. (.txt만 허용)");
		} else {
			ResultReportService.writeAnalysisReport("정상 파일 체크 종료!! - 이상없음");			
		}
		
		return isValidFile;
	}
	
	/**
	 * 파일 확장자 체크 (.txt파일만 정상임)
	 * @param filename
	 * @return
	 */
	private boolean isValidFileExtension(String filename) {
		return filename.endsWith(Constants.DOT_TXT);
	}
	
	/**
	 * 파일 구분자 정상 존재 여부 (tab이 구분자이고, split한 결과가 한 row에 10개씩이어야 함)
	 * 단, #으로 시작하는 경우 체크하지 않음
	 * @param filename
	 * @return
	 */
	public boolean isValidFileContent(String filename) {
		ResultReportService.writeAnalysisReport("파일 content 체크 시작");

		boolean isValidFileContent = true;
		int lineNum=1;
		int invalidCount=0;
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line = br.readLine();

			while(line != null) {
				if(!line.startsWith(Constants.SHARP)) { // #로 시작하는 경우 읽지 않음(주석처리) 
					String str[] = line.split(Constants.DELIMINATOR);
					if(!isValidColumnNumPerRow(str)) {
						ResultReportService.writeAnalysisReport(lineNum + "번째 열 : 입력컬럼수가 잘못되었습니다. (12개여야 함)");
						isValidFileContent = false;
						invalidCount++;
					}
				}
				lineNum++;
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!isValidFileContent) {
			ResultReportService.writeAnalysisReport("파일 content가 잘못된 row수 : " + invalidCount);
		}else {
			ResultReportService.writeAnalysisReport("파일 content 체크 완료!! - 이상없음");
		}

		return isValidFileContent;
	}
	
	/**
	 * 한줄에 12개 column이 입력되어야 함
	 * @param data
	 * @return
	 */
	private boolean isValidColumnNumPerRow(String data[]) {
		if(data.length != Constants.SCHEMA_FILE_COLUMN_NUM_PER_ROW) {
		// 빈줄은 data.length가 1로 체크됨. 빈줄 인경우 false가 리턴되게 됨
			return false;
		}
		return true;
	}
}
