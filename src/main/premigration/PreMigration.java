package main.premigration;

import main.common.oracel2mysql.Oracle2MySqlSchemaMappingInfo;
import main.db.mysql.MyMySQLConnection;
import main.db.oracle.MyOracleConnection;

public class PreMigration {

	private static String SCHEMAINFO_FILE_NAME = "D:\\dev\\eclipse_workspace\\DataMigration\\src\\SchemaInfo.txt"; //TODO argument로 변경

	public static void main(String[] args) {
		
		setup();
		
		dbConnectionTest();
		
		//TODO Bridge Table 생성 (SourceSchemaInfo로 생성)
		
		//TODO Source Table -> Bridge Table로 데이터 이관
		
		//TODO Target Table Schema체크
		// Analysis에서 입력받은 TargetSchemaInfo가 실제 Target Table에 모두 존재하는지, Column Type이 동일한지 체크
		// 사용자 누락으로 입력하지 않은 Target Schema정보가 존재하는지 체크
		
		//TODO Target Table의 PK정보로 Bridge Table 조회시 중복 데이터 존재하는지 확인
		//TODO Target Table의 FK정보로 Bridge Table 내 Reference 데이터가 모두 존재하는지 확인
		//TODO Target Table의 nullable정보로 Bridge Table 데이터 확인

		
//		System.out.println(MyMySQLConnection.getHost());

//		ResultReportService.writeAnalysisReport("Analysis 시작 ------------------");
//		
//		AnalysisService analysis = new AnalysisService();
//		
//		boolean isConvertableColumnType = true;
//		boolean isConvaertableColumnSize = true;
//		boolean validationResult = true;
//		
//		//스키마정보, 데이터 정의 -> model로 데이터 converting
//		List<SourceInfo> sourceInfoList = analysis.loadSchemaInfoFromFile(SCHEMAINFO_FILE_NAME);
//		if(sourceInfoList != null) {
//			//Source, Target Column Type 호환 체크
//			isConvertableColumnType = analysis.isCompatibilityColumnType(sourceInfoList);
//
//			//Source, Target Column 길이 호환 체크
//			isConvaertableColumnSize = analysis.isCompatibilityColumnSize(sourceInfoList);
//
//			//as-is 데이터 정의 (클랜징 데이터 존재하는지 여부)
//			validationResult = analysis.findCleansingData(sourceInfoList);
//		}
//
//		if(!isConvertableColumnType || !isConvaertableColumnSize || !validationResult) {
//			ResultReportService.writeAnalysisReport("Analysis 분석결과 문제가 있는 데이터가 존재합니다. Report상에서 문제 부분 확인하여 필요시 조치하세요.");
//		}
//			
//		ResultReportService.writeAnalysisReport("Analysis 종료 !!! --------------");
//
//		finalizeConnections();

	}
	
	private static void setup() {
		Oracle2MySqlSchemaMappingInfo loadSchema = new Oracle2MySqlSchemaMappingInfo(); //Load Schema Data
		MyOracleConnection oracleConnection = new MyOracleConnection(); //Load Oracle Connection
		MyMySQLConnection mysqlConnection = new MyMySQLConnection(); // Load MySQL Connection
		//TODO 수정 필요
//		ReportAnalysisFileConnection reportAnalysisConnection = new ReportAnalysisFileConnection();
	}
	
	private static void finalizeConnections() {
		//TODO 수정 필요
//		ReportAnalysisFileConnection.closeFileStream();
	}
	
	public static void dbConnectionTest() {
		//oracle connection test
		if(MyOracleConnection.getConnection() == null) {
			System.out.println("not connected");
		} else {
			System.out.println("connected");
		}
		//mysql connection test
		if(MyMySQLConnection.getConnection() == null) {
			System.out.println("not connected");
		} else {
			System.out.println("connected");
		}
	}
}
