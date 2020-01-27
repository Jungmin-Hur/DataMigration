package main.analysis.model;

import java.util.Date;

public class SourceInfo {
	private String id;
	private TargetInfo targetInfo;
	private String tableName;
	private String columnName;
	private String columnType;
	private String columnSize; //숫자 타입인 경우 0,0 형태로 입력될 수 있음
	private String validationQuery;
	private Date createdDate;
	private Date updatedDate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TargetInfo getTargetInfo() {
		return targetInfo;
	}
	public void setTargetInfo(TargetInfo targetInfo) {
		this.targetInfo = targetInfo;
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
	public String getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
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
