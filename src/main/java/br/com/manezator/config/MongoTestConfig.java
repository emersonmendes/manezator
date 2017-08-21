package br.com.manezator.config;

import static de.flapdoodle.embed.mongo.distribution.Version.Main.PRODUCTION;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClientOptions;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;

@Configuration
@Import(AppConfig.class)
@EnableAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class )
public class MongoTestConfig extends AbstractMongoConfiguration {

    @Autowired
    private MongoProperties properties;
    
    @Autowired(required = false)
    private MongoClientOptions options;

    @Bean(destroyMethod = "close")
    public Mongo mongo() throws IOException {
        final Net net = mongod().getConfig().net();
        properties.setHost(net.getServerAddress().getHostName());
        properties.setPort(net.getPort());
        return properties.createMongoClient(options, null);
    }

    @Bean(destroyMethod = "stop")
    public MongodProcess mongod() throws IOException {
        return mongodExe().start();
    }

    @Bean(destroyMethod = "stop")
    public MongodExecutable mongodExe() throws IOException {
        return MongodStarter.getDefaultInstance().prepare(mongodConfig());
    }

    @Bean
    public IMongodConfig mongodConfig() throws IOException {
        return new MongodConfigBuilder().version(PRODUCTION).build();
    }

	@Override
	protected String getDatabaseName() {
		return "mongo-test-" + UUID.randomUUID();
	}
    
}