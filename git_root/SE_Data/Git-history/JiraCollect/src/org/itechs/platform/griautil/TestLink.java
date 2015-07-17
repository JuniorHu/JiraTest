package org.itechs.platform.griautil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Encoder;

public class TestLink {

	private String url_pre = "https://jira.hpdd.intel.com/rest/";

	public TestLink() {

	}

	public void connectJira() {

		//
		BASE64Encoder base64 = new BASE64Encoder();
		// String encoding =Base64.
		String id = "Junior Hu:wo2hujie";
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

			// 循环请求issue的信息，一次处理50个,总数为6640
			int totalnub = 6640;
			int loopnub = totalnub / 50;
			for (int nub = 0; nub <= loopnub; nub++) {
				int tempstart = nub * 50;
				String url_string = url_pre
						+ "api/2/search?jql=project='Lustre'&startAt="
						+ tempstart
						+ "&maxResults=50&fields=issuetype&fields=created"
						+ "&fields=summary&fields=reporter&fields=creator&fields=assignee&fields=status";
				// 发送rest请求
				URL url = new URL(url_string);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setDoOutput(true);
				connection.setRequestProperty("Authorization", "Basic "
						+ encoding);
				byte[] buffer = new byte[1024];
				InputStream in = (InputStream) connection.getInputStream();

				int bytesRead = 0;
				FileOperations fo = new FileOperations();

				String fn = "d:/data/jira/data" + nub + ".txt";

				BufferedInputStream bis = new BufferedInputStream(in);
				while ((bytesRead = bis.read(buffer)) != -1) {
					String chunk = new String(buffer, 0, bytesRead);
                    //过滤转义字符/'
					String massage = chunk.replace('\'', ' ');
					fo.contentToTxt(fn, massage);

				}
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

		TestLink tl = new TestLink();
		tl.connectJira();
		System.out.println("Success!");
	}

}
