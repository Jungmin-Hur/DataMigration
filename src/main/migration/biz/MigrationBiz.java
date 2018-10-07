package main.migration.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.analysis.model.SourceInfo;
import main.analysis.model.TargetInfo;
import main.db.mysql.MyMySQLExecutor;
import main.query.ResultQueryService;

public class MigrationBiz {
	
	private static String SPACE = " ";
	private static String COMMA = ",";
	private static String SEMICOLON = ";";

	/**
	 * sorceInfoList에서 SourceTable명 뽑기
	 * @param sourceInfoList
	 * @return
	 */
	public List<String> getSourceTableList(List<SourceInfo> sourceInfoList){
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

	/**
	 * sourceInfoList에서 TargetTable명 뽑기
	 * @param sourceInfoList
	 * @return
	 */
	public List<String> getTargetTableList(List<SourceInfo> sourceInfoList){
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
	
	public List<SourceInfo> extractSourceInfo(List<SourceInfo> sourceInfoList, String tableName){
		List<SourceInfo> extractResult = new ArrayList<>();
		for(SourceInfo sourceInfo : sourceInfoList) {
			if(sourceInfo.getTableName().equals(tableName)) {
				if(!sourceInfo.getTargetInfo().getTableName().equals("N/A") &&
						sourceInfo.getTargetInfo().getMappingLimitation().equals("N/A")	) {
					extractResult.add(sourceInfo);
				}
			}
		}
		return extractResult;
	}
	
	public List<SourceInfo> extractMappingLimitationSourceInfo(List<SourceInfo> sourceInfoList, String tableName){
		List<SourceInfo> extractResult = new ArrayList<>();
		for(SourceInfo sourceInfo : sourceInfoList) {
			if(sourceInfo.getTableName().equals(tableName)) {
				if(!sourceInfo.getTargetInfo().getTableName().equals("N/A") &&
						!sourceInfo.getTargetInfo().getMappingLimitation().equals("N/A")	) {
					extractResult.add(sourceInfo);
				}
			}
		}
		return extractResult;
	}
	
	public String makeSelectQuery(List<SourceInfo> sourceInfoList, String samplingCondition) {
		if(sourceInfoList == null || sourceInfoList.isEmpty()) {
			return null;
		}

		StringBuffer query = new StringBuffer();
		query.append("SELECT");
		query.append(SPACE);
		
		int index = 0;
		for(SourceInfo sourceInfo : sourceInfoList) {
			TargetInfo targetInfo = sourceInfo.getTargetInfo();
			if(index > 0) {
				query.append(COMMA);	
			}
			query.append("(");
			query.append(targetInfo.getMappingDefinition());
			query.append(")");
			index++;
		}
		query.append(SPACE);
		query.append("FROM");
		query.append(SPACE);
		query.append("BT_" + sourceInfoList.get(0).getTableName()); //bridge table에서 찾아야함
		query.append(SPACE);
		query.append("WHERE MIGRATE_YN='N'");
		if(samplingCondition != null && !samplingCondition.isEmpty()) {
			query.append(SPACE);
			query.append("AND");
			query.append(SPACE);
			query.append(samplingCondition);
		}
		query.append(SEMICOLON);
		
		if(index == 0) {
			return null;
		}

		return query.toString();
	}

	public List<String> makeMappingLimitationSelectQuery(List<SourceInfo> sourceInfoList, String samplingCondition) {
		if(sourceInfoList == null || sourceInfoList.isEmpty()) {
			return null;
		}

		int index = 0;
		List<String> result = new ArrayList<>(); 
		for(SourceInfo sourceInfo : sourceInfoList) {
			StringBuffer query = new StringBuffer();
			query.append("SELECT");
			query.append(SPACE);
			TargetInfo targetInfo = sourceInfo.getTargetInfo();
			query.append(targetInfo.getMappingDefinition().split(",")[0]);
			query.append(COMMA);	
			query.append(targetInfo.getMappingDefinition().split(",")[1]);
			query.append(SPACE);
			query.append("FROM");
			query.append(SPACE);
			query.append("BT_" + sourceInfoList.get(0).getTableName()); //bridge table에서 찾아야함
			query.append(SPACE);
			query.append("WHERE MIGRATE_YN='N'");
			if(samplingCondition != null && !samplingCondition.isEmpty()) {
				query.append(SPACE);
				query.append("AND");
				query.append(SPACE);
				query.append(samplingCondition);
			}
			query.append(SEMICOLON);
			
			result.add(query.toString());
			index++;
		}
		
		if(index == 0) {
			return null;
		}

		return result;
	}
	
	public int makeInsertQuery(List<SourceInfo> sourceInfoList, String selectQuery) {
		if(selectQuery == null) {
			return 0;
		}
		if(sourceInfoList == null) {
			return 0;
		}

		//DB에서 데이터 조회
		List<Map<String, String>> insertQueryDataList = MyMySQLExecutor.makeInsertQuery(selectQuery, sourceInfoList.size());
		int totalQueryCount = insertQueryDataList.size();
		List<String> queryList = new ArrayList<>();

		//컬럼 STRING 추출
		//조회한 데이터로 QUERY만들기
		for(int i=0; i < totalQueryCount; i++) {
			StringBuffer query = new StringBuffer();
			query.append("REPLACE INTO");
			query.append(SPACE);
			query.append(sourceInfoList.get(0).getTargetInfo().getTableName());
			query.append(SPACE);
			query.append("(");
			query.append(extractTargetColumns(sourceInfoList));
			query.append(")");
			query.append(SPACE);
			query.append("VALUES");
			query.append(SPACE);
			query.append("(");
			query.append(extractDataMapToString(insertQueryDataList.get(i)));
			query.append(")");
			query.append(SEMICOLON);
			
			ResultQueryService.writeResultQuery(query.toString()); 
			queryList.add(query.toString());
			
			if((i+1)%100 == 0) {
				System.out.println(sourceInfoList.get(0).getTargetInfo().getTableName() + " : " + (i+1) + "번째 쿼리 생성중..");
			}
		}
		System.out.println(sourceInfoList.get(0).getTargetInfo().getTableName() + " : " + queryList.size() + "개 쿼리가 생성되었습니다.");
		
		return queryList.size();
	}
	
	public boolean updateMigrateYnToN(List<SourceInfo> sourceInfoList, String samplingCondition) {
		if(sourceInfoList == null || sourceInfoList.isEmpty()) {
			return false;
		}

		StringBuffer query = new StringBuffer();
		query.append("UPDATE");
		query.append(SPACE);
		query.append("BT_" + sourceInfoList.get(0).getTableName());
		query.append(SPACE);
		query.append("SET MIGRATE_YN='Y'");
		if(samplingCondition != null && !samplingCondition.isEmpty()) {
			query.append(SPACE);
			query.append("WHERE");
			query.append(SPACE);
			query.append(samplingCondition);
		}
		query.append(SEMICOLON);

		if(MyMySQLExecutor.queryExecuter(query.toString())) {
			System.out.println("BT_" + sourceInfoList.get(0).getTableName() + " : 작업 대상 데이터의 MigrationYN 필드가 Y로 업데이트 되었습니다.");
			return true;
		}
		
		return false;
	}
	
	public List<String> makeMappingLimitationInsertQuery(List<SourceInfo> sourceInfoList, String selectQuery) {
		if(selectQuery == null) {
			return null;
		}
		if(sourceInfoList == null) {
			return null;
		}

		//DB에서 데이터 조회
		List<Map<String, String>> insertQueryDataList = MyMySQLExecutor.makeInsertQuery(selectQuery, 2); //두개씩만
		int totalQueryCount = insertQueryDataList.size();
		List<String> queryList = new ArrayList<>();

		//컬럼 STRING 추출
		//조회한 데이터로 QUERY만들기
		for(int i=0; i < totalQueryCount; i++) {
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO");
			query.append(SPACE);
			query.append(sourceInfoList.get(0).getTargetInfo().getTableName());
			query.append(SPACE);
			query.append("(");
			query.append(sourceInfoList.get(0).getTargetInfo().getMappingLimitation());
			query.append(")");
			query.append(SPACE);
			query.append("VALUES");
			query.append(SPACE);
			query.append("(");
			query.append(extractDataMapToString(insertQueryDataList.get(i)));
			query.append(")");
			query.append(SPACE);
			query.append("ON DUPLICATE KEY UPDATE");
			query.append(SPACE);
			query.append(sourceInfoList.get(0).getTargetInfo().getMappingLimitation().split(",")[0]); //키가 중복되었을 때 업데이트할 컬럼
			query.append("="); //키가 중복되었을 때 업데이트할 컬럼
			query.append("VALUES("+ sourceInfoList.get(0).getTargetInfo().getMappingLimitation().split(",")[0] + ")"); //키가 중복되었을 때 업데이트할 컬럼
			query.append(SPACE);
			query.append(SEMICOLON);
			
			ResultQueryService.writeResultQuery(query.toString()); 
			queryList.add(query.toString());

			if((i+1)%100 == 0) {
				System.out.println(sourceInfoList.get(0).getTargetInfo().getTableName() + " : " + (i+1) + "번째 Mapping Limitation 쿼리 생성중..");
			}
		}
		
		System.out.println(sourceInfoList.get(0).getTargetInfo().getTableName() + " : " + queryList.size() + "개 Mapping Limitation 쿼리가 생성되었습니다.");
		
		return queryList;
	}

	private String extractTargetColumns(List<SourceInfo> sourceInfoList) {
		StringBuffer columnsString = new StringBuffer();
		int index = 0;
		for(SourceInfo sourceInfo : sourceInfoList) {
			if(index > 0) {
				columnsString.append(COMMA);	
			}
			columnsString.append(sourceInfo.getTargetInfo().getColumnName());
			index++;
		}
		return columnsString.toString();
	}

	private String extractDataMapToString(Map<String, String> dataMap) {
		StringBuffer columnsString = new StringBuffer();
		int size = dataMap.size();
		
		for(int i=0; i < size; i++) {
			if(i > 0) {
				columnsString.append(COMMA);	
			}
			
			String data = dataMap.get(String.valueOf(i+1));
			columnsString.append("'");
			if(data == null || data.equals("null")) {
				data = "";
			}
			columnsString.append(data);
			columnsString.append("'");
		}
		return columnsString.toString();
	}
}
