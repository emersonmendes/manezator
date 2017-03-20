package br.com.manezator.model;

import org.springframework.data.annotation.Id;

/**
 * @author Emerson Mendes
 * @mail emerson.emendes@gmail.com
 **/
public class Traduction {
	
	public Traduction() {}
	
	public Traduction(String text, Integer relevancy) {
		this.text = text;
		this.relevancy = relevancy;
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
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getRelevancy() {
		return relevancy;
	}

	public void setRelevancy(Integer relevancy) {
		this.relevancy = relevancy;
	}
    
}
