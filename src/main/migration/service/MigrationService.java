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
import main.migration.model.MigrationPlan;
import main.report.ResultReportService;

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

	public void makeInsertQueryFile(List<SourceInfo> sourceInfoList) {
		//TODO Insert 대상 select query 만들기
		
		//TODO Insert Query 만들기
		
		//TODO Write File
	}
}
