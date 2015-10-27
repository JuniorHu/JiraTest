package org.itechs.platform.griautil;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itechs.platform.griautil.giraObj.FieldObj;
import org.itechs.platform.griautil.giraObj.IssueObj;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//输入一个issue 文本 解析属性和值 
public class JiraIssue {

	// 将Json对象转换成Map

	
	int i = 0, j = 0;

	public Object parseIssuesArrays(String str, String key)
			throws JSONException {
		
		List<Object> lo = new ArrayList<Object>();
		JSONArray ja = null;
		if(key!=""){
		JSONObject jo = new JSONObject(str);
		ja= jo.getJSONArray(key);}
		else
		{
              ja = new JSONArray(str);
			
		}
		//System.out.println(ja.length());
		for (int i = 0; i < ja.length(); i++) {
			
			JSONObject joo = (JSONObject) ja.get(i);
			Map<String, String> temp_map = toMap(joo);
			IssueObj iobj = new IssueObj();
			boolean oo = lo.add(toJavaBean(iobj, temp_map));
			
			
		}
		System.out.println(lo.size());
		return lo;
	}

	public Map<String, String> toMap(JSONObject jsonObject)
			throws JSONException {

		// JSONObject jsonObject = new JSONObject(jsonString);

		Map<String, String> result = new HashMap<String, String>();
		Iterator iterator = jsonObject.keys();
		String key = null;
		Object value = null;

		while (iterator.hasNext()) {

			key = (String) iterator.next();
			value = jsonObject.get(key).toString();
			result.put(key, value.toString());

		}
		return result;

	}
	public FieldObj parsingFields(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
    	Map<String, String> temp_map = toMap(jsonObject);
    	FieldObj fo = new FieldObj();
    	fo = (FieldObj) toJavaBean(fo, temp_map);
            
    	return fo;
		}
	public String queryJsonByKey(String jsonString, String key){
		
		  JSONObject jsonObject = new JSONObject(jsonString);
		  
		  String name = jsonObject.getString(key);
		
		  return name;
		
	}
	
	
	
	public Object toJavaBean(Object javabean, Map data) {

		Method[] methods =javabean.getClass().getDeclaredMethods();
		for (Method method : methods) {

			try {
				if (method.getName().startsWith("set")) {

					String field = method.getName();
//					field = field.substring(field.indexOf("set") + 3);
//					field = field.toLowerCase().charAt(0) + field.substring(1);
					field = field.substring(field.indexOf("set") + 3).toLowerCase();
					field = field.charAt(0) + field.substring(1);
					method.invoke(javabean, new Object[] {

					data.get(field)

					});

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return javabean;

	}

	public static void main(String[] arfd) {
		
        for (int nub=0;nub<=2; nub++)
        {
		String fileName = "f:/data/jira/data"+nub+".txt";
		FileUtil fu = new FileUtil();
		String rawIssue = fu.readFileByIssue(fileName);
		JiraIssue ji = new JiraIssue();
		List<IssueObj> result = (List<IssueObj>) ji.parseIssuesArrays(rawIssue, "issues");
		System.out.println(result.size());
		for (int i = 0; i < result.size(); i++) {

			IssueObj iobj = result.get(i);
			System.out.println("ID:" + iobj.getId());
			//System.out.println("key:" + iobj.getKey());
			String ikey=iobj.getKey();
			
			String fields = iobj.getFields();
			
			FieldObj fo = new FieldObj();
			fo = ji.parsingFields(fields);
			String key = "name";
			//System.out.println("description:" + fo.getDescription());
			//System.out.println("Created:"+ fo.getCreated());
			String creator = ji.queryJsonByKey(fo.getCreator(), key);
			//System.out.println("cerator:" + creator);
			String cre_mail = ji.queryJsonByKey(fo.getCreator(), "emailAddress");
			//System.out.println("cerator:" + creator+"<"+cre_mail+">");
			String issueType = ji.queryJsonByKey(fo.getIssuetype(), key);
			//System.out.println("issueType:" + issueType);
			String reporter = ji.queryJsonByKey(fo.getReporter(), key);
			//System.out.println("reporter:" + reporter);
			String reporter_emal = ji.queryJsonByKey(fo.getReporter(), "emailAddress");
			//System.out.println("reporter:" + reporter+"<"+reporter_emal+">");
			String assignee = "";
			String assignee_emal = "";
            if(fo.getAssignee()!="null"){
            	
            	assignee = ji.queryJsonByKey(fo.getAssignee(), key);
            	assignee_emal = ji.queryJsonByKey(fo.getAssignee(), "emailAddress");
            }
            else
            {
            	assignee="";
            }
           // System.out.println("assignee:" + assignee+"<"+assignee_emal+">");
            String status = ji.queryJsonByKey(fo.getStatus(), key);
            //System.out.println("status:" + status);
			//System.out.println("------------------------------------"); 
			//写入数据库
			String url="jdbc:mysql://127.0.0.1:3306/hujuntest";
			String user="root";
			String pwd="hj123456";
			MySQLUtil my = new MySQLUtil(url,user,pwd);
			//my.createConn();
			//the following is the jira data to store in db
			//String tbName="issue";
			String tbName="jira";
			String sql="";
			//新增加的五个对象
			String ityp=issueType;
			String ides=fo.getSummary();
			String idate=fo.getCreated();
			String itsp = fo.getTimeSpent();
		    String itores = fo.getTimeOriginalEstimate();
			String ites = fo.getTimeEstimate();
			String iddate = fo.getDueDate();
			String iresodate = fo.getResolutionDate();
			String icre=creator;
			String iass=assignee;
			String istu=status;
			
//			System.out.println("Created: "+ idate);
//			System.out.println("timespent1: "+ itsp);
//			System.out.println("timespent2: "+ itores);
//			System.out.println("timespent3: "+ ites);
//			System.out.println("duedate: "+ iddate);
//			System.out.println("resodate: "+ iresodate);
			
			sql+="insert ignore into "+tbName+"(isskey,isstype,issdes,isstime,isscreator,issassign,issstatus,isstimesp,isstimeores,isstimees,issddate,issresodate)"+
			" values"+"('"+ikey+"',"+"'"+ ityp+"',"+"'"+ides+"',"+"'"+idate+"',"+"'"+icre+"',"+"'"+iass+"',"+"'"+istu+"',"
			+"'"+itsp+"',"+"'"+itores+"',"+"'"+ites+"',"+"'"+iddate+"',"+"'"+iresodate+"'"+")";
			my.updateData(sql);
			
			//System.out.println(sql);
			
		}

	}//end for
        System.out.println("End!!");
	}
}