package analysis;

import java.util.List;

import analysis.model.SchemaInfo;
import analysis.service.AnalysisService;

public class Analysis {
	
	private static String SCHEMAINFO_FILE_NAME = "D:\\ECLIPSE\\workspace\\DataMigration\\src\\SchemaInfo.txt"; //TODO argument�� ���濹��

	public static void main(String[] args) {
		
		AnalysisService analysis = new AnalysisService();
		
		//��Ű������, ������ ���� -> model�� ������ converting
		List<SchemaInfo> schemaInfoList = analysis.loadSchemaInfoFromFile(SCHEMAINFO_FILE_NAME);
		
		//as-is, to-be ��Ű�� ����
		boolean isConvertable = analysis.isConvertableBetweenAsisAndTobe(schemaInfoList);
		
		//as-is ������ ���� (Ŭ��¡ ������ �����ϴ��� ����)
		boolean validationResult = analysis.validationAsisDefinition(schemaInfoList);

		//���� ���� ���� ��� Report
		boolean analysisResult = analysis.anaysisReport();
		
		System.out.println("isConvertable : " + isConvertable);
		System.out.println("validationResult : " + validationResult);
		System.out.println("analysisResult : " + analysisResult);
	}
}
