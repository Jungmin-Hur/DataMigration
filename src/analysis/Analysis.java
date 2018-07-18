package analysis;

import java.util.List;

import analysis.model.Migration;
import analysis.service.AnalysisService;

public class Analysis {
	
	public static void main(String[] args) {
		AnalysisService analysis = new AnalysisService();
		
		//��Ű������, ������ ���� -> model�� ������ converting
		List<Migration> migrations = analysis.convertFileToMigrationModel();
		
		//error code define
		
		//as-is, to-be ��Ű�� ����
		boolean isConvertable = analysis.isConvertableBetweenAsisAndTobe(migrations);
		
		//as-is ������ ���� (Ŭ��¡ ������ �����ϴ��� ����)
		boolean validationResult = analysis.validationAsisDefinition(migrations);

		//���� ���� ���� ��� Report
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
