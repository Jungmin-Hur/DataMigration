package test.oracel2mysql;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class OracleConnectionTest {
	
	private String SERVER = "localhost";
	private String SID = "orcl";
	private String USER = "jason";
	private String PWD = "1234";

	@Test
	void OracleConnectTest() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:oracle:thin:@"+SERVER+":1521:"+SID,USER,PWD);
		
		if(connection != null) {
			System.out.println("connect!!!");
		}else {
			System.out.println("not connect!!!");
		}
		
		assertNotNull(connection);

		connection.close();
	}
}
