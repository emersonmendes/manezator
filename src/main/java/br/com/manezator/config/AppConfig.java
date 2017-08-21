package br.com.manezator.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("br.com.manezator.repository")
@ComponentScan({ "br.com.manezator.controller", "br.com.manezator.service" })
public class AppConfig {}
