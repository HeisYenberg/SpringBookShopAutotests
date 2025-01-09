package com.heisyenberg.data;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class RegistrationData {
  public static final String USERNAME_ERROR_MESSAGE = "Длина имени должен быть от 3 до 20 символов";
  public static final String RESERVED_EMAIL_ERROR_MESSAGE = "Данная почта уже зарегистрирована";
  public static final String PASSWORD_ERROR_MESSAGE =
      "Длина пароля должен быть от 8 до 30 символов";
}
