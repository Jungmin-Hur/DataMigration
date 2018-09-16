package main.premigration.service;

import java.util.List;

import main.analysis.model.SourceInfo;
import main.analysis.model.TargetInfo;

public interface IPreMigrationService {
	public void migrationToBridgeTable(List<SourceInfo> sourceInfoList);
	public void createBridgeTableSchema(List<TargetInfo> targetInfo);
}
