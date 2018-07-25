package main.analysis.biz;

import java.util.List;

import main.common.oracel2mysql.Oracle2MySqlSchemaMappingInfo;

public class ValidationSchemaBiz {
	/**
	 * 스키마 전환 가능 여부
	 * @param sourceType
	 * @param targetType
	 * @return 스키마 전환 가능 여부
	 */
	public boolean checkColumnType(String sourceType, String targetType) {
		List<String> availableList = Oracle2MySqlSchemaMappingInfo.availableConvertColumns(sourceType);

		boolean available = false;
		if(availableList.contains(targetType)) {
			available = true;
		}
		return available;
	}
	
	/**
	 * Column Size 전환 가능 여부
	 * targetSize가 더 큰 경우 수작업으로 확인 필요 함
	 * @param sourceSize
	 * @param targetSize
	 * @return
	 */
	public boolean checkColumnSize(String sourceSize, String targetSize) {
		int iSourceSize = Integer.valueOf(sourceSize);
		int iTargetSize = Integer.valueOf(targetSize);

		boolean available = false;
		if(iSourceSize >= iTargetSize) {
			available = true;
		}
		return available;
	}
}
