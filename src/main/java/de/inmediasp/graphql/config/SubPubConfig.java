package de.inmediasp.graphql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.inmediasp.graphql.persistence.Flight;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SubPubConfig {
    
    @Bean
    public Sinks.Many<Flight> flightSink(){
        return Sinks.many().multicast().directBestEffort();
    }

    @Bean
    public Flux<Flight> flightSource(Sinks.Many<Flight> flightSink){
        return flightSink.asFlux();
    }

}
