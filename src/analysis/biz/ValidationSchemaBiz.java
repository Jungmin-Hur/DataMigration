package analysis.biz;

import java.util.List;

import oracel2mysql.Oracle2MySqlSchemaMappingInfo;

public class ValidationSchemaBiz {
	/**
	 * 스키마 전환 가능 여부
	 * @param sourceType
	 * @param targetType
	 * @return 스키마 전환 가능 여부
	 */
	public boolean isAvailableConverting(String sourceType, String targetType) {
		List<String> availableList = Oracle2MySqlSchemaMappingInfo.availableConvertColumns(sourceType);
		boolean available = false;
		if(availableList.contains(targetType)) {
			available = true;
		}
		return available;
	}
}
