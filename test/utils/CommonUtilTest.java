package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import oracel2mysql.Oracle2MySqlSchemaMappingInfo;
import utils.CommonUtil;

class CommonUtilTest {
	
	@Test
	void Oracle2MySqlSchemaTest() {
		Oracle2MySqlSchemaMappingInfo loadSchemaMapping = new Oracle2MySqlSchemaMappingInfo();
		CommonUtil.prettyStringListPrint(Oracle2MySqlSchemaMappingInfo.availableConvertColumns("VARCHAR"));
		assertEquals(Oracle2MySqlSchemaMappingInfo.availableConvertColumns("VARCHAR").size(), 2);
	}

}
