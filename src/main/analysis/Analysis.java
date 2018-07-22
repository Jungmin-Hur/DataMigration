package main.analysis;

import java.util.Map;

import main.analysis.model.SourceInfo;
import main.analysis.service.AnalysisService;
import main.oracel2mysql.Oracle2MySqlSchemaMappingInfo;

public class Analysis {
	//TODO 전체적으로 Exception만들어서 throw처리해야함
	//TODO 로그 파일 떨어지는 부분, 일단 파일로 만들고 나중에 LOG4J
	//TODO maven 고민해볼 것 (log4j같은거)
	//TODO oracle2mysqlschema데이터 property로 빼는 것 검토해볼 것
	//TODO Report는 html하는건 어떨지
	
	private static String SCHEMAINFO_FILE_NAME = "D:\\ECLIPSE\\workspace\\DataMigration\\src\\SchemaInfo.Txt"; //TODO argument로 변경예정

	public static void main(String[] args) {

		setup();

		AnalysisService analysis = new AnalysisService();
		
		//스키마정보, 데이터 정의 -> model로 데이터 converting
		Map<String, SourceInfo> sourceInfoMap = analysis.loadSchemaInfoFromFile(SCHEMAINFO_FILE_NAME);
		
		//as-is, to-be 스키마 검증
		boolean isConvertable = analysis.isConvertableBetweenAsisAndTobe(sourceInfoMap);

		//as-is 데이터 정의 (클랜징 데이터 존재하는지 여부)
//		boolean validationResult = analysis.validationAsisDefinition(sourceInfoList);

		//최종 사전 검증 결과 Report
//		boolean analysisResult = analysis.anaysisReport();
		
		System.out.println("isConvertable : " + isConvertable);
//		System.out.println("validationResult : " + validationResult);
//		System.out.println("analysisResult : " + analysisResult);
	}
	
	public static void setup() {
		Oracle2MySqlSchemaMappingInfo loadSchema = new Oracle2MySqlSchemaMappingInfo(); //Load Schema Data
	}
}
