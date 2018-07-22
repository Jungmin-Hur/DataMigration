package main.analysis.model;

import java.util.Date;

public class TargetInfo {
	private String id;
	private SourceInfo sourceInfo;
	private String tableName;
	private String columnName;
	private String columnType;
	private String validationQuery;
	private String mappingDefinition;
	private String mappingLimitation;
	private String mappingQuery; //TODO reserved. when mappingdefinition and mappinglimitation input, this value will be set.
	private Date createdDate;
	private Date updatedDate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SourceInfo getSourceInfo() {
		return sourceInfo;
	}
	public void setSourceInfo(SourceInfo sourceInfo) {
		this.sourceInfo = sourceInfo;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	public String getMappingDefinition() {
		return mappingDefinition;
	}
	public void setMappingDefinition(String mappingDefinition) {
		this.mappingDefinition = mappingDefinition;
	}
	public String getMappingLimitation() {
		return mappingLimitation;
	}
	public void setMappingLimitation(String mappingLimitation) {
		this.mappingLimitation = mappingLimitation;
	}
	public String getMappingQuery() {
		return mappingQuery;
	}
	public void setMappingQuery(String mappingQuery) {
		this.mappingQuery = mappingQuery;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
