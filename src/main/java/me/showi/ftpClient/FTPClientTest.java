package me.showi.ftpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @author seamus
 * @date 2016年7月26日 下午2:38:57
 * @description
 */
public class FTPClientTest {

	public static void main(String[] args) {
		try {
			FileInputStream in = new FileInputStream(new File("D:/1938316.png"));
			boolean flag = uploadFile("101.231.74.130", 21, "admin", "infopower2016", "/test/a", "18.png", in);
			createDir("101.231.74.130", 21, "admin", "infopower2016", "/test/a/b/c");
			System.out.println(flag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向FTP服务器上传文件
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param path FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String url, int port, String username, String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			// 如果路径不存在,自动创建
			if (!ftp.changeWorkingDirectory(path)) {
				if (!createDir(url, port, username, password, path)) {
					input.close();
					ftp.logout();
					return success;
				}
			};
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.storeFile(filename, input);
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/**
	 * 创建文件夹
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param path FTP服务器保存目录
	 * @return 成功返回true，否则返回false
	 */
	public static boolean createDir(String url, int port, String username, String password, String path) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			// 如果路径不存在,自动创建
			if (!ftp.changeWorkingDirectory(path)) {
				path = path.replaceAll("//", "/");
				String[] dirArr = path.split("/");
				List<String> dirList = new ArrayList<String>();
				String dir = "";
				for (int i = 0; i < dirArr.length; i++) {
					dir += dirArr[i] + "/";
					dirList.add(dir);
				}
				for (String string : dirList) {
					if (!ftp.changeWorkingDirectory(string)) {
						ftp.makeDirectory(string);
					}
				}
			};
			ftp.enterLocalPassiveMode();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

}
