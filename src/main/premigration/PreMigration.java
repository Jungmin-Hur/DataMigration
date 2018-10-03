package main.premigration;

import java.util.List;

import main.analysis.model.SourceInfo;
import main.analysis.service.AnalysisService;
import main.common.bridgetable.Oracle2MySqlBridgeTableSchemaMappingInfo;
import main.common.oracel2mysql.Oracle2MySqlSchemaMappingInfo;
import main.db.mysql.MyMySQLConnection;
import main.db.oracle.MyOracleConnection;
import main.premigration.service.PreMigrationService;
import main.report.ReportAnalysisFileConnection;

public class PreMigration {

	private static String SCHEMAINFO_FILE_NAME = "D:\\dev\\eclipse_workspace\\DataMigration\\src\\SchemaInfo.txt"; //TODO argument로 변경

	public static void main(String[] args) {
		
		setup();
		
		dbConnectionTest();
		
		//Bridge Table 생성, source db에서 bridge table로 데이터 이동 (SourceSchemaInfo로 생성)
		//sourceInfoList로 Schema Mapping 정보 Load - 한 item당 한개의 Mapping 정보
		AnalysisService analysisService = new AnalysisService();
		List<SourceInfo> sourceInfoList = analysisService.loadSchemaInfoFromFile(SCHEMAINFO_FILE_NAME);

		PreMigrationService preMigrationService = new PreMigrationService();
		preMigrationService.migrationToBridgeTable(sourceInfoList);
		
		///////////////////////
		//TODO Target Table의 PK정보로 Bridge Table 조회시 중복 데이터 존재하는지 확인
		//TODO Target Table의 FK정보로 Bridge Table 내 Reference 데이터가 모두 존재하는지 확인
		//TODO Target Table의 nullable정보로 Bridge Table 데이터 확인

		finalizeConnections();
	}
	
	private static void setup() {
		Oracle2MySqlSchemaMappingInfo loadSchema = new Oracle2MySqlSchemaMappingInfo(); //Load Schema Data
		Oracle2MySqlBridgeTableSchemaMappingInfo loadBridgeTableMapping = new Oracle2MySqlBridgeTableSchemaMappingInfo(); //Load MySql Bridge table Mapping info
		MyOracleConnection oracleConnection = new MyOracleConnection(); //Load Oracle Connection
		MyMySQLConnection mysqlConnection = new MyMySQLConnection(); // Load MySQL Connection
		ReportAnalysisFileConnection reportAnalysisConnection = new ReportAnalysisFileConnection();
	}
	
	private static void finalizeConnections() {
		ReportAnalysisFileConnection.closeFileStream();
	}
	
	private static void dbConnectionTest() {
		//oracle connection test
		if(MyOracleConnection.getConnection() == null) {
			System.out.println("Oracle connection was not created.");
		} else {
			System.out.println("Oracle connection was created successfully.");
		}

		//mysql connection test
		if(MyMySQLConnection.getConnection() == null) {
			System.out.println("Mysql connection was not created.");
		} else {
			System.out.println("Mysql connection was created successfully.");
		}
		
		System.out.println("");
	}
	
	private static void loadDataTest() {
		Oracle2MySqlBridgeTableSchemaMappingInfo.getRepresentativeColumnType("VARCHAR(10");
		Oracle2MySqlBridgeTableSchemaMappingInfo.getRepresentativeColumnType("NUMBER");
//		System.out.println(MySqlBridgeTableSchemaMappingInfo.getRepresentativeColumnType("LON1GTEXT")); // negativetest
//		System.out.println(MySqlBridgeTableSchemaMappingInfo.getRepresentativeColumnType("")); // negativetest
//		System.out.println(MySqlBridgeTableSchemaMappingInfo.getRepresentativeColumnType(new String())); // negativetest
	}
}
