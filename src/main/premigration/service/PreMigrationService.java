package main.premigration.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.analysis.model.Constants;
import main.analysis.model.SourceInfo;
import main.analysis.model.TargetInfo;
import main.common.bridgetable.Oracle2MySqlBridgeTableSchemaMappingInfo;
import main.db.mysql.MyMySQLExecutor;
import main.db.oracle.MyOracleExecutor;

public class PreMigrationService implements IPreMigrationService {

	@Override
	public void migrationToBridgeTable(List<SourceInfo> sourceInfoList) {
		
		List<String> sourceTableNameList = getSourceTableList(sourceInfoList);
		List<String> targetTableNameList = getTargetTableList(sourceInfoList);

		if(isExistSourceTable(sourceTableNameList)) {

			boolean availableStartingPreMigration = true;
			
			//Analysis에서 입력받은 TargetSchemaInfo가 실제 Target Table에 모두 존재하는지, Column Type이 동일한지 체크
			if(!checkTargetTableSchema(targetTableNameList, sourceInfoList)) {
				availableStartingPreMigration = false;
				System.out.println("SchemaInfo.txt의 Target Table 정보와 실제 Target Table Schema 정보가 일치하지 않습니다.");
			} else {
				System.out.println("SchemaInfo.txt의 Target Table 정보와 실제 Target Table Schema 정보 일치 검증 완료");
			}
			
			//Mapping Definition check
			if(!checkTargetMappingDefinition(sourceInfoList)) {
				availableStartingPreMigration = false;
				System.out.println("SchemaInfo.txt의 Target Table Mapping Definition이 올바르지 않습니다.");
			} else {
				System.out.println("SchemaInfo.txt의 Target Table Mapping Definition 검증 완료");
			}
			
			//Mapping Limitation check
			if(!checkTargetMappingLimitation(sourceInfoList)) {
				availableStartingPreMigration = false;
				System.out.println("SchemaInfo.txt의 Target Table Mapping Limitation이 올바르지 않습니다.");
			} else {
				System.out.println("SchemaInfo.txt의 Target Table Mapping Limitation 검증 완료");
			}
			
			//TODO Target Table검증 (기존에 존재하는 데이터가 정상적인지 체크) : 이 부분이 필요한지 여부 추가 검토 필요
			
			if(availableStartingPreMigration) {
				//bridge table이 이미 존재하면, 기존 bridge table을 backup하고 삭제
				backupTable(sourceTableNameList, sourceInfoList);

				//bridge table schema 생성
				createBridgeTableSchema(sourceTableNameList, sourceInfoList);

				//Source Table (oracle) -> Bridge Table (mysql) 로 데이터 이동
				moveSourceToBridgeTable(sourceTableNameList, sourceInfoList);
			}
		} else {
			System.out.println("source db에 존재하지 않는 테이블이 존재합니다.");
		}
	}
	
	
	private boolean checkTargetMappingLimitation (List<SourceInfo> sourceInfoList) {
		
		boolean isExistCleansingData = true;
		
		for(SourceInfo sourceInfo :  sourceInfoList) {
			TargetInfo targetInfo = sourceInfo.getTargetInfo();
			
			if(!Constants.NOT_APPLICABLE.equals(targetInfo.getMappingLimitation())) {

				StringBuffer mappingLimitation = new StringBuffer();
				mappingLimitation.append("SELECT");
				mappingLimitation.append(" ");
				mappingLimitation.append(targetInfo.getMappingDefinition());
				mappingLimitation.append(" ");
				mappingLimitation.append("FROM");
				mappingLimitation.append(" ");
				mappingLimitation.append(sourceInfo.getTableName());
				mappingLimitation.append(" ");
				mappingLimitation.append(sourceInfo.getTableName()); // Alias
				mappingLimitation.append(" ");
				mappingLimitation.append("WHERE");
				mappingLimitation.append(" ");
				mappingLimitation.append(targetInfo.getMappingLimitation());

				boolean result = MyOracleExecutor.queryExecuter(mappingLimitation.toString());

				if(result == false) {
					System.out.println(mappingLimitation.toString());
					System.out.println("Target Mapping Definition에 오류가 존재합니다. Source테이블명:" + sourceInfo.getTableName() + ",Source컬럼명:" + sourceInfo.getColumnName() + ",Target테이블명:" + targetInfo.getTableName() + ",Target컬럼명:" + targetInfo.getColumnName());
					isExistCleansingData = false;
				}
			}
		}
		
		return isExistCleansingData;
	}
	
	private boolean checkTargetMappingDefinition (List<SourceInfo> sourceInfoList) {
		
		boolean isExistCleansingData = true;
		
		for(SourceInfo sourceInfo :  sourceInfoList) {
			TargetInfo targetInfo = sourceInfo.getTargetInfo();
			
			if(!Constants.NOT_APPLICABLE.equals(targetInfo.getMappingDefinition())) {

				StringBuffer mappingDefinition = new StringBuffer();
				mappingDefinition.append("SELECT");
				mappingDefinition.append(" (");
				mappingDefinition.append(targetInfo.getMappingDefinition());
				mappingDefinition.append(") ");
				mappingDefinition.append("FROM");
				mappingDefinition.append(" ");
				mappingDefinition.append(sourceInfo.getTableName());
				mappingDefinition.append(" ");
				mappingDefinition.append(sourceInfo.getTableName()); // Alias

				//System.out.println(mappingDefinition.toString());
				boolean result = MyOracleExecutor.queryExecuter(mappingDefinition.toString());
				if(result == false) {
					System.out.println(mappingDefinition.toString());
					System.out.println("Target Mapping Definition에 오류가 존재합니다. Source테이블명:" + sourceInfo.getTableName() + ",Source컬럼명:" + sourceInfo.getColumnName() + ",Target테이블명:" + targetInfo.getTableName() + ",Target컬럼명:" + targetInfo.getColumnName());
					isExistCleansingData = false;
				}
			}
		}
		
		return isExistCleansingData;
	}
	
	private void moveSourceToBridgeTable(List<String> tableNameList, List<SourceInfo> sourceInfoList) {
		for(String tableName : tableNameList) {
			List<String> columnNames = new ArrayList<>();
			for(SourceInfo sourceInfo : sourceInfoList) {
				if(!sourceInfo.getColumnName().equals("N/A")) {
					if(tableName.equals(sourceInfo.getTableName())) {
						columnNames.add(sourceInfo.getColumnName());
					}
				}
			}

			MyOracleExecutor.moveDataOracle2MySQL(tableName, columnNames);
			
			System.out.println("Bridge Table에 데이터 이동이 완료되었습니다. 테이블명 : " + tableName);
		}
	}
	
	private boolean isExistSourceTable(List<String> tableNameList) {
		boolean exist = true;
		for(String tableName : tableNameList) {
			String result = "";
			//테이블이 존재하는지 확인. 기존에 테이블이 존재하고 있으면 result가 0보다 큼
			result = MyOracleExecutor.selectTableName(tableName);
			
			if(result.equals("0")) {
				System.out.println("[Source DB] " + tableName + " 테이블이 존재하지 않습니다.");
				exist = false;
			} else {
				System.out.println("[Source DB] " + tableName + " 테이블이 정상적으로 존재합니다.");
			}
		}
		return exist;
	}

	private void backupTable(List<String> tableNameList, List<SourceInfo> sourceInfoList) {
		for(String tableName : tableNameList) {
			String result = "";
			tableName = "BT_" + tableName;
			
			//테이블이 존재하는지 확인. 기존에 테이블이 존재하고 있으면 result가 0보다 큼
			result = MyMySQLExecutor.selectTableName(tableName);
		
			//테이블 존재하면 기존꺼 백업(BACK_~)하고 drop(BT_)
			//테이블 존재하지 않으면 skip
			if(result.equalsIgnoreCase(tableName)) { //bridge table이 존재함
				MyMySQLExecutor.backupTable(tableName);
				MyMySQLExecutor.dropTable(tableName);

				System.out.println("[Bridge Table] 테이블이 존재해서 기존 테이블을 백업하고 Drop 했습니다. Drop 테이블명 : " + tableName);
				
			} else {
				System.out.println("[Bridge Table] " + tableName + "테이블은 존재하지 않아서 backup하지 않습니다.");
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

	private List<String> getTargetTableList(List<SourceInfo> sourceInfoList){
		Map<String, String> tableMap = new HashMap<>();
		for(SourceInfo sourceInfo : sourceInfoList) {
			if(!sourceInfo.getTargetInfo().getTableName().equals("N/A")) {
				tableMap.put(sourceInfo.getTargetInfo().getTableName(), "1");
			}
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
			if(MyMySQLExecutor.queryExecuter(schemaQuery.toString())) {
				System.out.println("Bridge Table이 생성되었습니다. 테이블명 : " + tableName);
			}
		}
	}

	private boolean checkTargetTableSchema(List<String> tableNameList, List<SourceInfo> sourceInfoList) {
		boolean result = true;
		for(String tableName : tableNameList) {
			List<String> columnNames = new ArrayList<>();
			for(SourceInfo sourceInfo : sourceInfoList) {
				TargetInfo targetInfo = sourceInfo.getTargetInfo();
				if(!targetInfo.getColumnName().equals("N/A")) {
					if(tableName.equals(targetInfo.getTableName())) {
						columnNames.add(targetInfo.getColumnName());
					}
				}
			}

			//target db에 없는 targetInfo가 존재하는지 여부
			if(!isExistTargetColumn(tableName, sourceInfoList)) {
				result = false;
			}
		}
		
		return result;
	}
	
	private boolean isExistTargetColumn(String tableName, List<SourceInfo> sourceInfoList) {
		int notExistCount = 0;
		List<String> targetTableColumnNames = MyMySQLExecutor.selectColumnNames(tableName);
		for(String columnName : targetTableColumnNames) {
			boolean find = false;
			for(SourceInfo sourceInfo : sourceInfoList) {
				TargetInfo targetInfo = sourceInfo.getTargetInfo();
				if(targetInfo.getTableName().equals(tableName)) {
					if(targetInfo.getColumnName().equals(columnName)) {
						find = true;
					}
				}
			}
			if(find == false) {
				System.out.println("SchemaInfo.txt의 Target 컬럼에 실제 존재하지 않는 Target 테이블 컬럼이 있습니다. 테이블명 : " + tableName + ", 컬럼명 : " + columnName);
				notExistCount ++;
			}
		}
		
		System.out.println("테이블명 : " + tableName + ", SchemaInfo.txt에는 존재하고 실제 존재하지 않는 컬럼 건수 : "  + notExistCount);
		
		return notExistCount > 0 ? false : true;
	}
}
