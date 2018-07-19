package analysis;

import java.util.List;
import analysis.model.SchemaInfo;
import analysis.service.AnalysisService;
import utils.CommonUtil;

public class Analysis {
	
	private static String SCHEMAINFO_FILE_NAME = "D:\\ECLIPSE\\workspace\\DataMigration\\src\\SchemaInfo.txt"; //TODO argument로 변경예정

	public static void main(String[] args) {

		for(int i =0;i<10000;i++) {
			System.out.println(CommonUtil.generateUniqueId());
		}

		AnalysisService analysis = new AnalysisService();
		
		//스키마정보, 데이터 정의 -> model로 데이터 converting
		List<SchemaInfo> schemaInfoList = analysis.loadSchemaInfoFromFile(SCHEMAINFO_FILE_NAME);
		
		//as-is, to-be 스키마 검증
		boolean isConvertable = analysis.isConvertableBetweenAsisAndTobe(schemaInfoList);
		
		//as-is 데이터 정의 (클랜징 데이터 존재하는지 여부)
		boolean validationResult = analysis.validationAsisDefinition(schemaInfoList);

		//최종 사전 검증 결과 Report
		boolean analysisResult = analysis.anaysisReport();
		
		System.out.println("isConvertable : " + isConvertable);
		System.out.println("validationResult : " + validationResult);
		System.out.println("analysisResult : " + analysisResult);
	}
}
