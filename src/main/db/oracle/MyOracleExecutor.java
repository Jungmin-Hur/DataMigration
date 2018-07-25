package main.db.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.common.exception.DataMigrationExceptionCode;

public class MyOracleExecutor {

	public static String selectOneQueryExecutor(String query) throws SQLException {
		Connection conn = MyOracleConnection.getConnection();
		
		//SELECT만 허용
		if(!query.toUpperCase().startsWith("SELECT")) {
			System.out.println(DataMigrationExceptionCode.get("ANALYSIS_INVALID_QUERY_TYPE"));
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
