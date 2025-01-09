package com.heisyenberg.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItem {
  @JsonProperty("book_id")
  private int bookId;

  @JsonProperty("book_title")
  private String bookTitle;

  @JsonProperty("image_name")
  private String imageUrl;

  @JsonProperty("book_price")
  private Double bookPrice;

  private int quantity;
}
