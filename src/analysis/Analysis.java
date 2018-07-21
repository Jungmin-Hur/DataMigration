package analysis;

import java.util.Map;

import analysis.model.SourceInfo;
import analysis.service.AnalysisService;
import oracel2mysql.Oracle2MySqlSchema;

public class Analysis {
	//TODO 전체적으로 Exception만들어서 throw처리해야함
	private static String SCHEMAINFO_FILE_NAME = "D:\\ECLIPSE\\workspace\\DataMigration\\src\\SchemaInfo.txt"; //TODO argument로 변경예정

	public static void main(String[] args) {
//		for(int i =0;i<10000;i++) {
//			System.out.println(CommonUtil.generateUniqueId());
//		}
		setup();

		AnalysisService analysis = new AnalysisService();
		
		//스키마정보, 데이터 정의 -> model로 데이터 converting
		Map<String, SourceInfo> sourceInfoMap = analysis.loadSchemaInfoFromFile(SCHEMAINFO_FILE_NAME);
//		System.out.println(sourceInfoMap.values());
//		System.out.println(CommonUtil.prettyPrintMap(sourceInfoMap.get(key)));
		
//		//as-is, to-be 스키마 검증
//		boolean isConvertable = analysis.isConvertableBetweenAsisAndTobe(sourceInfoList);
//		
//		//as-is 데이터 정의 (클랜징 데이터 존재하는지 여부)
//		boolean validationResult = analysis.validationAsisDefinition(sourceInfoList);
//
//		//최종 사전 검증 결과 Report
//		boolean analysisResult = analysis.anaysisReport();
//		
//		System.out.println("isConvertable : " + isConvertable);
//		System.out.println("validationResult : " + validationResult);
//		System.out.println("analysisResult : " + analysisResult);
	}
	
	public static void setup() {
		Oracle2MySqlSchema loadSchema = new Oracle2MySqlSchema(); //Load Schema Data
	}
}
