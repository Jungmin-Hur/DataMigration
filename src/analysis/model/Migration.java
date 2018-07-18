package analysis.model;

import java.util.Date;

public class Migration {
	String id;
	String relatedMigrationId;
	migrationType migrationType;
	String columnName;
	String columnType;
	String validationQuery;
	Date createdDate;
	Date updatedDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRelatedMigrationId() {
		return relatedMigrationId;
	}
	public void setRelatedMigrationId(String relatedMigrationId) {
		this.relatedMigrationId = relatedMigrationId;
	}
	public migrationType getMigrationType() {
		return migrationType;
	}
	public void setMigrationType(migrationType migrationType) {
		this.migrationType = migrationType;
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
