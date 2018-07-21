package utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import analysis.model.SourceInfo;

public class CommonUtil {
	
	public static int index = 0;
	public static String generateUniqueId() {
		StringBuffer sb = new StringBuffer();
		sb.append(System.currentTimeMillis());
		sb.append(String.format("%03d", (index = index % 1000)));
		index++;
		return sb.toString();
	}
	
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
		        sb.append(',').append(' ');
		    }
		}
		return sb.toString();
	}
}
