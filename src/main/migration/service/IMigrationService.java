package main.migration.service;

import java.util.List;

import main.analysis.model.SourceInfo;
import main.migration.model.MigrationPlan;

public interface IMigrationService {
	
	/**
	 * MigrationPlan.txt -> MigrationPlan class에 Loading
	 * @param filename
	 * @return
	 */
	public List<MigrationPlan> loadMigrationPlanFromFile(String filename);
	
	/**
	 * Insert File 생성 (flyway용)
	 * @param sourceInfoList
	 */
	public void makeInsertQueryFile(List<SourceInfo> sourceInfoList);
}
