package main.analysis.service;

import java.util.List;
import java.util.Map;

import main.analysis.model.SourceInfo;

public interface IAnalysisService {

	/**
	 * 파일 Read -> Model Converting
	 * @param filename
	 * @return List<SchemaInfo>
	 */
	public Map<String,SourceInfo> loadSchemaInfoFromFile(String filename);
	
	/**
	 * as-is, to-be 스키마 검증
	 * 결과는 file write함
	 * @param schemaInfoList
	 * @return
	 */
	public boolean isConvertableBetweenAsisAndTobe(Map<String, SourceInfo> sourceInfoMap);
	
	/**
	 * AS-IS 데이터 정의 검증 (클랜징 데이터 존재 여부)
	 * 실데이터와 확인해야 함
	 * @return
	 */
	public boolean validationAsisDefinition(List<SourceInfo> SourceInfoList);

	/**
	 * file read하여 stdout에 결과 보여주기
	 * @return
	 */
	public boolean anaysisReport();
}
