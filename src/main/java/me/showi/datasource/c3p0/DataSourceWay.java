package me.showi.datasource.c3p0;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author seamus
 * @date 2016年2月26日 下午5:15:27
 * @description c3P0数据源配置的三种方式
 * 
 */
public class DataSourceWay {

	public static Logger logger = LoggerFactory.getLogger(DataSourceWay.class);

	public static void main(String[] args) {
		way1();
		way2();
	}

	/**
	 * 方式一:setters一个个的设置各个配置项
	 */
	public static void way1() {
		ResourceBundle rb =  ResourceBundle.getBundle("c3p0");
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(rb.getString("driverClass"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setJdbcUrl(rb.getString("jdbcUrl"));;
		cpds.setUser(rb.getString("username"));
		cpds.setPassword(rb.getString("password"));
		cpds.setInitialPoolSize(3);
		cpds.setMinPoolSize(1);
		cpds.setMaxPoolSize(10);
		cpds.setMaxStatements(50);
		cpds.setMaxIdleTime(60);
		cpds.setAcquireIncrement(5);
		Connection con = null;
		try {
			con = cpds.getConnection();
			String sql = "SELECT TOP 100 * from serverCrawlerInfo;";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			String[][] arr = convertResult(result);
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					logger.info(arr[i][j]);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static String[][] convertResult(ResultSet res) {
		String[][] result = null;
		int column = 0;
		Vector<Object> vector = new Vector<Object>();
		try {
			while (res.next()) {
				column = res.getMetaData().getColumnCount();
				String[] str = new String[column];
				for (int i = 1; i < column + 1; i++) {
					Object ob = res.getObject(i);
					if (ob == null)
						str[i - 1] = "";
					else
						str[i - 1] = ob.toString();
				}
				vector.addElement(str.clone());
			}
			result = new String[vector.size()][column];
			vector.copyInto(result);
			vector.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static void way2() {
		// TODO Auto-generated method stub
		
	}
}
