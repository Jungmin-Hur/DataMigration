package test.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import main.common.utils.CommonUtil;

class CommonUtilTest {
	
	@Test
	void generateUniqueIdTest() {
		int loopCount = 10000;
		Map<String, String> idMap = new HashMap<>();
		for(int i=0; i<loopCount; i++) {
			idMap.put(CommonUtil.generateUniqueId(), "1");
		}
		assertEquals(loopCount, idMap.size());
	}
	
	@Test
	void prettyStringListPrintTest() {
		String s1 = "1";
		String s2 = "2";
		String s3 = "3";
		
		List<String> list = new ArrayList<>();
		list.add(s1);
		list.add(s2);
		list.add(s3);
		
		String expectedString = s1 + "," + s2 + "," + s3;
		assertEquals(expectedString, CommonUtil.prettyStringListPrint(list));
	}
}
