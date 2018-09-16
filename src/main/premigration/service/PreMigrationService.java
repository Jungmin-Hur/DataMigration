package main.premigration.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.analysis.model.SourceInfo;
import main.analysis.model.TargetInfo;
import main.common.mysql.MySqlBridgeTableSchemaMappingInfo;
import main.db.mysql.MyMySQLExecutor;

public class PreMigrationService implements IPreMigrationService {

	@Override
	public void migrationToBridgeTable(List<SourceInfo> sourceInfoList) {
		//targetInfoList에 loading
		List<TargetInfo> targetInfoList = makeTargetInfoList(sourceInfoList);
		createBridgeTableSchema(targetInfoList);
		
		//기존에 작업하던 테이블은 자동으로 backup하고 삭제함
		//CREATE TABLE TEST1 AS SELECT * FROM TEST (백업)
		//DELETE TABLE TEST (삭제)
		//
	}
	
	private List<TargetInfo> makeTargetInfoList(List<SourceInfo> sourceInfoList){
		List<TargetInfo> targetInfoList = new ArrayList<TargetInfo>();
		Map<String, String> infoMap = new HashMap<>();
		for(SourceInfo sourceInfo : sourceInfoList) {
			String mapKey = sourceInfo.getTableName().concat("|").concat(sourceInfo.getColumnName());

			if(infoMap.get(mapKey) == null) {
				TargetInfo targetInfo = new TargetInfo();
				targetInfo.setTableName(sourceInfo.getTableName());
				targetInfo.setColumnName(sourceInfo.getColumnName());
				targetInfo.setColumnType(MySqlBridgeTableSchemaMappingInfo.extractType(sourceInfo.getColumnType()));
				targetInfoList.add(targetInfo);
				
				infoMap.put(mapKey, "1");
			}
		}

		return targetInfoList;
	}

	@Override
	public void createBridgeTableSchema(List<TargetInfo> targetInfo) {
		//TODO 동일 테이블명이 존재하는지 확인
		try {
			isAreadyExistTable(targetInfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isAreadyExistTable(List<TargetInfo> targetInfoList) throws SQLException {
		Map<String, String> tableMap = new HashMap<>();
		String result;

		for(TargetInfo targetInfo : targetInfoList) {
			tableMap.put(targetInfo.getTableName(), "1");
		}
		Iterator<String> keys = tableMap.keySet().iterator();
		while(keys.hasNext()) {
			String tableName = keys.next();
			result = MyMySQLExecutor.selectTableInfo(tableName);
			if(result != null && !result.isEmpty()) {
				System.out.println("bridgeTable 이 존재합니다. 기존 Bridge Table을 백업한 후 삭제한 후 다시 진행하세요. 테이블명 : " + result);
				return false;
			}
		}

		return true;
	}
}
