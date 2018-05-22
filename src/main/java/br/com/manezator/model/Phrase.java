package br.com.manezator.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Emerson Mendes
 * @mail emerson.emendes@gmail.com
 **/
@Document
public class Phrase {
	
	public Phrase() {}

	@Id
    private String id;
    
    private String text;
    
    private Date register;
    
    private List<Traduction> traductions;
    
    private Boolean manezes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getRegister() {
		return register;
	}

	public void setRegister(Date register) {
		this.register = register;
	}

	public List<Traduction> getTraductions() {
		return traductions;
	}

	public void setTraductions(List<Traduction> traductions) {
		this.traductions = traductions;
	}

	public Boolean getManezes() {
		return manezes;
	}

	public void setManezes(Boolean manezes) {
		this.manezes = manezes;
	}
	
	@Override
	public String toString() {
		return "phrase : {\n"
			 + "	id : '" + id + "',\n"
			 + "	text : '" + text + "',\n"
			 + "	register : '" + register + "',\n"
			 + "	manezes: " + manezes + ",\n"
			 + "	traductions: " + traductions + "\n"
			 + "}";
	}
  
}
