package main.db.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
