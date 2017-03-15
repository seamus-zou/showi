package me.showi.excel;

import java.io.IOException;
import java.util.List;

import me.showi.datasource.c3p0.C3p0Util;

/**
 * @author seamus
 * @date 2016年4月20日 下午3:14:39
 * @description
 */
public class Client {
	public static void main(String[] args) throws IOException {
		// String excel2003_2007 = Common.STUDENT_INFO_XLS_PATH;
		// String excel2010 = Common.STUDENT_INFO_XLSX_PATH;
		String excel2003_2007 = "";
		String excel2010 = "E:\\项目文档\\百事促销\\BCC SKU.xlsx";
		// read the 2003-2007 excel
		// List<Student> list = new ReadExcel().readExcel(excel2003_2007);
		// if (list != null) {
		// for (Student student : list) {
		// System.out.println("No. : " + student.getNo() + ", name : " + student.getName() + ", age : " + student.getAge() + ", score : "
		// + student.getScore());
		// }
		// }
		// System.out.println("======================================");
		// read the 2010 excel
		List<List<String>> arr = new ReadExcel().readExcel(excel2010);
		for (int i = 0; i < arr.size(); i++) {
			String[][] result = C3p0Util.excuteeQuery("SELECT client FROM [dbo].[promotionGoods] WHERE egoodsId='" + arr.get(i).get(1) + "' and company='"+arr.get(i).get(3)+"'");
			if (result.length>0) {
				String oldClient = result[0][0];
				if(oldClient.contains(arr.get(i).get(4))){
					continue;
				}
				String newClient = "";
				if(oldClient.equals("pc")){
					 newClient = oldClient+"&"+arr.get(i).get(4);
				}else{
					 newClient = arr.get(i).get(4)+"&"+oldClient;
				}
				C3p0Util.excuteSql("UPDATE promotionGoods SET client='"+newClient+"',updateTime=GETDATE() WHERE egoodsId='" + arr.get(i).get(1) + "' and company='"+arr.get(i).get(3)+"'");
				
			} else {
				String sql = "INSERT INTO promotionGoods(egoodsId,goodsName,company,etype,client,updateTime) VALUES ('" + arr.get(i).get(1)
						+ "','" + arr.get(i).get(2).replace("'", "''") + "','" + arr.get(i).get(3) + "','" + arr.get(i).get(0) + "','" + arr.get(i).get(4)
						+ "',GETDATE());";
				C3p0Util.excuteSql(sql);
			}
		}
	}

	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	
	public static String ToSBC(String input)
	{ //半角转全角：
	    char[] c=input.toCharArray();
	      for (int i = 0; i < c.length; i++)
	    {
	       if (c[i]==32)
	    {
	    c[i]=(char)12288; continue;
	  }
	   if (c[i]<127) c[i]=(char)(c[i]+65248);
	   }
	return new String(c);
	}
}
