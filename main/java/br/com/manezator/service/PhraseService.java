package br.com.manezator.service;

import br.com.manezator.model.Phrase;

public interface PhraseService {

	Phrase translate(String originalPhrase, Boolean manezes);

	void sendTraduction(String originalPhrase, String translatedPhrase, Boolean manezes);

}
