package com.heisyenberg.data;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class LoginData {
    public static final int ADMIN_ID = 1;
    public static final String ADMIN_USERNAME = "HeisYenberg";
    public static final String ADMIN_EMAIL = "heisyenberg@gmail.com";
    public static final String ADMIN_PASSWORD = "heisyenberg";
    public static final int USER_ID = 2;
    public static final String USER_USERNAME = "Butterba";
    public static final String USER_EMAIL = "butterba@gmail.com";
    public static final String USER_PASSWORD = "butterba";
    public static final String INVALID_EMAIL_ADDRESS_FORMAT = "Адрес электронной почты должен содержать символ \"@\". В адресе \"%s\" отсутствует символ \"@\".";
    public static final String INVALID_EMAIL_OR_PASSWORD_ERROR = "Неправильные почта или пароль.";
}
