package test.oracel2mysql;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import main.common.oracel2mysql.Oracle2MySqlSchemaMappingInfo;
import main.common.utils.CommonUtil;

class Oracle2MySqlSchemaMappingInfoTest {
	
	Oracle2MySqlSchemaMappingInfo loadSchemaMapping;
	
	@Test
	void Oracle2MySqlSchemaTest() {
		loadSchemaMapping = new Oracle2MySqlSchemaMappingInfo();
		CommonUtil.prettyStringListPrint(Oracle2MySqlSchemaMappingInfo.availableConvertColumns("VARCHAR"));
		assertEquals(Oracle2MySqlSchemaMappingInfo.availableConvertColumns("VARCHAR").size(), 1); //Test필요
	}
}
