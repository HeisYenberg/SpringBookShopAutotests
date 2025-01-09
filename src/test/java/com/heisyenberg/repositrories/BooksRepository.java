package com.heisyenberg.repositrories;

import com.heisyenberg.models.Book;
import com.heisyenberg.utils.DatabaseUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BooksRepository {
  public static void saveBook(final Book book) {
    DatabaseUtil.update(
        "INSERT INTO books (title, author, genre, price, description, image_name) "
            + "VALUES (?, ?, ?, ?, ?, ?);",
        book.getTitle(),
        book.getAuthor(),
        book.getGenre(),
        book.getPrice(),
        book.getDescription(),
        book.getImageUrl());
  }

  public static void deleteBook(final Book book) {
    DatabaseUtil.update(
        "DELETE FROM books "
            + "WHERE title = ? AND author = ? AND genre = ? AND price = ? AND description = ?",
        book.getTitle(),
        book.getAuthor(),
        book.getGenre(),
        book.getPrice(),
        book.getDescription());
  }
}
