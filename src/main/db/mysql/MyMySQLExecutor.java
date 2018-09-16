package main.db.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.report.ResultReportService;

public class MyMySQLExecutor {

	public static String selectOneQueryExecutor(String query) throws SQLException {
		Connection conn = MyMySQLConnection.getConnection();
		
		//SELECT만 허용
		if(!query.toUpperCase().startsWith("SELECT")) {
			ResultReportService.writeAnalysisReport("select query 만 실행할 수 있습니다.");
			return null;
		}

		Statement stmt = null;
		String result = "";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			result = rs.getString(1);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}
	
	public static void queryExecuter(String query) {
		Connection conn = MyMySQLConnection.getConnection();
		
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeQuery(query);
		} catch(SQLException e1){
			System.out.println(e1.getMessage());
		} catch(Exception e2) {
			System.out.println(e2.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public static String selectTableInfo(String tableName)  throws SQLException {
		Connection conn = MyMySQLConnection.getConnection();
		
		Statement stmt = null;
		String result = "";
		String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = " + tableName;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			result = rs.getString(1);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}

	public static String backupTable(String tableName)  throws SQLException {
		Connection conn = MyMySQLConnection.getConnection();
		
		Statement stmt = null;
		String result = "";
		String createTableQuery = "CREATE TABLE TEST1 AS SELECT * FROM " + tableName;
		String deleteTableQuery = "DELETE TABLE " + tableName;

		try {
			stmt = conn.createStatement();
			stmt.executeQuery(createTableQuery);
			stmt.executeQuery(deleteTableQuery);
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}
}
