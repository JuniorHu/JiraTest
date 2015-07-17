package org.itechs.platform.griautil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.itechs.platform.griautil.giraObj.DataColumn;
import org.itechs.platform.griautil.giraObj.DataRow;
import org.itechs.platform.griautil.giraObj.DataTable;

public class MySQLUtil {

	private String dbRL="";
    private Connection conn =null;
    private String username = "";
    private String password = "";
    public  MySQLUtil(String url,String _username, String _password){
    	
    	this.dbRL = url;
        this.username = _username;
        this.password = _password;
        
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
public void createConn(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");   
			conn = DriverManager.getConnection(dbRL, username, password);
			System.out.println("数据库连接已建立");  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
				
	}
	public void closeDB(){
		 try {
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("数据库连接已关闭");  
	}
	public void updateData(String sql){
		
		
		try {
			createConn();
		
			PreparedStatement ps = conn.prepareStatement(sql);  
			ps.executeUpdate();  
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		closeDB();
	}
	public DataTable selectDB(String sql){
		
		DataTable dt = new DataTable();
		try {
			createConn();
			
			
			Statement stmt;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);  
			ResultSetMetaData rsmd=rs.getMetaData();  
			
			List<DataRow> row=new ArrayList<DataRow>(); //表所有行集合  
	        List<DataColumn> col=null; //行所有列集合  
	        DataRow r=null;// 单独一行  
	        DataColumn c=null;//单独一列  
	              
	        String columnName;  
	        int iRowCount = 0;
	        Object value;  
	      
	        while(rs.next())//开始循环读取，每次往表中插入一行记录  
            {  
                iRowCount++;  
                col=new ArrayList<DataColumn>();//初始化列集合  
                dt.ColumnCount = rsmd.getColumnCount();  
                for(int i=1;i<=dt.ColumnCount;i++)  
                {  
                    columnName=rsmd.getColumnName(i);  
                    value=rs.getObject(columnName);  
                    c=new DataColumn(columnName,value);//初始化单元列  
                    col.add(c); //将列信息加入到列集合  
                }  
                r=new DataRow(col);//初始化单元行  
                row.add(r);//将行信息加入到行集合  
            }  
            dt = new DataTable(row);  
            dt.RowCount=iRowCount;  
            //stmt.close();
                		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
       
		closeDB();
		
		return dt;
		
		
	}
}
