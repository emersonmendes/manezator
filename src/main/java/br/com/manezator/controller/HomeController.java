package br.com.manezator.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.manezator.model.Phrase;
import br.com.manezator.service.PhraseService;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private PhraseService phraseService;
    
    @RequestMapping(value = "/translate", method = RequestMethod.GET)
    public ResponseEntity<Phrase> translate(@RequestParam("originalPhrase") String originalPhrase, @RequestParam("manezes") Boolean manezes) {
        return new ResponseEntity<Phrase>(phraseService.translate(originalPhrase, manezes), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/sendTraduction", method = RequestMethod.POST)
    public ResponseEntity<Void> sendTraduction(@RequestBody Map<String,Object> phrases) {
    	phraseService.sendTraduction(phrases.get("originalPhrase").toString(), phrases.get("translatedPhrase").toString(), (Boolean) phrases.get("manezes"));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<List<Phrase>> find(@RequestParam("phrase") String phrase, @RequestParam("manezes") Boolean manezes) {
        return new ResponseEntity<List<Phrase>>(phraseService.find(phrase, manezes), HttpStatus.OK);
    }

}
