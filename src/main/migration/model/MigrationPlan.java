package main.migration.model;

import java.util.Date;

public class MigrationPlan {
	private String id;
	private String tableName;
	private String availableSmpling;
	private String samplingYN;
	private String samplingCondition;
	private Date createdDate;
	private Date updatedDate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getAvailableSmpling() {
		return availableSmpling;
	}
	public void setAvailableSmpling(String availableSmpling) {
		this.availableSmpling = availableSmpling;
	}
	public String getSamplingYN() {
		return samplingYN;
	}
	public void setSamplingYN(String samplingYN) {
		this.samplingYN = samplingYN;
	}
	public String getSamplingCondition() {
		return samplingCondition;
	}
	public void setSamplingCondition(String samplingCondition) {
		this.samplingCondition = samplingCondition;
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
