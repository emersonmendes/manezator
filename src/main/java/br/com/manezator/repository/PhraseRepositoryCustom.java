package br.com.manezator.repository;

import br.com.manezator.model.Phrase;

public interface PhraseRepositoryCustom  {

	Phrase findByTextAndManezes(String lowerCase, Boolean manezes);
	
}
