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
	
	public static boolean queryExecuter(String query) {
		Connection conn = MyMySQLConnection.getConnection();
		
		Statement stmt = null;
		boolean result = false;
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			result = true;
		} catch(SQLException e1){
			e1.printStackTrace();
		} catch(Exception e2) {
			e2.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static String selectTableName(String tableName) {
		Connection conn = MyMySQLConnection.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		String result = "";
		String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '" + tableName + "'";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} 
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public static String backupTable(String tableName) {
		Connection conn = MyMySQLConnection.getConnection();
		
		Statement stmt = null;
		String result = "";
		String createTableQuery = "CREATE TABLE " + "BACK_" + tableName + " SELECT * FROM " + tableName;

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(createTableQuery);
		} catch (SQLException e){
			System.out.println(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static String dropTable(String tableName) {
		Connection conn = MyMySQLConnection.getConnection();
		
		Statement stmt = null;
		String result = "";
		String dropTableQuery = "DROP TABLE " + tableName;

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(dropTableQuery);
		} catch (SQLException e){
			System.out.println(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
