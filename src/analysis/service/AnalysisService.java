package analysis.service;

import java.util.List;
import analysis.model.Migration;

public class AnalysisService {
	/**
	 * 파일 Read -> Model Converting
	 * @return List<Migration>
	 */
	public List<Migration> convertFileToMigrationModel() {
		return null;
	}
	
	/**
	 * as-is, to-be 스키마 검증
	 * 결과는 file write함
	 * @param migrations
	 * @return
	 */
	public boolean isConvertableBetweenAsisAndTobe(List<Migration> migrations) {
		return true;
	}
	
	/**
	 * AS-IS 데이터 정의 검증 (클랜징 데이터 존재 여부)
	 * 실데이터와 확인해야 함
	 * @return
	 */
	public boolean validationAsisDefinition(List<Migration> migrations){
		return true;
	}

	/**
	 * file read하여 stdout에 결과 보여주기
	 * @return
	 */
	public boolean anaysisReport(){
		return true;
	}
}
