package client;

import io.restassured.response.Response;
import model.Recipe;

import static io.restassured.RestAssured.given;

public class RecipeClient {

    public Response getAllRecipes() {
        return given()
                .when()
                .get("/recipes");
    }

    public Response getRecipeById(long id) {
        return given()
                .pathParam("id", id)
                .when()
                .get("/recipes/{id}");
    }

    public Response searchRecipes(String query) {
        return given()
                .queryParam("q", query)
                .when()
                .get("/recipes/search");
    }

    public Response getRecipesWithLimitSkipAndSelect(int limit, int skip, String select) {
        return given()
                .queryParam("limit", limit)
                .queryParam("skip", skip)
                .queryParam("select", select)
                .when()
                .get("/recipes");
    }

    public Response getRecipesSorted(String sortBy, String order) {
        return given()
                .queryParam("sortBy", sortBy)
                .queryParam("order", order)
                .when()
                .get("/recipes");
    }

    public Response getAllTags() {
        return given()
                .when()
                .get("/recipes/tags");
    }

    public Response getRecipesByTag(String tag) {
        return given()
                .pathParam("tag", tag)
                .when()
                .get("/recipes/tag/{tag}");
    }

    public Response getRecipesByMealType(String mealType) {
        return given()
                .pathParam("mealType", mealType)
                .when()
                .get("/recipes/meal-type/{mealType}");
    }

    public Response addRecipe(Recipe recipe) {
        return given()
                .body(recipe)
                .when()
                .post("/recipes/add");
    }

    public Response updateRecipeWithPut(long id, Object payload) {
        return given()
                .pathParam("id", id)
                .body(payload)
                .when()
                .put("/recipes/{id}");
    }

    public Response updateRecipeWithPatch(long id, Object payload) {
        return given()
                .pathParam("id", id)
                .body(payload)
                .when()
                .patch("/recipes/{id}");
    }

    public Response deleteRecipe(long id) {
        return given()
                .pathParam("id", id)
                .when()
                .delete("/recipes/{id}");
    }
}
