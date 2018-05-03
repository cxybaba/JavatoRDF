package com.cxy.ansj;

import java.util.ArrayList;

public class Member {
	public String name;
	public ArrayList<Member> ca = new ArrayList<>();
	public String dbsc;
	
	
	public Member(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Member> getCa() {
		return ca;
	}

	public void setCa(ArrayList<Member> ca) {
		this.ca = ca;
	}

	public String getDbsc() {
		return dbsc;
	}

	public void setDbsc(String dbsc) {
		this.dbsc = dbsc;
	}

	@Override
	public String toString() {
		return "Member [name=" + name + ", ca=" + ca + ", dbsc=" + dbsc + "]";
	}
	
    
}
