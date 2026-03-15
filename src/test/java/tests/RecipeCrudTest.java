package tests;

import base.BaseTest;
import client.RecipeClient;
import model.Recipe;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class RecipeCrudTest extends BaseTest {

    private final RecipeClient recipeClient = new RecipeClient();

    @Test
    void shouldGetAllRecipes() {
        recipeClient.getAllRecipes()
                .then()
                .statusCode(200)
                .body("recipes", notNullValue())
                .body("recipes.size()", greaterThan(0))
                .body("total", greaterThan(0));
    }

    @Test
    void shouldGetRecipeById() {
        recipeClient.getRecipeById(1)
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", containsString("Pizza"))
                .body("ingredients", notNullValue());
    }

    @Test
    void shouldSearchRecipes() {
        String query = "Margherita";

        recipeClient.searchRecipes(query)
                .then()
                .statusCode(200)
                .body("recipes", notNullValue())
                .body("recipes.size()", greaterThan(0))
                .body("recipes.name", hasItem(containsString(query)));
    }

    @Test
    void shouldGetRecipesWithLimitSkipAndSelect() {
        recipeClient.getRecipesWithLimitSkipAndSelect(10, 10, "name,image")
                .then()
                .statusCode(200)
                .body("limit", equalTo(10))
                .body("skip", equalTo(10))
                .body("recipes", hasSize(10))
                .body("recipes[0].name", notNullValue())
                .body("recipes[0].image", notNullValue())
                .body("recipes[0].ingredients", nullValue())
                .body("recipes[0].instructions", nullValue());
    }

    @Test
    void shouldSortRecipesByNameAscending() {
        recipeClient.getRecipesSorted("name", "asc")
                .then()
                .statusCode(200)
                .body("recipes[0].name", equalTo("Aloo Keema"));
    }

    @Test
    void shouldGetAllTags() {
        recipeClient.getAllTags()
                .then()
                .statusCode(200)
                .body("$", notNullValue())
                .body("$", hasItem("Pakistani"))
                .body("$", hasItem("Asian"));

    }

    @Test
    void shouldGetRecipesByTag() {
        String tag = "Pakistani";

        recipeClient.getRecipesByTag(tag)
                .then()
                .statusCode(200)
                .body("recipes.size()", greaterThan(0))
                .body("recipes.tags.flatten()", hasItem(tag));
    }

    @Test
    void shouldGetRecipesByMealType() {
        String mealType = "snack";

        recipeClient.getRecipesByMealType(mealType)
                .then()
                .statusCode(200)
                .body("recipes.size()", greaterThan(0))
                .body("recipes.mealType.flatten()", hasItem(containsStringIgnoringCase(mealType)));
    }

    @Test
    void shouldAddNewRecipe() {
        String recipeName = "recipe_" + System.currentTimeMillis();

        Recipe recipeRequest = Recipe.builder()
                .name(recipeName)
                .ingredients(List.of("Dough", "Tomato", "Cheese"))
                .instructions(List.of("Mix", "Bake"))
                .prepTimeMinutes(15)
                .cookTimeMinutes(20)
                .servings(2)
                .difficulty("Easy")
                .cuisine("Italian")
                .caloriesPerServing(250)
                .tags(List.of("Test"))
                .image("https://example.com/pizza.jpg")
                .rating(4.5)
                .reviewCount(0)
                .mealType(List.of("Dinner"))
                .build();

        recipeClient.addRecipe(recipeRequest)
                .then()
                .statusCode(200)
                .body("name", equalTo(recipeName))
                .body("id", notNullValue());
    }

    @Test
    void shouldUpdateRecipeWithPut() {
        Map<String, String> updatePayload = Map.of("name", "Tasty Pizza");

        recipeClient.updateRecipeWithPut(1, updatePayload)
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Tasty Pizza"));
    }

    @Test
    void shouldUpdateRecipeWithPatch() {
        Map<String, String> updatePayload = Map.of("name", "Tasty Pizza");

        recipeClient.updateRecipeWithPatch(1, updatePayload)
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Tasty Pizza"));
    }

    @Test
    void shouldDeleteRecipe() {
        recipeClient.deleteRecipe(1)
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("isDeleted", equalTo(true))
                .body("deletedOn", notNullValue());
    }
}
