package com.cxy.jdbc;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class jdbc {
	public static ComboPooledDataSource getsource() {
		ComboPooledDataSource source = new ComboPooledDataSource();
		return source;
	}
	//jdbc.propertiy提供数据库连接
	public static Connection getConnection() throws Exception
	{
	    Properties prop = new Properties();
		FileReader fr = new FileReader("jdbc.properties");//配置文件都是文字，用字符流
		prop.load(fr);//把键值对加载过去
		
		String driverClass = prop.getProperty("driver");
		String jdbcurl = prop.getProperty("jdbcurl");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");
		
		Class.forName(driverClass);//加载驱动程序,提示驱动管理管理什么类型的数据库
		
		//public Connection getConnection(String url,string  user,String password)
		Connection connection = DriverManager.getConnection(jdbcurl, user, password);
		return connection;
  }
	public static void main(String[] args) throws Exception {
		String name="cxy";
		String cs="cxy";
		String ep="cxy";		
		List<rdfname> list = new ArrayList<> ();
		Connection con = getConnection();
		QueryRunner queryrunner = new QueryRunner();
		String sql = "insert into rdftable(Name,Classfication,Explaining) values(?,?,?)";
		//批量执行测试
		String params[][] = new String[2][3];
		params[0][0] = "cxy";params[0][1] = "cxy";params[0][2] = "cxy";
		params[1][0] = "fuck";params[1][1] = "fuck";params[1][2] = "fuck";
		queryrunner.batch(con,sql, params);
		
		//查询测试
		 sql = "select ID,Name,Classfication,Explaining from rdftable";
		 list = queryrunner.query(con, sql,new BeanListHandler<>(rdfname.class));
		
		for(rdfname pa : list) {
			System.out.println(pa.toString());		
		}
			
		con.close();getsource().close();
				
	}
	public static void insertsql(String params[][]) throws Exception {
		Connection con = getsource().getConnection();
		QueryRunner queryrunner = new QueryRunner();
		String sql = "insert into rdftable(Name,Classfication,Explaining,Type) values(?,?,?,?)";
		//批量执行测试
		queryrunner.batch(con,sql, params);
		
	}

}
