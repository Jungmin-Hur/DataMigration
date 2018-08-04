package main.analysis.service;

import java.util.List;

import main.analysis.model.SourceInfo;

public interface IAnalysisService {

	/**
	 * 파일 Read -> Model Converting
	 * @param filename
	 * @return List<SchemaInfo>
	 */
	public List<SourceInfo> loadSchemaInfoFromFile(String filename);
	
	/**
	 * Source, Target Column Type 호환 체크
	 * @param schemaInfoList
	 * @return
	 */
	public boolean isCompatibilityColumnType(List<SourceInfo> sourceInfoList);
	
	/**
	 * Source, Target Column 길이 호환 체크
	 * @param sourceInfoList
	 * @return
	 */
	public boolean isCompatibilityColumnSize(List<SourceInfo> sourceInfoList);
	
	/**
	 * AS-IS 데이터 정의 검증 (클랜징 데이터 존재 여부)
	 * 실데이터와 확인해야 함
	 * @return
	 */
	public boolean findCleansingData(List<SourceInfo> sourceInfoList);

}
