package utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import analysis.model.Constants;
import analysis.model.SourceInfo;

public class CommonUtil {
	
	public static int generatingKeyIndex = 0;
	public static String generateUniqueId() {
		StringBuffer sb = new StringBuffer();
		sb.append(System.currentTimeMillis());
		sb.append(String.format("%03d", (generatingKeyIndex = generatingKeyIndex % 1000)));
		generatingKeyIndex++;
		return sb.toString();
	}
	
	public static String prettyStringListPrint(List<String> list) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for(String item : list) {
			String str = item;
			if(index == 0) {
			} else {
				str = Constants.COMMA + item;
			}
			sb.append(str);
			index++;
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	@Deprecated
	public static String prettyPrintMap(Map<String, SourceInfo> sourceInfoMap){
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, SourceInfo>> iter = sourceInfoMap.entrySet().iterator();
		while (iter.hasNext()) {
		    Entry<String, SourceInfo> entry = iter.next();
		    sb.append(entry.getKey());
		    sb.append('=').append('"');
		    sb.append(entry.getValue());
		    sb.append('"');
		    if (iter.hasNext()) {
		        sb.append(Constants.COMMA).append(Constants.COMMA);
		    }
		}
		return sb.toString();
	}
}
