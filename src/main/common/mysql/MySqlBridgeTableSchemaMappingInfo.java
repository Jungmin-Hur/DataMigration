package main.common.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlBridgeTableSchemaMappingInfo {

	static Map<String, List<String>> mySqlBridgeTableMappingSchema = new HashMap<>();

	static String[][] mySqlBridgeTableRawData =
		{
			//Column : 대표column
			{"BIGINT","VARCHAR(65535)"}
			,{"BINARY","VARCHAR(65535)"}
			,{"CHAR","VARCHAR(65535)"}
			,{"CHARACTER","VARCHAR(65535)"}
			,{"DATETIME","VARCHAR(65535)"}
			,{"DEC","VARCHAR(65535)"}
			,{"DECIMAL","VARCHAR(65535)"}
			,{"DOUBLE","VARCHAR(65535)"}
			,{"DOUBLE PRECISION","VARCHAR(65535)"}
			,{"FLOAT","VARCHAR(65535)"}
			,{"INT","VARCHAR(65535)"}
			,{"LONGBLOB","LONGTEXT"}
			,{"LONGTEXT","LONGTEXT"}
			,{"NCHAR","VARCHAR(65535)"}
			,{"NCHAR VARYING","VARCHAR(65535)"}
			,{"NUMERIC","VARCHAR(65535)"}
			,{"NVARCHAR","VARCHAR(65535)"}
			,{"SMALLINT","VARCHAR(65535)"}
			,{"TINYINT","VARCHAR(65535)"}
			,{"VARBINARY","VARCHAR(65535)"}
			,{"VARCHAR","VARCHAR(65535)"}
		};
	
	public MySqlBridgeTableSchemaMappingInfo(){
		for(int i = 0; i<mySqlBridgeTableRawData.length; i++) {
			List<String> convertableList = new ArrayList<>();
			
			for(int j=1; j<mySqlBridgeTableRawData[i].length; j++) { //첫 데이터는 대표 데이터 이므로 list에 넣지 않음
				convertableList.add(mySqlBridgeTableRawData[i][j]);
			}
			mySqlBridgeTableMappingSchema.put(mySqlBridgeTableRawData[i][0], convertableList);
		}
	}
	
	/**
	 * 전환 가능한 columnType List구하기
	 * @param columnType
	 * @return
	 */
	public static String getRepresentativeColumnType(String columnType) {
		if(columnType.isEmpty()) {
			System.out.println("Input Column Type이 없습니다. see MySqlBridgeTableSchemaMappingInfo");
			return null;
		}
		
		List<String> columnTypes = mySqlBridgeTableMappingSchema.get(extractType(columnType));
		if(columnTypes == null || columnTypes.isEmpty()) {
			System.out.println("MySQL Bridge Table 에서 처리할 수 없는 Column Type입니다. see MySqlBridgeTableSchemaMappingInfo : " + columnType);
			return null;
		}
		
		if(columnTypes.size() != 1) {
			System.out.println("MySQL Bridge Table Mapping data 이상");
		}

		return columnTypes.get(0);
	}

	public static String extractType(String type) {
		return type.split("\\(")[0];
	}

//		public static void main(String arg[]) {
//		System.out.println("123");
//		String testStr = "varchar(12)";
//		System.out.println(testStr.split("\\(")[0]);
//	}
}
