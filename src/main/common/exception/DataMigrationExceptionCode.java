package main.common.exception;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DataMigrationExceptionCode {
	//Analysis
	ANALYSIS_NOT_EXIST_REFERENCE_DATA("NOT_EXIST_REFERENCE_DATA","참조키가 없습니다."),
	ANALYSIS_INVALID_DATA_TYPE("INVALID_DATA_TYPE","입력한 DATA 형식이 잘못되었습니다."),
	ANALYSIS_NOT_MATCH_DATA_TYPE("NOT_MATCH_DATA_TYPE","데이터 타입이 맞지 않습니다."),
	ANALYSIS_INVALID_FILE_FORMAT("INVALID_FILE_FORMAT","잘못된 파일 형식입니다."),
	ANALYSIS_INVALID_QUERY_TYPE("ANALYSIS_INVALID_QUERY_TYPE","허용되지 않는 쿼리 형식입니다."),
	ANALYSIS_DEPLICATED_INPUT("DEPLICATED_INPUT","중복된 입력입니다.");
	
	private String code;
	private String desc;

	DataMigrationExceptionCode(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	private static final Map<String, String> exceptionCodes = new HashMap<>();
	
	static {
		for(DataMigrationExceptionCode d: EnumSet.allOf(DataMigrationExceptionCode.class)) {
			exceptionCodes.put(d.code, d.desc);
		}
	}
	
	public static DataMigrationExceptionCode get(String code) {
		return DataMigrationExceptionCode.get(code);
	}
}
