package main.analysis;

import java.util.List;

import main.analysis.model.SourceInfo;
import main.analysis.service.AnalysisService;
import main.common.oracel2mysql.Oracle2MySqlSchemaMappingInfo;
import main.db.oracle.MyOracleConnection;

public class Analysis {

	private static String SCHEMAINFO_FILE_NAME = "D:\\dev\\eclipse_workspace\\DataMigration\\src\\SchemaInfo.txt"; //TODO argument로 변경예정

	public static void main(String[] args) {
		setup();

		AnalysisService analysis = new AnalysisService();
		
		//스키마정보, 데이터 정의 -> model로 데이터 converting
		List<SourceInfo> sourceInfoList = analysis.loadSchemaInfoFromFile(SCHEMAINFO_FILE_NAME);
		
		//Source, Target Column Type 호환 체크
		boolean isConvertableColumnType = analysis.isCompatibilityColumnType(sourceInfoList);

		//Source, Target Column 길이 호환 체크
		boolean isConvaertableColumnSize = analysis.isCompatibilityColumnSize(sourceInfoList);

		//TODO 옵션처리
		//as-is 데이터 정의 (클랜징 데이터 존재하는지 여부)
		boolean validationResult = analysis.findCleansingData(sourceInfoList);

		//최종 사전 검증 결과 Report
		boolean analysisResult = analysis.anaysisReport(); //TODO Implementation
		
		System.out.println("Column Type Compatibility : " + isConvertableColumnType);
		System.out.println("Column Size Compatibility : " + isConvaertableColumnSize);
		System.out.println("validationResult : " + validationResult);
		System.out.println("analysisResult : " + analysisResult);
	}
	
	public static void setup() {
		Oracle2MySqlSchemaMappingInfo loadSchema = new Oracle2MySqlSchemaMappingInfo(); //Load Schema Data
		MyOracleConnection oracleConnection = new MyOracleConnection(); //Load Oracle Connection
	}
	
	public static void test() {
		//oracle connection test
		if(MyOracleConnection.getConnection() == null) {
			System.out.println("not connected");
		} else {
			System.out.println("connected");
		}

	}
}
