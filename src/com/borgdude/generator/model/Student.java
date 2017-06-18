package com.borgdude.generator.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
	private final SimpleStringProperty firstName;
	private final SimpleStringProperty lastName;
	private final SimpleStringProperty picture;
	
	public Student() {
        this("", "", "");
    }
	
	public Student(String firstName, String lastName, String picture){
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.picture = new SimpleStringProperty(picture);
	}
	
	public String getFirstName(){
		return firstName.get();
	}
	
	public void setFirstName(String name){
		this.firstName.set(name);
	}
	
	public String getLastName(){
		return lastName.get();
	}
	
	public void setLastName(String name){
		this.lastName.set(name);		
	}
	
	public String getPicture(){
		return picture.get();
	}
	
	public void setPicture(String pic){
		this.picture.set(pic);
	}
	
	public String getFullName(){
		return (firstName.get() + " " + lastName.get());
	}

}
