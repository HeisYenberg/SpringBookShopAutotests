package com.heisyenberg.utils;

import com.github.javafaker.Faker;
import com.heisyenberg.models.Book;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataGenerationUtil {
  private static final Faker FAKER = new Faker();

  public static Book generateBook() {
    String title = FAKER.book().title();
    String author = FAKER.book().author();
    return Book.builder()
        .title(title)
        .author(author)
        .genre(FAKER.book().genre())
        .price(FAKER.number().randomDouble(0, 100, 1500))
        .description(title + " " + author)
        .imageUrl("book_cover.png")
        .build();
  }
}
