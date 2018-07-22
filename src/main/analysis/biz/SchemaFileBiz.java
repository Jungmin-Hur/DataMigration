package main.analysis.biz;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import main.analysis.model.Constants;

public class SchemaFileBiz {
	/**
	 * 파일 확장자 체크 (.txt만 가능)
	 * @param filename
	 * @return
	 */
	public boolean isValidFileExtensions(String filename) {
		System.out.println("Start Checking File Extension. Filename : " + filename);
		boolean isValidFile = isValidFileExtension(filename.toUpperCase());
		if(!isValidFile) {
			System.out.println("Not supported File extension (Only .txt is allowed) ");
			System.out.println("Inputted filename : " + filename);
			
		}else {
			System.out.println("Finish Checking File Extension. : Vaild File Extension!!");
		}
		return isValidFile;
	}
	
	/**
	 * 정상 파일 여부 (.txt파일만 정상임)
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
		System.out.println("Start Checking File Contents. Filename : " + filename);
		
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
						System.out.println("Line Num : " + lineNum); //잘못된 모든 row를 찍기 위해서 멈추지 않음
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
			System.out.println(invalidCount + "Row(s) is(are) invalid. ");
			
		}else {
			System.out.println("Finish Checking File Contents. : Vaild File Contents !!");
		}
		return isValidFileContent;
	}
	
	/**
	 * 한줄에 10개 column이 입력되어야 함
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
