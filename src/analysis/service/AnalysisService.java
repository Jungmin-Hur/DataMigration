package analysis.service;

import java.util.List;
import analysis.model.Migration;

public class AnalysisService {
	/**
	 * ���� Read -> Model Converting
	 * @return List<Migration>
	 */
	public List<Migration> convertFileToMigrationModel() {
		return null;
	}
	
	/**
	 * as-is, to-be ��Ű�� ����
	 * ����� file write��
	 * @param migrations
	 * @return
	 */
	public boolean isConvertableBetweenAsisAndTobe(List<Migration> migrations) {
		return true;
	}
	
	/**
	 * AS-IS ������ ���� ���� (Ŭ��¡ ������ ���� ����)
	 * �ǵ����Ϳ� Ȯ���ؾ� ��
	 * @return
	 */
	public boolean validationAsisDefinition(List<Migration> migrations){
		return true;
	}

	/**
	 * file read�Ͽ� stdout�� ��� �����ֱ�
	 * @return
	 */
	public boolean anaysisReport(){
		return true;
	}
}
