package test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import oracel2mysql.Oracle2MySqlSchema;
import utils.CommonUtil;

class CommonUtilTest {
	
	@Test
	void Oracle2MySqlSchemaTest() {
		Oracle2MySqlSchema loadSchemaMapping = new Oracle2MySqlSchema();
		CommonUtil.prettyStringListPrint(Oracle2MySqlSchema.availableConvertColumns("VARCHAR"));
		assertEquals(Oracle2MySqlSchema.availableConvertColumns("VARCHAR").size(), 2);
	}

}
