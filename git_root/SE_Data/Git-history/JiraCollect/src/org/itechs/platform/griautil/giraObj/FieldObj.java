package org.itechs.platform.griautil.giraObj;

public class FieldObj {

	private String issuetype;
	private String creator;
	private String description;
	private String created;
	private String reporter;
	private String assignee;
	private String status;
	private String summary;
	private String timespent;
	private String timeoriginalestimate;
	private String timeestimate;
	private String duedate;
	private String resolutiondate;

	public String getIssuetype() {
		return this.issuetype;
	}

	public void setIssuetype(String str) {
		this.issuetype = str;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String str) {
		this.creator = str;
	}

	public String getCreated() {
		return this.created;
	}

	public void setCreated(String str) {
		this.created = str;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String str) {
		this.description = str;
	}
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String str) {
		this.summary = str;
	}

	public String getReporter() {
		return this.reporter;
	}

	public void setReporter(String str) {
		this.reporter = str;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String str) {
		this.assignee = str;
	}
	
	public String getTimeSpent (){
		return this.timespent;
	}
	
	public void setTimeSpent(String str){
		this.timespent = str;
	}
	
	public String getTimeEstimate (){
		return this.timeestimate;
	}

	public void setTimeEstimate(String str){
		this.timeestimate = str;
	}
	
	public String getTimeOriginalEstimate (){
		return this.timeoriginalestimate;
	}
	
	public void setTimeOriginalEstimate(String str){
		this.timeoriginalestimate = str;
	}
	
	public String getDueDate (){
		return this.duedate;
	}
	
	public void setDueDate(String str){
		this.duedate = str;
	}
	
	public String getResolutionDate (){
		return this.resolutiondate;
	}
	
	public void setResolutionDate(String str){
		this.resolutiondate = str;
	}	
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String str) {
		this.status = str;
	}
}