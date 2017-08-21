package br.com.manezator.integration;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.manezator.config.MongoTestConfig;
import br.com.manezator.model.Phrase;
import br.com.manezator.repository.PhraseRepository;
import br.com.manezator.service.PhraseService;
import junit.framework.TestCase;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MongoTestConfig.class })
public class PhraseTest extends TestCase {

	private static final String SIGA_EM_FRENTE = "siga em frente";
	private static final String SEGUE_RETO_TODA_VIDA = "segue reto toda vida";
	private static final String SORTUDO = "sortudo";
	private static final String CAGAO = "cagão";
	
	private static final boolean IS_MANEZES = true;
	private static final boolean IS_PORTUGUESE = false;

	@Autowired
	private PhraseService service;

	@Autowired
	private PhraseRepository repository;

	@Before
	public void populateDb() {
		service.sendTraduction(SEGUE_RETO_TODA_VIDA, SIGA_EM_FRENTE, IS_MANEZES);
		service.sendTraduction(SORTUDO, CAGAO, IS_PORTUGUESE);
	}

	@Test
	public void shouldCreateAPhrase() {

		String manezesText = "bucica";
		String portugueseText = "cachorro";

		service.sendTraduction(manezesText, portugueseText, IS_MANEZES);

		assertEquals("Não conseguiu criar em português", portugueseText, getFirstManezesTraduction(manezesText));
		assertEquals("Não conseguiu criar em manézês", manezesText, getFirstPortugueseTraduction(portugueseText));

	}

	@Test
	public void shouldTranslateAPhrase() {
		assertEquals(SORTUDO, service.translate(CAGAO, IS_MANEZES).getTraductions().get(0).getText());
		assertEquals(SEGUE_RETO_TODA_VIDA, service.translate(SIGA_EM_FRENTE, IS_PORTUGUESE).getTraductions().get(0).getText());
	}
	
	@Test
	public void shouldFindMostRelevantFirst() {
		
		String manezesText = "manezestext";
		String mostRelevant = "thisisthemostrelevant";

		service.sendTraduction(manezesText, "nottoorelevant", IS_MANEZES);
		service.sendTraduction(manezesText, "nottoorelevant", IS_MANEZES);
		service.sendTraduction(manezesText, mostRelevant, IS_MANEZES);
		service.sendTraduction(manezesText, "notrelevant", IS_MANEZES);
		service.sendTraduction(manezesText, mostRelevant, IS_MANEZES);
		service.sendTraduction(manezesText, mostRelevant, IS_MANEZES);
		
		assertEquals(
			"Não conseguiu trazer o mais relevante", 
			mostRelevant, 
			service.translate(manezesText, IS_MANEZES).getTraductions().get(0).getText()
		);
		
	}
	
	@Test
	public void shouldFindAlphabeticallySorted() {
		
		String message = "Não conseguiu trazer ordenado";
		
		String abelha = "manezes_abelha";
		String cavalo = "manezes_cavalo";
		String zebra = "manezes_zebra";
		String macaco = "manezes_macaco";
		String abacate = "manezes_abacate";
		
		service.sendTraduction(abelha, "abelha", true);
		service.sendTraduction(cavalo, "cavalo", true);
		service.sendTraduction(zebra, "zebra", true);
		service.sendTraduction(macaco, "macaco", true);
		service.sendTraduction(abacate, "abacate", true);
		
		List<Phrase> phrases = service.find("manezes_", true);
		
		assertEquals(message, phrases.get(0).getText(), abacate);
		assertEquals(message, phrases.get(1).getText(), abelha);
		assertEquals(message, phrases.get(2).getText(), cavalo);
		assertEquals(message, phrases.get(3).getText(), macaco);
		assertEquals(message, phrases.get(4).getText(), zebra);
		
	}

	private String getFirstTraduction(String phrase, Boolean manezes) {
		return repository.findOneByTextAndManezes(phrase, manezes).getTraductions().get(0).getText();
	}

	private String getFirstManezesTraduction(String phrase) {
		return getFirstTraduction(phrase, true);
	}

	private String getFirstPortugueseTraduction(String phrase) {
		return getFirstTraduction(phrase, false);
	}

}
