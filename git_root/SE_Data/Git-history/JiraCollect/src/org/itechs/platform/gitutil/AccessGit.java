package org.itechs.platform.gitutil;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.itechs.platform.griautil.MySQLUtil;

public class AccessGit {
	static Git git;
	static int i = 0;// 统计所有的commit的数量
	static int s = 0;// 统计给定时间段commit的数量

	public AccessGit() {
		// TODO Auto-generated constructor stub
	}

	public static void getHistoryInfo() {
		// File gitDir = new File("F:/work/nw-android/.git");
		File gitDir = new File("F:/work/demo-rio/.git");
		try {
			if (git == null) {
				git = Git.open(gitDir);
			}
			// Iterable<RevCommit> gitlog= git.log().call();
			Iterable<RevCommit> gitlog = git.log().call();
			for (RevCommit revCommit : gitlog) {
				// String version=revCommit.getName();//版本号
				// String name = revCommit.getAuthorIdent().getName();
				// revCommit.getAuthorIdent().getEmailAddress();
				// Date when = System.out.printlngetAuthorIdent().getWhen();//时间
				// String massage = revCommit.getFullMessage();
				// System.out.println(name);
				// System.out.println(when.toString());
				// System.out.println(massage);
				// System.out.println("----------------------------------------------");
				String version = revCommit.getName();// 版本号
				String aname = revCommit.getAuthorIdent().getName();
				String aemail = revCommit.getAuthorIdent().getEmailAddress();
				Date awhen = revCommit.getAuthorIdent().getWhen();
				Date cwhen = revCommit.getCommitterIdent().getWhen();
				String comId = revCommit.getId().getName();
				String commitId = revCommit.getId().abbreviate(5).toString().substring(20,25);
				//change to five short id
				String cname = revCommit.getCommitterIdent().getName();// committer
				String cemail = revCommit.getCommitterIdent().getEmailAddress();
				String iniMessage = revCommit.getFullMessage();
				String message = iniMessage.replace('\'', ' ');
				String fileNubs ="";
				String changeLines="";
				List<String> test = revCommit.getFooterLines("Tested-by");
				List<String> review = revCommit.getFooterLines("Reviewed-by");
				List<String> changeid = revCommit.getFooterLines("Change-Id");
				List<String> signoff = revCommit.getFooterLines("Signed-off-by");
				// Jira Id
				String jiraId ="";
				int val = message.indexOf("WEB");
				// System.out.println("val= "+ val);
				// if(message.length()>=8)
				//
				// jiraId=message.substring(0, 7);

				if (val == 0) {
					if (iniMessage.indexOf(" ") != -1)
						jiraId = message.substring(0, message.indexOf(" "));
					else if (message.indexOf(":") != -1)
						jiraId = message.substring(0, message.indexOf(":"));

				}
				
				

				int year = cwhen.getYear();
				int month = cwhen.getMonth();

//				System.out.println("commit infor: " + jiraId);
//				System.out.println("--------Line------");

				// //处理某年某月之后的commit
				// //写入数据库
				String url = "jdbc:mysql://127.0.0.1:3306/hujuntest";
				String user = "root";
				String pwd = "hj123456";
				MySQLUtil my = new MySQLUtil(url, user, pwd);
				// my.createConn();
				// the following is the jira data to store in db
				String dbName = "demo";
				String utb = "user1";
				String sql = "";
				String sql1 = "";
				// System.out.println("Date:"+ cwhen);
				// System.out.println("Message: "+massage);
				// System.out.println("----------line--------");
				if (year > 114 && month >= 6) {
					//change files and lines
					
					 GitDiff gDiff = new GitDiff();
					 
					 String result="";
				     result= gDiff.getDiffText(comId);
				     fileNubs = result.substring(0, result.indexOf("&"));
				     changeLines = result.substring(result.indexOf("&")+1,result.length());
					// System.out.println("Date:"+ cwhen);
					// System.out.println("Message: "+massage);
					// System.out.println("----------line--------");
					//
					sql += "insert into "
							+ dbName
							+ "(author,adate,jid,committer,cdate,cinfo,review,test,signoff,changeid)"
							+ " values" + "('" + aname + "'," + "'"
							+ awhen.toString() + "'," + "'" + jiraId + "',"
							+ "'" + cname + "'," + "'" + cwhen.toString()
							+ "'," + "'" + message + "'," + "'" + fileNubs + "',"
							+ "'" + changeLines + "'," + "'" + comId + "'," + "'"
							+ commitId + "'" + ")";
					// //System.out.println(sql);
					// sql1+="insert ignore into "
					// +utb+"(username,email)"+" values"+"('"+aname+"',"+"'"+aemail+"'"+")";
					my.updateData(sql);
					// my.updateData(sql1);
					// s++;
					// System.out.println("Author: "+aname+"<"+aemail+">");
					// System.out.println(awhen.toString());
					// System.out.println("Commiter: "+cname+"<"+cemail+">");
					// System.out.println(cwhen.toString());
					// System.out.println("JiraID: "+massage.substring(0, 7));
					// System.out.println("CommitInfo: "+ massage);
					// System.out.println("CommitId:" + comId);
					// System.out.println("----------Line--------------");
					// System.out.println("Tested-by: "+ test);
					// System.out.println("Signed-off: "+ signoff);
					// System.out.println("Change-id: "+ changeid);
					// System.out.println("Reviewed-by: "+ review);
					// System.out.println("the monthe is"+ month);
					//
					// System.out.println("success!"); }
					// i++;
				} // if
			}// for

			//
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(i);
		// System.out.println(s);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AccessGit.getHistoryInfo();
		System.out.println("End!");
	}

}
