package org.itechs.platform.griautil;



public class dbTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="jdbc:mysql://127.0.0.1:3306/hujuntest";
		String user="root";
		String pwd="hj123456";
		MySQLUtil my = new MySQLUtil(url,user,pwd);
		//my.createConn();
		//the following is the jira data to store in db
		String tbName="issue";
		String sql="";
		String ikey="TEST-5";
		String ityp="BUG";
		String ides="Test for JIRA";
		String idate="2015.7.12";
		String icre="jun hu";
		String iass="Jie Hu";
		String istu="open";
		
		sql+="insert into "+tbName+"(isskey,isstype,issdes,isstime,isscreator,issassign,issstatus)"+
		" values"+"('"+ikey+"',"+"'"+ ityp+"',"+"'"+ides+"',"+"'"+idate+"',"+"'"+icre+"',"+"'"+iass+"',"+"'"+istu+"'"+")";
		my.updateData(sql);
		
		System.out.println(sql);
	
	   // System.out.printlin("Result:"+result);	
		my.closeDB();
		

	}

}