package me.showi.ftpClient;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @author seamus
 * @date 2016年7月26日 下午5:55:24
 * @description
 */
public class Test {
	 private  FTPClient ftp;      
	    /**  
	     *   
	     * @param path 上传到ftp服务器哪个路径下     
	     * @param addr 地址  
	     * @param port 端口号  
	     * @param username 用户名  
	     * @param password 密码  
	     * @return  
	     * @throws Exception  
	     */    
	    private  boolean connect(String path,String addr,int port,String username,String password) throws Exception {      
	        boolean result = false;      
	        ftp = new FTPClient();      
	        int reply;      
	        ftp.connect(addr,port);      
	        ftp.login(username,password);      
	        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
	        reply = ftp.getReplyCode();      
	        if (!FTPReply.isPositiveCompletion(reply)) {      
	            ftp.disconnect();      
	            return result;      
	        }      
	        ftp.changeWorkingDirectory(path);      
	        result = true;      
	        return result;      
	    }      
	    /**  
	     *   
	     * @param file 上传的文件或文件夹  
	     * @throws Exception  
	     */    
	    private void upload(File file) throws Exception{      
	        if(file.isDirectory()){           
	            ftp.makeDirectory(file.getName());                
	            ftp.changeWorkingDirectory(file.getName());      
	            String[] files = file.list();             
	            for (int i = 0; i < files.length; i++) {      
	                File file1 = new File(file.getPath()+"\\"+files[i] );      
	                if(file1.isDirectory()){      
	                    upload(file1);      
	                    ftp.changeToParentDirectory();      
	                }else{                    
	                    File file2 = new File(file.getPath()+"\\"+files[i]);      
	                    FileInputStream input = new FileInputStream(file2);      
	                    ftp.storeFile(file2.getName(), input);      
	                    input.close();                            
	                }                 
	            }      
	        }else{      
	            File file2 = new File(file.getPath());      
	            FileInputStream input = new FileInputStream(file2); 
	            ftp.enterLocalPassiveMode();
	            ftp.storeFile(file2.getName(), input);      
	            input.close();        
	        }      
	    }      
	   public static void main(String[] args) throws Exception{    
	      Test t = new Test(); 
//	      "101.231.74.130", 21, "admin", "infopower2016", "F:\\FTPjpg\\test", "aa.txt", in);  
	      t.connect("/test", "101.231.74.130", 21, "admin", "infopower2016");    
	      File file = new File("D:/aa.txt");    
	      t.upload(file);    
	   }    
}

