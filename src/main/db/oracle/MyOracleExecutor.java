package main.db.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import main.report.ResultReportService;

public class MyOracleExecutor {

	public static String selectOneQueryExecutor(String query) throws SQLException {
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
			rs.next();
			result = rs.getString(1);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return result;
	}
	
	public static void moveDataOracle2MySQL(String tableName, List<String> columnNames) {
		Connection conn = MyOracleConnection.getConnection();
		
//		if(!tableName.equals("LOT_INFO")) return;
		
		Statement stmt = null;
//		Statement stmt1 = null;

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
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(selectQuery.toString());
			
			if(rs == null) {
				System.out.println("널인디?");
			}
			else {
				System.out.println("널은 아닌디");
			}
			if(rs.next() == false) {
				System.out.println("다음이 널인디?");
				
			}
			else {
				System.out.println("널은 아닌디2");
			}

			if(rs.getObject(0) == null) {
				System.out.println("널인디1?");
			}
			else {
				System.out.println("널은 아닌디3");
			}

			System.out.println(selectQuery);	
			System.out.println("뭐지뭐지잉");
			System.out.println(rs.getRow());
			if(rs.next()) {
				System.out.println(rs.getString(1));
				System.out.println("뭐지뭐지");
			}
			
			if(rs != null) {
				rs.close();
				
			}
			if(stmt != null) {
				stmt.close();
			}
			
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
