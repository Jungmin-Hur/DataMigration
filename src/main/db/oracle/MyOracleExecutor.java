package main.db.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import main.db.mysql.MyMySQLConnection;
import main.report.ResultReportService;

public class MyOracleExecutor {

	public static String selectOneQueryExecutor(String query) {
		Connection conn = MyOracleConnection.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		String result = "";
		
		//SELECT만 허용
		if(!query.toUpperCase().startsWith("SELECT")) {
			ResultReportService.writeAnalysisReport("select query 만 실행할 수 있습니다.");
			return null;
		}

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
	
	public static String selectTableName(String tableName) {
		Connection conn = MyOracleConnection.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		String result = "";
		String query = "SELECT TABLE_NAME FROM TABS WHERE TABLE_NAME = '" + tableName + "'"; 
		
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
	
	
	public static boolean queryExecuter(String query) {
		Connection conn = MyOracleConnection.getConnection();

		Statement stmt = null;
		boolean result = false;
		
		try {
			stmt = conn.createStatement();
			stmt.executeQuery(query);
			result = true;
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
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
	
	public static String backupTable(String tableName) {
		Connection conn = MyOracleConnection.getConnection();
		
		Statement stmt = null;
		String result = "";
		String createTableQuery = "CREATE TABLE " + "B_" + tableName + " AS SELECT * FROM " + tableName;

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
		Connection conn = MyOracleConnection.getConnection();
		
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
	
	public static void moveDataOracle2MySQL(String tableName, List<String> columnNames) {
		Connection oracleConn = MyOracleConnection.getConnection();
		Connection mysqlConn = MyMySQLConnection.getConnection();
		
		Statement oracleStmt = null;
		Statement mySqlStmt = null;
		ResultSet oracleRs = null;

		int insertedRow = 0;
	
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT ");
			int ii = 0;
			for(String columnName: columnNames) {
				if(ii != 0) {
					selectQuery.append(",");
				}
				selectQuery.append(columnName + " ");
				ii++;
			}
			selectQuery.append("FROM " + tableName);
			oracleStmt = oracleConn.createStatement();
			oracleRs = oracleStmt.executeQuery(selectQuery.toString());
			
			while(oracleRs.next()) {
				mySqlStmt = mysqlConn.createStatement();
				
				StringBuffer insertQuery = new StringBuffer();
				insertQuery.append("INSERT INTO " + "BT_" + tableName + " ( MIGRATE_YN, ");
				int index = 0;
				for(String columnName: columnNames) {
					if(index != 0) {
						insertQuery.append(", ");
					}
					insertQuery.append(columnName);
					index++;
				}
				insertQuery.append(") VALUES ( 'N', ");
				for(int i = 0; i < index ; i++) {
					if(i != 0) {
						insertQuery.append(", ");
					}
					if(oracleRs.getString(i+1) == null) {
						insertQuery.append(" " + null + "");	
						
					} else {
						insertQuery.append(" '" + oracleRs.getString(i+1) + "'");	
					}
				}
				insertQuery.append(")");
				insertedRow = insertedRow + mySqlStmt.executeUpdate(insertQuery.toString());
				
				if(insertedRow % 1000 == 0) {
					System.out.println("[진행중]" + tableName + "테이블에 " + insertedRow + "가 insert 되었습니다.");
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (oracleRs != null) {
				try {
					oracleRs.close();
				} catch (SQLException e) {
					System.out.println(e.getStackTrace());
				}
			}
			if (oracleStmt != null) {
				try {
					oracleStmt.close();
				} catch (SQLException e) {
					System.out.println(e.getStackTrace());
				}
			}
			if (mySqlStmt != null) {
				try {
					mySqlStmt.close();
				} catch (SQLException e) {
					System.out.println(e.getStackTrace());
				}
			}
		}
		
		System.out.println("[완료]" + tableName + "테이블에 " + insertedRow + "가 insert 되었습니다.");
	}
}
