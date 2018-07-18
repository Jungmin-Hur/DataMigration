package analysis.model;

import java.util.Date;

public class SchemaInfo {

	private String id; // id
	private RelationType relationType; // source, target�� ���� ����
	private String sourceId; // sourceId, �� object�� source�� sourceId�� id�� ����
	private String targetId; // targetId, �� object�� target�̸� targetId�� id�� ����
	private String columnName;
	private String columnType;
	private String validationQuery;
	private Date createdDate;
	private Date updatedDate;

	public boolean isSource() {
		return this.id.equals(this.sourceId);
	}
	public boolean isTarget() {
		return this.id.equals(this.targetId);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public RelationType getRelationType() {
		return relationType;
	}
	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
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