package main.analysis.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.analysis.biz.ConvertSchemaBiz;
import main.analysis.biz.SchemaFileBiz;
import main.analysis.biz.ValidationSchemaBiz;
import main.analysis.model.Constants;
import main.analysis.model.SourceInfo;
import main.db.oracle.MyOracleExecutor;

public class AnalysisService implements IAnalysisService {

	public List<SourceInfo> loadSchemaInfoFromFile(String filename) {
		SchemaFileBiz schemaFileBiz = new SchemaFileBiz();

		boolean isVaildFileExtensions = schemaFileBiz.isValidFileExtensions(filename);
		if(!isVaildFileExtensions) {
			return null;
		}
		
		boolean isValidFileContent = schemaFileBiz.isValidFileContent(filename);
		if(!isValidFileContent) {
			return null;
		}

		return loadSchemaInfo(filename);
	}

	private List<SourceInfo> loadSchemaInfo(String filename) {
		System.out.println("Start Loading SchemaInfo From File!!");

		List<SourceInfo> sourceInfoList = new ArrayList<>();
		Map<String, SourceInfo> sourceInfoMap = new HashMap<>(); //This valiable for checking the duplicated input

		ConvertSchemaBiz convertSchemaBiz = new ConvertSchemaBiz();

		boolean isDuplicatedSchemaInfo = false;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line = br.readLine();
			
			while(line != null) {
				if(!line.startsWith(Constants.SHARP)) { // #로 시작하는 경우 읽지 않음(주석처리) 

					String str[] = line.split(Constants.DELIMINATOR);
					SourceInfo sourceInfo = convertSchemaBiz.makeSchemaInfo(str);

					String mapKey = convertSchemaBiz.makeMapKey(sourceInfo);
					isDuplicatedSchemaInfo = convertSchemaBiz.isDuplicatedSchemaInfo(sourceInfoMap, mapKey);
					if(isDuplicatedSchemaInfo) {
						break;
					} else {
						sourceInfoList.add(sourceInfo);
						sourceInfoMap.put(mapKey, sourceInfo);
					}
				}
				line = br.readLine();
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(isDuplicatedSchemaInfo) {
			System.out.println("Exist Duplicated Input(s). Loading SchemaInfo From File is failed.");
			return null;
		} else {
			System.out.println("Finish Loading SchemaInfo From File!! " + sourceInfoMap.size() + " items are loaded.");
		}

		return sourceInfoList;
	}
	
	public boolean isCompatibilityColumnType(List<SourceInfo> sourceInfoList) {
		ValidationSchemaBiz validationSchemaBiz = new ValidationSchemaBiz();
		
		boolean result = true;

		for(SourceInfo sourceInfo : sourceInfoList) {
			// N/A 항목은 전환 가능 여부에 대해 판단하지 않음 
			if(Constants.NOT_APPLICABLE.equals(sourceInfo.getColumnType()) 
					|| Constants.NOT_APPLICABLE.equals(sourceInfo.getTargetInfo().getColumnType())) {
				continue;
			}

			boolean available = validationSchemaBiz.checkColumnType(sourceInfo.getColumnType(), sourceInfo.getTargetInfo().getColumnType());
			if(!available) {
				System.out.println("[Error] it doesnot support column type.(" + sourceInfo.getColumnType() + "/" + sourceInfo.getTargetInfo().getColumnType() +")"); //TODO Need more logging
				result = false;
			}
		}
		return result; //전체 결과
	}
	
	public boolean isCompatibilityColumnSize(List<SourceInfo> sourceInfoList) {
		ValidationSchemaBiz validationSchemaBiz = new ValidationSchemaBiz();
		
		boolean result = true;

		for(SourceInfo sourceInfo : sourceInfoList) {
			// N/A 항목은 전환 가능 여부에 대해 판단하지 않음 
			if(Constants.NOT_APPLICABLE.equals(sourceInfo.getColumnSize()) 
					|| Constants.NOT_APPLICABLE.equals(sourceInfo.getTargetInfo().getColumnSize())) {
				continue;
			}

			//TODO 0,0 타입도 확인이 필요함
			boolean available = validationSchemaBiz.checkColumnSize(sourceInfo.getColumnSize(), sourceInfo.getTargetInfo().getColumnSize());
			if(!available) {
				System.out.println("[Warning] Target Column Size보다 Source Column Size보다 큽니다. " + sourceInfo.getColumnSize() + "/" + sourceInfo.getTargetInfo().getColumnSize() +")");
				result = false;
			}
		}
		return result; //전체 결과
	}
	
	public boolean findCleansingData(List<SourceInfo> sourceInfoList){
		System.out.println("Start Find Cleansing Data in Source !! ----------");
		
		String localResult = "";
		int invalidCount = 0;
		boolean isExistCleansingData = true;
		for(SourceInfo sourceInfo :  sourceInfoList) {
			
			String query = sourceInfo.getValidationQuery();
			if(!Constants.NOT_APPLICABLE.equals(query)) {
				try {
					localResult = MyOracleExecutor.selectOneQueryExecutor(query);
					if(!localResult.equals("1")) {
						System.out.println("[Error] Invalid Definition data. Query : " + query );
						isExistCleansingData = false;
						invalidCount++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}
		
		if(isExistCleansingData) {
			System.out.println("Finish Find Cleansing Data in Source !! There is no cleansing data.----------");
		} else {
			System.out.println("Finish Find Cleansing Data in Source : " + invalidCount + " invalid data.----------");
		}
		
		return isExistCleansingData;
	}
	
	public boolean anaysisReport(){
		return true;
	}
}
