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
 * @date 2016年2月22日 下午5:30:15
 * @description 1.提供静态方法 2.通过c3p0.properties文件配置datasource信息 3.支持多个数据源 4.提供默认库
 */
public class C3p0Util {

	public static Logger logger = LoggerFactory.getLogger(C3p0Util.class);
	public static Connection con = null;

	// 默认初始化一个库
	static {
		ResourceBundle rb = ResourceBundle.getBundle("c3p0");
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(rb.getString("driverClass"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setJdbcUrl(rb.getString("jdbcUrl"));
		cpds.setUser(rb.getString("username"));
		cpds.setPassword(rb.getString("password"));
		cpds.setInitialPoolSize(3);
		cpds.setMinPoolSize(1);
		cpds.setMaxPoolSize(10);
		cpds.setMaxStatements(50);
		cpds.setMaxIdleTime(60);
		cpds.setAcquireIncrement(5);
		try {
			con = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection(String driverClass, String jdbcUrl, String username, String password) {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(driverClass);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setJdbcUrl(jdbcUrl);
		cpds.setUser(username);
		cpds.setPassword(password);
		cpds.setInitialPoolSize(3);
		cpds.setMinPoolSize(1);
		cpds.setMaxPoolSize(10);
		cpds.setMaxStatements(50);
		cpds.setMaxIdleTime(60);
		cpds.setAcquireIncrement(5);
		try {
			return cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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

	public static boolean ifExist(String sql) {
		Boolean flag = false;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.getRow() != 0) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public static void excuteSql(String sql) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String[][] excuteeQuery(String sql) {
		PreparedStatement ps = null;
		String[][] result =null;
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs =ps.executeQuery();
			 result = convertResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}


	public static void test() {
		String sql = "select top 10 * from serverCrawlerInfo where id='0'";
		boolean flag = ifExist(sql);
		PreparedStatement ps;
		try {
			ps = C3p0Util.con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			String[][] result = convertResult(rs);
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < result[i].length; j++) {
					logger.info(result[i][j]);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
