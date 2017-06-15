package br.com.manezator.repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import br.com.manezator.model.Phrase;
import br.com.manezator.model.Traduction;

public class PhraseRepositoryImpl implements PhraseRepositoryCustom {
	
	@Autowired
	MongoOperations mongoOperations;
	
	@Override
	public Phrase findOneByTextAndManezes(String text, Boolean manezes) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("text").is(text));
		query.addCriteria(Criteria.where("manezes").is(manezes));
		
		Phrase phrase = mongoOperations.findOne(query, Phrase.class);
		
		if(phrase == null)
			return null;
		
		//TODO: MELHORAR ISSO, JA TRAZER DO BANCO O MAIS RELEVANTE
		phrase.setTraductions(
			phrase.getTraductions().stream().sorted(
				Comparator.comparing(Traduction::getRelevancy).reversed()
			).collect(Collectors.toList())
		);
		
		return phrase;
	}

	@Override
	public List<Phrase> findByTextAndManezes(String phrase, Boolean manezes) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("manezes").is(manezes));
		query.addCriteria(Criteria.where("text").regex(phrase));
		query.limit(5);
		
		return mongoOperations.find(query, Phrase.class);
		
	}
	
}
