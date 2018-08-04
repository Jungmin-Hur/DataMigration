package main.common.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MigrationProgressType {

	ANALYSIS("analysis","Analysis"),
	PREMIGRATION("premigration","Pre-Migration"),
	MIGRATION("migration","Migration");
	
	private String name;
	private String desc;
	
	MigrationProgressType(String name, String desc){
		this.name = name;
		this.desc = desc;
	}
	
	private static final Map<String, String> migrationProgressType = new HashMap<>();
	
	static {
		for(MigrationProgressType r: EnumSet.allOf(MigrationProgressType.class)) {
			migrationProgressType.put(r.name, r.desc);
		}
	}
	
	public static MigrationProgressType get(String name) {
		return MigrationProgressType.get(name);
	}
	
	public String getName() {
		return name;
	}
}
