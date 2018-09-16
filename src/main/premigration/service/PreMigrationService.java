package main.premigration.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.analysis.model.SourceInfo;
import main.common.mysql.MySqlBridgeTableSchemaMappingInfo;
import main.db.mysql.MyMySQLExecutor;

public class PreMigrationService implements IPreMigrationService {

	@Override
	public void migrationToBridgeTable(List<SourceInfo> sourceInfoList) {
		List<String> tableNameList = getSourceTableList(sourceInfoList);
		
		//bridge table이 이미 존재하면, 기존 bridge table을 backup하고 삭제
		backupTable(tableNameList, sourceInfoList);

		createBridgeTableSchema(tableNameList, sourceInfoList);
		
		//bridge table에 데이터 이동
	}

	private void backupTable(List<String> tableNameList, List<SourceInfo> sourceInfoList) {
		for(String tableName : tableNameList) {
			String result = "";
			try {
				result = MyMySQLExecutor.selectTableInfo(tableName);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			if(result != null && !result.isEmpty()) {
				try {
					MyMySQLExecutor.backupTable(tableName);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
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
			StringBuffer schemaQuery = new StringBuffer();
			schemaQuery.append("CREATE TABLE " + tableName + "(");
			int index = 0;
			for(SourceInfo sourceInfo : sourceInfoList) {
				if(index > 0) {
					schemaQuery.append(",");
				}
				if(tableName.equals(sourceInfo.getTableName())) {
					schemaQuery.append(sourceInfo.getColumnName() + " " + MySqlBridgeTableSchemaMappingInfo.getRepresentativeColumnType(sourceInfo.getColumnType()));
				}
				index ++;
			}
			schemaQuery.append(")");
			
			MyMySQLExecutor.queryExecuter(schemaQuery.toString());
		}
	}

}
