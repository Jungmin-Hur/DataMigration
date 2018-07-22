package main.db.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyOracleConnection {

	private static final String SERVER = "localhost";
	private static final String SID = "orcl";
	private static final String USER = "jason";
	private static final String PWD = "1234";
	
	private static Connection connection = null;

	public MyOracleConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@"+SERVER+":1521:"+SID,USER,PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	@Override
	protected void finalize() throws Throwable {
		connection.close();
		super.finalize();
	}
}
