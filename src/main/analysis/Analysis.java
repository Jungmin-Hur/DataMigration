package main.analysis;

import java.util.List;

import main.analysis.model.SourceInfo;
import main.analysis.service.AnalysisService;
import main.common.oracel2mysql.Oracle2MySqlSchemaMappingInfo;
import main.db.oracle.MyOracleConnection;

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
		//TODO 여기서는 VARCHAR에서 길이체크는 하지 않아도 됨
		boolean isConvertable = analysis.isConvertableBetweenAsisAndTobe(sourceInfoList);

		//TODO Schema간 비교
		//AS-IS와 TO-BE가 호환되는지 여부 (ex. VARCHAR10은 VARCHAR20과 호환됨)
		//TODO Model추가되어야 함. 데이터 타입은 string, date, int 셋 중 하나임
		//데이터 타입에 따라서 schema 비교가 달라짐

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
