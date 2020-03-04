package br.com.manezator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.manezator.model.Phrase;
import br.com.manezator.model.Traduction;
import br.com.manezator.repository.PhraseRepository;

@Service
public class PhraseServiceImpl implements PhraseService {

    public static final char SPACE = ' ';
    @Autowired
	public PhraseRepository phraseRepository;
	
	public Phrase translate(String originalPhraseText, Boolean manezes) {

 	    final Phrase phrase = phraseRepository.findOneByTextAndManezes(originalPhraseText.toLowerCase().trim(), manezes);

        final String[] splitPhrase = originalPhraseText.split(" ");

        if(phrase != null || splitPhrase.length == 1){
            return phrase;
        }

        AtomicReference<String> translatedPhraseTextAR = new AtomicReference<>("");

        Stream.of(splitPhrase).forEach(text -> {

            final String trimmedText = text.toLowerCase().trim();

            if(trimmedText.equals("")){
               return;
            }

            final Phrase p = phraseRepository.findOneByTextAndManezes(trimmedText, manezes);

            if(p == null){
                translatedPhraseTextAR.set(translatedPhraseTextAR.get().concat(trimmedText));
            } else {
                translatedPhraseTextAR.set(translatedPhraseTextAR.get().concat(p.getTraductions().get(0).getText()));
            }

            translatedPhraseTextAR.set(translatedPhraseTextAR.get() + SPACE);

        });

        return buildPhrase(originalPhraseText, translatedPhraseTextAR.get(), manezes);

    }

    private Phrase buildPhrase(String originalPhraseText, String translatedPhraseText, Boolean manezes) {
        final Phrase phrase = new Phrase();
        phrase.setManezes(manezes);
        phrase.setRegister(new Date());
        phrase.setText(originalPhraseText);
        final Traduction traduction = new Traduction();
        traduction.setText(translatedPhraseText);
        phrase.setTraductions(Collections.singletonList(traduction));
        return phrase;
    }

    @Override
	public void sendTraduction(String originalPhrase, String translatedPhrase, Boolean manezes) {
		originalPhrase = originalPhrase.toLowerCase().trim();
		translatedPhrase = translatedPhrase.toLowerCase().trim();
		saveTraduction(originalPhrase, translatedPhrase, manezes);
		saveTraduction(translatedPhrase, originalPhrase, !manezes);
	}

	private void saveTraduction(String originalPhrase, String translatedPhrase, Boolean manezes) {
		
		Phrase phrase = phraseRepository.findOneByTextAndManezes(originalPhrase, manezes);
		
		if(phrase == null){
			Phrase newPhrase = new Phrase();
			newPhrase.setManezes(manezes);
			newPhrase.setText(originalPhrase);
			ArrayList<Traduction> traductions = new ArrayList<>();
			traductions.add(new Traduction(1,translatedPhrase));
			newPhrase.setTraductions(traductions);
			phrase = newPhrase; 
		} else {
			
			List<Traduction> traductions = phrase.getTraductions();
			
			//TODO: Melhorar isso aqui, trazer já ordenado do banco!!!!
			Traduction traduction = traductions.stream().filter(
				value -> value.getText().equals(translatedPhrase)
			).findFirst().orElse(null);
			
			if(traduction == null){
				traductions.add(new Traduction(1,translatedPhrase));
			} else {
				traduction.setRelevancy(traduction.getRelevancy() + 1);
			}
			
		}
		
		phrase.setRegister(new Date());
		
		phraseRepository.save(phrase);
		
	}

	@Override
	public List<Phrase> find(String phrase, Boolean manezes) {
		return phraseRepository.findByTextAndManezesOrderByPhraseAsc(phrase.toLowerCase().trim(), manezes);
	}
    
}
