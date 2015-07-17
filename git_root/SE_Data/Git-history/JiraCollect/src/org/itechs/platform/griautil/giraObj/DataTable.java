package org.itechs.platform.griautil.giraObj;

import java.util.List;

public class DataTable {
	   List<DataRow> row;  
	    public DataTable(){}  
	    public DataTable(List<DataRow> _row)
		{
			row=_row;
		}

	      
	    public List<DataRow> GetRow()  
	    {  
	        return row;  
	    }  
	      
	    public void SetRow(List<DataRow> _row)  
	    {  
	        row = _row;  
	    }  
	   
	    public static void PrintTable(DataTable dt) {   
	        for (DataRow r : dt.GetRow()) {   
	            for (DataColumn c : r.GetColumn()) {   
	                System.out.print(c.GetKey() + ":" + c.GetValue() + "  ");   
	            }   
	            System.out.println("");   
	        }   
	    }   
	      
	    public static int RowCount=0;  
	    public static int ColumnCount=0;  
}
