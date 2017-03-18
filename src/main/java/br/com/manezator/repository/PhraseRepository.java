package br.com.manezator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.manezator.model.Phrase;

public interface PhraseRepository extends MongoRepository<Phrase, String>, PhraseRepositoryCustom {

	
}
