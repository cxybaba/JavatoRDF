package com.cxy.jdbc;

public class rdfname{
	int ID;
	String Name;
	String Classfication;
	String Explaining;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getClassfication() {
		return Classfication;
	}
	public void setClassfication(String classfication) {
		Classfication = classfication;
	}
	public String getExplaining() {
		return Explaining;
	}
	public void setExplaining(String explaining) {
		Explaining = explaining;
	}
	@Override
	public String toString() {
		return "rdfname [ID=" + ID + ", Name=" + Name + ", Classfication=" + Classfication + ", Explaining=" + Explaining
				+ "]";
	}
	
	
}