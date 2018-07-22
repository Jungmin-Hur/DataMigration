package test.oracel2mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.oracel2mysql.Oracle2MySqlSchemaMappingInfo;
import main.utils.CommonUtil;

class Oracle2MySqlSchemaMappingInfoTest {
	
	Oracle2MySqlSchemaMappingInfo loadSchemaMapping;
	
	@Test
	void Oracle2MySqlSchemaTest() {
		loadSchemaMapping = new Oracle2MySqlSchemaMappingInfo();
		CommonUtil.prettyStringListPrint(Oracle2MySqlSchemaMappingInfo.availableConvertColumns("VARCHAR"));
		assertEquals(Oracle2MySqlSchemaMappingInfo.availableConvertColumns("VARCHAR").size(), 2);
	}
}
