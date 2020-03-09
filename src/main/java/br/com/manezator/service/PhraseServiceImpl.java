package br.com.manezator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
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

        final String trimmedText = originalPhraseText.toLowerCase().trim();

        final Phrase phrase = getPhrase(trimmedText, manezes);

        final String[] splitPhrase = originalPhraseText.split(" ");

        if(phrase != null || splitPhrase.length == 1){
            return phrase;
        }

        return translateSplit(originalPhraseText, manezes, splitPhrase);

    }

    public static void main(String[] args) {

        List<String> lista = new ArrayList<>();

        lista.add("cachorro");
        lista.add("é");
        lista.add("muito");
        lista.add("feio");
        lista.add("branco");

        List<String> phrases = getAllPossibleTexts(lista);

        for (String word : phrases) {
            System.out.println(word);
        }

    }

    private static List<String> getAllPossibleTexts(List<String> lista) {
        final String[] array = lista.toArray(new String[]{});
	    List<String> result = new ArrayList<>();
	    int count = 1;
	    while (count < array.length){
            for (int i = 0; i < array.length; i++) {
                String word = "";
                if((count + i) > array.length){
                    continue;
                }
                for (int j = i; j < (count + i); j++) {
                    word = word.concat(array[j]).concat(" ");
                }
                result.add(word.trim());
            }
            count++;
        }
	    return result;
    }

    private Phrase getPhrase(String trimmedText, Boolean manezes) {
        return phraseRepository.findOneByTextAndManezes(trimmedText, manezes);
    }

    private Phrase translateSplit(String originalPhraseText, Boolean manezes, String[] splitPhrase) {

	    String translatedPhraseText = originalPhraseText;

        final List<String> allPossibleTexts = getAllPossibleTexts(splitOriginalPhraseText(splitPhrase));

        Map<String,String> translatedMap = new HashMap<>();

        for (String text : allPossibleTexts) {
            final Phrase p = getPhrase(text, manezes);
            if(p != null){
                translatedMap.put(text, p.getTraductions().get(0).getText());
            }
        }

        for (String key : translatedMap.keySet()) {
            translatedPhraseText = translatedPhraseText.replace(key, translatedMap.get(key));
        }

        return buildPhrase(originalPhraseText, translatedPhraseText, manezes);

    }

    private List<String> splitOriginalPhraseText(String[] splitPhrase) {
        return Stream.of(splitPhrase)
            .filter(text -> !text.toLowerCase().trim().equals(""))
            .collect(Collectors.toList());
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
		
		Phrase phrase = getPhrase(originalPhrase, manezes);
		
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
