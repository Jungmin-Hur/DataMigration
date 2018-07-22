package main.db.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyOracleExecutor {

	public static String queryExecutor(String query) throws SQLException {
		Connection conn = MyOracleConnection.getConnection();
		
		Statement stmt = null;
		String result = "";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			result = rs.getString(1);
//			System.out.println(result);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return result;
	}
}
