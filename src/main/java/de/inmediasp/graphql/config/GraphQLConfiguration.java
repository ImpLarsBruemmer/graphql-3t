package de.inmediasp.graphql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coxautodev.graphql.tools.SchemaParserOptions;

@Configuration
public class GraphQLConfiguration {
    @Bean
    public SchemaParserOptions schemaParserOptions(final CustomObjectMapperConfigurer customObjectMapperConfigurer) {
        return SchemaParserOptions.newOptions().objectMapperConfigurer(customObjectMapperConfigurer).build();
    }
}
