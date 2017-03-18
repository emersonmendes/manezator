package br.com.manezator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.manezator.model.Phrase;
import br.com.manezator.model.Traduction;
import br.com.manezator.repository.PhraseRepository;

@Service
public class PhraseServiceImpl implements PhraseService {

	@Autowired
	public PhraseRepository phraseRepository;
	
	public Phrase translate(String originalPhrase, Boolean manezes) {
		phraseRepository.findAll();
		return phraseRepository.findByTextAndManezes(originalPhrase.toLowerCase(), manezes);
	}

	@Override
	public void sendTraduction(String originalPhrase, String translatedPhrase, Boolean manezes) {
		originalPhrase = originalPhrase.toLowerCase();
		translatedPhrase = translatedPhrase.toLowerCase();
		saveTraduction(originalPhrase, translatedPhrase, manezes);
		saveTraduction(translatedPhrase, originalPhrase, !manezes);
	}

	private void saveTraduction(String originalPhrase, String translatedPhrase, Boolean manezes) {
		
		Phrase phrase = phraseRepository.findByTextAndManezes(originalPhrase, manezes);
		
		if(phrase == null){
			Phrase newPhrase = new Phrase();
			newPhrase.setManezes(manezes);
			newPhrase.setText(originalPhrase);
			ArrayList<Traduction> traductions = new ArrayList<>();
			traductions.add(new Traduction(translatedPhrase,1));
			newPhrase.setTraductions(traductions);
			phrase = newPhrase; 
		} else {
			
			List<Traduction> traductions = phrase.getTraductions();
			
			Traduction traduction = traductions.stream().filter(
				value -> value.getText().equals(translatedPhrase)
			).findFirst().orElse(null);
			
			if(traduction == null){
				traductions.add(new Traduction(translatedPhrase,1));
			} else {
				traduction.setRelevancy(traduction.getRelevancy() + 1);
			}
			
		}
		
		phraseRepository.save(phrase);
		
	}
    
}
