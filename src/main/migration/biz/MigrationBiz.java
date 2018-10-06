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
	
	public String makeSelectQuery(List<SourceInfo> sourceInfoList) {
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
		
		if(index == 0) {
			return null;
		}

		return query.toString();
	}

	public List<String> makeInsertQuery(List<SourceInfo> sourceInfoList, String selectQuery) {
		if(selectQuery == null) {
			return null;
		}
		if(sourceInfoList == null) {
			return null;
		}

		//DB에서 데이터 조회
		List<Map<String, String>> insertQueryDataList = MyMySQLExecutor.makeInsertQuery(selectQuery, sourceInfoList.size());
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
			query.append(extractTargetColumns(sourceInfoList));
			query.append(")");
			query.append(SPACE);
			query.append("VALUES");
			query.append(SPACE);
			query.append("(");
			query.append(extractDataMapToString(insertQueryDataList.get(i)));
			query.append(")");
			
			ResultQueryService.writeResultQuery(query.toString()); 
			queryList.add(query.toString());
		}
		
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
		int index = 0;
		int size = dataMap.size();
		
		for(int i=0; i < size; i++) {
			if(i > 0) {
				columnsString.append(COMMA);	
			}
			
			String data = dataMap.get(String.valueOf(i+1));
			columnsString.append("'");
			columnsString.append(data);
			columnsString.append("'");
		}
		return columnsString.toString();
	}
}
