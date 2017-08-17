package br.com.manezator.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * @author Emerson Mendes
 * @mail emerson.emendes@gmail.com
 **/

@Data
public class Phrase {

    @Id
    private String id;
    
    private String text;
    
    private Date register;
    
    private List<Traduction> traductions;
    
    private Boolean manezes;
  
}
