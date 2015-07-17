package org.itechs.platform.griautil.giraObj;


public class IssueObj {

	private String expand;
	private String id;
	private String self;
	private String key;
	private String fields;

	public void setExpand(String str) {

		this.expand = str;
	}

	public String getExpand() {

		return this.expand;
	}

	public String getId() {

		return this.id;
	}

	public void setid(String str) {

		this.id = str;
	}

	public String getself() {

		return this.self;
	}

	public void setSelf(String str) {

		this.self = str;
	}

	public String getKey() {

		return this.key;
	}

	public void setKey(String str) {

		this.key = str;
	}

	public void setFields(String obj) {

		this.fields = obj;
	}

	public String getFields() {

		return this.fields;

	}

}