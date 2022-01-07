package de.inmediasp.graphql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.inmediasp.graphql.persistence.Flight;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Configuration
public class SubPubConfig {
    
    @Bean
    public Many<Flight> flightSink(){
        return Sinks.many().multicast().directBestEffort();
    }

    @Bean
    public Flux<Flight> flightSource(final Many<Flight> flightSink){
        return flightSink.asFlux();
    }

}
