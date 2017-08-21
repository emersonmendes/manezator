package br.com.manezator.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Emerson Mendes
 * @mail emerson.emendes@gmail.com
 **/

@Document
public class Traduction {
	
	public Traduction() {}
	
	public Traduction(Integer relevancy, String text) {
		this.relevancy = relevancy;
		this.text = text;
	}

	@Id
    private String id;
    
    private Integer relevancy;
    
    private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getRelevancy() {
		return relevancy;
	}

	public void setRelevancy(Integer relevancy) {
		this.relevancy = relevancy;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "traduction : {\n"
			 + "	id : '" + id + "',\n"
			 + "	text : '" + text + "',\n"
			 + "	relevancy : " + relevancy + "\n"
			 + "}";
	}
    
}
