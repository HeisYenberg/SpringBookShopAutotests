package com.heisyenberg.data;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class BookData {
    public static final String ADD_BOOK_HEADER = "Новая книга";
    public static final String EMPTY_TITLE_ERROR_MESSAGE = "Необходимо указать название";
    public static final String EMPTY_AUTHOR_ERROR_MESSAGE = "Необходимо указать автора";
    public static final String EMPTY_GENRE_ERROR_MESSAGE = "Необходимо указать жанр";
    public static final String EMPTY_PRICE_ERROR_MESSAGE = "Необходимо указать цену";
    public static final String PRICE_TO_LOW_ERROR_MESSAGE = "Минимальная цена от 100 рублей";
    public static final String EMPTY_DESCRIPTION_ERROR_MESSAGE = "Описание должно быть в пределах от 10 до 500 символов";
    public static final String EMPTY_IMAGE_FILE_ERROR_MESSAGE = "Изображение не было загружено";
    public static final String IMAGE_FILE_PATH = "src/test/resources/book_cover.png";
}
