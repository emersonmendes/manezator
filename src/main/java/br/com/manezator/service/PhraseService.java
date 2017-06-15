package br.com.manezator.service;

import java.util.List;

import br.com.manezator.model.Phrase;

public interface PhraseService {

	Phrase translate(String originalPhrase, Boolean manezes);

	void sendTraduction(String originalPhrase, String translatedPhrase, Boolean manezes);

	List<Phrase> find(String phrase, Boolean manezes);

}
