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
			System.out.println("���ݿ������ѽ���");  
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
		 System.out.println("���ݿ������ѹر�");  
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
			
			List<DataRow> row=new ArrayList<DataRow>(); //�������м���  
	        List<DataColumn> col=null; //�������м���  
	        DataRow r=null;// ����һ��  
	        DataColumn c=null;//����һ��  
	              
	        String columnName;  
	        int iRowCount = 0;
	        Object value;  
	      
	        while(rs.next())//��ʼѭ����ȡ��ÿ�������в���һ�м�¼  
            {  
                iRowCount++;  
                col=new ArrayList<DataColumn>();//��ʼ���м���  
                dt.ColumnCount = rsmd.getColumnCount();  
                for(int i=1;i<=dt.ColumnCount;i++)  
                {  
                    columnName=rsmd.getColumnName(i);  
                    value=rs.getObject(columnName);  
                    c=new DataColumn(columnName,value);//��ʼ����Ԫ��  
                    col.add(c); //������Ϣ���뵽�м���  
                }  
                r=new DataRow(col);//��ʼ����Ԫ��  
                row.add(r);//������Ϣ���뵽�м���  
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
