package de.cgabrisch.metering.graphql;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;

class TimestampIso8601Coercing implements Coercing<LocalDateTime, String> {
  @Override
  public String serialize(Object input, GraphQLContext context, Locale locale) {
    if (input instanceof LocalDateTime ldt) {
      return ldt.toString();
    }

    throw new CoercingSerializeException(
        String.format("Cannot serialize '%s' to ISO timestamp", input));
  }

  @Override
  public LocalDateTime parseValue(Object input, GraphQLContext context, Locale locale) {
    try {
      return LocalDateTime.parse(String.valueOf(input));
    } catch (DateTimeParseException e) {
      throw new CoercingParseValueException(
          String.format("Value '%s' is not an ISO timestamp", input));
    }
  }

  @Override
  public LocalDateTime parseLiteral(
      Value<?> input, CoercedVariables variables, GraphQLContext context, Locale locale) {
    if (input instanceof StringValue sv) {
      try {
        return LocalDateTime.parse(sv.getValue());
      } catch (DateTimeParseException ignore) {
      }
    }
    throw new CoercingParseValueException(
        String.format("Value '%s' is not an ISO timestamp", input));
  }
}
