package analysis;

import java.util.List;

import analysis.model.Migration;
import analysis.service.AnalysisService;

public class Analysis {
	
	public static void main(String[] args) {
		AnalysisService analysis = new AnalysisService();
		
		//스키마정보, 데이터 정의 -> model로 데이터 converting
		List<Migration> migrations = analysis.convertFileToMigrationModel();
		
		//error code define
		
		//as-is, to-be 스키마 검증
		boolean isConvertable = analysis.isConvertableBetweenAsisAndTobe(migrations);
		
		//as-is 데이터 정의 (클랜징 데이터 존재하는지 여부)
		boolean validationResult = analysis.validationAsisDefinition(migrations);

		//최종 사전 검증 결과 Report
		boolean analysisResult = analysis.anaysisReport();
		
		System.out.println("isConvertable : " + isConvertable);
		System.out.println("validationResult : " + validationResult);
		System.out.println("analysisResult : " + analysisResult);
		
//		BigInteger b = new BigInteger("9");
////		System.out.println(b.multiply(b).multiply(b).multiply(b).multiply(b).multiply(b).multiply(b).multiply(b).multiply(b));
//		BigInteger b1 = b.multiply(b).multiply(b).multiply(b).multiply(b).multiply(b).multiply(b).multiply(b).multiply(b);
//		System.out.println(b1.multiply(b1).multiply(b1));
//		BigInteger b3 = b1.multiply(b1).multiply(b1);
//		System.out.println(b3.mod(new BigInteger("55")));
	}
}
