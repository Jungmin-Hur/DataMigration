package main.db.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import main.report.ResultReportService;

public class MyOracleExecutor {

	public static String selectOneQueryExecutor(String query) throws SQLException {
		Connection conn = MyOracleConnection.getConnection();
		
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
	
	public static void moveDataOracle2MySQL(String tableName, List<String> columnNames) {
		Connection conn = MyOracleConnection.getConnection();
		
		PreparedStatement pstmt = null;
		Statement stmt1 = null;

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
			System.out.println(selectQuery);
			pstmt = conn.prepareStatement(selectQuery.toString());
			
			ResultSet rs = pstmt.executeQuery();
			System.out.println("뭐지뭐지잉");
			rs.next();
			System.out.println(rs.getString(1));
			System.out.println("뭐지뭐지");
			
			pstmt.close();
			rs.close();
//			
//			while(rs.next()) {
//				stmt1 = conn.createStatement();
//				
//				StringBuffer insertQuery = new StringBuffer();
//				insertQuery.append("INSERT INTO " + "BT_" + tableName + " (");
//				int index = 0;
//				for(String columnName: columnNames) {
//					if(index != 0) {
//						insertQuery.append(", ");
//					}
//					insertQuery.append(columnName);
//					index++;
//				}
//				insertQuery.append(") VALUES (");
//				for(int i = 0; i < index ; i++) {
//					if(i != 0) {
//						insertQuery.append(", ");
//					}
//					insertQuery.append(" '" + rs.getString(i) + "'");
//				}
//				ResultSet rs1 = stmt1.executeQuery(insertQuery.toString());
//				
//			}
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					System.out.println(e.getStackTrace());
//				}
//			}
//			if (stmt1 != null) {
//				try {
//					stmt1.close();
//				} catch (SQLException e) {
//					System.out.println(e.getStackTrace());
//				}
//			}
//		}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
