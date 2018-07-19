package utils;

public class CommonUtil {
	
	public static int index = 0;
	public static String generateUniqueId() {
		StringBuffer sb = new StringBuffer();
		sb.append(System.currentTimeMillis());
		sb.append(String.format("%03d", (index = index % 1000)));
		index++;
		return sb.toString();
	}
}
