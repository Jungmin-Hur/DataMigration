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
import main.report.ResultReportService;

public class AnalysisService implements IAnalysisService {

	public List<SourceInfo> loadSchemaInfoFromFile(String filename) {
		
		ResultReportService.writeAnalysisReport("파일에서 Model로 변환 시작");
		
		SchemaFileBiz schemaFileBiz = new SchemaFileBiz();

		boolean isVaildFileExtensions = schemaFileBiz.isValidFileExtensions(filename);
		if(!isVaildFileExtensions) {
			return null;
		}
		
		boolean isValidFileContent = schemaFileBiz.isValidFileContent(filename);
		if(!isValidFileContent) {
			return null;
		}
		
		List<SourceInfo> sourceInfoList = loadSchemaInfo(filename);
		if(sourceInfoList == null) {
			ResultReportService.writeAnalysisReport("파일에서 Model로 변환 중지!! - 대상이 없거나 오류가 존재함", true);
		} else {
			ResultReportService.writeAnalysisReport("파일에서 Model로 변환 종료!!", true);
		}
		
		return sourceInfoList;
	}

	private List<SourceInfo> loadSchemaInfo(String filename) {
		ResultReportService.writeAnalysisReport("파일에서 Model로 loading 시작");
		
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
						ResultReportService.writeAnalysisReport("중복된 입력값이 존재합니다. mapKey : " + mapKey);
						
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
			ResultReportService.writeAnalysisReport("FileNotFoundException 발생!!");
			e.printStackTrace();
		} catch (IOException e) {
			ResultReportService.writeAnalysisReport("IOException 발생!!");
			e.printStackTrace();
		}
		
		if(isDuplicatedSchemaInfo) {
			return null;
		} else {
			ResultReportService.writeAnalysisReport("파일에서 Model로 loading 종료!! 총 item수 : " + sourceInfoMap.size());
		}

		return sourceInfoList;
	}
	
	public boolean isCompatibilityColumnType(List<SourceInfo> sourceInfoList) {
		ResultReportService.writeAnalysisReport("컬럼 Type 호환성 체크 시작");
		
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
				ResultReportService.writeAnalysisReport("지원되지 않는 컬럼 Type 존재, 오류가 아닐 수도 있으니 수작업 검토 필요 (" + sourceInfo.getColumnType() + "/" + sourceInfo.getTargetInfo().getColumnType() +")");
				result = false;
			}
		}
		
		if(result) {
			ResultReportService.writeAnalysisReport("컬럼 Type 호환성 체크 종료!! - 이상없음", true);
		} else {
			ResultReportService.writeAnalysisReport("컬럼 Type 호환성 체크 종료!! - 추가 확인 데이터 존재함", true);
		}
		
		return result; //전체 결과
	}
	
	public boolean isCompatibilityColumnSize(List<SourceInfo> sourceInfoList) {
		ResultReportService.writeAnalysisReport("컬럼 사이즈 호환 체크 시작");
		
		ValidationSchemaBiz validationSchemaBiz = new ValidationSchemaBiz();
		
		boolean result = true;

		for(SourceInfo sourceInfo : sourceInfoList) {
			// N/A 항목은 전환 가능 여부에 대해 판단하지 않음 
			if(Constants.NOT_APPLICABLE.equals(sourceInfo.getColumnSize()) 
					|| Constants.NOT_APPLICABLE.equals(sourceInfo.getTargetInfo().getColumnSize())) {
				continue;
			}

			boolean available = validationSchemaBiz.checkColumnSize(sourceInfo.getColumnSize(), sourceInfo.getTargetInfo().getColumnSize());
			if(!available) {
				ResultReportService.writeAnalysisReport("Target Column Size보다 Source Column Size보다 큼. 수작업 확인 필요 " + sourceInfo.getColumnSize() + "/" + sourceInfo.getTargetInfo().getColumnSize() +")");
				result = false;
			}
		}

		if(result) {
			ResultReportService.writeAnalysisReport("컬럼 사이즈 호환 체크 종료!! - 이상없음", true);
		} else {
			ResultReportService.writeAnalysisReport("컬럼 사이즈 호환 체크 종료!! - 추가 확인 데이터 존재함", true);
		}
		
		return result;
	}
	
	public boolean findCleansingData(List<SourceInfo> sourceInfoList){
		ResultReportService.writeAnalysisReport("Source에서 클랜징 대상 데이터 존재여부 체크 시작");
		
		String localResult = "";
		boolean isExistCleansingData = true;
		for(SourceInfo sourceInfo :  sourceInfoList) {
			
			String query = sourceInfo.getValidationQuery();
			if(!Constants.NOT_APPLICABLE.equals(query)) {
				try {
					localResult = MyOracleExecutor.selectOneQueryExecutor(query);
					if(!localResult.equals("1")) {
						ResultReportService.writeAnalysisReport("클랜징 대상 쿼리 발견 : " + query);
						isExistCleansingData = false;
					}
				} catch (SQLException e) {
					ResultReportService.writeAnalysisReport("클랜징 대상 쿼리 발견 중 query 오류 " + query);
					e.printStackTrace();
				}				
			}
		}
		
		if(isExistCleansingData) {
			ResultReportService.writeAnalysisReport("Source에서 클랜징 대상 데이터 존재여부 체크 종료!! - 이상없음", true);
		} else {
			ResultReportService.writeAnalysisReport("Source에서 클랜징 대상 데이터 존재여부 체크 종료!! - 추가 확인 데이터 존재함", true);
		}
		
		return isExistCleansingData;
	}
}
