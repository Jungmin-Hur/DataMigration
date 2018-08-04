package main.common.oracel2mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.analysis.model.Constants;

public class Oracle2MySqlSchemaMappingInfo {
	
	static Map<String, List<String>> oracle2MySqlSchema = new HashMap<>();

	static String[][] oracle2MySqlRawData =
		{
				{"VARCHAR", "VARCHAR", "BLOB", "CLOB"}
				,{"VARCHAR1", "VARCHAR", "BLOB", "CLOB"}
				,{"VARCHAR2", "VARCHAR", "BLOB", "CLOB"}
				,{"VARCHAR3", "VARCHAR", "BLOB", "CLOB"}
				,{"BIGINT", "INT", "INTEAGER"}
				,{"BIGINT1", "INT", "INTEAGER"}
				,{"BIGINT2", "INT", "INTEAGER"}
				,{"BIGINT3", "INT", "INTEAGER"}
				,{"BIGINT4", "INT", "INTEAGER"}
				
				,{"DATE", "DATE"}
		};
	
	public Oracle2MySqlSchemaMappingInfo(){
		for(int i = 0; i<oracle2MySqlRawData.length; i++) {
			List<String> convertableList = new ArrayList<>();
			
			for(int j=1; j<oracle2MySqlRawData[i].length; j++) { //첫 데이터는 대표 데이터 이므로 list에 넣지 않음
				convertableList.add(oracle2MySqlRawData[i][j]);
			}
			oracle2MySqlSchema.put(oracle2MySqlRawData[i][0], convertableList);
		}
	}
	
	/**
	 * 전환 가능한 columnType List구하기
	 * @param columnType
	 * @return
	 */
	public static List<String> availableConvertColumns(String columnType) {
		return oracle2MySqlSchema.get(columnType);
	}
	
	/**
	 * 전환 가능한 columnType List Print
	 * @param columnType
	 * @return
	 */
	public static void availableConvertColumnsPrint(String columnType) {
		List<String> list =  oracle2MySqlSchema.get(columnType);
		System.out.print(columnType + Constants.DELIMINATOR);
		for(String item : list) {
			System.out.print(item + Constants.COMMA);
		}
		System.out.println("");
	}
}
