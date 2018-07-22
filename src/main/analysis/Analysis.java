package main.analysis;

import java.util.List;

import main.analysis.model.SourceInfo;
import main.analysis.service.AnalysisService;
import main.db.oracle.MyOracleConnection;
import main.oracel2mysql.Oracle2MySqlSchemaMappingInfo;

public class Analysis {

	private static String SCHEMAINFO_FILE_NAME = "D:\\ECLIPSE\\workspace\\DataMigration\\src\\SchemaInfo.Txt"; //TODO argument로 변경예정

	public static void main(String[] args) {
		setup();
		//connection test
//		if(MyOracleConnection.getConnection() == null) {
//			System.out.println("not connected");
//		} else {
//			System.out.println("connected");
//		}

		AnalysisService analysis = new AnalysisService();
		
		//스키마정보, 데이터 정의 -> model로 데이터 converting
		List<SourceInfo> sourceInfoList = analysis.loadSchemaInfoFromFile(SCHEMAINFO_FILE_NAME);
		
		//as-is, to-be 스키마 검증
		boolean isConvertable = analysis.isConvertableBetweenAsisAndTobe(sourceInfoList);

		//as-is 데이터 정의 (클랜징 데이터 존재하는지 여부)
		boolean validationResult = analysis.validationAsisDefinition(sourceInfoList);

		//최종 사전 검증 결과 Report
		boolean analysisResult = analysis.anaysisReport(); //TODO Implementation
		
		System.out.println("isConvertable : " + isConvertable);
		System.out.println("validationResult : " + validationResult);
		System.out.println("analysisResult : " + analysisResult);
	}
	
	public static void setup() {
		Oracle2MySqlSchemaMappingInfo loadSchema = new Oracle2MySqlSchemaMappingInfo(); //Load Schema Data
		MyOracleConnection oracleConnection = new MyOracleConnection(); //Load Oracle Connection
	}
}
