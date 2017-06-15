package br.com.manezator.model;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * @author Emerson Mendes
 * @mail emerson.emendes@gmail.com
 **/
public class Phrase {

    @Id
    private String id;
    
    private String text;
    
    private List<Traduction> traductions;
    
    private Boolean manezes;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Traduction> getTraductions() {
		return traductions;
	}

	public void setTraductions(List<Traduction> traductions) {
		this.traductions = traductions;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getManezes() {
		return manezes;
	}

	public void setManezes(Boolean manezes) {
		this.manezes = manezes;
	}
}
