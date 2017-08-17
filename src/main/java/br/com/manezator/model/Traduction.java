package br.com.manezator.model;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Emerson Mendes
 * @mail emerson.emendes@gmail.com
 **/

@Data
@RequiredArgsConstructor
public class Traduction {

    @Id
    private String id;
    
    @NonNull
    private Integer relevancy;
    
    @NonNull
    private String text;
    
}
