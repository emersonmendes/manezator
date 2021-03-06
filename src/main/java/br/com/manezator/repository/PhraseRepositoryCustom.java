package br.com.manezator.repository;

import java.util.List;

import br.com.manezator.model.Phrase;

public interface PhraseRepositoryCustom  {

	Phrase findOneByTextAndManezes(String lowerCase, Boolean manezes);
	
	List<Phrase> findByTextAndManezesOrderByPhraseAsc(String phrase, Boolean manezes);
	
}
