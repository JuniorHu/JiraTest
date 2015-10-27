package org.itechs.platform.griautil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Encoder;

public class AccessJira {

	private String url_pre = "http://192.168.1.36:8080/rest/";

	public AccessJira() {

	}

	public void connectJira() {

		//
		BASE64Encoder base64 = new BASE64Encoder();
		// String encoding =Base64.
		String id = "hujun:hj123456";
		String encoding = base64.encode(id.getBytes());
		/*
		 * String url_string = url_pre +
		 * "api/2/search?jql=project='Lustre'&startAt=0&maxResults=5&fields=issuetype&fields=created"
		 * +
		 * "&fields=description&fields=reporter&fields=creator&fields=assignee&fields=status"
		 * ;
		 */
		// HttpClient httpClient = new DefaultHttpClient();
		try {

			// 循环请求issue的信息，一次处理50个,总数为当前issue数
			int totalnub = 143;
			int loopnub = totalnub / 50;
			for (int nub = 0; nub <= loopnub; nub++) {
				int tempstart = nub * 50;
				String url_string = url_pre
						+ "api/2/search?jql=project='webde'&startAt="
						+ tempstart
						+ "&maxResults=50&fields=issuetype&fields=created"
						+ "&fields=summary&fields=reporter&fields=creator&fields=assignee"
						+ "&fields=timespent&fields=timeoriginalestimate&fields=timeestimate&fields=duedate"
						+ "&fields=resolutiondate"
						+ "&fields=status";
				// 发送rest请求
				URL url = new URL(url_string);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setDoOutput(true);
				connection.setRequestProperty("Authorization", "Basic "
						+ encoding);
				char[] buffer = new char[1024];
				InputStream in = (InputStream) connection.getInputStream();
				
				//对输入流进行格式处理
				//StringBulider builder = new StringBuilder();
				int bytesRead = 0;
				FileOperations fo = new FileOperations();
				//
				String fn = "f:/data/jira/data" + nub + ".txt";
				BufferedReader reader= null;
				reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
				while ((bytesRead = reader.read(buffer)) != -1) {
//					String chunk = new String(buffer, 0, bytesRead);
					
				String temp = new String(buffer,0, bytesRead);

				String massage = temp.replace('\'', ' ');
				fo.contentToTxt(fn, massage);
				//System.out.println("the output: "+massage);
				
				}

//				int bytesRead = 0;
//				FileOperations fo = new FileOperations();
//
//				String fn = "f:/data/jira/data" + nub + ".txt";
//
//				BufferedInputStream bis = new BufferedInputStream(in);
//				while ((bytesRead = reader.read(buffer)) != -1) {
//					String chunk = new String(buffer, 0, bytesRead);
//                    //过滤转义字符/'
//					String massage = chunk.replace('\'', ' ');
//					//fo.contentToTxt(fn, massage);
//					System.out.println("the out put: "+massage);
//
//				}
			} // end for
		} catch (Exception exception) {

			exception.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AccessJira tl = new AccessJira();
		tl.connectJira();
		System.out.println("Success!");
	}

}