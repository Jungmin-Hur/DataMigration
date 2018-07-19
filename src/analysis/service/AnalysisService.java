package analysis.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import analysis.model.SchemaInfo;

public class AnalysisService implements IAnalysisService {
	
	public List<SchemaInfo> loadSchemaInfoFromFile(String filename) {
		System.out.println("loadSchemaInfoFromFile started!!");

		List<SchemaInfo> schemaInfoList = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			int lineCount = 0;
			String line = br.readLine();
			
			while(line != null) {
				String str[] = line.split("\t");
				System.out.println(str[0]);
				
				List<SchemaInfo> list = makeSchemaInfo("");
				schemaInfoList.addAll(list);
				lineCount++;
				line = br.readLine();
			}
			br.close();
			
			System.out.println("Line Count : " + lineCount);
			System.out.println("SchemaInfoList's size : " + schemaInfoList.size());
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//TODO 입력받은 스키마 정보가 진짜 존재하는 스키마 정보인지 확인하는 로직 추가 필요

		System.out.println(schemaInfoList.size() + "schemaInfoList are loaded.");
		System.out.println("loadSchemaInfoFromFile finished!!");
		
		return schemaInfoList;
	}
	
	private boolean checkFile() {
		return true;
	}
	
	private List<SchemaInfo> makeSchemaInfo(String data){
		//TODO
		//split data
		//generate key 2개 : UniqueIdUtil 써서...
		//mapping schemainfo 2개 model (두 객체간 연계 key값도 세팅하기)
		return null;
	}
	
	public boolean isConvertableBetweenAsisAndTobe(List<SchemaInfo> schemaInfoList) {
		return true;
	}
	
	public boolean validationAsisDefinition(List<SchemaInfo> schemaInfoList){
		return true;
	}
	
	public boolean anaysisReport(){
		return true;
	}
}
