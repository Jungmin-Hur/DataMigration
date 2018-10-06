package main.migration.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import main.analysis.model.Constants;
import main.analysis.model.SourceInfo;
import main.common.utils.CommonUtil;
import main.migration.biz.MigrationBiz;
import main.migration.model.MigrationPlan;

public class MigrationService implements IMigrationService {

	@Override
	public List<MigrationPlan> loadMigrationPlanFromFile(String filename) {
		List<MigrationPlan> migrationPlanList = new ArrayList<>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line = br.readLine();
			while(line != null) {
				if(!line.startsWith(Constants.SHARP)) { // #로 시작하는 경우 읽지 않음(주석처리) 

					String str[] = line.split(Constants.DELIMINATOR);
					
					MigrationPlan migrationPlan = makeMigrationPlan(str);
					migrationPlanList.add(migrationPlan);
				}
				line = br.readLine();
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return migrationPlanList;
	}
	
	private MigrationPlan makeMigrationPlan(String data[]) {
		MigrationPlan migrationPlan = new MigrationPlan();
		migrationPlan.setId(CommonUtil.generateUniqueId());
		migrationPlan.setTableName(data[0]);
		migrationPlan.setAvailableSmpling(data[1]);
		migrationPlan.setSamplingYN(data[2]);
		if(!data[3].isEmpty()) {
			migrationPlan.setSamplingCondition(data[3]);
		}
		return migrationPlan;
	}
	
	public void makeInsertQueryFile(List<MigrationPlan> migrationPlanList, List<SourceInfo> sourceInfoList) {
		MigrationBiz migrationBiz = new MigrationBiz();
		
		for(MigrationPlan migrationPlan : migrationPlanList) {
			
			String tableName = migrationPlan.getTableName();
			
//			if(!tableName.equals("REAL_ESTATE_TRANSACTION")) continue;
			
			//#step1
			//작업용 데이터만 추출
			List<SourceInfo> wksourceInfoList = migrationBiz.extractSourceInfo(sourceInfoList, tableName);
			
			//Selectquery생성
			String selectQuery = migrationBiz.makeSelectQuery(wksourceInfoList);
			
			//Insertquery생성 & write file
			int insertQueryCount = migrationBiz.makeInsertQuery(wksourceInfoList, selectQuery);

			System.out.println(tableName + " : " + insertQueryCount + "row 스크립트가 생성되었습니다.");
			
			//#step2
			//Mapping Definition 작업 데이터만 추출
			List<SourceInfo> wkMappingLimitationSourceInfoList = migrationBiz.extractMappingLimitationSourceInfo(sourceInfoList, tableName);
			if(wkMappingLimitationSourceInfoList != null && wkMappingLimitationSourceInfoList.size() > 0) {
				//SELECT QUERY생성
				List<String> selectMappingLimitationQueryList = migrationBiz.makeMappingLimitationSelectQuery(wkMappingLimitationSourceInfoList);
				
				//INSERT (ON DUPLICATE KEY) QUERY 생성
				int index = 0;
				for(String query : selectMappingLimitationQueryList) {
					List<String> insertMappingLimitationQueryList = migrationBiz.makeMappingLimitationInsertQuery(wkMappingLimitationSourceInfoList, query);
					index++;
				}
				
				System.out.println(tableName + " : " + index + "row 스크립트가 Definition에 의해 생성되었습니다.");
			}
		}
	}
}
