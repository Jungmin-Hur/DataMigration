package main.premigration.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.analysis.model.SourceInfo;
import main.common.bridgetable.Oracle2MySqlBridgeTableSchemaMappingInfo;
import main.db.mysql.MyMySQLExecutor;
import main.db.oracle.MyOracleExecutor;

public class PreMigrationService implements IPreMigrationService {

	@Override
	public void migrationToBridgeTable(List<SourceInfo> sourceInfoList) {
		List<String> tableNameList = getSourceTableList(sourceInfoList);
		
		//bridge table이 이미 존재하면, 기존 bridge table을 backup하고 삭제
		backupTable(tableNameList, sourceInfoList);

		createBridgeTableSchema(tableNameList, sourceInfoList);

		//TODO Source Table -> Bridge Table로 데이터 이동
		moveSourceToBridgeTable(tableNameList, sourceInfoList);

		//TODO Target Table Schema체크
		// Analysis에서 입력받은 TargetSchemaInfo가 실제 Target Table에 모두 존재하는지, Column Type이 동일한지 체크
		// 사용자 누락으로 입력하지 않은 Target Schema정보가 존재하는지 체크
	}
	
	private void moveSourceToBridgeTable(List<String> tableNameList, List<SourceInfo> sourceInfoList) {
		//x라인씩...
		//batch처리에 대해서 oracle쪽, mysql쪽 실험결과
		//일단 한개 읽어서 한개로..
		//select from source
		//insert to target

		for(String tableName : tableNameList) {
			List<String> columnNames = new ArrayList<>();
			for(SourceInfo sourceInfo : sourceInfoList) {
				if(!sourceInfo.getColumnName().equals("N/A")) {
					if(tableName.equals(sourceInfo.getTableName())) {
						columnNames.add(sourceInfo.getColumnName());
					}
				}
			}
//			try {
//				System.out.println("1AAAA");
////				System.out.println(MyOracleExecutor.selectOneQueryExecutor("SELECT LOT_INFO_ID FROM LOT_INFO WHERE LOT_INFO_ID = '1168010100106210018023369'" )); //TODO 삭제
//				System.out.println("BBB");
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			MyOracleExecutor.moveDataOracle2MySQL(tableName, columnNames);
			
			System.out.println("Bridge Table에 데이터 이동이 완료되었습니다. 테이블명 : " + tableName);
			break; //TODO 삭제
		}

	}

	private void backupTable(List<String> tableNameList, List<SourceInfo> sourceInfoList) {
		for(String tableName : tableNameList) {
			String result = "";
			tableName = "BT_" + tableName;
			
			//테이블이 존재하는지 확인. 기존에 테이블이 존재하고 있으면 result가 0보다 큼
			try {
				result = MyMySQLExecutor.selectTableInfo(tableName);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		
			//테이블 존재하면 기존꺼 백업(BACK_~)하고 drop(BT~)
			//테이블 존재하지 않으면 할일 없음
			if(!result.equals("0")) {
				try {
					MyMySQLExecutor.backupTable(tableName);
					MyMySQLExecutor.dropTable(tableName);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("테이블이 존재해서 기존 테이블을 백업하고 Drop 했습니다. Drop 테이블명 : " + tableName);
			}
		}
	}
	
	private List<String> getSourceTableList(List<SourceInfo> sourceInfoList){
		Map<String, String> tableMap = new HashMap<>();
		for(SourceInfo sourceInfo : sourceInfoList) {
			tableMap.put(sourceInfo.getTableName(), "1");
		}
		
		List<String> tableNameList = new ArrayList<>();
		Iterator<String> keys = tableMap.keySet().iterator();
		while(keys.hasNext()) {
			tableNameList.add(keys.next());
		}
		return tableNameList;
	}
	
	private void createBridgeTableSchema(List<String> tableNameList, List<SourceInfo> sourceInfoList) {
		for(String tableName : tableNameList) {
			tableName = "BT_" + tableName;
			StringBuffer schemaQuery = new StringBuffer();
			schemaQuery.append("CREATE TABLE " + tableName + "( MIGRATE_YN VARCHAR(1) "); // Default 컬럼

			for(SourceInfo sourceInfo : sourceInfoList) {
				if(!sourceInfo.getColumnName().equals("N/A")) {
					if(tableName.equals("BT_" + sourceInfo.getTableName())) {
						schemaQuery.append(",");
						schemaQuery.append(sourceInfo.getColumnName() + " " + Oracle2MySqlBridgeTableSchemaMappingInfo.getRepresentativeColumnType(sourceInfo.getColumnType()));
					}
				}
			}
			schemaQuery.append(")");
			MyMySQLExecutor.queryExecuter(schemaQuery.toString());
			System.out.println("Bridge Table이 생성되었습니다. 테이블명 : " + tableName);
		}
	}

}
