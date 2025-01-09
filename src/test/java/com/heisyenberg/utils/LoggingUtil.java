package com.heisyenberg.utils;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public final class LoggingUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingUtil.class);
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().enable(INDENT_OUTPUT);

  public static void log(final LogLevel level, final String message) {
    log(level, message, null);
  }

  public static void log(final LogLevel level, final String message, final Throwable throwable) {
    switch (level) {
      case ERROR:
        LOGGER.error(message, throwable);
        break;
      case WARN:
        LOGGER.warn(message, throwable);
        break;
      case INFO:
        LOGGER.info(message, throwable);
        break;
      case DEBUG:
        LOGGER.debug(message, throwable);
        break;
      case TRACE:
        LOGGER.trace(message, throwable);
        break;
    }
  }

  public static void log(final LogLevel level, final Object object) {
    log(level, "", object);
  }

  public static void log(final LogLevel level, final String message, final Object object) {
    try {
      log(level, String.format("%s\n%s", message, OBJECT_MAPPER.writeValueAsString(object)));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
