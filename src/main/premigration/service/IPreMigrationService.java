package main.premigration.service;

import java.util.List;

import main.analysis.model.SourceInfo;

public interface IPreMigrationService {
	public void migrationToBridgeTable(List<SourceInfo> sourceInfoList);
}
