package com.heisyenberg.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
  private String title;
  private String genre;
  private String author;
  private Double price;
  private String description;

  @JsonProperty("image_name")
  private String imageUrl;
}
