package com.heisyenberg.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@UtilityClass
public class JsonDeserializerUtil {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static <T> T readObject(String pathToJson, Class<T> dataClass) {
    URL resource = getResource(pathToJson);
    try {
      return OBJECT_MAPPER.readValue(resource, dataClass);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> List<T> readList(String pathToJson, Class<T> dataClass) {
    URL resource = getResource(pathToJson);
    try {
      return OBJECT_MAPPER.readValue(
          resource, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, dataClass));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static URL getResource(String path) {
    return JsonDeserializerUtil.class.getClassLoader().getResource(path);
  }
}
