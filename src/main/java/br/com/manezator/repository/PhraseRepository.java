package br.com.manezator.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.manezator.model.Phrase;

public interface PhraseRepository extends MongoRepository<Phrase, String>, PhraseRepositoryCustom {

	List<Phrase> findByTextAndManezes(String phrase, Boolean manezes);
	
}
