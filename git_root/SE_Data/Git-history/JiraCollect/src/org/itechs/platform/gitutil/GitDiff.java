package org.itechs.platform.gitutil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RenameDetector;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.itechs.report.util.ConfigUtil;

public class GitDiff {
	
	private Git git;  
	private List<String> fileTypes = new ArrayList<String>();
	String gitUrl = "";
	
	public GitDiff(){
		
		initFileTypes();
	}
	public void initFileTypes(){
		
		this.fileTypes = ConfigUtil.getProperties("/config/fileTypes/file");
	}
	public String getDiffText(String str){
		int filenubs =0;
		int lines =0;
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		File gitDir = new File("F:/work/demo-rio/.git");  
	    Repository repository = null;
	    try {  
            if (git == null) {  
                git = Git.open(gitDir);  }
            } catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    	} 
	    	try {
	    		String cid =str;
	    		repository = builder.setGitDir(gitDir).readEnvironment().findGitDir().build();
	    		RevWalk walk = new RevWalk(repository);
	    		ObjectId objId = ObjectId.fromString(cid);  
	    		RevCommit commit  = walk.parseCommit(objId);
	    		System.out.println(commit.getFullMessage());
	    		
	    		TreeWalk tw = new TreeWalk(repository);  
	    		
	    		RevCommit[] parent_commits = commit.getParents(); 
	    		if(parent_commits.length == 0){
	    			throw new Exception("当前只有一个版本");  
	    		}
	    		
	    		ObjectId objId2  = parent_commits[0].toObjectId();
	    		RevCommit paren_commit = walk.parseCommit(objId2);
	    		tw.addTree(paren_commit.getTree());
	    		tw.addTree(commit.getTree());
	    		tw.setRecursive(true);  
	    		
	    		ByteArrayOutputStream out = new ByteArrayOutputStream();  
                DiffFormatter df = new DiffFormatter(out);  
                df.setRepository(git.getRepository());  
	    	    RenameDetector rd = new RenameDetector(repository);  
	    	    rd.addAll(DiffEntry.scan(tw));  
	    	    List<DiffEntry> diffEntries = rd.compute();  
	    	    if (diffEntries != null || diffEntries.size() != 0) {  

	    	        Iterator<DiffEntry> iterator = new ArrayList<DiffEntry>(diffEntries).iterator();  
	    	         DiffEntry diffEntry = null; 
	    	         while (iterator.hasNext()) {  
	    	             diffEntry = iterator.next();  
	    	             String changeType = diffEntry.getChangeType().toString();
	    	             String type = "";
	    	             if(changeType.equals("DELETE"))
	    	             {
	    	            	 type =  ConfigUtil.getFileType(diffEntry.getOldPath());
	    	            	 filenubs++;
	    	            	 System.out.println(diffEntry.getOldPath());
	    	             }
	    	             else {
	    	            	 type = ConfigUtil.getFileType(diffEntry.getNewPath());	
	    	            	 filenubs++;
	    	            	 System.out.println(diffEntry.getNewPath());
						 }
	    	             //检查文件的后缀
	    	             //System.out.println(type);
	    	             if(fileTypes.contains(type)){ 
	    	            	 df.format(diffEntry);  
	    	            	 String diffText = out.toString("UTF-8"); 
	    	            	 lines += scanDiffText(diffText);	
	    	            	 
	    	             }	   	            	 
	    	             }
	    	         }
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return filenubs+"&"+lines;
	    	//System.out.println("the changed file nubs: "+ filenubs);
	    	//System.out.println("the changed linr nubs: "+ lines);
	}
    
	public int scanDiffText(String txt) {
		
		String result = "";
		int del_lines = 0;
		int add_lines = 0;
		int total_lines =0;
		//System.out.println(txt);
		String[] list_array = txt.split("\n");
		for(int i = 0; i< list_array.length; i++){
			
			String line = list_array[i];
			//去除空行
			if(line.trim().length()> 1){
			if(line.startsWith("-") && !line.startsWith("---")){del_lines++; }
			if(line.startsWith("+") && !line.startsWith("+++")){add_lines++; }
			}
		}
		
		System.out.println("@@delete lines " + del_lines);
		System.out.println("@@add lines " + add_lines);
		
		System.out.println("-------------------------------------------");
		return add_lines;
		
	}
	public static void main(String[] args){
		
		 GitDiff gDiff = new GitDiff();
		 String commitId = "e9d9b22d1044526c230d3f04d2b3bbbcaa39b6de";
		 String result="";
	     result= gDiff.getDiffText(commitId);
	     //System.out.println("return values " + result);
	}
}
