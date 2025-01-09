package com.heisyenberg.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import lombok.experimental.UtilityClass;

import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.executeJavaScript;

@UtilityClass
public class ElementsUtil {
  private static final String CENTER_VIEW = "{block: 'center'}";

  public static SelenideElement waitAndClick(final SelenideElement element) {
    element.shouldBe(visible, interactable).hover().click();
    return element;
  }

  public static SelenideElement clickElementWithJavaScript(final SelenideElement element) {
    executeJavaScript("arguments[0].click();", element);
    return element;
  }

  public static SelenideElement scrollToElement(final SelenideElement element) {
    element.scrollIntoView(CENTER_VIEW);
    return element;
  }

  public static SelenideElement checkValidationMessage(
      final SelenideElement element, final String message) {
    element.shouldBe(visible).shouldHave(attributeMatching("validationMessage", message));
    return element;
  }

  public static SelenideElement checkImageSrc(final SelenideElement element, final String src) {
    String baseUrl = Configuration.baseUrl;
    element.shouldHave(attributeMatching("src", baseUrl + src));
    return element;
  }
}
