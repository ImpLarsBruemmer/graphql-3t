package de.inmediasp.graphql.config;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import graphql.kickstart.tools.ObjectMapperConfigurer;
import graphql.kickstart.tools.ObjectMapperConfigurerContext;

@Component
public class CustomObjectMapperConfigurer implements ObjectMapperConfigurer {
    @Override
    public void configure(final ObjectMapper mapper, final ObjectMapperConfigurerContext context) {
        mapper.registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
