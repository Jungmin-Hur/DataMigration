package main.analysis;

import java.util.List;

import main.analysis.model.SourceInfo;
import main.analysis.service.AnalysisService;
import main.common.oracel2mysql.Oracle2MySqlSchemaMappingInfo;
import main.db.oracle.MyOracleConnection;
import main.report.ReportAnalysisFileConnection;
import main.report.ResultReportService;

public class Analysis {

	private static String SCHEMAINFO_FILE_NAME = "D:\\dev\\eclipse_workspace\\DataMigration\\src\\SchemaInfo.txt"; //TODO argument로 변경예정

	public static void main(String[] args) {
		
		setup();
//		test();
		ResultReportService.writeAnalysisReport("Analysis 시작 ------------------");
		
		AnalysisService analysis = new AnalysisService();
		
		boolean isConvertableColumnType = true;
		boolean isConvaertableColumnSize = true;
		boolean validationResult = true;
		
		//스키마정보, 데이터 정의 -> model로 데이터 converting
		List<SourceInfo> sourceInfoList = analysis.loadSchemaInfoFromFile(SCHEMAINFO_FILE_NAME);
		if(sourceInfoList != null) {
			//Source, Target Column Type 호환 체크
			isConvertableColumnType = analysis.isCompatibilityColumnType(sourceInfoList);

			//Source, Target Column 길이 호환 체크
			isConvaertableColumnSize = analysis.isCompatibilityColumnSize(sourceInfoList);

			//as-is 데이터 정의 (클랜징 데이터 존재하는지 여부)
			validationResult = analysis.findCleansingData(sourceInfoList);
		}
		

		if(!isConvertableColumnType || !isConvaertableColumnSize || !validationResult) {
			ResultReportService.writeAnalysisReport("Analysis 분석결과 문제가 있는 컬럼이 존재합니다. Report상에서 문제부분 확인하여 필요시 조치하세요.");
		}
			
		ResultReportService.writeAnalysisReport("Analysis 종료 !!! --------------");

		finalizeConnections();

	}
	
	public static void setup() {
		Oracle2MySqlSchemaMappingInfo loadSchema = new Oracle2MySqlSchemaMappingInfo(); //Load Schema Data
		MyOracleConnection oracleConnection = new MyOracleConnection(); //Load Oracle Connection
		ReportAnalysisFileConnection reportAnalysisConnection = new ReportAnalysisFileConnection();
	}
	
	public static void finalizeConnections() {
		ReportAnalysisFileConnection.closeFileStream();
	}
	
	public static void test() {
		//oracle connection test
		if(MyOracleConnection.getConnection() == null) {
			System.out.println("not connected");
		} else {
			System.out.println("connected");
		}
//		ResultReportService resultReportService1 = new ResultReportService();
//		ResultReportService resultReportService2 = new ResultReportService();
//		ResultReportService resultReportService3 = new ResultReportService();
//		ResultReportService resultReportService4 = new ResultReportService();
//		
//		resultReportService1.writeReport(MigrationProgressType.ANALYSIS, "1");
//		resultReportService2.writeReport(MigrationProgressType.PREMIGRATION, "2");
//		resultReportService1.writeReport(MigrationProgressType.MIGRATION, "3");
//		resultReportService3.writeReport(MigrationProgressType.ANALYSIS, "4");
//		resultReportService4.writeReport(MigrationProgressType.PREMIGRATION, "하하하");
//		resultReportService1.writeReport(MigrationProgressType.ANALYSIS, "5");
//		resultReportService2.writeReport(MigrationProgressType.PREMIGRATION, "호호호");

	}
}
