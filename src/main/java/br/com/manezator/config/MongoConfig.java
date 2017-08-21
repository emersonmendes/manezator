package br.com.manezator.config;

import static java.util.Collections.singletonList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

	@Value("${mongodb.host}")
	private String host;

	@Value("${mongodb.port}")
	private Integer port;

	@Value("${mongodb.username}")
	private String username;

	@Value("${mongodb.database}")
	private String database;

	@Value("${mongodb.password}")
	private String password;

	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
		return new ValidatingMongoEventListener(validator());
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	@Override
	public String getDatabaseName() {
		return database;
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient(
			singletonList(new ServerAddress(host, port)),
			singletonList(MongoCredential.createCredential(username, database, password.toCharArray()))
		);
	}

}
