package me.showi.excel;
/**
 * @author seamus
 * @date 2016年4月20日 下午3:15:45
 * @description
 */
public class Util {
	public static String getPostfix(String path) {
		if (path == null || Common.EMPTY.equals(path.trim())) {
			return Common.EMPTY;
		}
		if (path.contains(Common.POINT)) {
			return path.substring(path.lastIndexOf(Common.POINT) + 1, path.length());
		}
		return Common.EMPTY;
	}
}
