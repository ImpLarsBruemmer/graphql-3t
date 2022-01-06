package de.inmediasp.graphql.config;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.ObjectMapperConfigurer;
import com.coxautodev.graphql.tools.ObjectMapperConfigurerContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class CustomObjectMapperConfigurer implements ObjectMapperConfigurer {
    @Override
    public void configure(final ObjectMapper mapper, final ObjectMapperConfigurerContext context) {
        mapper.registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
