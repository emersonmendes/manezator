package br.com.manezator.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

	/*Tentativa de manter heroku sempre up, procurar solução melhor mais tarde*/
    @Scheduled(fixedRate = 300000)
    public void keepHerokuAppAlive() {
    	new RestTemplate().getForObject("https://manezator.herokuapp.com",Void.class);
    }
    
}