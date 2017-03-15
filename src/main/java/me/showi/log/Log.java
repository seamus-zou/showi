package me.showi.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author seamus
 * @date 2016年2月22日 下午5:14:22
 * @description
 */
public class Log {

	public static void info() {
		// System.setProperty("log4j.properties", "log4j.properties");
		Logger log = LoggerFactory.getLogger(Log.class);
		log.info("今天星期{},一月{}号,星期{}", 2, 26,3);
		
//		log.error("今天星期{}",2,new InterruptedException());
	}
	public static void main(String[] args) {
		info();
	}
}
