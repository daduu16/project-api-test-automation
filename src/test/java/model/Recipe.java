package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    private Long id;
    private String name;
    private List<String> ingredients;
    private List<String> instructions;
    private Integer prepTimeMinutes;
    private Integer cookTimeMinutes;
    private Integer servings;
    private String difficulty;
    private String cuisine;
    private Integer caloriesPerServing;
    private List<String> tags;
    private Integer userId;
    private String image;
    private Double rating;
    private Integer reviewCount;
    private List<String> mealType;

    @JsonProperty("isDeleted")
    private Boolean deleted;
    private String deletedOn;
}
