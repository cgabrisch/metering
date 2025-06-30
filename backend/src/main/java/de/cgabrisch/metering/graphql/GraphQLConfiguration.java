package de.cgabrisch.metering.graphql;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
class GraphQLConfiguration {

  @Bean
  RuntimeWiringConfigurer runtimeWiringConfigurer() {
    return (wiringBuilder) ->
        wiringBuilder.scalar(
            GraphQLScalarType.newScalar()
                .name("TimestampIso8601")
                .description("ISO-8601 date+time")
                .coercing(new TimestampIso8601Coercing())
                .build());
  }
}
