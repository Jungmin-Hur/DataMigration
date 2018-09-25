package main.common.bridgetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Oracle2MySqlBridgeTableSchemaMappingInfo {

	static Map<String, List<String>> oracle2MySqlBridgeTableMappingSchema = new HashMap<>();

	static String[][] oracle2MySqlBridgeTableRawData =
		{
			//Column : 대표column
			{"BFILE","VARCHAR(1000)"}
			,{"BINARY_FLOAT","VARCHAR(1000)"}
			,{"BINARY_DOUBLE","VARCHAR(1000)"}
			,{"BLOB","LONGTEXT"}
			,{"CHAR","VARCHAR(1000)"}
			,{"CHARACTER","VARCHAR(1000)"}
			,{"CLOB","LONGTEXT"}
			,{"DATE","VARCHAR(1000)"}
			,{"DECIMAL","VARCHAR(1000)"}
			,{"DEC","VARCHAR(1000)"}
			,{"DOUBLE PRECISION","VARCHAR(1000)"}
			,{"FLOAT","VARCHAR(1000)"}
			,{"INTEGER","VARCHAR(1000)"}
			,{"INT","VARCHAR(1000)"}
			,{"LONG","VARCHAR(1000)"}
			,{"LONG RAW","VARCHAR(1000)"}
			,{"NCHAR","VARCHAR(1000)"}
			,{"NCHAR ","VARCHAR(1000)"}
			,{"VARYING","VARCHAR(1000)"}
			,{"NCLOB","VARCHAR(1000)"}
			,{"NUMBER","VARCHAR(1000)"}
			,{"NUMERIC","VARCHAR(1000)"}
			,{"NVARCHAR2","VARCHAR(1000)"}
			,{"RAW(n)","VARCHAR(1000)"}
			,{"REAL","VARCHAR(1000)"}
			,{"ROWID","VARCHAR(1000)"}
			,{"SMALLINT","VARCHAR(1000)"}
			,{"TIMESTAMP","VARCHAR(1000)"}
			,{"UROWID","VARCHAR(1000)"}
			,{"VARCHAR","VARCHAR(1000)"}
			,{"VARCHAR2","VARCHAR(1000)"}
			,{"XMLTYPE","VARCHAR(1000)"}
//			{"BIGINT","VARCHAR(1000)"}
//			,{"BINARY","VARCHAR(1000)"}
//			,{"CHAR","VARCHAR(1000)"}
//			,{"CHARACTER","VARCHAR(1000)"}
//			,{"DATETIME","VARCHAR(1000)"}
//			,{"DEC","VARCHAR(1000)"}
//			,{"DECIMAL","VARCHAR(1000)"}
//			,{"DOUBLE","VARCHAR(1000)"}
//			,{"DOUBLE PRECISION","VARCHAR(1000)"}
//			,{"FLOAT","VARCHAR(1000)"}
//			,{"INT","VARCHAR(1000)"}
//			,{"LONGBLOB","LONGTEXT"}
//			,{"LONGTEXT","LONGTEXT"}
//			,{"NCHAR","VARCHAR(1000)"}
//			,{"NCHAR VARYING","VARCHAR(1000)"}
//			,{"NUMERIC","VARCHAR(1000)"}
//			,{"NVARCHAR","VARCHAR(1000)"}
//			,{"SMALLINT","VARCHAR(1000)"}
//			,{"TINYINT","VARCHAR(1000)"}
//			,{"VARBINARY","VARCHAR(1000)"}
//			,{"VARCHAR","VARCHAR(1000)"}
		};
	
	public Oracle2MySqlBridgeTableSchemaMappingInfo(){
		for(int i = 0; i<oracle2MySqlBridgeTableRawData.length; i++) {
			List<String> convertableList = new ArrayList<>();
			
			for(int j=1; j<oracle2MySqlBridgeTableRawData[i].length; j++) { //첫 데이터는 대표 데이터 이므로 list에 넣지 않음
				convertableList.add(oracle2MySqlBridgeTableRawData[i][j]);
			}
			oracle2MySqlBridgeTableMappingSchema.put(oracle2MySqlBridgeTableRawData[i][0], convertableList);
		}
	}
	
	/**
	 * 전환 가능한 columnType List구하기
	 * @param columnType
	 * @return
	 */
	public static String getRepresentativeColumnType(String columnType) {
		if(columnType.isEmpty()) {
			System.out.println("Input Column Type이 없습니다. see Oracle2MySqlBridgeTableSchemaMappingInfo");
			return null;
		}
		List<String> columnTypes = oracle2MySqlBridgeTableMappingSchema.get(extractType(columnType));
		if(columnTypes == null || columnTypes.isEmpty()) {
			System.out.println("MySQL Bridge Table 에서 처리할 수 없는 Column Type입니다. see MySqlBridgeTableSchemaMappingInfo : " + columnType + " " + extractType(columnType));
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
